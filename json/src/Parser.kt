import com.google.gson.GsonBuilder
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class App(
    val app: String,
    val rating: Double?,
    val reviews: Long?,
    val size: String?,
    val installs: Long?,
    val type: String?,
    val price: Boolean,
    val contentRating: String?,
    val genres: List<String>,
    val lastUpdated: String?,
    val currentVer: String?,
    val androidVer: Int?
)

class Parser(private val csvPath: String) {

    private val data = linkedMapOf<String, MutableList<App>>()

    fun readFile() {
        val lines = File(csvPath).readLines().filter { it.isNotBlank() }
        if (lines.isEmpty()) return

        val header = splitCsv(lines.first())
        for (line in lines.drop(1)) {
            val parts = splitCsv(line)
            if (parts.size != header.size) continue

            val row = header.zip(parts).toMap()
            val category = row["Category"]?.trim().orEmpty()
            val name = row["App"]?.trim().orEmpty()
            if (category.isEmpty() || name.isEmpty()) continue

            data.getOrPut(category) { mutableListOf() }.add(
                App(
                    app = name,
                    rating = row["Rating"]?.toDoubleOrNull()?.takeIf { !it.isNaN() },
                    reviews = row["Reviews"]?.replace(",", "")?.toLongOrNull(),
                    size = row["Size"]?.trim(),
                    installs = row["Installs"]?.replace(",", "")?.replace("+", "")?.trim()?.toLongOrNull(),
                    type = row["Type"]?.trim(),
                    price = (row["Price"]?.removePrefix("$")?.toDoubleOrNull() ?: 0.0) > 0.0,
                    contentRating = row["Content Rating"]?.trim(),
                    genres = row["Genres"]?.split(";")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList(),
                    lastUpdated = row["Last Updated"]?.let(::toIso),
                    currentVer = row["Current Ver"]?.trim(),
                    androidVer = apiLevel(row["Android Ver"])
                )
            )
        }
    }

    fun saveToJson(path: String) {
        val json = GsonBuilder().setPrettyPrinting().serializeNulls().create()
        File(path).writeText(json.toJson(data))
    }

    private fun splitCsv(line: String): List<String> {
        val out = mutableListOf<String>()
        val sb = StringBuilder()
        var quoted = false
        var i = 0
        while (i < line.length) {
            val c = line[i]
            if (c == '"') {
                if (quoted && line.getOrNull(i + 1) == '"') { sb.append('"'); i++ } else quoted = !quoted
            } else if (c == ',' && !quoted) { out.add(sb.toString()); sb.clear() } else sb.append(c)
            i++
        }
        out.add(sb.toString())
        return out
    }

    private fun toIso(raw: String?): String? = try {
        LocalDate.parse(raw?.trim(), DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)).toString()
    } catch (e: Exception) { null }

    private fun apiLevel(raw: String?): Int? {
        val v = raw?.trim()?.takeIf { !it.startsWith("Varies", true) } ?: return null
        return apiMap[v.substringBefore(" and up").trim()]
    }

    companion object {
        private val apiMap = mapOf(
            "1.0" to 1, "1.5" to 3, "1.6" to 4, "2.0" to 5, "2.1" to 7, "2.2" to 8, "2.3" to 9,
            "3.0" to 11, "3.1" to 12, "3.2" to 13, "4.0" to 14, "4.1" to 16, "4.2" to 17,
            "4.3" to 18, "4.4" to 19, "5.0" to 21, "5.1" to 22, "6.0" to 23, "7.0" to 24,
            "7.1" to 25, "8.0" to 26, "8.1" to 27, "9" to 28, "10" to 29, "11" to 30,
            "12" to 31, "13" to 33, "14" to 34, "15" to 35
        )
    }
}