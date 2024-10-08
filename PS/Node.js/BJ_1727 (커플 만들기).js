const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
const inf = 1<<29
let __idx = 0
/*
1727번 커플 만들기

두 배열이 있다.
크기가 더 작은 배열의 모든 원소를 큰 배열의 원소와 매칭시킬 때,
매칭된 원소의 차의 합이 최소가 되게하려한다.
최솟값은 몇일까?

그냥 뭐 대충 dp인가~
하고 그냥 슥 탑다운.
탑다운은 신이고 무적이다
*/
const loop = (n, m) => {
    if (n == A.length) return 0
    if (n > A.length || m >= B.length) return 1<<30
    if (dp[n][m] != -1) return dp[n][m]
    dp[n][m] = Math.min(loop(n+1, m+1)+Math.abs(A[n]-B[m]), loop(n, m+1))
    return dp[n][m]
}

const [N, M] = ins()
let A = ins().sort((i, j) => i-j)
let B = ins().sort((i, j) => i-j)
if (N > M) {
    let tmp = A
    A = B
    B = tmp
}
const dp = Array(1001).fill(0).map(() => Array(1001).fill(-1))
console.log(loop(0, 0))
