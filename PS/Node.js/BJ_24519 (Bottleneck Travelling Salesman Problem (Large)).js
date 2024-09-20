const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
const inf = 1<<29
let __idx = 0
/*
24519번 Bottleneck Travelling Salesman Problem (Large)

모든 정점을 한번씩 방문하고 돌아오는 최단거리 대신 지난 정점의 최대 가중치를 최소로 하는 경로를 찾아보자.

첨엔 매개변수탐색으로했는데..
아니 생각해보니까 그냥 dp[i][j]를 i번째에서 j비트만큼 방문했을 때 최솟값으로 하면 딱 한방에 되더라고요?
현타 조금 왔습니다 솔직히..
*/
const loop = (x, vis) => {
    if (vis+1 == 1<<N) return G[x][0] == -1 ? inf : G[x][0]
    if (dp[x][vis] != -1) return dp[x][vis]
    dp[x][vis] = inf
    for (let i = 0; i < N; i++) {
        if ((vis&(1<<i)) != 0) continue
        if (G[x][i] == -1) continue
        let ret = Math.max(loop(i, vis|(1<<i)), G[x][i])
        if (ret < dp[x][vis]) {
            dp[x][vis] = ret
            V[x][vis] = i
        }
    }
    return dp[x][vis]
}

let [N, M] = ins()
let G = Array(N).fill(0).map(() => Array(N).fill(-1))
for (let i = 0; i < M; i++) {
    let [u, v, w] = ins()
    G[u-1][v-1] = w
}
let dp = Array(N).fill(0).map(() => Array(1<<N).fill(-1))
let V = Array(N).fill(0).map(() => Array(1<<N).fill(-1))
loop(0, 1)
if (dp[0][1] == inf) {
    console.log(-1)
} else {
    let ret = `${dp[0][1]}\n1 `
    let cur = V[0][1]
    let vis = 1
    while (vis+1 != 1<<N) {
        ret += `${cur+1} `
        vis |= 1<<cur
        cur = V[cur][vis]
    }
    console.log(ret)
}
