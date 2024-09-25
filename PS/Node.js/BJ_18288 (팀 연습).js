const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
const inf = 1<<29
let __idx = 0
/*
18288번 팀 연습

세 사람이 문제를 아래 규칙으로 풀려고 한다.
문제를 푸는 순서의 경우의 수가 어떻게될까?
1. 문제는 1번부터 순서대로 푼다.
2. 문제는 한 명만 푼다.
3. 최종적으로 A는 K의 배수개, B는 연속적이지 않게, C는 한 문제 이상 풀어야한다.

4중dp. 그냥 싹 긁어버리면 끝나죠.
그리고 마지막에 A가 0인지, C가 1문제 이상 풀었는지만 확인해주면 됩니다.
간단한 dp.
*/
const MOD = 1_000_000_007
const loop = (i, j, k, l) => {
    if (i == N) return j == 0 && l == 1 ? 1 : 0
    if (dp[i][j][k][l] != -1) return dp[i][j][k][l]
    dp[i][j][k][l] = 0
    if (K != 0) {
        dp[i][j][k][l] = loop(i+1, (j+1)%K, 0, l)%MOD
    }
    if (k == 0) {
        dp[i][j][k][l] = (dp[i][j][k][l]+loop(i+1, j, 1, l))%MOD
    }
    dp[i][j][k][l] = (dp[i][j][k][l]+loop(i+1, j, 0, 1))%MOD
    return dp[i][j][k][l]
}

let [N, K] = ins()
let dp = []
// dp[i][j][k][l] = i번째 문제에서 A가 j개 풀었고 B가 이전에 문제를 풀었고 C가 문제를 풀었는지
for (let i = 0; i < N; i++) {
    dp.push([])
    for (let j = 0; j <= K; j++) {
        dp[i].push([])
        for (let k = 0; k <= 1; k++) {
            dp[i][j].push([-1, -1])
        }
    }
}
console.log(loop(0, 0, 0, 0))
