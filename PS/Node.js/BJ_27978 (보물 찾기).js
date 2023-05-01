const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
27978번 보물 찾기

맵이 주어진다. K에서 *로 가는 최단거리를 출력해보자. #로는 이동할 수 없다.
인접한 8칸으로 이동 가능하며,
(r, c)에서 (r-1, c+1), (r, c+1), (r+1, c+1)중 하나로 이동한다면 연료가 소모되지 않는다.

오랜만에 보는 정석+꼬임없는 BFS
충격과 공포의 BFS만 만나니 마음이 아팠는데, 그리웠습니다..
*/
let INF = 1_000_000_000
let [r, c] = input[0].split(" ").map(Number)
let V = []
let M = []
let [S, E] = [[], []]
for (let i = 1; i <= r; i++) {
    V.push(Array(c).fill(INF))
    M.push(input[i])
    for (let j = 0; j < c; j++) {
        if (M[M.length - 1][j] === "K")
            S = [j, i-1]
        if (M[M.length - 1][j] === "*")
            E = [j, i-1]
    }
}
V[S[1]][S[0]] = 0
let dr = [1, 1, 1, 0, 0, -1, -1, -1]
let dc = [-1, 0, 1, 1, -1, -1, 0, 1]
let Q = [S]
while (Q.length > 0) {
    let [x, y] = Q.shift()
    if ([x, y] === E) continue
    for (let i = 0; i < 8; i++) {
        let [nx, ny] = [x+dc[i], y+dr[i]]
        let fuel = i === 7 || i === 3 || i === 2 ? V[y][x] : V[y][x] + 1
        if (!(0 <= nx && nx < c)) continue
        if (!(0 <= ny && ny < r)) continue
        if (M[ny][nx] === "#") continue
        if (V[ny][nx] <= fuel) continue
        Q.push([nx, ny])
        V[ny][nx] = fuel
    }
}
console.log(V[E[1]][E[0]] === INF ? -1 : V[E[1]][E[0]])