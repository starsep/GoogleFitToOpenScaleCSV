#!/bin/sh
set -e
kscript ../GoogleFitToOpenScaleCSV.kt
diff openscale.csv openscale-expected.csv || (echo "Output different than expected"; exit 1)
