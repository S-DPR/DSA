const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
30870번 사이클 없는 그래프 만들기

사이클이 있는 그래프가 주어진다.
그리고 K개의 정점이 주어진다. T = 1이면 이 정점들이 사라진다.
T > 1의 경우, T-1에서 제거된 정점과 이어진 정점들이 사라진다.
정점이 사라질 떄는 그 정점과 이어진 모든 간선이 사라진다.
이 때, T가 몇이어야 그래프에서 사이클이 사라질까?

와
할게 있기도 하고 잘 모르겠어서 태그 깠는데 이게 매개변수네
매개변수 진짜 가끔 튀어나와서 갑자기 괴롭히고가..

일정 T를 지나면 그래프에 사이클이 사라진 상태가 유지되므로,
매개변수탐색의 조건에 맞습니다.
그래서 매개변수 걸고 정점 제거한 뒤 dfs로 사이클체크하면 AC.
사이클체크 코드도 슬슬 외워야겠네요. 은근 나오는거같아..
*/
let dfs = (x, prv, vis, rm) => {
    vis[x] = true
    let ret = false
    G[x].forEach(nxtN => {
        if (!rm[nxtN] && prv != nxtN) {
            ret = ret || vis[nxtN]
            if (!vis[nxtN]) {
                ret = ret || dfs(nxtN, x, vis, rm)
            }
        }
    })
    return ret
}

let [N, M, K] = ins()
let G = []
for (let i = 0; i <= N; i++) {
    G[i] = []
}
for (let i = 0; i < M; i++) {
    let [u, v] = ins()
    G[u].push(v)
    G[v].push(u)
}
let f = ins()
let [lo, hi] = [1, N]
while (lo < hi) {
    let mid = (lo + hi) >> 1
    let rm = Array(N+1).fill(false)
    let qvis = Array(N+1).fill(false)
    let [Q, lQ] = [[], []]
    f.forEach(i => lQ.push(i))
    for (let i = 0; i < mid; i++) {
        while (lQ.length > 0) Q.push(lQ.pop())
        while (Q.length > 0) {
            let cur = Q.pop()
            rm[cur] = true
            G[cur].forEach(nxtN => {
                if (!qvis[nxtN]) {
                    qvis[nxtN] = true
                    lQ.push(nxtN)
                }
            })
        }
    }
    let isCycle = false
    let vis = Array(N+1).fill(false)
    for (let i = 1; i <= N; i++) {
        if (!vis[i] && !rm[i]) {
            isCycle = isCycle || dfs(i, -1, vis, rm)
        }
    }
    if (!isCycle) {
        hi = mid
    } else {
        lo = mid + 1
    }
}
console.log(hi)
