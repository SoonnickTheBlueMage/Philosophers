import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val table = Table(5)
    table.run(5, false)
    println("${table.checkEveryoneAte()}")
}