const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
28102번 단순한 그래프와 이상한 쿼리

가중치가 모두 1인 그래프가 주어진다.
a에서 b로 가는 경로 중, 사용하는 간선의 개수가 k의 배수인 경우가 있을까?
어떤 간선이든 원하는 만큼 사용할 수 있다.

크 아쉽다
k가 홀수면 그냥 같은 집합내에 있는지만 체크하면 된다는걸 답지 보고 깨달아버렸다
k가 짝수인건 그냥 그대로 하면 됐는데..
오늘도 능지 +1 적립
*/
let union = (u, v) => {
    U[find(u)] = U[find(v)]
}

let find = (x) => {
    if (U[x] != x)
        U[x] = find(U[x])
    return U[x]
}

let dfs = (x, w) => {
    if (V[x][w]) return
    V[x][w] = true
    G[x].forEach(i => {
        union(x, i)
        dfs(i, w^1)
    })
}

let [N, M, Q] = ins()
let [G, V] = [[], []]
let U = []
for (let i = 0; i <= N; i++) {
    G[i] = []
    U[i] = i
    V[i] = [false, false]
}
for (let i = 0; i < M; i++) {
    let [u, v, w] = ins()
    G[u].push(v)
    G[v].push(u)
}
for (let i = 1; i <= N; i++) {
    if (V[i][0] || V[i][1]) continue
    dfs(i, 0)
}
let ret = ""
for (let i = 0; i < Q; i++) {
    let [u, v, k] = ins()
    let canBeK = 0
    if (~k&1) {
        canBeK |= V[u][0] && V[v][0]
        canBeK |= V[u][1] && V[v][1]
        canBeK &= find(u) == find(v)
    } else {
        canBeK = find(u) == find(v)
    }
    ret += canBeK ? "Yes\n" : "No\n"
}
console.log(ret)
