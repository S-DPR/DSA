const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
2157번 여행

가중치가 있는 그래프가 주어진다.
m개 이하의 노드만을 이동하여 n으로 이동할 때, n에서의 최대 가중치를 구해보자.
단, 이동한 노드는 반드시 오름차순이여야한다.

그래프 당연히 맞다고 생각했었는데 시간초과나는거보고 ?? 하다가
분류보고 이게 왜 다이나믹이야?? 했다가 접었던 문제
다시보니까 갑자기 방법이 떠올라서 풀었습니다.

'갱신이 된 값'일 때 DP를 새로 갱신할 자격(?)을 갖게되는.. 그런류.
간선 자체에 조건에 안맞는 (예를들어 3->2로 가는) 녀석들은 애초에 거르고,
이미 있는 간선이면 최댓값만 주워먹고.
이렇게해서 그래프를 받고..
그거로 DP를 채워가는 부류입니다.

그래프문제라고 무작정 BFS 생각할게아니라 조건보고 DP가 된다는거도 알게되었네요. 참..
*/
let [n, m, k] = input[0].split(" ").map(Number)
let G = []
for (let i = 0; i <= n; i++)
    G[i] = new Map()
for (let i = 1; i <= k; i++) {
    let [x, y, w] = input[i].split(" ").map(Number)
    if (x > y) continue
    if (G[x].has(y)) G[x].set(y, Math.max(G[x].get(y), w))
    else G[x].set(y, w)
}

let dp = []
for (let i = 0; i <= n; i++) {
    dp[i] = []
    for (let j = 0; j <= m; j++)
        dp[i][j] = -1
}

dp[1][1] = 0
for (let i = 1; i < n; i++) {
    for (let j = 1; j < m; j++) {
        if (dp[i][j] === -1) continue
        G[i].forEach((weight, next) => {
            dp[next][j+1] = Math.max(dp[next][j+1], dp[i][j]+weight)
        })
    }
}
console.log(Math.max(...dp[n]))