let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
let inf = 1<<28
let lnf = Int64(1)<<60
/*
13936번 Iron and Coal

정점이 N개인 그래프가 주어진다.
이 중 K개는 특별노드 1, M개는 특별노드 2이다.
1번에서 출발해 특별노드 1과 특별노드 2를 각각 적어도 하나 방문하는 최소 거리를 구해보자.

으
역방향그래프를 너무 오랜만에 봐서 생각도 못했네요
BFS자체는 파악을 했는데 아쉽다..
*/
let (N, M, K) = hni()
let (X, Y) = (spi(), spi())
var G = [[Int]](repeating: [Int](), count: N+1)
var RG = [[Int]](repeating: [Int](), count: N+1)
for i in 1...N {
    let A = spi()
    for j in 1..<A.count {
        G[i].append(A[j])
        RG[A[j]].append(i)
    }
}

func bfs(_ S: [Int], _ G: [[Int]]) -> [Int] {
    var (Q, lQ) = (S, [Int]())
    var V = [Int](repeating: inf, count: N+1)
    for i in S { V[i] = 0 }
    while Q.count+lQ.count > 0 {
        if Q.count == 0 {
            Q = lQ.reversed()
            lQ = []
        }
        let x = Q.removeLast()
        for i in G[x] {
            if V[i] <= V[x]+1 { continue }
            V[i] = V[x]+1
            lQ.append(i)
        }
    }
    return V
}

let (z, x, y) = (bfs([1], G), bfs(X, RG), bfs(Y, RG))
var ret = inf
for i in 1...N {
    ret = min(ret, z[i]+x[i]+y[i])
}
print(ret == inf ? "impossible" : ret)
