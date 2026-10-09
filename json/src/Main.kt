import java.io.File

fun main() {
    Parser("googleplaystore.csv").apply {
        readFile()
        saveToJson("output.json")
    }
    println("Ready: output.json")
}