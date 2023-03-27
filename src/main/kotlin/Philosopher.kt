import kotlinx.coroutines.delay

enum class Status {
    Empty, Fork
}

enum class Hand {
    Left, Right
}

class Philosopher(private val name: String) {
    private var leftHand: Status = Status.Empty
    private var rightHand: Status = Status.Empty
    private var done: Boolean = false

    fun checkIfDone(): Boolean = done
    private fun checkIfReady(): Boolean = leftHand == Status.Fork && rightHand == Status.Fork

    suspend fun eat() {
        done = checkIfReady()
        println("$name is eating")
        delay(1000)
    }

    suspend fun think() {
        println("$name is thinking")
        delay(1000)
    }

    suspend fun takeFork(hand: Hand)
    {
        if (hand == Hand.Left && leftHand == Status.Empty) {
            leftHand = Status.Fork
        }
        else if (hand == Hand.Right && rightHand == Status.Empty) {
            rightHand = Status.Fork
        }
        delay(1000)
    }

    suspend fun putFork(hand: Hand)
    {
        if (hand == Hand.Left && leftHand == Status.Fork) {
            leftHand = Status.Empty
        }
        else if (hand == Hand.Right && rightHand == Status.Fork) {
            rightHand = Status.Empty
        }
        delay(1000)
    }
}
