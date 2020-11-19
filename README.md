# Use case
This script allows for converting
from [Google Fit](https://fit.google.com) format used in [Google Takeout](https://takeout.google.com)
to [OpenScale](https://github.com/oliexdev/openScale).

# Usage
1. Download zip file from Google Takeout.
2. Unzip it
3. Copy `Takeout/Fit/Daily Aggregations/Daily Summaries.csv` to directory with this script
4. Install [kscript](https://github.com/holgerbrandl/kscript)
5. Run
```bash
kscript GoogleFitToOpenScaleCSV.kt
```
6. Output should be in openscale.csv

# Possible improvements
1. Script takes average weight from each day, it could parse each measurement from CSVs provided by Google.
2. Time of a measurement is hardcoded to 8am, due to simplicity of not having to parse all the files.

# openScale sync
You can also use [openScale Sync](https://play.google.com/store/apps/details?id=com.health.openscale.sync) Android app.
It is paid app, made by author of openScale.
I haven't tried it myself, it seems worth mentioning anyway.
