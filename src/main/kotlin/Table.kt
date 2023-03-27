import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class Table(private val size: Int) {
    private val philosophers: MutableList<Philosopher> = mutableListOf()
    private val forks: MutableList<AtomicBoolean> = mutableListOf()
    // fork[i] - is on left for philosophers[i]
    init {
        for (i in 1..size) {
            philosophers.add(Philosopher("philosopher $i"))
            forks.add(AtomicBoolean(true))
        }
    }

    fun checkEveryoneAte(): Boolean {
        for (philosopher in philosophers) {
            if (!philosopher.checkIfDone()) return false
        }
        return true
    }

    private fun getLeftFork(index: Int): Boolean = forks[index].get()
    private fun getRightFork(index: Int): Boolean = forks[(index + 1) % size].get()
    private suspend fun takeLeftFork(index: Int) {
        forks[index].set(false)
        philosophers[index].takeFork(Hand.Left)
    }
    private suspend fun takeRightFork(index: Int) {
        forks[(index + 1) % size].set(false)
        philosophers[index].takeFork(Hand.Right)
    }
    private suspend fun putLeftFork(index: Int) {
        forks[index].set(true)
        philosophers[index].putFork(Hand.Left)
    }
    private suspend fun putRightFork(index: Int) {
        forks[(index + 1) % size].set(true)
        philosophers[index].putFork(Hand.Right)
    }

    private suspend fun dinnerIteration(i: Int, naive: Boolean) {
        var waitingTime = 0

        if (naive)  {
            while (!getLeftFork(i)) {
                philosophers[i].think()
                waitingTime++
                if (waitingTime > 100) return
            }
            takeLeftFork(i)
            while (!getRightFork(i)) {
                philosophers[i].think()
                waitingTime++
                if (waitingTime > 100) return
            }
            takeRightFork(i)
            philosophers[i].eat()
            putLeftFork(i)
            putRightFork(i)
        }
    }

    suspend fun run(naive: Boolean = true) = coroutineScope {
        for (i in philosophers.indices)
            launch { dinnerIteration(i, naive) }
    }
}
