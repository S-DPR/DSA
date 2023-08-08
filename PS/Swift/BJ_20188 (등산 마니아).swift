/*
20188번 등산 마니아

노드의 개수가 N개인 트리가 주어진다.
이 트리에서, 어떤 정점 i, j를 잡았을 때, i -> 1 -> j로 가려고 한다.
1 <= i < j <= N인 모든 i, j에 대하여, 위 경우 이동하는 간선의 개수를 세어보자.
단, 어떤 (i, j)에서 두 번 이상 사용한 간선은 해당 (i, j)에서 한 개를 사용한 것으로 취급한다.

하아
이진트리로 생각해서 멸망한 문제
더 어이없는건 그 논리 그대로 들고와서 일반화하니까 AC..
그래도.. 계산식 맞았으면 된거야.. 그치..?
그래.. 된거야.. 된거여야해..

dp도 쓰고, 수학도 쓰고, 이것저것 쓰는 문제.
dp는 단순하게 해당 노드의 자식노드는 몇 개가 있나요~ 를 계산합니다.
이제 또 어디서 많이해본 문제가 나오는데..

1. 해당 노드와 자식노드를 선택하는 경우
2. 자식노드중 하나와 다른 자식노드 중 하나를 선택하는 경우
를 따로 계산해줘야합니다. 

func dfs(_ x: Int, _ depth: Int64) -> Int64 {
    var left: Int64 = 0
    var right: Int64 = 0
    var left_node: Int64 = 0
    var right_node: Int64 = 0
    if TG[x].count >= 1 {
        left = dfs(TG[x][0], depth+1)
        left_node = dp[TG[x][0]]
    }
    if TG[x].count >= 2 {
        right = dfs(TG[x][1], depth+1)
        right_node = dp[TG[x][1]]
    }
    ret += (left+left_node)*right_node+(right+right_node)*left_node
    ret += (left_node*right_node*depth)
    ret += (left+left_node+left_node*depth)
    ret += (right+right_node+right_node*depth)
    return (left+left_node)+(right+right_node)
}

이거 보는게 좀 더 이해가 쉬울거에요. 이진트리일때만 해둔거라..
그림이랑 같이 보면.. 쉬울겁니다.
아.. 하아..
아.....
*/
let N = Int(readLine()!)!
var G = [[Int]](repeating: [Int](), count: N+1)
var TG = [[Int]](repeating: [Int](), count: N+1)
var V = [Bool](repeating: false, count: N+1)
var dp = [Int64](repeating: 1, count: N+1)
for _ in 1..<N {
    let I = readLine()!.split(separator: " ").map {Int($0)!}
    G[I[0]].append(I[1])
    G[I[1]].append(I[0])
}
func tree_dfs(_ x: Int) {
    V[x] = true
    for i in G[x] {
        if V[i] { continue }
        TG[x].append(i)
        tree_dfs(i)
        dp[x] += dp[i]
    }
}
tree_dfs(1)

var ret: Int64 = 0
func dfs(_ x: Int, _ depth: Int64) -> Int64 {
    var dist_total: Int64 = 0
    var node_total: Int64 = 0
    var dist_items = [Int64]()
    var node_items = [Int64]()
    for i in TG[x] {
        let next_dist = dfs(i, depth+1)
        dist_items.append(next_dist)
        node_items.append(dp[i])
        dist_total += next_dist
        node_total += dp[i]
    }
    let ret_dist = dist_total
    let ret_node = node_total
    for i in 0..<TG[x].count {
        dist_total -= dist_items[i]
        node_total -= node_items[i]
        ret += (dist_items[i]+node_items[i])*node_total
        ret += (dist_total+node_total)*node_items[i]
        ret += node_items[i]*node_total*depth
        ret += dist_items[i]+node_items[i]+node_items[i]*depth
    }
    return ret_dist + ret_node
}
dfs(1, 0)
print(ret)