const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*

*/
let [N, K] = ins()
let A = []
for (let i = 0; i <= 10; i++) {
    A.push([])
}
for (let i = 0; i < N; i++) {
    let [c, k] = ins()
    A[k].push(c)
}
for (let i = 0; i <= 10; i++) {
    A[i].sort((i, j) => j-i)
    A[i].unshift(0)
    for (let j = 1; j < A[i].length; j++) {
        A[i][j] += (j-1)*2+A[i][j-1]
    }
}
let prv = Array(K+1).fill(-1)
prv[0] = 0
for (let i = 0; i <= 10; i++) {
    let dp = prv.map(i => i)
    for (let k = 1; k < A[i].length; k++) {
        for (let j = K; j-k >= 0; j--) {
            if (prv[j-k] == -1) continue
            dp[j] = Math.max(dp[j], prv[j-k]+A[i][k])
        }
    }
    prv = dp
}
console.log(prv[K])
