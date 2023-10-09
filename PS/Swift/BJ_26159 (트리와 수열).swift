let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
26159번 트리와 수열

트리가 있고, 그 간선에 수열 A에 들어있는 가중치를 붙이려한다.
모든 정점 쌍 (i, j)에 대해, 최단거리로 이동할 때 모든 가중치의 합을 최소화해보자.

대충 이게 뭔문제여 하다가 '이거 그냥 간선 제일 적게 사용되는거에 제일 큰거 붙이면 되겠는데'라는 생각을 하고,
그러면 그냥 머 트리dp구나 하고 슥슥 푼 문제.
쉽게 맞췄습니다. 이런거말고 아예 처음보는 테크닉 배워야하는데..
*/
let N = Int(readLine()!)!
var G = [[Int]](repeating: [Int](), count: N+1)
var E = [[Int]]()
var D = [Int64](repeating: 0, count: N+1)
var V = [Bool](repeating: false, count: N+1)
for _ in 1..<N {
    let I = spi()
    let (u, v) = (I[0], I[1])
    E.append([u, v])
    G[u].append(v)
    G[v].append(u)
}
let A = spl().sorted()
func dfs(_ x: Int) -> Int64 {
    if D[x] != 0 { return D[x] }
    V[x] = true
    D[x] = 1
    for i in G[x] {
        if !V[i] { D[x] += dfs(i) }
    }
    return D[x]
}
dfs(1)
var W = [Int64]()
for e in E {
    var (u, v) = (e[0], e[1])
    if D[u] > D[v] { (u, v) = (v, u) }
    W.append(D[u]*(Int64(N)-D[u]))
}
W.sort(by: >)
var R: Int64 = 0
for i in 0..<N-1 {
    R = (R + W[i]*A[i]) % 1_000_000_007
}
print(R)
