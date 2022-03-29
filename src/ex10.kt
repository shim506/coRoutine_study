//import kotlinx.coroutines.*
//
//suspend fun main() {
//    doOneTwoThree()
//    doSomething()
//}
//
//
//fun doOneTwoThree() = CoroutineScope(Dispatchers.Default).launch {
//// CoroutineScope 는 nonSuspense 이고
//// coroutineScope 는 suspense 이다.
//    println(Thread.currentThread().name)
//    delay(1000L)
//    println("hi")
//}
//
//
//suspend fun doSomething() = coroutineScope {
//    println(Thread.currentThread().name)
//    delay(500L)
//    println("hello")
//}