# ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
# ☆ Author: ☆ MelodyHSong ☆
# ☆ Language: Python
# ☆ File Name: clean_flag_duplicates.py
# ☆ Date: 2025-12-14
# ☆
# ☆ Description: Cleans up duplicate image files for Flags based on Puerto Rican municipality names,
# ☆ prioritizes PNGs, then SVGs, and keeps the largest file size. Also lists missing flags.
# ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆

import os
from pathlib import Path
from collections import defaultdict
import unicodedata 
import re

# ☆ Description: List of all 78 municipalities of Puerto Rico (Verified Complete)
MUNICIPALITIES = [
    "Adjuntas", "Aguada", "Aguadilla", "Aguas Buenas", "Aibonito", "Añasco", "Arecibo", 
    "Arroyo", "Barceloneta", "Barranquitas", "Bayamón", "Cabo Rojo", "Caguas", "Camuy", 
    "Canóvanas", "Carolina", "Cataño", "Cayey", "Ceiba", "Ciales", "Cidra", "Coamo", 
    "Comerío", "Corozal", "Culebra", "Dorado", "Fajardo", "Florida", "Guánica", 
    "Guayama", "Guayanilla", "Guaynabo", "Gurabo", "Hatillo", "Hormigueros", "Humacao", 
    "Isabela", "Jayuya", "Juana Díaz", "Juncos", "Lajas", "Lares", "Las Marías", 
    "Las Piedras", "Loíza", "Luquillo", "Manatí", "Maricao", "Maunabo", "Mayagüez", 
    "Moca", "Morovis", "Naguabo", "Naranjito", "Orocovis", "Patillas", "Peñuelas", 
    "Ponce", "Quebradillas", "Rincón", "Río Grande", "Sabana Grande", "Salinas", 
    "San Germán", "San Juan", "San Lorenzo", "San Sebastián", "Santa Isabel", 
    "Toa Alta", "Toa Baja", "Trujillo Alto", "Utuado", "Vega Alta", "Vega Baja", 
    "Vieques", "Villalba", "Yabucoa", "Yauco"
]

# --- CONFIGURATION ---

TARGET_FOLDER = Path(r"") #// Path to the folder containing flag images

IMAGE_EXTENSIONS = {'.jpg', '.jpeg', '.png', '.gif', '.bmp', '.tiff', '.webp', '.svg'}

def normalize_name(text):
# ☆ Description: Helper function to remove accents and punctuation for robust matching.
    text = unicodedata.normalize('NFD', text)
    text = ''.join(char for char in text if unicodedata.category(char) != 'Mn')
    text = text.lower().replace('-', ' ').replace('_', ' ').replace('.', '').replace('(', ' ').replace(')', ' ')
    return ' '.join(text.split())

def find_municipality_in_name(file_name):
# ☆ Description: Checks if any municipality name is in the filename using robust normalization.
    normalized_file_name = normalize_name(file_name)
    
    for muni in MUNICIPALITIES:
        normalized_muni = normalize_name(muni)
        
        if normalized_muni in normalized_file_name:
            return muni 
            
    return None

def get_file_priority_key(file_path):
# ☆ Description: Custom key for sorting based on file type priority and size (largest first).
    ext = file_path.suffix.lower()
    size = file_path.stat().st_size
    
    # Priority: PNG (3, highest) > SVG (2) > Other (1, lowest)
    if ext == '.png':
        priority = 3
    elif ext == '.svg':
        priority = 2
    else:
        priority = 1
        
    # Sort key: (Priority, Size). Negative size ensures largest size comes first in descending order.
    return (priority, -size) 

def get_run_mode_from_user():
# ☆ Description: Prompts the user to select between a Dry Run (safe) or a Normal Run (delete) with a confirmation for deletion.
    print("\n--- RUN MODE SELECTION (FLAGS) ---")
    print("Do you want to run the script in DRY RUN mode (no files deleted)?")
    
    while True:
        choice = input("Enter 'Y' for Dry Run (Recommended) or 'N' to execute DELETION: ").strip().upper()
        if choice == 'Y':
            print("\n** Running in DRY RUN mode (No files will be deleted). **")
            return True
        elif choice == 'N':
            print("\n!!! DANGER ZONE !!!")
            print("You have selected NORMAL RUN. This script WILL PERMANENTLY DELETE files.")
            final_confirm = input("Are you absolutely sure you want to proceed with DELETION? Type 'CONFIRM' to proceed: ").strip()
            
            if final_confirm == 'CONFIRM':
                print("\n** NORMAL MODE ACTIVATED. Files WILL be deleted based on priority. **")
                return False
            else:
                print("Deletion cancelled. Returning to Dry Run selection.")
                continue 
        else:
            print("Invalid input. Please enter 'Y' or 'N'.")

def clean_duplicates_by_municipality(dry_run):
# ☆ Description: Main function to clean duplicate flag files and report missing flags.
    if not TARGET_FOLDER.exists():
        print(f"Folder not found: {TARGET_FOLDER}")
        return

    # 1. Filter and group files by municipality
    municipality_groups = defaultdict(list)
    found_municipalities = set() 
    files = [f for f in TARGET_FOLDER.iterdir() if f.is_file() and f.suffix.lower() in IMAGE_EXTENSIONS]

    print(f"\nScanning {len(files)} flags in '{TARGET_FOLDER.name}'...\n")
    
    for f in files:
        muni_name = find_municipality_in_name(f.name)
        if muni_name:
            municipality_groups[muni_name].append(f)
            found_municipalities.add(muni_name) 

    total_removals = 0
    
    # 2. Process each group for cleanup
    for muni, group in municipality_groups.items():
        if len(group) <= 1:
            continue 

        print(f"--- PR Municipality: {muni} (Found {len(group)} flag files) ---") 
        
        group.sort(key=get_file_priority_key, reverse=True) 

        keeper = group[0]
        removals = group[1:]
        total_removals += len(removals)

        print(f"[KEEP] (Priority: {keeper.suffix.upper()}, Size: {keeper.stat().st_size} bytes): {keeper.name}") 
        
        for removable in removals:
            print(f"   [DEL] (Priority: {removable.suffix.upper()}, Size: {removable.stat().st_size} bytes): {removable.name}") 
            
            if not dry_run:
                try:
                    os.remove(removable)
                except Exception as e:
                    print(f"      Error deleting: {e}")
        
        print("-" * 30)
        
    # 3. Final Summary and Missing List Report
    
    all_municipalities_set = set(MUNICIPALITIES)
    missing_municipalities = sorted(list(all_municipalities_set - found_municipalities))

    print("\n" + "#" * 50)
    
    if missing_municipalities:
        print("☆☆ MUNICIPALITY FLAGS MISSING ☆☆")
        print(f"You are missing flags for {len(missing_municipalities)} municipalities:")
        for muni in missing_municipalities:
            print(f" * {muni}")
        print("-" * 50)
    else:
        print("☆☆ CONGRATULATIONS! ALL 78 MUNICIPALITY FLAGS WERE FOUND! ☆☆")
        
    if dry_run:
        print(f"☆☆ [DRY RUN COMPLETE] ☆☆")
        print(f"A total of {total_removals} files *would have been* deleted.")
    else:
        print(f"☆☆ [CLEANUP COMPLETE] ☆☆")
        print(f"A total of {total_removals} files were deleted.")
    
    print("#" * 50)


if __name__ == "__main__":
    should_dry_run = get_run_mode_from_user()
    clean_duplicates_by_municipality(should_dry_run)
    input("Press Enter to exit.")