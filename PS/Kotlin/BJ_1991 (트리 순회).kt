import java.io.*

/*
1991번 트리 순회

이진트리가 주어진다.
이것을 전위 순회, 중위 순회, 후위 순회를 해본 결과를 출력해보자.

코틀린쓰다가 답답해 죽을뻔한게 참 흔하지 않은데..
아 그저 파이썬 루비 자바스크립트님 그립습니다..
문제 자체는 그냥 노드를 제대로 입력받고 재귀때리면 풀리는 쉬운 실버문제입니다.
이제 이걸 어떻게 char형을 int로 int를 char로 바꾸는가에 대한 고민을 했습니다..
*/

data class Node (
    var left: Int,
    var right: Int
)

fun preorder(G:Array<Node>, x:Int): String {
    var returnValue = (x+'A'.code-1).toChar().toString()
    if (G[x].left != 0) returnValue += preorder(G, G[x].left)
    if (G[x].right != 0) returnValue += preorder(G, G[x].right)
    return returnValue
}

fun inorder(G:Array<Node>, x:Int): String {
    var returnValue = ""
    if (G[x].left != 0) returnValue += inorder(G, G[x].left)
    returnValue += (x+'A'.code-1).toChar().toString()
    if (G[x].right != 0) returnValue += inorder(G, G[x].right)
    return returnValue
}

fun postorder(G:Array<Node>, x:Int): String {
    var returnValue = ""
    if (G[x].left != 0) returnValue += postorder(G, G[x].left)
    if (G[x].right != 0) returnValue += postorder(G, G[x].right)
    returnValue += (x+'A'.code-1).toChar().toString()
    return returnValue
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val N = br.readLine().toInt()
    val G = Array(N+1) {Node(0, 0)}
    repeat(N) {
        val (root, left, right) = br.readLine().split(" ").map {it.toCharArray()[0]}
        if (left != '.') {
            G[root-'A'+1].left = left-'A'+1
        }
        if (right != '.') {
            G[root-'A'+1].right = right-'A'+1
        }
    }
    println(preorder(G, 1))
    println(inorder(G, 1))
    println(postorder(G, 1))

    br.close()
    bw.flush()
    bw.close()
}
