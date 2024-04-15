let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
1368번 물대기

각 노드를 선택하는데는 C[i]만큼의 비용이 들고,
노드 두 개를 잇는데는 E[i][j]만큼의 비용이 든다.
모든 정점이 선택되거나 선택된 노드와 연결되었으면 한다.
이 때, 최소비용을 구하시오.

대충 MST겠구나 했는데,
대신.. 테크닉 하나.
저는 지금 0부터 N-1개의 정점을 실제 노드로 두었는데,
N번을 가상노드로 두고 그 노드와 잇는 간선의 비용을 그 노드를 선택하는데 필요한 비용으로 둡시다.
그러면 깔끔하게 처리되겠죠. 간단합니다.

.. 사실 구현은 위 생각 못하고 그냥 짜서 좀 드럽게됐지만..
*/
let N = lni()
var C = [[Int64]]()
var E = [[Int64]]()
for i in 0..<N {
    C.append([lni(), i])
}
for i in 0..<N {
    let A = spl()
    for j in 0..<i {
        let jdx = Int(j)
        E.append([A[jdx], i, j])
    }
}
C.sort{$0[0] < $1[0]}
E.sort{$0[0] < $1[0]}
var cIdx = 0
var U = Array(0...Int(N+1))
func union(_ u: Int, _ v: Int) -> Bool {
    let up = find(u)
    let vp = find(v)
    if up == vp { return false }
    U[up] = U[vp]
    return true
}
func find(_ x: Int) -> Int {
    if U[x] != x {
        U[x] = find(U[x])
    }
    return U[x]
}
var weight: Int64 = 0
for i in E {
    while cIdx < C.count && C[cIdx][0] <= i[0] {
        if union(Int(N), Int(C[cIdx][1])) {
            weight += C[cIdx][0]
        }
        cIdx += 1
    }
    if union(Int(i[1]), Int(i[2])) {
        weight += i[0]
    }
}
while cIdx < C.count {
    if union(Int(N), Int(C[cIdx][1])) {
        weight += C[cIdx][0]
    }
    cIdx += 1
}
print(weight)
