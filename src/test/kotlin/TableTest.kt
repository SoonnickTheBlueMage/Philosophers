import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class TableTest {
    @Test
    fun deadlockTest() = runBlocking{
        val tale = Table(5)
        tale.run(5, true)
        assert(!tale.checkEveryoneAte())
    }

    @Test
    fun waiterTest() = runBlocking{
        val tale = Table(5)
        tale.run(5, false)
        assert(tale.checkEveryoneAte())
    }
}