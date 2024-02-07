let nxti = { () -> Int in return Int(readLine()!)! }
let nxtl = { () -> Int64 in return Int64(readLine()!)! }
let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let twoi = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let twol = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let thri = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let thrl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
24526번 전화 돌리기

어떤 정점 i에서 출발했을 때, 사이클이 생기지 않는 정점 i의 개수를 구해보자.

정해는 아마 역그래프 위상정렬같지만..
다행히 dfs+dp로 간단하게 풀립니다.
vis를 다 사용하면 초기화해줘야하긴 한데, 어차피 dp가 그걸 저장하고있어서..
간단한 문제였네요.
*/
let (N, K) = twoi()
var G = [Set<Int>](repeating: Set<Int>(), count: N+1)
for _ in 0..<K {
    let (u, v) = twoi()
    G[u].insert(v)
}
var V = [Bool](repeating: false, count: N+1)
var dp = [Int](repeating: -1, count: N+1)
func dfs(_ x: Int) -> Int {
    if V[x] {
        dp[x] = 0
    }
    if dp[x] != -1  {
        return dp[x]
    }
    V[x] = true
    dp[x] = 1
    for i in G[x] {
        dp[x] &= dfs(i)
    }
    V[x] = false
    return dp[x]
}
for i in 1...N {
    dfs(i)
}
print(dp.reduce(0, +) + 1)
