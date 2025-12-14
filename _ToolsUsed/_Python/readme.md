# 🐍 Python Utility Scripts for Towns.IO Asset Management

This directory contains a suite of Python scripts (`.py`) developed to automate the cleanup, standardization, and renaming of image assets (specifically **Municipal Shields** and **Flags**) for the Towns.IO project.

The primary goal of these scripts is to transform raw downloaded image files into a clean, standardized, and easily parsable format for use in the final application, based on a definitive list of Puerto Rican municipalities and their regional groupings.

---

## 🛠️ Scripts Overview

| File Name | Purpose | Target Asset Type |
| :--- | :--- | :--- |
| `clean_names.py` | **Duplicate Cleanup & Missing Report** | Shields |
| `clean_flag_duplicates.py` | **Duplicate Cleanup & Missing Report** | Flags |
| `rename_files.py` | **File Renaming & Standardization** | Shields |
| `rename_flag_files.py` | **File Renaming & Standardization** | Flags |

---

## 💡 Core Logic & Standardization

All renaming and cleanup logic is based on the verified list of 78 Puerto Rican municipalities and their regional groupings defined within the scripts (derived from `Kotlin TownDataSource.kt`).

### 1. Standardization Logic (`clean_*.py` files)

The cleanup scripts handle duplicate images found for a single municipality based on a defined priority system to ensure only the highest-quality or preferred format is kept:

1.  **Grouping:** Files are grouped by the municipality name found in the filename (using robust accent/punctuation-agnostic matching).
2.  **Priority Ranking (Highest to Lowest):**
    * **PNG** (`.png`)
    * **SVG** (`.svg`)
    * **Other** (e.g., `.jpg`, `.webp`)
3.  **Tie-breaker:** Within the same priority group, the **largest file size** is kept.
4.  **Output:** Reports which files were kept, which were marked for deletion, and lists any municipalities for which an image was not found.

### 2. Renaming Logic (`rename_*.py` files)

The renaming scripts apply a consistent `snake_case` naming convention:

* **Format:** `{region}_{municipality_name}_{asset_type}{.extension}`
* **Example (Shield):** `sur_juana_diaz_shield.png`
* **Example (Flag):** `norte_san_juan_flag.svg`
* **Steps:**
    1.  Municipality name is **normalized** (accents/diacritics removed).
    2.  Name is converted to **snake\_case** (spaces and hyphens replaced with underscores).
    3.  The relevant `region` prefix (e.g., `norte`, `sur`) and `asset_type` suffix (e.g., `_shield`, `_flag`) are added.

---

## ⚠️ Operation & Safety

All scripts are designed to be run from the command line and include an essential safety feature:

* **Dry Run (Recommended):** By default, the scripts prompt the user to run in **DRY RUN** mode (`Y`). In this mode, no files are physically deleted or renamed; the script only prints what *would* happen, allowing for verification.
* **Normal Run (Deletion/Rename):** To execute permanent changes (`N`), the user must explicitly type `CONFIRM` after a warning prompt. **Use this mode with caution and on backed-up data.**

### How to Run

1.  **Prerequisites:** Ensure you have Python installed (version 3.6+ is recommended).
2.  **Install Libraries:** These scripts rely only on standard libraries: `os`, `pathlib`, `re`, and `unicodedata`. No external packages are required.
3.  **Update Path:** Before running, you must update the `TARGET_FOLDER` variable at the top of the script to point to the correct directory containing the files you wish to process.
4.  **Execute:** Run the desired script from your terminal:

    ```bash
    python clean_names.py
    ```
