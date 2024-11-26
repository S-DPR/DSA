const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
const inf = 1<<29
let __idx = 0
/*
15559번 내 선물을 받아줘

SWEN으로 구성된 맵이 주어진다.
밟은 칸이 S는 아래, W는 왼쪽, E는 오른쪽, N은 위쪽으로 가게된다고 할 때,
사이클의 개수를 구하시오.

보자마자 유니온파인드 박고 AC.
쉬운문제.
*/
const union = (u, v) => {
    let [up, vp] = [find(u), find(v)]
    if (up == vp) return false
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

let [R, C] = ins()
let M = Array(R).fill(0).map(() => input[__idx++].split(""))
let U = Array(R*C).fill(0).map((_, i) => i)
let direction = {
    'S': (r, c) => [r+1, c],
    'W': (r, c) => [r, c-1],
    'E': (r, c) => [r, c+1],
    'N': (r, c) => [r-1, c]
}
for (let r = 0; r < R; r++) {
    for (let c = 0; c < C; c++) {
        let [nr, nc] = direction[M[r][c]](r, c)
        if (!(0 <= nr && nr < R)) continue
        if (!(0 <= nc && nc < C)) continue
        union(r*C+c, nr*C+nc)
    }
}
let ret = {}
for (let r = 0; r < R; r++) {
    for (let c = 0; c < C; c++) {
        ret[find(r*C+c)] = true
    }
}
console.log(Object.keys(ret).length)
