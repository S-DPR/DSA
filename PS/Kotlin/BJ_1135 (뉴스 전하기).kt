import java.io.*
/*
1135번 뉴스 전하기

트리가 주어진다.
각 노드로 정보를 전달하려 하는데, 처음엔 루트노드만 정보를 가지고있다.
정보를 전달하는데는 정확히 1분이 걸린다.
정보를 전달받은 노드는 똑같이 다른 연결된 노드에게 정보를 전달할 수 있다.
정보가 전달이 되는 최소 시간을 구해보자.

후..
진짜 못풀었으면 마음 꺾일뻔했다..

처음엔 그리디로 접근했습니다.
어떤그리디냐? 노드가 가장 많은곳이 무조건 정보 전달이 가장 느릴것이다!
그러므로, 자식노드가 가장 많은 녀석에게 정보를 먼저 전달하자.

.. 대차게 깨졌습니다.
질문글에 저랑 똑같은 생각을 한사람이 있었는데,
댓글에 '노드가 더 많아도 적당히 퍼져있다면 비대칭한쪽보다 빨리 끝난다'라며,
배럭의 중요성을 알게해주었습니다.

그래서 생각을 한게, '트리의 자식은 트리다'
[각 노드마다, 언제 끝나는지 기록을 해두자.]
그리고 자식노드중 가장 빨리 끝나는 녀석과 자식의 개수를 더한값과,
자식 노드중 가장 늦게 끝나는 녀석+1의 값중 더 큰 값을 넣자.

.. 이거도 대차게 깨졌습니다.
이후 1시간동안 반례찾으려고 이것저것 입력하다가,
'가장 빨리 끝나는 녀석이 아니라, 중간에 끝나는 녀석에서 최댓값이 나올 수 있다'라는 점을 깨닫고,
그 부분을 고쳐 AC를 맞았습니다.
*/
fun dfs(x: Int, G: Array<MutableList<Int>>, result: IntArray) {
    G[x].forEach {
        dfs(it, G, result)
    }
    var res = 0
    G[x].sortedBy{-result[it]}.map{result[it]}.forEachIndexed{idx, i -> res=maxOf(res, i+idx+1)}
    result[x] = res
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val N = br.readLine().toInt()
    val G = Array(N) { mutableListOf<Int>() }

    val info = br.readLine().split(" ").map {it.toInt()}
    info.forEachIndexed{idx, i ->
        if (i == -1) return@forEachIndexed
        G[i].add(idx)
    }

    val result = IntArray(N) {0}
    dfs(0,  G,  result)
    println(result[0])

    br.close()
    bw.flush()
    bw.close()
}
