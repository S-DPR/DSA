const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
const inf = 1<<29
let __idx = 0
/*
32203번 연락

N명의 성별이 남자는 홀수, 여자는 짝수로 주어진다.
모임이 M개 있다. 이 모임에서는 반드시 번호 교환이 일어난다.
u와 v가 번호를 교환하고 v와 w가 번호를 교환했으면, u와 w도 번호를 교환했다고 칠 때,
각 모임마다 번호를 교환한 남자와 여자 쌍의 개수를 구해보자.

대충 문제 읽어보니 어려워보여서 손절치려고했는데,
생각해보니 그냥 유니온파인드문제..
각 모임마다 여자와 남자의 개수를 센 뒤 둘을 곱하여 처리합니다.
이미 한 집합인 경우 값을 업데이트하지 않습니다.
그러면 AC. 쉽네..
*/
const union = (u, v) => {
    let [up, vp] = [find(u), find(v)]
    if (up == vp) return false
    if (S[vp] < S[up]) [up, vp] = [vp, up]
    S[up] += S[vp]
    M[up] += M[vp]
    U[vp] = up
    return true
}

const find = (u) => {
    while (U[u] != u) {
        U[u] = U[U[u]]
        u = U[u]
    }
    return U[u]
}

let [N, Q] = ins()
let A = [0].concat(ins().map(i => i&1))
let U = Array(N+1).fill(0).map((_, idx) => idx)
let S = Array(N+1).fill(1)
let M = A.map(i => i)
let R = 0
let str = ""
while (Q--) {
    let [u, v] = ins()
    let [up, vp] = [find(u), find(v)]
    let prvU = (S[up]-M[up])*M[up]
    let prvV = (S[vp]-M[vp])*M[vp]
    if (union(u, v)) {
        up = find(u)
        R -= prvU+prvV
        R += (S[up]-M[up])*M[up]
    }
    str += `${R}\n`
}
console.log(str)
