const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
10653번 마라톤 2

N개의 체크포인트가 있다.
1번 체크포인트부터 N번 체크포인트까지 뛰어갈건데, 최대 K개의 체크포인트를 건너 뛸 수 있다.
체크포인트의 촤표가 x y꼴로 N개 주어진다. N에 도착할 때 최단거리를 구해보자.
(1 <= N <= 500, K < N)

아니 님들아 막 이게 루비가 막 똑같이 해도 시간초과를 막 내고 막 그런거에요 진짜 막 억울해서 못살아

.. 어쨌든 자바스크립트로 하니까 가뿐하게 통과했습니다. 대체 루비는 어떤 언어일까..
플레 5에 맞고, 골드 2에도 맞고, 골드 3으로 도망쳐서 얻은 문제입니다.
보고나서 한 수학문제가 생각났는데, 그건 잊도록 하고.

O(NKK)로 풀면 됩니다. for문 하나는 현재 위치를, 하나는 현재까지 스킵한 횟수를, 하나는 이번에 스킵할 횟수를 나타냅니다.
그냥 그거로 dp 돌리면 됩니다. 간단하죠.
*/
let INF = 1<<30
let [N, K] = input[0].split(" ").map(Number)
let coord = []
for (let i = 1 ; i <= N; i++)
    coord.push(input[i].split(" ").map(Number))
let dp = []
for (let i = 0 ; i <= K; i++)
    dp.push(Array(N).fill(INF))
dp[0][0] = 0
let dist = (i, j) => { return Math.abs(i[0]-j[0])+Math.abs(i[1]-j[1]) }
for (let i = 0; i < N; i++) {
    for (let j = 0; j <= K; j++) {
        for (let k = 0; k <= K; k++) {
            if (i+k+1 >= N || j+k > K) break
            dp[j+k][i+k+1] = Math.min(dp[j+k][i+k+1], dp[j][i]+dist(coord[i], coord[i+k+1]))
        }
    }
}
let result = INF
for (let i = 0; i <= K; i++)
    result = Math.min(result, dp[i][N-1])
console.log(result)