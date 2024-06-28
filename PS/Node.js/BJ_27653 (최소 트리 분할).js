const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
27653번 최소 트리 분할

정점이 N개인 트리가 주어진다.
여기서 연결그래프를 하나 잡고 거기에 있는 모든 노드의 가중치를 1 올리는 연산을 할 수 있다.
초기 모든 노드의 가중치는 0일 때, 각 정점의 가중치가 A[i]가 되게 하는 최소 연산 횟수를 구하시오.

이왜골1
그냥 정점 가장 작은거 루트로 하고 dfs굴렸는데..
사실 그럴필요도없이 그냥 1번으로 굴려도 될거같은데?
쉬운문제.. 골4기여해야지
*/
let dfs = (x, p) => {
    C[x] = A[p]
    ret += Math.max(0, A[x]-C[x])
    G[x].forEach(i => {
        if (p != i) dfs(i, x)
    })
}
let N = ini()
let A = [0].concat(ins())
let C = Array(N+1).fill(-1)
let G = []
for (let i = 0; i <= N; i++) G[i] = []
for (let i = 1; i < N; i++) {
    let [u, v] = ins()
    G[u].push(v)
    G[v].push(u)
}
let [mn, root] = [1<<30, 0]
for (let i = 1; i <= N; i++) {
    if (A[i] < mn) {
        mn = A[i]
        root = i
    }
}
let ret = 0
dfs(root, 0)
console.log(ret)
