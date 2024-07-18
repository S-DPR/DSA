const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
const inf = 1<<30
let __idx = 0
/*
22954번 그래프 트리 분할

그래프가 주어진다. 이 그래프에서 원하는대로 간선을 제거할 수 있다.
서로 다른 크기의 두 트리로 그래프를 분할해보자.

솔직히 문제 아이디어는 간단하다
그냥 대충 MST든 뭐든 만들고 노드 하나 똑 떼면 되니까..
그런데 아마 반례때문에 난이도가 오른거같네요.

우선.. N = 2거나 컴포넌트가 3개 이상이면 무조건 -1. 여기에 분리집합쓰고,
컴포넌트가 두개면 DFS트리 구현해서 사용 간선이랑 정점 얻고 처리.
하나면 dfs트리 구현한 뒤 끝쪽 노드 하나 떼서 트리 두개 구현.
이러면 끝납니다.
*/
const union = (u, v) => {
    const [up, vp] = [find(u), find(v)]
    if (up == vp) return false
    U[up] = U[vp]
    return true
}

const find = (u) => {
    if (U[u] != u) U[u] = find(U[u])
    return U[u]
}

const dfs = (x, v, n, e) => {
    n.push(x)
    v[x] = true
    G[x].forEach(([nn, ni]) => {
        if (!v[nn]) {
            e.push(ni)
            dfs(nn, v, n, e)
        }
    })
    return [n, e]
}

const [N, K] = ins()
const [G, Gi] = [[], []]
const U = []
for (let i = 0; i <= N; i++) {
    G[i] = []
    U[i] = i
}
for (let i = 0; i < K; i++) {
    const [u, v] = ins()
    G[u].push([v, i+1])
    G[v].push([u, i+1])
    Gi[i+1] = [u, v]
    union(u, v)
}
const T = Array(N+1).fill(0)
for (let i = 1; i <= N; i++) {
    T[find(i)]++
}
const cnt = T.reduce((acc, cur) => acc+Math.min(1, cur), 0)
let ret = ""
if (cnt >= 3 || N <= 2) {
    ret += `-1\n`
} else if (cnt == 2) {
    const cnt = [...Object.values(T.slice(1, N+1).reduce((acc, curr) => {
        acc[curr] = (acc[curr] || 0) + 1;
        return acc;
    }, {}))]
    if (cnt[0] == cnt[1]) {
        ret += `-1\n`
    } else {
        const vis = Array(N+1).fill(false)
        const [ret_n, ret_e] = [[], []]
        for (let i = 1; i <= N; i++) {
            if (vis[i]) continue
            const [n, e] = dfs(i, vis, [], [])
            ret_n.push(n)
            ret_e.push(e)
        }
        ret += `${ret_n[0].length} ${ret_n[1].length}\n`
        for (let i = 0; i < 2; i++) {
            ret_n[i].forEach(j => {
                ret += `${j} `
            })
            ret += "\n"
            ret_e[i].forEach(j => {
                ret += `${j} `
            })
            ret += "\n"
        }
    }
} else {
    ret += `1 ${N-1}\n`
    const [n, e] = dfs(1, Array(N+1).fill(false), [], [])
    const indep = Array(N+1).fill(0)
    e.forEach(i => {
        const [u, v] = Gi[i]
        indep[u]++
        indep[v]++
    })
    for (let i = 1; i <= N; i++) {
        if (indep[i] != 1) continue
        ret += `${i}\n\n`
        for (let j = 1; j <= N; j++) {
            if (i == j) continue
            ret += `${j} `
        }
        ret += `\n`
        e.forEach(j => {
            const [u, v] = Gi[j]
            if (u != i && v != i) {
                ret += `${j} `
            }
        })
        break
    }
}
console.log(ret)
