const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
13003번 배수열

1부터 N까지의 수를 사용하고 길이가 L인 경우의 수를 구해보자.
단, 수열은 아래 조건을 만족해야한다.
1. 수열은 비 내림차순이다.
2. 수열의 모든 수는 서로 배수관계이다.

대충 dp 긁어주면 AC.
바텀업도 될거같은데 이건 아무리봐도 재귀가 GOAT.
대충대충 합시다. 골드4따리 난이도같은데 왜 골드2에있지?
*/
let loop = (n, l) => {
    if (l == 0) return 1
    if (dp[n][l] != -1) return dp[n][l]
    dp[n][l] = 0
    for (let i = n; i <= N; i += n) {
        dp[n][l] = (dp[n][l] + loop(i, l-1)) % MOD
    }
    return dp[n][l]
}

let MOD = 1_000_000_007
let [N, L] = ins()
let dp = []
for (let i = 0; i <= N; i++) {
    dp.push(Array(L+1).fill(-1))
}
console.log(loop(1, L))
