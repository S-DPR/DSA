const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
const inf = 1<<30
let __idx = 0
/*
2632번 피자판매

원형 수열이 두 개 주어진다.
각 원형 수열에서 연속한 수들을 가져온다고 해보자.
그 수들의 합이 N이 되게 하려 한다.
가능한 경우의 수를 구하시오.

음..
어렵진않지 확실히
근데 음..
살짝 어.. 이상하게 시간 날린 느낌이네요

그냥 대충 pf 해주고 map으로 처리하면 끝납니다.
이거 실버라인업 문제아닌가..
*/
let N = ini()
let [n, m] = ins()
let [A, B] = [[0], [0]]
let [Am, Bm] = [{0: 1}, {0: 1}]
for (let i = 0; i < n; i++) {
    A.push(ini())
}
for (let i = 0; i < m; i++) {
    B.push(ini())
}
let AS = A.reduce((acc, cur) => acc+cur, 0)
let BS = B.reduce((acc, cur) => acc+cur, 0)
A = A.concat(A.slice(1, n+1))
B = B.concat(B.slice(1, m+1))
for (let i = 1; i <= n+n; i++) A[i] += A[i-1]
for (let i = 1; i <= m+m; i++) B[i] += B[i-1]
for (let i = 1; i < n; i++) {
    for (let j = 0; j < n; j++) {
        let sum = A[i+j]-A[j]
        if (!Am[sum]) Am[sum] = 0
        Am[sum]++
    }
}
for (let i = 1; i < m; i++) {
    for (let j = 0; j < m; j++) {
        let sum = B[i+j]-B[j]
        if (!Bm[sum]) Bm[sum] = 0
        Bm[sum]++
    }
}
if (!Am[AS]) Am[AS] = 0
if (!Bm[BS]) Bm[BS] = 0
Am[AS]++
Bm[BS]++
let ret = 0
Object.entries(Am).forEach(([k, v]) => {
    if (Bm[N-k]) {
        ret += v*Bm[N-k]
    }
})
console.log(ret)
