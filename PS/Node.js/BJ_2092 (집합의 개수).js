const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
2092번 집합의 개수

수열 A에서 K개의 수를 뽑아보자.
이 때 나올 수 있는 조합의 수를 T_K개라고 하자.
T_L + T_L+1 + ... + T_R-1 + T_R의 값을 구해보자.

으음..
솔직히 못풀어서 그냥 답지보고 따라쳤습니다.
시간없기도하고.. 문제는 재밌어보이고..
이거 왜 이렇게되는지는.. 공부 좀 해봐야겠는데요.
dp.. 너무어렵다..
*/
let [T, A, L, R] = ins()
let arr = ins()
let cnts = Array(T+1).fill(0)
let dp = []
arr.forEach(i => cnts[i]++)
for (let i = 0; i <= T; i++)
    dp.push(Array(A+1).fill(0))
for (let i = 1; i <= T; i++) {
    for (let k = 1; k <= cnts[i]; k++)
        dp[i][k]++
    for (let j = 1; j <= A; j++) {
        dp[i][j] += dp[i-1][j]
        for (let k = 1; k <= cnts[i]; k++) {
            if (j-k <= 0) continue
            dp[i][j] = (dp[i][j]+dp[i-1][j-k])%1_000_000
        }
    }
}
let ret = 0
for (let i = L; i <= R; i++) {
    ret = (ret+dp[T][i])%1_000_000
}
console.log(ret)
