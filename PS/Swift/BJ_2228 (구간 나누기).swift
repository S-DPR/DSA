/*
2228번 구간 나누기

수열을 아래 규칙으로 M개의 구간으로 나누자. 그리고 그 구간들의 합의 최대를 구해보자.
1. 나누어진 구간은 인접할 수 없다.
2. 나누어진 구간은 겹칠 수 없다.

이게.. 골드3..?
난..뭐지..?

농담이고 그냥 O(N^4)이 통과해서 G3인 문제입니다.
아마 N이 충분히 컸다면 골1~플레정도 난이도였을거같기도 하고..
의외로 골2~3 방어했을거같기도하고..

제가 처음에 푼 방법은 O(N^4). 650ms정도고요.
답지보고  "와 이게 돼??" 하면서 따라한게 16ms.
진짜 답지 보면서 세상엔 천재가 많단걸 느꼈습니다..

공통아이디어는 dp[i][j]는 i번째 인덱스가 제일 오른쪽일 때 j번째 구간인 경우의 최댓값이라는겁니다.
*/
func loop(prefixS: [Int], dp: inout [[Int]], idx: Int, sum: Int, segment: Int) -> Int {
    let N = dp.count-1
    let M = dp[0].count
    if segment == M { return sum }
    if idx+2 > N { return -35000*100 }
    if dp[idx][segment] >= sum { return -35000*100 }
    dp[idx][segment] = sum
    var mx = -35000*100
    for i in idx+2...N {
        for j in i...N {
            mx = max(mx,
                loop(prefixS: prefixS, dp: &dp, idx: j, sum: prefixS[j]-prefixS[i-1] + sum, segment: segment+1))
        }
    }
    return mx
}
let I = readLine()!.split(separator: " ").map { Int($0)! }
let N = I[0], M = I[1]
var arr: [Int] = []
for _ in 0..<N { arr.append(Int(readLine()!)!) }
var prefixS = [Int](repeating: 0, count: N+1)
for i in 0..<N { prefixS[i+1] = arr[i] + prefixS[i] }
var dp = [[Int]](repeating: [Int](repeating: -35000*100, count: M), count:N+1)
var res = -35000*100
for i in 1...N {
    for j in i...N {
        res = max(res, loop(prefixS: prefixS, dp: &dp, idx: j, sum: prefixS[j]-prefixS[i-1], segment: 1))
    }
}
print(res)
/*
시간복잡도는 모르겠지만, O(N^2)정도 되지않을까요?
어떻게 이런 좋은 방법을 생각해내지?

let INF = 1_000_000_000
let I = readLine()!.split(separator: " ").map {Int($0)!}
let N = I[0], M = I[1]
var dp = [[Int]](repeating: [Int](repeating: -INF, count: N+1), count: N+1)
var visit = [[Bool]](repeating: [Bool](repeating: false, count: N+1), count: N+1)
var arr = [0]
for i in 1...N { arr.append(arr[i-1] + Int(readLine()!)!) }

func loop(maxIdx: Int, curSegment: Int) -> Int {
    if curSegment == 0 { return 0 }
    if maxIdx <= 0 { return -INF }
    if visit[maxIdx][curSegment] { return dp[maxIdx][curSegment] }
    visit[maxIdx][curSegment] = true
    dp[maxIdx][curSegment] = loop(maxIdx: maxIdx-1, curSegment: curSegment)
    for i in (1...maxIdx).reversed() {
        dp[maxIdx][curSegment] = max(dp[maxIdx][curSegment], loop(maxIdx: i-2, curSegment: curSegment-1) + arr[maxIdx] - arr[i-1])
    }
    return dp[maxIdx][curSegment]
}

print(loop(maxIdx: N, curSegment: M))
*/