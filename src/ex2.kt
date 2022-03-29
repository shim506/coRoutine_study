import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        // 수신 객체가 코루틴스코프이다.
        println(this)

    }

}