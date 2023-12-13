const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
24545번 Y

트리가 주어진다. 여기서 K개의 노드를 제거해 아래 조건에 맞도록 고치려한다.
트리의 크기는 트리를 이루는 노드의 개수라 할 때, 최대 어느정도의 크기가 될 수 있을까?
 - 4개 이상의 간선을 갖는 노드는 없다.
 - 1개의 간선을 갖는 노드는 정확히 3개있다.
 - 3개의 간선을 갖는 노드는 정확히 1개있다.

트리dp인줄알고 막 갈궈보다가 30%부근에서 안되길래 다시 생각해보니..
"이거 그냥 트리지름 하나 구하고 거기서 제일 먼 거리 구하면 되지않나?"
그런데 이게 확신이 없어서 그냥 답지보고 확신생기면 구현해야지, 했는데,
해설지에 덤으로 들어가서 다행히 확신갖고 구현해서 풀었습니다.

휴.. 어려워따..
*/
let dfs = (x, d) => {
    V[x] = true
    let [N, D] = [x, d]
    G[x].forEach(i => {
        if (!V[i]) {
            p[i] = x
            let [u, v] = dfs(i, d+1)
            if (D < v) {
                N = u
                D = v
            }
        }
    })
    return [N, D]
}

let N = ini()
let [G, V] = [[], Array(N+1).fill(false)]
for (let i = 0; i <= N; i++)
    G[i] = []
for (let i = 1; i < N; i++) {
    let [u, v] = ins()
    G[u].push(v)
    G[v].push(u)
}
let p = Array(N+1).fill(-1)
let [f, _] = dfs(1, 0)
V = Array(N+1).fill(false)
let [s, d] = dfs(f, 0)
let [Q, lQ] = [[], []]
while (s != f) {
    Q.push(s)
    s = p[s]
}
Q.push(s)
let dist = Array(N+1).fill(1<<30)
dist[0] = 0
Q.forEach(i => dist[i] = 0)
while (Q.length + lQ.length > 0) {
    if (Q.length == 0) {
        while (lQ.length > 0) {
            Q.push(lQ.pop());
        }
    }
    let x = Q.pop()
    G[x].forEach(i => {
        if (dist[x]+1 < dist[i]) {
            dist[i] = dist[x]+1
            lQ.push(i)
        }
    })
}
let mx = Math.max(...dist)
console.log(mx == 0 ? 0 : d+mx+1)
