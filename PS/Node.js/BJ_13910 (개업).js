const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
13910번 개업

수 N과 수열 K가 주어진다.
수열 K의 서로 다른 인덱스에 있는 두 수를 하나로 묶어 만든 새로운 배열을 M이라고 하자.
K와 M에서 적절히 수를 가져와 N을 만들어보자. 그리고 가져오는 수를 최소화해보자.
만약 N을 만들 수 없다면 -1을 출력하자.

시험이라서 빨리 골드5풀고 공부해야지, 했는데,
과거의 나에게 이끌려서 도전을 수락했다가 30분이 증발했습니다.
그때는 dp로한 기억이 있는데 이건 수열 최대 길이가 100이길래 브루트포스도 되나? 에 시간을 갈았습니다..

뭐.. 그냥 동전처럼 푸는 dp입니다. 아..
*/
let [N, M] = input[0].split(" ").map(Number)
let arr = input[1].split(" ").map(Number)
let dp = Array(N+1).fill(1_000_000)
for (let i = 0; i < M; i++) {
    for (let j = i + 1; j < M; j++)
        dp[arr[i] + arr[j]] = 1
    dp[arr[i]] = 1
}
for (let i = 2; i <= N; i++)
    for (let j = 0; j <= i/2; j++)
        dp[i] = Math.min(dp[i], dp[j]+dp[i-j])
console.log(dp[N] < 1_000_000 ? dp[N] : -1)