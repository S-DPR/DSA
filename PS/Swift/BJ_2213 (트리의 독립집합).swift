/*
2213번 트리의 독립집합

노드마다 점수가 있는 트리가 주어진다.
서로 인접하지 않도록 노드를 적절하게 골라 최대점수를 얻어보자.
그리고, 어떻게 골라야하는지 구해보자.

저저 한달 전에 푼 우수마을이랑 똑같은데 역추적 추가된문제.
역추적 계속 생각하다가 조건 하나는 맞췄는데, 다른 하나를 생각 못해서 힌트보고 맞췄습니다.
참.. 너무 간단했는데..

일단 우수마을이랑 똑같이 dp를 구해줍니다.
그리고 dfs를 한 번 더 돌리는데,
이 때는 '이전 노드가 선택되었는가'와 '이번 노드에서, dp[1]이 dp[0]보다 더 큰가'를 따지면됩니다.
후자는 생각했는데, 전자는 생각을 못했네요. 정말 당연한건데요..
너무 아쉽고..
*/
let N = Int(readLine()!)!
let W = [0] + readLine()!.split(separator: " ").map {Int($0)!}
var dp = [[Int]](repeating: [0, 0], count: N+1)
var G = [[Int]](repeating: [Int](), count: N+1)
var V = [Bool](repeating: false, count: N+1)
var T = [Bool](repeating: false, count: N+1)
for _ in 1..<N {
    let I = readLine()!.split(separator: " ").map {Int($0)!}
    G[I[0]].append(I[1])
    G[I[1]].append(I[0])
}

func dfs(_ x: Int) -> [Int] {
    V[x] = true
    dp[x][1] = W[x]
    for i in G[x] {
        if V[i] { continue }
        let item = dfs(i)
        dp[x][0] += item.max()!
        dp[x][1] += dp[i][0]
    }
    return dp[x]
}

var ret = [Int]()
var vis = [Bool](repeating: false, count: N+1)
func backT(_ x: Int, _ prvSelect: Bool) {
    vis[x] = true
    var prv = prvSelect
    if !prvSelect && dp[x][0] < dp[x][1] {
        ret += [x]
        prv = true
    } else {
        prv = false
    }
    for i in G[x] {
        if vis[i] { continue }
        backT(i, prv)
    }
}

print(dfs(1).max()!)
backT(1, false)
var res = ""
for i in ret.sorted() {
    res += "\(i) "
}
print(res)