import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val table = Table(5)
    table.run()
    println("${table.checkEveryoneAte()}")
}