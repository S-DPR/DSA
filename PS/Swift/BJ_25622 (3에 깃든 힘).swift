/*
25622번 3에 깃든 힘

트리가 있다. 이 트리를 인접한 노드 3개씩 묶으려 한다.
단, 모든 노드는 하나의 묶음 안에 속해야한다.
가능하면 S와 그 묶음들을, 불가능하면 U를 출력하자.

구현푸는 느낌 쎄게 받은 문제
일단 임의의 루트노드를 정해서 출발하면 대참사가 일어나는게 그려지니,
위상정렬 느낌으로 리프노드부터 시작합시다.

리프노드는 자기랑 이어진 노드가 하나이므로, (트리의 특성)
그냥 위쪽에 자기 숫자를 넣어줍니다.
그리고 그거도 어차피 위로는 하나만 이어져있을테니까 (부모노드는 하나)
그거도 이어줍니다. 그리고 그 셋을 떼냅니다.
이런식으로 쭉 반복하면 됩니다. 다들 dfs느낌으로 했나본데 전 bfs느낌으로 했네요.
*/
let N = Int(readLine()!)!
var G = [[Int]](repeating: [Int](), count: N+1)
var D = [Int](repeating: 0, count: N+1)
var conn = [[Int]](repeating: [Int](), count: N+1)
for i in 1...N {
    conn[i].append(i)
}
for _ in 1..<N {
    let I = readLine()!.split(separator: " ").map {Int($0)!}
    G[I[0]].append(I[1])
    G[I[1]].append(I[0])
    D[I[0]] += 1
    D[I[1]] += 1
}
var leaf = [Int]()
var wait = [Int]()
for i in 1...N {
    if G[i].count == 1 {
        leaf.append(i)
    }
}
var visit = [Bool](repeating:false, count:N+1)
var bundle = [[Int]]()
var canBundle = true
while !leaf.isEmpty || !wait.isEmpty {
    if leaf.isEmpty {
        leaf = wait.reversed()
        wait = []
    }
    let cur = leaf.removeLast()
    if visit[cur] { continue }
    visit[cur] = true
    if conn[cur].count == 3 { bundle.append(conn[cur]) }
    for i in G[cur] {
        if visit[i] { continue }
        if conn[cur].count + conn[i].count <= 3 {
            conn[i] += conn[cur]
        }
        D[i] -= 1
        if D[i] == 1 { wait.append(i) }
    }
}
canBundle = canBundle && bundle.count*3 == N
print(canBundle ? "S" : "U")
if canBundle {
    for i in bundle {
        print(i[0], i[1], i[2])
    }
}