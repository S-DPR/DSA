const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
28435번 배수 피하기

집합 A의 부분집합 중, 서로 다른 두 원소를 더해 K로 나누어지지 않는 집합의 개수를 구해보자.
단, 집합의 크기는 2 이상이다.

거의 다 풀었는데 점점 멘탈 깎여서 답 본 문제
진짜 거의 다 풀었더라..고요..
아..

그나저나 10^18 안나올줄알았는데 MOD*MOD가 거의 근접해서 JS로는 BigInt 열심히 굴려야하네요.
너무슬퍼..
*/
let [N, K] = ins()
let A = ins()
let M = Array(K).fill(0)
A.forEach(i => {
    M[i%K]++
})
let MOD = 1_000_000_007n
let ret = 1n
for (let i = 1; i<<1 <= K; i++) {
    if (i<<1 == K) {
        ret *= BigInt(M[i])+1n
    } else {
        let x = [M[i], M[K-i]]
        let r = 0n
        x.forEach(j => {
            let k = 1n
            for (let a = 0; a < j; a++) {
                k <<= 1n
                k %= MOD
            }
            r += k
        })
        ret *= r - 1n
    }
    ret %= MOD
}
ret *= BigInt(M[0]) + 1n
console.log(Number((ret-BigInt(N)-1n+MOD)%MOD))
