const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
1981번 배열에서 이동

0부터 200까지 적힌 N*N 배열이 주어진다.
1, 1에서 이동해 N, N까지 갈 때 최댓값-최솟값의 최솟값을 구해보자.

예에전에 이분탐색 태그 보고 현타와서 관뒀다가 다시 풀려는데,
풀려고보니 안돼서 결국 답지보고 풀었습니다.
세상엔 천재가 많고 저는 바본가봅니다..

수의 크기가 0~200밖에 안됩니다.
그래서 매개변수탐색으로 최댓값-최솟값을 잡아보고, 그 값을 i라고 한다면
0~i, 1~1+i, 2~2+i, ..., 이 발판들만 밟으면서 N, N까지 갈 수 있나 테스트를 해봅니다.
결과에 따라 적당히 잘 좁히고.. 그러다가 나온 right값이 답이겠죠.

와.. 진짜 코악귀들 무섭네.. 이게 골드1이었다고?
*/
function bfs(N, M, diff) {
    for (let t = 0; t <= 200; t++) {
        let visit = []
        for (let i = 0; i < N; i++) {
            visit[i] = Array(N)
            visit[i].fill(false)
        }
        let dx = [1, -1, 0, 0]
        let dy = [0, 0, 1, -1]
        if (!(t <= M[0][0] && M[0][0] <= t+diff)) continue
        let Q = [[0, 0]]
        while (Q.length > 0) {
            let [curX, curY] = Q.shift()
            if (curX === N - 1 && curY === N - 1) return true
            for (let i = 0; i < 4; i++) {
                let [nx, ny] = [curX + dx[i], curY + dy[i]]
                if (!(0 <= nx && nx < N && 0 <= ny && ny < N)) continue
                if (!(t <= M[ny][nx] && M[ny][nx] <= t+diff)) continue
                if (visit[ny][nx]) continue
                Q.push([nx, ny])
                visit[ny][nx] = true
            }
        }
    }
    return false
}

let N = Number(input[0])
let M = []
for (let i = 1; i <= N; i++) {
    let item = input[i].split(" ").map(Number)
    M.push(item)
}
let left = 0, right = 200
while (left <= right) {
    let mid = (left + right) >> 1
    if (bfs(N, M, mid))
        right = mid - 1
    else
        left = mid + 1
}
console.log(right+1)