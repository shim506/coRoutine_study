import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch {
            println(Thread.currentThread().name)
            println("world")
        }
        println(Thread.currentThread().name)
        delay(500L)
        println("Hello")
    }
}