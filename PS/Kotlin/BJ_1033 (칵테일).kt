import java.io.*
import java.math.BigInteger
/*
1033번 칵테일

재료의 개수 N이 주어진다. 각 재료는 0~N-1의 수로 지칭한다.
이후 N-1줄에 걸쳐 a b p q의 포멧으로 주어진다. 재료 a, b의 비율이 p:q라는 뜻이다.
즉, a/b = p/q라는 뜻이다.

저어는 머리가 나빠서 플로이드로 때려박았는데,
값의 범위가 long을 넘는거를 빠르게 캐치하지 못해 시간이 질질 끌려서 5시간 넘게 박게되었습니다.
정해는 당연히 플로이드가 아닙니다. 뭐 당연하죠..

일단 플로이드를 하려면 아래 원리에 입각해야합니다.
재료 i와 j의 비율이 p, q임을 알고, j와 k의 비율이 n, m임을 안다고 합시다.
그러면 우리는 pi = qj, nj = mk라는 방정식을 세울 수 있고,
j로 맞추어주기 위해, 그리고 가능하면 나눗셈을 하지 않기 위해 q와 n의 최소공배수를 구하는 방식으로 통분을 해줍니다.
그러면 pni = qnj, qnj = mqk라는 식이 세워지고, qnj가 같으므로 pni = mqk가 됩니다.
즉, i:k = pn:mq가 됩니다. 이거를 i==j인 원소를 제외하고 다 채워질때까지 반복합니다. 얼마 되지도 않습니다.

이제 i==j인 원소를 하나 잡아(이 코드에선 i=0, j=0) 그 행을 다 곱합니다.
이유는 간단합니다. G[j][0]이 k라고 하면, 기준으로 삼을 0번의 재료는 적어도 k의 배수여야합니다.
그래야 나머지를 처리하기 편해지거든요.
조금 더 예를 들어서,
 0  1 21  7
 3  0 63 21
20 20  0  4
 5  5  3  0
가 최종 그래프라고 합시다. 위 내용 특성상 i와 j재료의 비율은 G[j][i]:G[i][j]입니다.
i번 재료의 양을 A[i]라고 잡겠습니다.
그러면 A[0] = 3*A[1]이 됩니다. 그러면 이제 A[0]와 A[1]이 모두 정수이므로 A[0]/3 = A[1], 즉 A[0]*1은 3의 배수여야 합니다.
A[0]*1 mod 3 = 0 라는 식이 나오게 됩니다.
*1이라는게 이해가 안갈 수 있으니 A[0]과 A[2]를 봅시다.
21*A[0] = 20*A[2]가 되는데, 똑같이 A[0]*21/20 = A[2]가 됩니다. A[2]는 정수이므로,
A[0]*21 mod 20 = 0을 만족해야합니다.
즉, 우리는 어떤 수 X에 대해 [자신이 i번째 재료라면 자신 행을 제외한 i번째 열값은 다 곱한 값]을 임시로 세워둘 수 있습니다.

이제 X는 자기 행에 있는 수에 대해 나누어떨어집니다. 왜냐면 X 자체가 자기 열에 있는 수를 다 곱한거거든요.
이제 이 X로 1, 2, ..., N-1번째 재료가 얼마나 함량되어있나 구해봅시다.
우리는 i:j = G[j][i]:G[i][j]임을 알고있습니다. 일단 i는 0으로 고정합시다.
0:j = G[j][0]:G[0][j]
j = 1을 넣어봅시다. 그러면 0:1 = G[1][0]:G[0][1]가 되고, 위 값을 보면 0:1 = 3:1입니다.
좌측에 0과 1은 그냥 재료의 이름을 지칭하는 이름임을 잊으면 안됩니다.
우리는 첫 번째 재료가 얼마나 필요한지 알고싶으므로 좌측 1값을 K로 두겠습니다.
그리고 좌측 0은 우리가 이미 X라고 구해뒀습니다. X는 상수이므로, 우리는 비례식을 이용해 K값을 구할 수 있습니다.
X:K = 3:1
3K = X
K = X/3
X는 당연히 3으로 나누어떨어지므로, 우리는 K의 값을 구할 수 있습니다.
이 값들을 모두 res에 저장해둡시다.

마지막으로, 우리는 이 res의 최대공약수를 구할 수 있습니다.
끝으로, 각 res를 gcd로 나눈 값을 출력합니다.
*/
fun gcd(x: Long, y: Long): Long = if (y == 0L) x else gcd(y, x%y)
fun lcm(x: Long, y: Long): Long = x/gcd(x, y)*y
fun checkZero(G: Array<LongArray>): Boolean {
    for (i in G.indices) {
        for (j in G.indices) {
            if (i == j) continue
            if (G[i][j] == 0L) return true
        }
    }
    return false
}

fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val N = br.readLine().toInt()
    val res = Array<BigInteger>(N) {BigInteger.valueOf(0) }
    val G = Array(N) { LongArray(N) {0} }
    repeat(N-1) {
        val (a, b, p, q) = br.readLine().split(" ").map {it.toInt()}
        val gcd = gcd(p.toLong(), q.toLong())
        G[a][b] = q.toLong()/gcd
        G[b][a] = p.toLong()/gcd
    }
    while (checkZero(G)) {
        for (i in 0 until N) {
            for (j in 0 until N) {
                if (G[i][j] == 0L) continue
                for (k in 0 until N) {
                    if (k == i) continue
                    if (G[j][k] == 0L) continue
                    if (G[i][k] != 0L) continue
                    val X = G[i][j]
                    val Y1 = G[j][i]
                    val Y2 = G[j][k]
                    val Z = G[k][j]
                    val lcm = lcm(Y1, Y2)
                    val gcd = gcd(X * (lcm / Y1), Z * (lcm / Y2))
                    G[i][k] = X * (lcm / Y1) / gcd
                    G[k][i] = Z * (lcm / Y2) / gcd
                }
            }
        }
    }

    var X = BigInteger.valueOf(1)
    G[0][0] = 1
    G.forEach{ X = X.times(BigInteger.valueOf(it[0])) }
    var gcd = X.div(BigInteger.valueOf(1))
    for (i in 0 until N) {
        res[i] = X.div(BigInteger.valueOf(G[i][0])).times(BigInteger.valueOf(G[0][i]))
        gcd = gcd.gcd(res[i])
    }
    res.forEach { bw.write("${it.div(gcd)} ") }

    br.close()
    bw.flush()
    bw.close()
}