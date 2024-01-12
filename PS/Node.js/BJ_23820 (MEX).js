const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
23820번 MEX

길이가 N인 수열 A에서, A_i*A_j꼴로 나타낼 수 없는 가장 작은 음이 아닌 자연수를 출력해보자.
A의 원소는 200만을 넘지 않는다.

아
아
아
아... 반례찾기 실력 +1..

그냥.. 이런건 200만을 넘는 가장 작은 소수인 200만+3을 생각하고,
조화수열 시간복잡도가 NlogN인거 이용해서 풀면 되는 쉬운 문제인데..
0 예외처리를 제대로 안해서 많이틀렸다..
난바보야..
*/
let MAX = 2_000_003
let N = ini()
let A = ins()
let B = Array(MAX+1).fill(false)
let C = Array(MAX+1).fill(0)
A.forEach(i => C[i] = 1)
for (let i = 1; i <= MAX; i++) {
    if (C[i] == 0) continue
    for (let j = 1; i*j <= MAX; j++) {
        if (C[j] == 0) continue
        B[i*j] = true
    }
}
if (C[0] == 0) {
    console.log(0)
} else {
    for (let i = 1; i <= MAX; i++) {
        if (B[i]) continue
        console.log(i)
        break
    }
}
