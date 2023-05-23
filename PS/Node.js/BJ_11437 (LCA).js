const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
11437번 LCA

트리가 주어진다. 두 정점이 주어질 때, 최소 공통 조상을 구해보자.

플레5문제에서 LCA 3문제에 얻어맞고 눈물을 흘리며 찾은 LCA 기초문제
새로운 알고리즘 + 올라가지 않는 레이팅 + 적절한 난이도
여러 블로그에서 LCA 알고리즘 보고 따라쳤습니다.
JS로 된건 없어서 또 C++보면서 했지만.

근데 이건 좀 뭔가 복잡..
*/
function dfs(G, V, S, D, cur, depth) {
    if (V[cur]) return
    V[cur] = true
    D[cur] = depth
    G[cur].forEach(i => {
        if (S && !V[i]) S[i][0] = cur
        dfs(G, V, S, D, i, depth+1)
    })
}

function lca(S, D, MDepth, u, v) {
    if (D[u] > D[v]) [u, v] = [v, u]
    for (let i = MDepth-1; i >= 0; i--)
        if (D[u] <= D[S[v][i]]) v = S[v][i]
    let ret = u
    for (let i = MDepth-1; i >= 0 && u !== v; i--) {
        if (S[u][i] !== S[v][i]) {
            u = S[u][i]
            v = S[v][i]
        }
        ret = S[u][i]
    }
    return ret
}

let N = Number(input[0])
let G = []
for (let i = 0; i <= N; i++)
    G.push([])
for (let i = 1; i < N; i++) {
    let [u, v] = input[i].split(" ").map(Number)
    G[u].push(v)
    G[v].push(u)
}
let depth = Array(N+1).fill(0)
dfs(G, Array(N+1).fill(false), null, depth, 1, 1)
let maxDepth = Math.max(...depth).toString(2).length
let sparse = []
for (let i = 0; i <= N; i++)
    sparse.push(Array(maxDepth).fill(0))
dfs(G, Array(N+1).fill(false), sparse, depth, 1, 1)
for (let i = 1; i < maxDepth; i++)
    for (let j = 1; j <= N; j++)
        sparse[j][i] = sparse[sparse[j][i-1]][i-1]
let Q = Number(input[N])
for (let i = N+1; i <= N+Q; i++) {
    let [u, v] = input[i].split(" ").map(Number)
    console.log(lca(sparse, depth, maxDepth, u, v))
}
