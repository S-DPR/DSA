const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let idx = 0
let ini = () => Number(input[idx++])
let ins = () => input[idx++].split(" ").map(Number)
/*
24439번 바리스타의 힘

맵이 주어진다. 자신이 서있는 곳을 중심으로, 한 방향의 벽을 모두 없앨 수 있다.
단, 이 능력은 한 번만 사용 가능하다. 좌측 상단에서 우측 하단으로 최단경로로 가는 거리를 구해보자.
갈 수 없다면 -1을 출력하자.

엄청 쉬운 P5네 ㅋㅋ 하고 북마크에 넣었다가 기록적인 16번째 제출에 정답.
그저께 멘탈 탈탈 털리다가 오늘 문제 다시 잡으니 알거같더라고요.
역시 사람은 휴식기를 조금 가져야해..

상태를 4개 가집니다. 능력 안쓴거, 가로로 쓴거, 세로로 쓴거, 능력 쓴거.
저는 이걸 다익스트라로 했다가 메모리 멸망의 길을 걸었습니다.
음.. BFS는 진짜 저게 끝이라 더 할말이 없네.
*/
let [R, C] = ins()
let [M, V] = [[], []]
for (let i = 0; i < R; i++) {
    M.push(Array.from(input[idx++].trim()).map(Number))
    V.push([])
    for (let j = 0; j < C; j++)
        V[i].push(Array(4).fill(false))
}
let ret = -1
let dx = [1, -1, 0, 0]
let dy = [0, 0, 1, -1]
let lQ = []
let Q = [[0, 0, 2, 0]]
V[0][0][0] = true
while (Q.length+lQ.length > 0) {
    if (Q.length === 0)
        while (lQ.length > 0)
            Q.push(lQ.pop())
    let [x, y, a, t] = Q.pop()
    if (x == C-1 && y == R-1) {
        ret = t
        break
    }
    for (let i = 0; i < 4; i++) {
        let [nx, ny] = [x+dx[i], y+dy[i]]
        let newA = a
        if (!(0 <= nx && nx < C)) continue
        if (!(0 <= ny && ny < R)) continue
        if (M[ny][nx] === 1) {
            if (newA === 2 || newA === i/2)
                newA = i/2
            else continue
        }
        else if (newA !== 2 && newA !== i/2) newA = 3
        if (V[ny][nx][newA]) continue
        V[ny][nx][newA] = true
        lQ.push([nx, ny, newA, t+1])
    }
}
console.log(ret)
