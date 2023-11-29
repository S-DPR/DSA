let nxtI = { () -> Int in
    return Int(readLine()!)!
}
let nxtL = { () -> Int64 in
    return Int64(readLine()!)!
}
let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
12912번 트리 수정

트리가 주어진다. 트리에서 한 간선을 떼어내 다시 붙여 트리의 지름이 최장이 되게 하려 한다.
단, 간선을 새로 붙였을 때 트리임이 유지되어야한다.
가능한 최장 지름을 구하시오.

정말 논리는 간단하게 세웠지만..
생각보다 구현에 난관을 부딪혔던 문제.
오랜만의 트리지름 문제였습니다.

트리에서 간선 하나를 제거하면 어떻게든 트리 2개가 됩니다.
각 트리의 지름을 구해줍니다.
그리고 그 둘과 제거했던 간선의 길이를 합쳐줍니다.
이걸 모든 간선에 대해 해줍니다.
마지막으로, 그 최댓값을 출력하면 됩니다.
*/
let N = nxtI()
var G = [[(nxt: Int, idx: Int, w: Int64)]](repeating: [(Int, Int, Int64)](), count: N)
var E = [Int64]()
for i in 0..<N-1 {
    let I = spi()
    let (u, v, w) = (I[0], I[1], Int64(I[2]))
    G[u].append((v, i, w))
    G[v].append((u, i, w))
    E.append(w)
}
var (far, weight) = (0, Int64(0))
var V = [Bool](repeating: false, count: N)
var totalVis = [Bool](repeating: false, count: N)
func farthest(_ x: Int, _ broken: Int, _ pf: Int64) {
    V[x] = true
    totalVis[x] = true
    if weight <= pf {
        far = x
        weight = pf
    }
    for (nxt, idx, w) in G[x] {
        if V[nxt] { continue }
        if idx == broken { continue }
        farthest(nxt, broken, pf+w)
    }
}
func getRadius(_ broken: Int) -> Int64 {
    var ret = Int64(0)
    totalVis = [Bool](repeating: false, count: N)
    for i in 0..<N {
        if totalVis[i] { continue }
        V = [Bool](repeating: false, count: N)
        weight = 0
        farthest(i, broken, 0)
        V = [Bool](repeating: false, count: N)
        weight = 0
        farthest(far, broken, 0)
        ret += weight
    }
    return ret
}
var ret = Int64(0)
for i in 0..<N-1 {
    ret = max(ret, getRadius(i)+E[i])
}
print(ret)
