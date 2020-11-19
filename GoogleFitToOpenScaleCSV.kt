@file:DependsOn("com.github.doyaaaaaken:kotlin-csv-jvm:0.13.0")
@file:DependsOn("com.github.ajalt.mordant:mordant-jvm:2.0.0-alpha1")

import com.github.ajalt.mordant.terminal.Terminal
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.math.BigDecimal
import java.math.RoundingMode

val terminal = Terminal()

fun main() {
    csvReader().open(GOOGLE_FIT_TAKEOUT_SUMMARY_FILE_NAME) {
        val googleFitSummaries = parseGoogleFitData(readAllWithHeaderAsSequence())
        writeOpenScaleData(googleFitSummaries)
    }
}

data class GoogleFitDaySummary(
    val date: String,
    val weight: BigDecimal,
)

fun parseGoogleFitData(googleFitData: Sequence<Map<String, String>>): List<GoogleFitDaySummary> =
    googleFitData.mapNotNull { row ->
        val date = row.getValue(COLUMN_DATE)
        val averageWeightText = row.getValue(COLUMN_AVERAGE_WEIGHT)
        val maxWeightText = row.getValue(COLUMN_MAX_WEIGHT)
        val minWeightText = row.getValue(COLUMN_MIN_WEIGHT)
        val weightTexts = listOf(averageWeightText, maxWeightText, minWeightText)
        if (weightTexts.all { it.isNotBlank()} ) {
            val averageWeight = weightWithOneDecimalPlace(averageWeightText)
            val maxWeight = weightWithOneDecimalPlace(maxWeightText)
            val minWeight = weightWithOneDecimalPlace(minWeightText)
            if (maxWeight - minWeight > BigDecimal(WEIGHT_DIFFERENCE_WARNING_THRESHOLD_IN_KG)) {
                terminal.warning("[WARNING] Different weights: date=$date average=$averageWeight max=$maxWeight min=$minWeight")
            }
            GoogleFitDaySummary(
                date = date + MEASUREMENT_TIME_SUFFIX,
                weight = averageWeight,
            )
        } else null
    }.toList()

fun writeOpenScaleData(googleFitDaySummaries: List<GoogleFitDaySummary>) {
    csvWriter().open(OUTPUT_FILE_NAME) {
        writeRow(OPEN_SCALE_HEADERS)
        googleFitDaySummaries.forEach {
            writeRow(listOf(it.date, it.weight))
        }
    }
    terminal.success("[SUCCESS] ${googleFitDaySummaries.size} rows have been saved to $OUTPUT_FILE_NAME")
}

fun weightWithOneDecimalPlace(weight: String) = BigDecimal(weight.toDouble()).setScale(1, RoundingMode.HALF_EVEN)

val OPEN_SCALE_HEADERS = listOf("dateTime", "weight")
const val GOOGLE_FIT_TAKEOUT_SUMMARY_FILE_NAME = "Daily Summaries.csv"
const val OUTPUT_FILE_NAME = "openscale.csv"
const val COLUMN_DATE = "Date"
const val COLUMN_AVERAGE_WEIGHT = "Average weight (kg)"
const val COLUMN_MAX_WEIGHT = "Max weight (kg)"
const val COLUMN_MIN_WEIGHT = "Min weight (kg)"
const val WEIGHT_DIFFERENCE_WARNING_THRESHOLD_IN_KG = 2.0
const val MEASUREMENT_TIME_SUFFIX = " 08:00"
