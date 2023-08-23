const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let ini = (i) => Number(input[i])
let ins = (i) => input[i].split(" ").map(Number)
let idx = 0
let MOD = 1_000_000_007n
/*
25182번 청정수열 (Hard)

1부터 N까지 수가 2번 나오는 수열이 있다.
수열을 적절히 섞어, 수열의 점수가 최대가 되게 하려한다.
수열의 점수란, A[i] = A[j]인 두 수 i, j (i < j)에 대해 A[i]부터 A[j]까지 더한 뒤, A[i]를 곱한 값을 의미한다.
수열의 최대 점수가 몇인지, 그 개수는 몇개인지 구해보자.

1부터 5까지 완전탐색 굴려서 규칙 찾고 맞춘 문제.
난이도 기여 가서 발상 보니까 놀랍던데..
어떻게 그런발상을 할까..
*/
let N = ini(idx)
let score = 0n
let curSum = 0n
let count = 1n
for (let i = 1n; i <= N; i++) {
    curSum = (curSum+i*2n) % MOD
    score = (score+curSum*i) % MOD
    count = (count*i) % MOD
}
console.log(Number(score), Number((count*count)%MOD))
