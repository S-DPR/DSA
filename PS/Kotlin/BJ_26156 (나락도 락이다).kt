import java.io.*

const val inf = 1_000_000_000
const val mod = 1_000_000_007
/*
26156번 나락도 락이다

알파벳 대문자로 이루어진 문자열이 주어진다.
ROCK로 끝나는 부분문자열의 개수는 몇 개일까?

틀린 이후 새로 보는데, 정말 1시간동안 곰곰히 생각해보다가 어 설마 이러면 되나? 하고 냈더니 맞은 문제
퍼센트 오르는거 보는데 이게 좀 어이가 없더라고요..

잘 보면, R 이후에는 OCK로 고정이 되어있습니다.
즉 실질적으로 개수에 영향을 미치는것은 R 이전의 글자의 개수입니다.
존재한다/안한다로 나눌 수 있으니 매 인덱스마다 2씩 곱해서 보관해주면 됩니다.

R이 나왔다면, R에다가 prefix값을 더하고 mod로 나눈 나머지를 구해둡니다.
이후 O가 나왔다면, 구해둔 R의 값을 더하고 mod로 나눈 나머지를 구하고,
C, K도 마찬가지로 합니다.
K는 결론이기 때문에, 최종 K의 값이 답이 됩니다.

그나저나 내가 생각해서푼건 어째 전부 골드4냐
*/
fun main(){
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    val N = br.readLine().toInt()
    val S = br.readLine()
    var (R, O, C, K) = longArrayOf(0L, 0L, 0L, 0L)
    var prefix = 1L
    S.forEach { char ->
        when (char) {
            'R' -> {
                R += prefix
                R %= mod
            }
            'O' -> {
                O += R
                O %= mod
            }
            'C' -> {
                C += O
                C %= mod
            }
            'K' -> {
                K += C
                K %= mod
            }
        }
        prefix = prefix shl 1
        prefix %= mod
    }
    bw.write("$K")

    br.close()
    bw.flush()
    bw.close()
}
