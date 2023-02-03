import java.io.*
/*
12904번 A와 B

문자열 S와 T가 주어진다.
S에 아래 연산을 하여 T를 만들 수 있는지 테스트해보자.
 - 문자열의 뒤에 'A'를 추가한다.
 - 문자열을 뒤집고 뒤에 'B'를 추가한다.

북마크에 박혀있는 'A와 B'라는 이름과, 보자마자 딱 든 생각
내가 너무 좋아할만한 문제제목인데?
그리고 딱 문제를 보고나서,
'이거 항상 반대로하더라'

반대로라는 뜻은, T에서 S를 만든다는 뜻입니다.
다른문제 뒤지다가 '와 이거 어캐하냐'싶은문제들 중 몇개는 그냥 안풀어보고 답지 보는데,
항상 역으로 바꿔버리더라고요.
이 문제도 딱 그런 문제 스타일이어서, 쉽게 풀었습니다.

 -> T의 맨 뒤에 A가 있는가? : 그냥 제거
 -> T의 맨 뒤에 B가 있는가? : 제거 후 문자열 뒤집기 : 단, 그냥 보는 방향만 다르게 할 것.
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val S = br.readLine()
    val T = ArrayDeque<Char>()
    br.readLine().forEach {
        T.add(it)
    }
    var cur = 0
    while (T.size > S.length) {
        if (cur == 0) {
            if (T.last() == 'B') cur = cur.xor(1)
            T.removeLast()
        } else {
            if (T.first() == 'B') cur = cur.xor(1)
            T.removeFirst()
        }
    }
    if (cur == 1) T.reverse()
    for (i in 0 until S.length) {
        if (T[i] != S[i]) {
            println(0)
            return
        }
    }
    println(1)

    br.close()
    bw.flush()
    bw.close()
}
