# ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆
# ☆ Author: ☆ MelodyHSong ☆
# ☆ Language: Python
# ☆ File Name: rename_flag_files.py
# ☆ Date: 2025-12-14
# ☆
# ☆ Description: Renames image files for Flags based on municipality and region, 
# ☆ converting to snake_case using a predefined regional map.
# ☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆

import os
from pathlib import Path
import re
import unicodedata

# --- CONFIGURATION ---


TARGET_FOLDER = Path(r"") #// Path to the folder containing flag images

# Include all image extensions that you previously used
IMAGE_EXTENSIONS = {'.png', '.svg' , '.jpg', '.jpeg', '.gif', '.bmp', '.tiff', '.webp'}

TOWNS_DATA = [
    # --- NORTE REGION (Metro & North Coast) ---
    {"name": "San Juan", "region": "Norte"},
    {"name": "Bayamón", "region": "Norte"},
    {"name": "Carolina", "region": "Norte"},
    {"name": "Guaynabo", "region": "Norte"},
    {"name": "Cataño", "region": "Norte"},
    {"name": "Toa Baja", "region": "Norte"},
    {"name": "Toa Alta", "region": "Norte"},
    {"name": "Dorado", "region": "Norte"},
    {"name": "Vega Alta", "region": "Norte"},
    {"name": "Vega Baja", "region": "Norte"},
    {"name": "Manatí", "region": "Norte"},
    {"name": "Barceloneta", "region": "Norte"},
    {"name": "Florida", "region": "Norte"},
    {"name": "Arecibo", "region": "Norte"},
    {"name": "Hatillo", "region": "Norte"},
    {"name": "Camuy", "region": "Norte"},
    {"name": "Quebradillas", "region": "Norte"},
    {"name": "Trujillo Alto", "region": "Norte"},

    # --- ESTE REGION ---
    {"name": "Loíza", "region": "Este"},
    {"name": "Canóvanas", "region": "Este"},
    {"name": "Río Grande", "region": "Este"},
    {"name": "Luquillo", "region": "Este"},
    {"name": "Fajardo", "region": "Este"},
    {"name": "Ceiba", "region": "Este"},
    {"name": "Naguabo", "region": "Este"},
    {"name": "Humacao", "region": "Este"},
    {"name": "Yabucoa", "region": "Este"},
    {"name": "Maunabo", "region": "Este"},
    {"name": "Vieques", "region": "Este"},
    {"name": "Culebra", "region": "Este"},
    {"name": "Juncos", "region": "Este"},

    # --- SUR REGION ---
    {"name": "Arroyo", "region": "Sur"},
    {"name": "Patillas", "region": "Sur"},
    {"name": "Guayama", "region": "Sur"},
    {"name": "Salinas", "region": "Sur"},
    {"name": "Santa Isabel", "region": "Sur"},
    {"name": "Coamo", "region": "Sur"},
    {"name": "Juana Díaz", "region": "Sur"},
    {"name": "Ponce", "region": "Sur"},
    {"name": "Villalba", "region": "Sur"},
    {"name": "Peñuelas", "region": "Sur"},
    {"name": "Guayanilla", "region": "Sur"},
    {"name": "Yauco", "region": "Sur"},
    {"name": "Guánica", "region": "Sur"},
    {"name": "Lajas", "region": "Sur"},
    {"name": "Sabana Grande", "region": "Sur"},

    # --- OESTE REGION ---
    {"name": "Cabo Rojo", "region": "Oeste"},
    {"name": "Mayagüez", "region": "Oeste"},
    {"name": "Hormigueros", "region": "Oeste"},
    {"name": "Añasco", "region": "Oeste"},
    {"name": "Rincón", "region": "Oeste"},
    {"name": "Aguada", "region": "Oeste"},
    {"name": "Aguadilla", "region": "Oeste"},
    {"name": "Moca", "region": "Oeste"},
    {"name": "Isabela", "region": "Oeste"},
    {"name": "San Sebastián", "region": "Oeste"},
    {"name": "San Germán", "region": "Oeste"},

    # --- CENTRAL REGION ---
    {"name": "Cidra", "region": "Central"},
    {"name": "Comerío", "region": "Central"},
    {"name": "Naranjito", "region": "Central"},
    {"name": "Corozal", "region": "Central"},
    {"name": "Orocovis", "region": "Central"},
    {"name": "Barranquitas", "region": "Central"},
    {"name": "Aibonito", "region": "Central"},
    {"name": "Ciales", "region": "Central"},
    {"name": "Morovis", "region": "Central"},
    {"name": "Utuado", "region": "Central"},
    {"name": "Lares", "region": "Central"},
    {"name": "Adjuntas", "region": "Central"},
    {"name": "Jayuya", "region": "Central"},
    {"name": "Maricao", "region": "Central"},
    {"name": "Las Marías", "region": "Central"},
    {"name": "Gurabo", "region": "Central"},
    {"name": "San Lorenzo", "region": "Central"},
    {"name": "Aguas Buenas", "region": "Central"},
    {"name": "Cayey", "region": "Central"},
    {"name": "Caguas", "region": "Central"},
    {"name": "Las Piedras", "region": "Central"},
]

def create_rename_map():
    
# ☆ Description: Creates a dictionary mapping normalized municipality name to the desired new snake_case prefix.
    rename_map = {}
    for town in TOWNS_DATA:
        muni_name = town["name"]
        region = town["region"]
        
        # 1. Normalize name (remove accents, lowercase)
        name_normalized = unicodedata.normalize('NFD', muni_name)
        name_unaccented = ''.join(char for char in name_normalized if unicodedata.category(char) != 'Mn')
        
        # 2. Convert to snake_case (replace spaces and hyphens with underscores, lowercase)
        name_snake_case = name_unaccented.lower().replace(' ', '_').replace('-', '_')
        
        # 3. Create the final prefix: {region}_{name}_flag <--- CHANGED TO '_flag'
        region_lower = region.lower()
        new_prefix = f"{region_lower}_{name_snake_case}_flag"
        
        rename_map[name_unaccented.lower()] = new_prefix
        
    return rename_map

def find_municipality_match(file_name, rename_map):
# ☆ Description: Finds the correct new name prefix for a given file name.
    # 1. Normalize the file name: remove accents, lowercase, remove extensions/junk
    normalized_file = unicodedata.normalize('NFD', file_name)
    normalized_file = ''.join(char for char in normalized_file if unicodedata.category(char) != 'Mn')
    normalized_file = normalized_file.lower()

    # Clean up common non-name parts (like suffixes, numbers, extensions)
    stem_clean = re.sub(r'[\W_]+', '', Path(normalized_file).stem)
    
    # Iterate through the rename map keys (standardized names)
    for muni_key_raw, new_prefix in rename_map.items():
        muni_key_clean = muni_key_raw.replace('_', '').replace(' ', '')
        
        # Check if the clean file stem contains the standardized municipality name
        if muni_key_clean in stem_clean:
            return muni_key_raw, new_prefix
            
    return None, None

def get_run_mode_from_user():

# ☆ Description: Prompts the user to select between a Dry Run (safe) or a Normal Run (rename) with a confirmation for renaming.
    print("\n--- RUN MODE SELECTION (FLAGS) ---")
    print("Do you want to run the script in DRY RUN mode (no files renamed)?")
    
    while True:
        choice = input("Enter 'Y' for Dry Run (Recommended) or 'N' to execute RENAME: ").strip().upper()
        if choice == 'Y':
            print("\n** Running in DRY RUN mode (No files will be renamed). **")
            return True
        elif choice == 'N':
            print("\n!!! DANGER ZONE !!!")
            print("You have selected NORMAL RUN. This script WILL RENAME AND OVERWRITE files.")
            final_confirm = input("Are you absolutely sure you want to proceed with RENAMING? Type 'CONFIRM' to proceed: ").strip()
            
            if final_confirm == 'CONFIRM':
                print("\n** NORMAL MODE ACTIVATED. Files WILL be renamed based on the Kotlin source map. **")
                return False
            else:
                print("Renaming cancelled. Returning to Dry Run selection.")
                continue 
        else:
            print("Invalid input. Please enter 'Y' or 'N'.")

def rename_shield_files(dry_run):

# ☆ Description: Main function to process and rename the flag files.
    if not TARGET_FOLDER.exists():
        print(f"Folder not found: {TARGET_FOLDER}")
        return

    rename_map = create_rename_map()
    files = [f for f in TARGET_FOLDER.iterdir() if f.is_file() and f.suffix.lower() in IMAGE_EXTENSIONS]
    
    renamed_count = 0
    skipped_count = 0
    
    print(f"\nScanning {len(files)} files in '{TARGET_FOLDER.name}' for renaming...\n")

    for current_file in files:
        muni_key, new_prefix = find_municipality_match(current_file.name, rename_map)
        
        if muni_key:
            # Construct the final new name
            new_name = f"{new_prefix}{current_file.suffix.lower()}"
            new_path = current_file.with_name(new_name)
            
            if current_file.name == new_name:
                print(f"-> SKIPPING: {current_file.name} is already correctly named.") 
                skipped_count += 1
                continue

            if new_path.exists():
                print(f"! WARNING: Target file already exists and will be OVERWRITTEN: {new_path.name}") 
                
            if not dry_run:
                try:
                    # Rename the file (this handles overwriting if the target exists)
                    current_file.rename(new_path)
                    print(f"* RENAMED: {current_file.name} -> {new_name}") 
                    renamed_count += 1
                except Exception as e:
                    print(f"      Error renaming {current_file.name}: {e}")
            else:
                print(f"[DRY RUN]: {current_file.name} -> {new_name}") 
                renamed_count += 1
        else:
            print(f"? SKIPPING: Could not find municipality match for {current_file.name}") 
            skipped_count += 1

    # Final Summary
    print("\n" + "#" * 50)
    print(f"☆☆ RENAME COMPLETE ☆☆")
    print(f"Total files renamed/processed: {renamed_count}")
    print(f"Total files skipped (no match): {skipped_count}")

    if dry_run:
        print(f"** Running in DRY RUN mode. No files were actually changed. **")
    else:
        print(f"** All changes executed successfully. **")
    print("#" * 50)


if __name__ == "__main__":
    should_dry_run = get_run_mode_from_user()
    rename_shield_files(should_dry_run)
    input("Press Enter to exit.")