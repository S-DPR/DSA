let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
let inf = 1<<30
let lnf = Int64(1)<<60
/*
20530번 양분

트리가 주어진다.
그런데 트리에는 없는, 트리의 두 정점을 잇는 간선이 하나 더 주어진다.
다음 쿼리에 답해보자.
u v : u에서 v로 가는 단순경로의 수를 구하시오.

아쉽당
풀이 제대로 떠올린지 15분만에 구현했는데 dfs에 i != p 조건 안넣어서 12시 AC돼버렸네
그런데 풀이를 떠올리기 자체는 까다로운거같아요.

트리에 선 하나 더 그으면 사이클생기는데,
그 사이클 찾고,
사이클에서 삐져나온부분은 분리집합으로 합치면.
이제 서로 다른 집합에 있는 정점은 반드시 2.
사이클 내부끼리는 반드시 2.
같은 집합에 있으면 1.
이렇게 출력하면 끝납니다.

여러모로 직관을 필요로 하는 문제였네요..
구현도 구현이지만..
*/
let (N, Q) = tni()
var G = [[Int]](repeating: [Int](), count: N+1)
var V = [Int](repeating: -1, count: N+1)
var U = Array(0...N)
for _ in 0..<N {
    let (u, v) = tni()
    G[u].append(v)
    G[v].append(u)
}

func findCycle(_ x: Int, _ p: Int) -> Int {
    V[x] = 0
    for i in G[x] {
        if p == i { continue }
        if V[i] != -1 {
            V[i] = 1
            V[x] = 1
            return 1
        }
        if findCycle(i, x) == 1 {
            if V[x] == 1 {
                return 0
            }
            V[x] = 1
            return 1
        }
    }
    return 0
}
findCycle(1, -1)

func union(_ u: Int, _ v: Int) -> Bool {
    let (up, vp) = (find(u), find(v))
    if up == vp { return false }
    U[up] = U[vp]
    return true
}

func find(_ u: Int) -> Int {
    if U[u] != u {
        U[u] = find(U[u])
    }
    return U[u]
}

func dfs(_ x: Int, _ p: Int) {
    for i in G[x] {
        if i == p { continue }
        if V[i] == 1 { continue }
        union(x, i)
        dfs(i, x)
    }
}

for i in 1...N {
    if V[i] != 1 { continue }
    dfs(i, -1)
}

var ret = ""
for _ in 0..<Q {
    let (u, v) = tni()
    if find(u) != find(v) || (V[u] == 1 && V[v] == 1) {
        ret += "2\n"
    } else {
        ret += "1\n"
    }
}
print(ret)
