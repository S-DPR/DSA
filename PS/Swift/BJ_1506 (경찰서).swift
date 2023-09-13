/*
1506번 경찰서

도시의 개수 N과 각 도시가 어떻게 이어져있는지 정보가 주어진다.
자기 자신에서 출발해 자기 자신으로 돌아오는 경로가 있을 때, 그 곳에 경찰서를 지을 수 있다.
각 도시에 경찰서를 짓기 위해 필요한 비용이 주어진다.
경찰서는 위 조건만 만족하면 그 경로에 있는 모든 도시를 커버할 수 있다.
경찰서를 짓는 최소비용을 구해보자.

SCC를 배운 문제.
타잔알고리즘..?이라고 하는데 보기보다는 간단해서 놀랐습니다.
SCC가 어떤 문제에서 쓰이는지는.. 아직 잘 모르겠네요.
이건 그냥 기초로 보여서요..

대충 scc 구현하고 그거 구성하는 노드중 최솟값만 쏙쏙 골라주면 됩니다.
그리고 그거 다 더하면 AC.
확실히 그래프에는 좀 재미있는게 많네요.
*/
let spl = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let N = Int(readLine()!)!
let C = spl()
var G = [[Int]](repeating: [Int](), count: N+1)
for i in 0..<N {
    let S = [Character](readLine()!)
    for j in 0..<N {
        if S[j] == "0" { continue }
        G[i].append(j)
    }
}
var stk = [Int]()
var tree = [Int](repeating: 0, count: N)
var curN = 1
var scc = [[Int]]()
var isScc = [Bool](repeating: false, count: N)
func dfs(_ x: Int) -> Int {
    tree[x] = curN
    var ret = tree[x]
    stk.append(x)
    curN += 1
    
    for i in G[x] {
        if tree[i] == 0 { ret = min(ret, dfs(i)) }
        else if !isScc[i] { ret = min(ret, tree[i]) }
    }
    if ret == tree[x] {
        var cScc = [Int]()
        while true {
            let item = stk.removeLast()
            cScc.append(item)
            isScc[item] = true
            if item == x { break }
        }
        scc.append(cScc)
    }
    return ret
}
for i in 0..<N {
    if tree[i] == 0 { dfs(i) }
}
print(scc.reduce(0) { $0 + $1.map { C[$0] }.min()! })
