const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
15681번 트리와 쿼리

루트가 R인 트리가 주어진다. N번 노드가 주어질 떄, 이 노드의 서브노드의 개수를 구해보자.

플레5풀려다가 머리 깨지고,
골드3풀려다가 머리 깨지고,
결국 골드5..

그냥 트리dp 기초중의 기초입니다. dfs 한번 생각없이 굴리고 출력.
그동안 본 트리dp에 비해 너무 쉽네요..
아니 문젠 트리랑 dp만풀다보니 다른거 다 까먹었어 못풀겠어
*/
function dfs(cur) {
    if (V[cur]) return 0
    V[cur] = true
    dp[cur] = 1
    G[cur].forEach(i => {
        dp[cur] += dfs(i)
    })
    return dp[cur]
}

let [N, R, Q] = input[0].split(" ").map(Number)
let G = []
for (let i = 0; i <= N; i++)
    G.push([])
for (let i = 1; i <= N-1; i++) {
    let [u, v] = input[i].split(" ").map(Number)
    G[u].push(v)
    G[v].push(u)
}
let dp = Array(N+1).fill(0)
let V = Array(N+1).fill(false)
dfs(R)
let ret = ""
for (let i = N; i <= N+Q-1; i++) {
    ret += `${dp[Number(input[i])]}\n`
}
console.log(ret)
