const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
11066번 파일 합치기

수열 A가 있다. 이 A에서 서로 인접한 두 수를 합치려 한다.
단, 합칠 때마다 두 수의 합만큼 비용이 발생한다.
A의 원소가 하나가 될 때까지 합칠 때 최소 비용을 출력하라.

정말 오랫동안 잡은 문제
솔루션 봐도 이해가 안되서 내가 뛴다 한 문제
후..

dp[i][j]를 정의합시다. A의 i번째 수부터 j번째 수까지 병합하는 최소 비용입니다.
이제 그 값을 계산할건데, [1~i-1] ~ [i~j]번째를 합칠 때 최소 비용을 구합니다.
예를들어, [1번 ~ 2~i번], [1~2번 ~ 3~i번], ..., [1~i-1번 ~ i번]을 병합할 때 든 최소 비용을 합치는 방법입니다.
[dp[1][1] + dp[i][3]], [dp[2][1] + dp[i][3]]... 이런 위치에 저장되어있겠죠.
그 두 값을 더한 뒤, 그 부분에 대해 누적합을 또 더해줘야합니다.

참.. 이게 글로만 쓰기 뭐하네요. 근데 표를 그리긴 귀찮으니 쓰진 않을겁니다.
2번째 예제를 i번째까지 최소비용을 구하면 어떻게 되나.. 를 엑셀 키고 직접 해보는게 큰 도움이 되었습니다.
어쨌든 풀어서 다행이다..
*/
let T = Number(input[0])
for (let i = 1; i <= T*2; i += 2) {
    let N = Number(input[i])
    let arr = input[i+1].split(" ").map(Number)
    let dp = []
    let prefix = [0]
    for (let _ = 0; _ < N; _++)
        dp.push(Array(N).fill(1_000_000_000))
    for (let j = 0; j < N; j++) {
        prefix.push(prefix[prefix.length-1]+arr[j])
        dp[j][j] = 0
        for (let k = j-1; k >= 0; k--) {
            for (let [p, t] = [1, k]; p+k <= j; p++, t++) {
                let item = dp[k][t] + dp[k+p][j] + prefix[j+1] - prefix[k]
                dp[k][j] = Math.min(dp[k][j], item)
            }
        }
    }
    console.log(dp[0][N-1])
}