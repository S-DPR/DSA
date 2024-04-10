const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
1109번 섬

x가 땅일 때, x의 8방으로 이을 수 있는 최대 집합을 섬이라고 하자.
섬은 내부에 섬을 포함할 수 있고, 이 경우 섬의 높이는 (내부 섬 최대 높이)+1이다.
내부에 섬이 없는 섬은 높이가 0일 때,
각 섬들의 높이를 0이 a개, 1이 b개, ..., M이 m개. 이런식으로 출력하자.

구현 그래프, 전형적인 풀기 싫은 문제
아니 이거 개꿀아닌가 하고 했다가 dfs bfs라는데 어버버 거리다가 때려치고,
그냥 0-1bfs, dfs 2번으로 풀었습니다.
파이썬으로 1000바이트.. 압축 좀 풀면 한 1500~2000바이트 나올만한 코드 있던데 그거 봐야겠어요.
전 좀 복잡하게 푼 것 같더라고요.

각 섬 높이 구하고 (문제내용과 반대로 몇 번째 내부 섬인지로),
그거를 뒤집고,
개수를 출력했으니..
*/
const dr = [1, -1, 0, 0, 1, 1, -1, -1]
const dc = [0, 0, 1, -1, 1, -1, 1, -1]
let [R, C] = ins()
let M = []
for (let i = 0; i < R; i++) {
    M.push(input[__idx++].trim().split(""))
}
let [Q, lQ] = [[], []]
let height = []
for (let r = 0; r < R; r++) {
    height.push(Array(C).fill(1<<30))
    const arr = [0, C-1]
    arr.forEach(c => {
        const isX = M[r][c] == 'x'
        const time = isX ? 1 : 0
        const targetQ = time == 1 ? Q : lQ
        height[r][c] = time
        targetQ.push([r, c, time, isX])
    })
}
for (let c = 0; c < C; c++) {
    const arr = [0, R-1]
    arr.forEach(r => {
        const isX = M[r][c] == 'x'
        const time = isX ? 1 : 0
        const targetQ = time == 1 ? Q : lQ
        height[r][c] = time
        targetQ.push([r, c, time, isX])
    })
}
while (Q.length + lQ.length > 0) {
    if (Q.length == 0) while (lQ.length > 0) Q.push(lQ.pop())
    let [r, c, t, isX] = Q.pop()
    if (height[r][c] != t) continue
    for (let i = 0; i < 8; i++) {
        let [nr, nc] = [r+dr[i], c+dc[i]]
        if (!(0 <= nr && nr < R)) continue
        if (!(0 <= nc && nc < C)) continue
        let nxtX = M[nr][nc] == 'x'
        let nxtT = nxtX && !isX ? t+1 : t
        if (height[nr][nc] <= nxtT) continue
        if (!(i < 4 || (isX && nxtX && i >= 4))) continue
        height[nr][nc] = nxtT
        let targetQ = nxtT == t ? Q : lQ
        targetQ.push([nr, nc, nxtT, nxtX])
    }
}
let [pos, ret] = [[], []]
let [revHeight, vis] = [[], []]
for (let i = 0; i <= R*C; i++) {
    pos.push([])
    ret.push(0)
}
for (let r = 0; r < R; r++) {
    revHeight.push(Array(C).fill(0))
    vis.push(Array(C).fill(false))
    for (let c = 0; c < C; c++) {
        pos[height[r][c]].push([r, c])
    }
}
let revHeightDFS = (or, oc, r, c, h) => {
    for (let i = 0; i < 8; i++) {
        let [nr, nc] = [r+dr[i], c+dc[i]]
        if (!(0 <= nr && nr < R)) continue
        if (!(0 <= nc && nc < C)) continue
        if (height[nr][nc] != h-1) continue
        if (revHeight[nr][nc] >= revHeight[or][oc]+1) continue
        revHeight[nr][nc] = revHeight[or][oc]+1
        revHeightDFS(or, oc, nr, nc, h)
    }
}
let dfs = (r, c) => {
    vis[r][c] = true
    for (let i = 0; i < 8; i++) {
        let [nr, nc] = [r+dr[i], c+dc[i]]
        if (!(0 <= nr && nr < R)) continue
        if (!(0 <= nc && nc < C)) continue
        if (M[nr][nc] != 'x') continue
        if (vis[nr][nc]) continue
        dfs(nr, nc)
    }
}
for (let h = R*C; h >= 0; h--) {
    pos[h].forEach(i => {
        let [r, c] = i
        revHeightDFS(r, c, r, c, h)
    })
}
let mx = -1
for (let r = 0; r < R; r++) {
    for (let c = 0; c < C; c++) {
        if (M[r][c] != 'x') continue
        mx = Math.max(mx, revHeight[r][c])
        if (vis[r][c]) continue
        ret[revHeight[r][c]]++
        dfs(r, c)
    }
}
let str = ""
for (let i = 0; i <= mx; i++) {
    str += `${ret[i]} `
}
console.log(mx == -1 ? -1 : str)
