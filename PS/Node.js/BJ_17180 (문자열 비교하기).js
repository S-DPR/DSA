const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
17180번 문자열 비교하기

문자열 S, T가 주어진다.
문자열 S, T에는 특수 연산을 적용할 수 있는데, S, T 내부 한 문자를 잡아 옆으로 복사할 수 있다.
S와 T의 차이를 최소화하려한다. S와 T의 차이란, abs(S[i]-T[i])의 합이 최소가 되게 하는 것이다.
이 때, 최솟값을 구하시오.

대충 보자마자 아 dp구나 싶었던 문제.
정말 정직하게 dp쓰면 됩니다.
dp[i][j] = S에선 i를, T에선 j를 볼 때 최솟값.
탑다운은 신이고 무적이다!
*/
const loop = (u, v) => {
    if (u >= N ||  v >= M) return 1<<30
    const diff = Math.abs(S[u].charCodeAt()-T[v].charCodeAt())
    if (u == N-1 && v == M-1) return diff
    if (dp[u][v] != -1) return dp[u][v]
    dp[u][v] = Math.min(loop(u+1, v), loop(u, v+1), loop(u+1, v+1))+diff
    return dp[u][v]
}

let [N, M] = ins()
let S = input[__idx++].trim()
let T = input[__idx++].trim()
let dp = []
for (let i = 0; i < N; i++) dp[i] = Array(M).fill(-1)
console.log(loop(0, 0))
