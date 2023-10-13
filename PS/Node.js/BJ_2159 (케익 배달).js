const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let idx = 0
let ini = () => Number(input[idx++])
let ins = () => input[idx++].split(" ").map(Number)
/*
2159번 케익 배달

현재 (x, y)가 주어진다.
순서대로 (x, y)로 이루어진 정점을 방문할건데,
그 좌표와 상하좌우로 인접한 칸에 가면 방문했다고 칠 생각이다.
모든 정점을 방문하는데 몇 칸을 이동해야할까?

와 이게 dp네
그리디+많은조건분기 때려넣다가 포기하고 태그 보니까 막 나쁜 생각이 들고..

dp[i][j] = i번째 정점을 갈 때 j번째 d로 이동한 경우.
그냥 그대로 간단하게 루프돌리면 됩니다.
아무래도 앞으론 그리디는 제일 뒤로 미루고 dp를 먼저 고려해야겠어요..
*/
let dx = [0, 1, -1, 0, 0]
let dy = [0, 0, 0, 1, -1]
let loop = (idx, d) => {
    if (idx == N) return 0
    if (dp[idx][d] != -1) return dp[idx][d]
    dp[idx][d] = INF
    let x = items[idx][0]+dx[d]
    let y = items[idx][1]+dy[d]
    for (let i = 0; i < 5; i++) {
        let nx = items[idx+1][0]+dx[i]
        let ny = items[idx+1][1]+dy[i]
        dp[idx][d] = Math.min(
            dp[idx][d],
            loop(idx+1, i, nx, ny) + Math.abs(nx-x) + Math.abs(ny-y)
        )
    }
    return dp[idx][d]
}

let INF = 2**60
let N = ini()
let [cx, cy] = ins()
let items = [[cx, cy]]
let dp = []
for (let i = 1; i <= N; i++)
    items[i] = ins()
for (let i = 0; i <= N; i++)
    dp[i] = Array(5).fill(-1)
console.log(loop(0, 0))