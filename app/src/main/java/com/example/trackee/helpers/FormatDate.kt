import java.text.SimpleDateFormat
import java.util.*

fun formatDate(isoDate: String): String {

    val patterns = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", // With milliseconds
        "yyyy-MM-dd'T'HH:mm:ssXXX"      // Without milliseconds
    )

    // Output format
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getDefault() // Set output timezone to local time

    for (pattern in patterns) {
        try {
            val inputFormat = SimpleDateFormat(pattern, Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Parse as UTC
            val date = inputFormat.parse(isoDate)
            if (date != null) {
                return outputFormat.format(date) // Format to dd/MM/yyyy if parsing succeeds
            }
        } catch (e: Exception) {
            // Ignore exception and try the next pattern
        }
    }
    return "Invalid Date"
}
