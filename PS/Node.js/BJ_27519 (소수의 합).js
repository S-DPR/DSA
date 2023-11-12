const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
27519번 소수의 합

수 i를 소수의 합으로 나타내는 경우의 수를 구해보자.
나타낸 수가 같고 순서만 다른 경우는 같은 경우이다.
한번만 하긴 재미업으니 T번해보자.

골드4에 동전1 + 에라토스테네스의 체
둘을 합치니까 골드2..?
사실 동전1이 진짜 난이도가 높은 기본dp긴한데 좀 많이 뛴거같기도하고..
오랜만에 오일러의 체 구현하고 dp 싹 굴려준 뒤 테케 다 처리해주면 됩니다.
*/
let MAX = 100_000
let MOD = 1_000_000_007
let dp = Array(MAX+1).fill(0)
let div = [...Array(MAX+1).keys()]
let prime = []
for (let i = 2; i <= MAX; i++) {
    if (div[i] == i) prime.push(i)
    for (let idx = 0; idx < prime.length; idx++) {
        let j = prime[idx]
        if (i*j > MAX) break
        div[i*j] = j
        if (i%j == 0) break
    }
}
dp[0] = 1
prime.forEach(i => {
    for (let j = i; j <= MAX; j++) {
        dp[j] += dp[j-i]
        dp[j] %= MOD
    }
})
let ret = ""
let T = ini()
for (let i = 0; i < T; i++) {
    ret += `${dp[ini()]}\n`
}
console.log(ret)
