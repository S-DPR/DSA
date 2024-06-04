const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
12872번 플레이리스트

아래 조건을 만족하는 수열의 개수를 구해보자.
1. 모든 수는 N 이하의 자연수이다.
2. 같은 수는 적어도 M의 간격을 두고 나온다.
3. N 이하의 모든 자연수가 나와야한다.

왜맞음? 진짜모름

거의 머 1년에 추가로 반년은 더 묵힌 문제인데 드디어..
옛날에 GPT한테 '해줘' 했더니 진짜 몇초만에 해줘버려서 허탈했던 기억이 있네요
그나마 그때는 3차원이었는데 푼건 2차원이라는점..?

나름 주석도 썼는데 왜 동작하는진 정확히 모르겠습니다.
Proof by AC 아몰라~
*/
const mod = (x, y) => (x+y)%1_000_000_007

const loop = (p, n) => {
    if (p < 0 || n < 0) return 0
    if (dp[p][n] != -1) return dp[p][n]
    dp[p][n] = 0
    if (n >= M) dp[p][n] = mod(dp[p][n], loop(p-1, n)*(n-M)) // M 이전에 쓴거 이번에 쓰기
    if (N-n >= 0) dp[p][n] = mod(dp[p][n], loop(p-1, n-1)*(N-n+1)) // 아직 안쓴 종류 갖다 쓰기
    return dp[p][n]
}
let [N, M, P] = ins()
let dp = [] // dp[p][n] = p번째까지 n개의 노래를 넣은 경우의 수
for (let i = 0; i <= P; i++) {
    dp.push(Array(N+1).fill(-1))
}
dp[0][0] = 1
console.log(loop(P, N))
