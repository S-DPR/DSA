const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
23634번 미안하다 이거 보여주려고 어그로 끌었다

0은 상하좌우로 퍼질 수 있다. 2로는 못간다.
합쳐질 수 있는 모든 0이 합쳐지는 순간의 최소 시간과 그때 퍼진 0의 개수를 구해보자.

문제 제목을 보고 절대 지나칠 수가 없었다..
BFS+분리집합인건 대충 눈치챘는데 구현 어떻게하지 하다가 시간 걸린 문제.
BFS한번으로 하려했는데 힘들더라고요. 그냥 BFS한번 유사BFS한번으로 처리했습니다.
size를 '서로 다른 유효한 두 집합'으로 사용한건 처음이었네요.
이런문제 재밌당
*/
let union = (u, v) => {
    size[find(v)] += size[find(u)]
    U[find(u)] = U[find(v)]
}

let find = (x) => {
    if (U[x] != x)
        U[x] = find(U[x])
    return U[x]
}

let dr = [1, -1, 0, 0]
let dc = [0, 0, 1, -1]
let [R, C] = ins()
let [M, V] = [[], []]
for (let i = 0; i < R; i++) {
    let row = input[__idx++].trim().split("").map(Number)
    V.push(Array(C).fill(-1))
    M.push(row)
}
let lQ = []
let Q = []
let visTime = [[]]
for (let r = 0; r < R; r++) {
    for (let c = 0; c < C; c++) {
        if (M[r][c] != 0) continue
        Q.push([r, c, 0])
        visTime[0].push([r, c])
        V[r][c] = 0
    }
}
while (Q.length+lQ.length > 0) {
    if (Q.length == 0) {
        while (lQ.length > 0)
            Q.push(lQ.pop())
    }
    let [r, c, t] = Q.pop()
    for (let i = 0; i < 4; i++) {
        let [nr, nc, nt] = [r+dr[i], c+dc[i], t+1]
        if (!(0 <= nr && nr < R)) continue
        if (!(0 <= nc && nc < C)) continue
        if (M[nr][nc] == 2) continue
        if (V[nr][nc] != -1) continue
        if (!visTime[nt]) visTime[nt] = []
        visTime[nt].push([nr, nc])
        V[nr][nc] = nt
        lQ.push([nr, nc, nt])
    }
}
let U = [...Array(R*C+1).keys()]
let size = Array(R*C+1).fill(0)
let ret = [0, visTime[0].length]
let pf = 0
visTime[0].forEach(item => {
    let [r, c] = item
    size[r*C+c] = 1
})
for (let i = 0; i < visTime.length; i++) {
    pf += visTime[i].length
    visTime[i].forEach(item => {
        let [r, c] = item
        let n = r*C+c
        for (let j = 0; j < 4; j++) {
            let [nr, nc] = [r+dr[j], c+dc[j]]
            let nn = nr*C+nc
            if (!(0 <= nr && nr < R)) continue
            if (!(0 <= nc && nc < C)) continue
            if (M[nr][nc] == 2) continue
            if (V[nr][nc] > i) continue
            if (find(n) != find(nn)) {
                if (size[find(n)]*size[find(nn)] != 0)
                    ret = [i, pf]
                union(n, nn)
            }
        }
    })
}
console.log(ret[0], ret[1])
