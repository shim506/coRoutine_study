# 코루틴 학습

## 1부. 코루틴과 동시성 프로그래밍

- 코루틴 실행을 위해서는 코루틴 스코프가 필요하고
- 그 안에서 빌더를 통해 실행한다
- 그리고 빌더는 구체적인 사항을 코루틴 컨텍스트를 통해 컨트롤 한다
- runBlocking 혹은 Global 스코프 내부에서의 실행이 아니라면 빌더를 갖는 코루틴 스코프의 컨텍스트를 반드시 지정해주어야한다.


1. 코루틴 스코프
    - runBlocking
        - 실행중 다른 코루틴을 , 다른 블록을 중지한다
        - 수신객체는 코루틴
    - CoroutineScope{} 로 만들 경우 컨텍스트를 반드시 추가한다.
    - coroutineScope{} 로 만들 경우 suspend 가 강제되고 상위 호출 함수 또한 강제된다.

2. 코루틴 빌더
    - 코루틴을 실행시키는 함수
    - 코루틴 스코프안에 존재해야한다.
    - 기존 실행되던 쓰레드의 함수가 우선 순위를 갖고 빌더 내 코드는 그 뒤에 큐에 추가되는 형식
    - launch
        - 반환되는 job 객체를 활용하여 코루틴을 제어할 수 있다.
            - .join() 을 통해서 해당 런치 코드동안 다른 런치 혹은 코루틴 들이 기다린다.
            - cancel 을 통해 job 의 행위를 멈출 수 있다.
            - cancel 을 잘 적용하기 위해서는 코드에 isActive 를 포함 하는것을 고려한다.
    - async
        - await 키워드를 통해 결과값을 반환 받을 수 있다.
        - await 를 통해 결과에 대한 콜백 없이 코드 구현이 가능하다

```
//await 는 suspension point 로서 결과가 반환될 때 다시 깨어난다.
fun main() = runBlocking {
    val elapsedTime = measureTimeMillis {
        val value1 = async { getRandom1() }
        val value2 = async { getRandom2() }
        println("${value1.await()} + ${value2.await()} = ${value1.await() + value2.await()}")
    }
    println(elapsedTime)
}
```

- delay vs sleep
    - delay 는 다른 스레드에게 양보를 해주지만 sleep 은 양보 없이 자신이 선점하여 시간 소요

- 코루틴의 계층 구조
    - 상위 코루틴은 하위 코루틴을 끝까지 책임진다.
    - 코루틴은 계층화 되어 상위 하위 코루틴으로 나뉘고 하위 코루틴이 종료될때까지 하위 코루틴은 종료 되지 않는다.
    - 자식 코루틴이 캔슬될 경우 그 형제와 부모 모두 캔슬된다.
        - 한가지 문제라도 있을 경우 전체 결과를 얻을 수 없는 경우 사용하기 좋다.

- 코루틴은 서스팬드 함수를 통해 분리할 수 있다.
    - delay 는 서스팬드 함수 또는 코루틴 스코프 내부에서만 실행된다.
    - 서스팬드 함수 단위로 코루틴을 분리하고 이를 각각 호출한다.
    - delay 가 없는 경우 suspend 를 붙일 필요가 없지 코루틴 사용시 관용적으로 붙인다.
    - delay 와 suspend 함수는 suspension point 로 볼 수 있다.

- withTimeOut(시간) 블러 안에서 코틀린을 돌리면 시간 제한을 줄 수있다.
    - 시간 초과시 예외 발생
    - withTimeOutOrNull 을 통해 예외 대신 null을 리턴 받을 수 있다.(elvis 연산자와 활용가능)
- 코루틴 캔슬
    - https://wooooooak.github.io/kotlin/2020/06/03/Coroutine_Cancellation/

## AAC ViewModel 에서 코루틴 cancel 안해줘도 되는 이유

- ViewModel 은 Map 형태로 각각의 viewModel 의 CloseableCoroutineScope map 형태로 들고있는다.
- CloseableCoroutineScope 를 갖고 이는 closeable 인터페이스를 구현한다.
- viewModel 이 엑티비티와 함께 파괴될때 clear() 가 호출된다.
- clear() 는 내부적으로 closeWithRuntimeException 를 발생시킨다.
- 이때 closeable 의 구현함수 close 가 호출되면서 해당 스코프의 모든 코루틴이 cancel 된다.

## try-with-resource / use

- 객체의 할당 해제가 반드시 필요한경우 try-catch-final 을 사용해서 해제해왔었다.
- 이를 개선한 try-with-resource 를 사용한다.
- kotlin 에서는 use 를 사용한다.

```
// try-catch-finall
BufferedReader br = new BufferedReader(new FileReader(path));
    try {
        return br.readLine();
    } finally {
        if (br != null) br.close();
    }
```

```
// try-with-resource
static readFirstLine(String path) throws IOException{
    BufferedReader br = new BufferedReader(new FileReader(path));
    try {
        return br.readLine;
    }finally {
        br.close();
    }
}
```

```
//use
fun readFirstLine(path: String): String {
    BufferedReader(FileReader(path)).use { br ->
        return br.readLine()
    }
}

```

참고
https://shinjekim.github.io/kotlin/2019/11/01/Kotlin-%EC%9E%90%EB%B0%94%EC%9D%98-try-with-resource-%EA%B5%AC%EB%AC%B8%EA%B3%BC-%EC%BD%94%ED%8B%80%EB%A6%B0%EC%9D%98-use-%ED%95%A8%EC%88%98/