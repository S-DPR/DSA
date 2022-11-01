import java.io.*
/*
20920번 영단어 암기는 괴로워

영단어가 N개 주어집니다.
다음 순서로 정렬한 뒤 출력해주세요.
1. 많이 나온 순서
2. 길이가 긴 순서
3. 사전순서

충격과 공포의 정렬문제입니다.
파이썬에서의 정렬은 그냥 적당적당히 하면 되는데,
이놈은 그게 또 아니거든요..

그냥 Map (파이썬의 dictionary) 자료구조로 다 받아준 뒤,
정렬을 하고 출력하면 되는 쉬운문제입니다.
그런데 정렬 하는법 찾느라 시간이 오래걸렸네요..
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val t = sortedMapOf<String, Int>()
    val (n, k) = br.readLine().split(" ").map {it.toInt()}
    repeat(n) {
        val str = br.readLine()
        if (str.length >= k) {
            t[str] = t.getOrDefault(str, 0) + 1
        }
    }
    t.toList().sortedWith(compareBy<Pair<String, Int>>{-it.second}.thenBy {-it.first.length}.thenBy {it.first}).forEach{ (i, _)->
        bw.write("$i\n")
    }
    br.close()
    bw.flush()
    bw.close()
}
