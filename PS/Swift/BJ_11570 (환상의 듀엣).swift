/*
11570번 환상의 듀엣

두 명이 노래를 부르려 한다.
노래의 난이도는 두 사람 각각이 다음 음을 부를 때 그 차이의 합이다.
처음 음을 부를때는 아무런 비용이 발생하지 않을 때, 그 노래의 최소난이도를 구해보자.

이 문제만 지금 세번째보는거같아
솔직히 요즘 슬럼프같아서 그냥 제가 옛날에 푼거 보고 따라쳤습니다. 복습겸..
그나저나 이거는 '경찰차'문제가 표준으로 박혀있고, 그 문제는 여기에 역추적까지 더한거같네요.
신기해..
*/
let spi = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}

let N = Int(readLine()!)!
let A = spi()
var dp = [[Int64]](repeating: [Int64](repeating: -1, count: N+1), count: N+1)

func loop(_ idx: Int, _ l: Int, _ r: Int) -> Int64 {
    if idx == N+1 {
        return 0
    }
    if dp[l][r] != -1 {
        return dp[l][r]
    }
    var left = loop(idx+1, idx, r)
    if l != 0 {
        left += A[l-1] > A[idx-1] ? A[l-1]-A[idx-1] : A[idx-1]-A[l-1]
    }
    var right = loop(idx+1, l, idx)
    if r != 0 {
        right += A[r-1] > A[idx-1] ? A[r-1]-A[idx-1] : A[idx-1]-A[r-1]
    }
    dp[l][r] = min(left, right)
    return dp[l][r]
}

print(loop(1, 0, 0))