const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
23818번 원수의 원수

세 사람의 관계는 아래 세개로 나타낼 수 있다.
1. A와 B가 원수관계이고 B와 C가 원수관계이면 A와 C는 친구관계이다.
2. A와 B가 원수관계이고 B와 C가 친구관계이면 A와 C는 원수관계이다.
3. A와 B가 친구관계이고 B와 C가 친구관계이면 A와 C는 친구관계이다.
각 사람들의 관계가 주어진다. 이후 Q개의 쿼리가 주어진다. 모두 처리해보자.
A B : A와 B의 관계를 출력한다. 아무 관계도 없으면 Unknown, 친구이기도 하고 원수이기도 하면 Error, 친구면 Friend, 원수면 Enemy이다.

이야..
이거 웬만한 플레5보다 어려운거같은데..

이분그래프에서 실수가 많이 나오는것같아요.
대놓고 0이나 1로 표현을 해야함에도 이분그래프임을 눈치채지 못했고,
분리집합 하나로 어떻게 해보려다 멸망..

dfs는 관계표시에, union-find는 그 관계 표시를 빠르게 찾는데 사용했습니다.
에러관련 유니온파인드도 만들어서 에러일 경우 0이랑 같은 집합이 되게 해주었고..
음.. 이렇게보면 너무 쉬운데.... 정말..
왜 생각 못했을까..
*/
const union = (U, u, v) => {
    let up = find(U, u)
    let vp = find(U, v)
    if (up == vp) return false
    let mn = Math.min(up, vp)
    let mx = Math.max(up, vp)
    U[mx] = U[mn]
    return true
}

const find = (U, u) => {
    if (U[u] != u) {
        U[u] = find(U, U[u])
    }
    return U[u]
}

const dfs = (x, c) => {
    color[x] = c
    G[x].forEach(i => {
        let [nxtN, nxtC] = i
        union(C, x, nxtN)
        union(E, x, nxtN)
        if (color[nxtN] == -1) {
            dfs(nxtN, c^nxtC)
        } else if (nxtC != color[x]^color[nxtN]) {
            union(E, x, 0)
        }
    })
}

let [N, M, Q] = ins()
let [G, E, C, color] = [[], [], [], []]
for (let i = 0; i <= N; i++) {
    G.push([])
    E.push(i)
    C.push(i)
    color.push(-1)
}
for (let i = 0; i < M; i++) {
    let [c, u, v] = ins()
    G[u].push([v, c])
    G[v].push([u, c])
}
for (let i = 1; i <= N; i++) {
    if (color[i] == -1) dfs(i, 0)
}
let ret = ""
for (let i = 0; i < Q; i++) {
    let [u, v] = ins()
    let isComponent = find(C, u) == find(C, v)
    let isError = find(E, u)*find(E, v) == 0
    if (!isComponent) ret += "Unknown\n"
    else if (isError) ret += "Error\n"
    else ret += color[u]^color[v] == 1 ? "Enemy\n" : "Friend\n"
}
console.log(ret)
