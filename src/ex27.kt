import kotlinx.coroutines.*

fun main() = runBlocking {
    runWithoutDispatcher()
    runDefaultDispatcher()
    runIODispatcher()
}

suspend fun runWithoutDispatcher() {
    coroutineScope {
        launch() {
            println("without ${Thread.currentThread().name}")
        }
    }
}

suspend fun runDefaultDispatcher() {
    coroutineScope {
        launch(Dispatchers.Default) {
            println("default ${Thread.currentThread().name}")
        }
    }
}

suspend fun runIODispatcher() {
    coroutineScope {
        withContext(Dispatchers.IO) {
            println("IO ${Thread.currentThread().name}")
        }
    }
}

