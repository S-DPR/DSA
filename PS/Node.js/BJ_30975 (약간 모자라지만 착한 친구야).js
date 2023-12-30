const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
30975번 약간 모자라지만 착한 친구야

N+1번 노드에서 출발해 1~N번 노드를 한번씩 방문한 뒤, N+1로 돌아올 때 최단거리를 구해보자.
단, i번 노드를 방문하려면 P[i]번 노드가 방문되어져 있어야 한다.
만약 i == P[i]일 경우 선행노드가 없다는 뜻이다.

핫하 정말 쉬운 외판원문제군! 하고 들어왔다가,
10분만에 풀거를 또 문제 제대로 안읽어서 한시간..
어휴..

그냥 외판원에다가 if문 단 한줄! 추가하면 됩니다.
간단해요. 그냥 쭉 풀었네요.
*/
let loop = (x, v) => {
    if (v+1 == 1<<(N+1)) return 0
    if (dp[x][v] != -1) return dp[x][v]
    dp[x][v] = 1<<30
    for (let i = 0; i <= N; i++) {
        if (G[x][i] == 1<<30) continue
        if ((v&(1<<i)) != 0) continue
        if (P[i] != i && (v&(1<<P[i])) == 0) continue
        if (i == 0 && v+2 != 1<<(N+1)) continue
        dp[x][v] = Math.min(dp[x][v], loop(i, v|(1<<i))+G[x][i])
    }
    return dp[x][v]
}

let [N, K] = ins()
let P = [0, ...ins()]
let G = []
let dp = []
for (let i = 0; i <= N; i++)
    G[i] = Array(N+1).fill(1<<30)
for (let i = 0; i <= N; i++) {
    dp[i] = Array(1<<(N+1)).fill(-1)
}
for (let i = 0; i < K; i++) {
    let [u, v, w] = ins()
    G[u%(N+1)][v%(N+1)] = Math.min(G[u%(N+1)][v%(N+1)], w)
}
let ret = loop(0, 0)
console.log(ret == 1<<30 ? -1 : ret)
