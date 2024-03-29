/*
1489번 대결

수열 A와 B가 주어진다.
A를 적절히 배치해, (A[i] > B[i]인 i의 개수*2 + A[i] == B[i]인 i의 개수)가 최대가 되게 하자.

하아
그리디가 됐구나..
한번에 처리하려던 내가 잘못했네..

저는 일단 dp로했는데.. 조금 이상해요 모양이.
아마 N이 10만이었으면 어떻게든 그리디로 생각했을텐데, 
50밖에 안돼서 이건 진짜 dp다, 아무리 잘 쳐줘도 dp에 그리디 혼합한거다 했는데..
어림도없지 진짜 순수 그리디가 대놓고 있네 ㅋㅋㅋㅋㅋㅋㅋㅋㅋ

아.. 슬프다..
*/
func spl() -> [Int] {
    readLine()!.split(separator: " ").map {Int($0)!}
}
let N = Int(readLine()!)!
let arr = spl().sorted()
let brr = spl().sorted()
// dp[i][j] = i번째 arr 원소가 j번째 brr 원소와 붙었을 때 최댓값
var dp = [[Int]](repeating: [Int](repeating: 0, count: N+1), count: N+1)
var trace = [[Int]](repeating: [Int](repeating: 0, count: N+1), count: N+1)

func traceBack(_ kth: Int, _ prv: Int, _ idx: Int) -> Bool {
    if kth == 0 { return true }
    if trace[kth][prv] == idx {
        return false
    }
    return traceBack(kth-1, trace[kth][prv], idx)
}

for i in 1...N {
    for j in 1...N {
        var score = 1
        if arr[i-1] > brr[j-1] { score += 1 }
        if arr[i-1] < brr[j-1] { score -= 1 }
        var M = 0
        for k in 1...N {
            if j == k { continue }
            if !traceBack(i-1, k, j) { continue }
            if M <= dp[i-1][k] && i != 1 {
                trace[i][j] = k
                M = dp[i-1][k]
            }
        }
        dp[i][j] = M + score
    }
}
print(dp[N].max()!)