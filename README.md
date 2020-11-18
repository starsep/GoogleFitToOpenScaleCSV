# Use case
This script allows for converting
from [Google Fit](https://fit.google.com) format used in [Google Takeout](https://takeout.google.com)
to [OpenScale](https://github.com/oliexdev/openScale).

# Usage
1. Download zip file from Google Takeout.
2. Unzip it
3. Copy `Takeout/Fit/Daily Aggregations/Daily Summaries.csv` to directory with this script
4. Run
```bash
kscript GoogleFitToOpenScaleCSV.kt
```
5. Output should be in openscale.csv

# Possible improvements
1. Script takes average weight from each day, it could parse each measurement from CSVs provided by Google.
2. Time of a measurement is hardcoded to 8am, due to simplicity of not having to parse all the files.
