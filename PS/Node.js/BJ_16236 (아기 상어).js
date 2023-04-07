const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
16236번 아기 상어

0과 1~6, 9로 이루어진 맵이 주어진다. 9는 1개만 주어짐이 보장된다.
1~6은 사이즈가 T인 물고기가 있는 칸이고, 0은 빈 칸이다.
아기 상어는 처음에 9의 위치에 있고 자신보다 작은 크기의 물고기를 먹을 수 있으며,
크기가 S일 때 S마리의 물고기를 먹으면 S+1크기로 자란다.
아기 상어의 처음 크기는 2이고, 자신보다 큰 물고기가 있는 칸은 지나갈 수 없다.
또한, 택시택거리로 가장 가까운 거리에 있는 물고기를 먹으러가며,
그런 물고기가 여러개라면 가장 위에, 그것도 여러개라면 가장 왼쪽에 있는 물고기를 선호한다.
아기상어가 물고기를 최대한 먹을 때 걸리는 시간을 구하여라. 아기상어는 1초에 상하좌우 1칸씩 움직일 수 있다.

그냥 30분동안 슥슥 하면 풀리는문제
문제 안읽어서그렇지, 제대로 읽었으면 그냥 10분만에 슥슥 하고 끝났을거같네요.
역시 BFS 골드 중하위 문제는 쉽습니다.

그냥 아기상어 위치 0으로 만들고 BFS 돌리고, 그동안 조건에 맞는 물고기 좌표비교하면서 얻어내면 됩니다.
그럼 끝나요. 진짜로. PQ까지 필요없는 쉬운문제였습니다.
*/
let N = Number(input[0])
let M = []
for (let i = 1; i <= N; i++)
    M.push(input[i].split(" ").map(Number))

let Q = []
for (let i = 0; i < N; i++)
    for (let j = 0; j < N; j++)
        if (M[i][j] === 9) Q.push([j, i, 2, 0])

let result = 0
let dx = [1, -1, 0, 0]
let dy = [0, 0, 1, -1]
while (true) {
    let [x, y, size, eat] = Q.shift()
    M[y][x] = 0
    let eatGoing = [N+1, N+1, 1_000_000_000]
    let QQ = [[x, y, 0]]
    let visit = []
    for (let j = 0; j < N; j++)
        visit.push(Array(N).fill(false))
    while (QQ.length > 0) {
        let [curX, curY, time] = QQ.shift()
        for (let i = 0; i < 4; i++) {
            let [nx, ny, nTime] = [curX+dx[i], curY+dy[i], time+1]
            let [eatX, eatY, eatT] = eatGoing
            if (eatT < nTime) break
            if (!(0 <= nx && nx < N && 0 <= ny && ny < N)) continue
            if (size < M[ny][nx]) continue
            if (visit[ny][nx]) continue
            if (M[ny][nx] > 0 && M[ny][nx] < size) {
                if (ny < eatY) eatGoing = [nx, ny, nTime]
                if (ny === eatY && nx < eatX) eatGoing = [nx, ny, nTime]
            }
            visit[ny][nx] = true
            QQ.push([nx, ny, time+1])
        }
    }
    let [eatX, eatY, T] = eatGoing
    if (eatX === N+1) break
    result += T
    Q.push([eatX, eatY, size+Math.floor((eat+1)/size), (eat+1)%size])
}
console.log(result)