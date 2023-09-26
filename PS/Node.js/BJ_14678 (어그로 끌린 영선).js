const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
14678번 어그로 끌린 영선

트리가 있다.
왼발로 트리의 어느 지점에 발을 딛고, 왼발과 오른발을 번갈아가며 트리를 이동한다 하자.
이미 밟았던 곳은 못 돌아간다고 규칙을 정하고, 트리의 리프노드에서 왼발을 딛고 서려고 한다.
어떤 한 노드에서 시작하면 그 경우의수가 최대가 될 것이다. 그 경우의 수를 구해보자.

재미있는 DFS문제
요즘 최단경로 빼면 BFS를 푸는 일이 엄청 적어진 것 같네요.

우선 트리를 색칠해줍니다. 이분그래프니까요.
리프노드에서는 자기자신에서 1을 뺀 값을, 아니면 자기 색을 답과 비교해줍니다.
그리고 결과를 출력에 넣어주면 됩니다.

아, 1이면 그냥 1로 출력해줍니다. 이거때매 틀렸네..
*/
let dfs = (cur, color) => {
    if (G[cur].length == 1) {
        isEnd[cur] = true
        cnt[color]++
    }
    depth[cur] = color
    G[cur].forEach(i => {
        if (depth[i] == -1) dfs(i, color^1)
    })
}

let N = ini()
let depth = Array(N+1).fill(-1)
let isEnd = Array(N+1).fill(false)
let cnt = [0, 0]
let G = []
for (let i = 0; i <= N; i++) G[i] = []
for (let i = 1; i < N; i++) {
    let [u, v] = ins()
    G[u].push(v)
    G[v].push(u)
}
dfs(1, 0)
let ret = 0
for (let i = 1; i <= N; i++) {
    if (isEnd[i]) {
        ret = Math.max(ret, cnt[depth[i]]-1)
    } else {
        ret = Math.max(ret, cnt[depth[i]])
    }
}
console.log(N == 1 ? 1 : ret)
