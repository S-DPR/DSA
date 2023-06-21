const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
3109번 빵집

x = 0에서 x = C-1로 최대한 많이 보냈을 때 최대 몇 개를 보낼 수 있나 구해보자.
현재가 (r, c)일 때 맵을 벗어나지 않는 선에서 (r-1, c+1), (r, c+1), (r+1, c+1)로만 이동이 가능하다.
또, 해당 위치가 x이면 갈 수 없다.
마지막으로, 어떤 점을 목적지까지 보냈을 때 그 경로는 다른 점이 이동할 때 사용할 수 없다.

dfs인건 옛날에 태그 봐서 알았는데 이게 그리디도 있네
그런데 dp는 없네??

전에 DP로 풀었다가 WA로 두들겨맞고 도망친 문제.
왜 dp로 안되는가 했는데, 목적지가 여러개네요..
DAG가 아니죠 그러면. dp로는 못푸는게 맞았습니다.

일단 디폴트아이디어는 최대한 위쪽으로 붙여서 보내기.
그리고 이미 해당 위치가 불가능함을 알았다면 불가능이라고 딱지에 써붙이기.
이 두 아이디어를 dfs에서 구현하면 AC입니다.
*/
let dr = [-1, 0, 1]
function dfs(r, c) {
    if (!V[r][c]) return false
    if (c === C-1) {
        M[r][c] = 'x'
        return true
    }
    for (let i = 0; i < 3; i++) {
        let [nr, nc] = [r+dr[i], c+1]
        if (!(0 <= nr && nr < R)) continue
        if (M[nr][nc] === 'x') continue
        let ret = dfs(nr, nc)
        if (!ret) continue
        M[r][c] = 'x'
        return true
    }
    V[r][c] = false
    return false
}

let [R, C] = input[0].split(" ").map(Number)
let M = []
let V = []
for (let i = 1; i <= R; i++) {
    M.push(input[i].trim().split(""))
    V.push(Array(C).fill(true))
}
for (let i = 0; i < R; i++)
    dfs(i, 0)
let ret = 0
for (let i = 0; i < R; i++)
    if (M[i][C-1] === 'x') ret++
console.log(ret)