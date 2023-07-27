/*
1562번 계단 수

길이가 N이고 숫자가 0부터 9까지 모두 나오며,
모든 수가 인접한 수와 1만큼 차이나는 수들의 개수를 구해보자.

웬일로 빨리풀었냐.. 했더니 북마크에 없던 문제였지..

저는 비트dp로 했는데, 이게 이런것같네요.
2차원 : 포함배제 필요
3차원 : bitDP 필요
4차원 : 딱히 필요한거 없음, 그냥 순수dp

dp[i][j][k] = 길이가 i이고, 끝 수가 j이며, 현재 방문한 수가 k(2진수)인.
그냥 너무 무난하게풀어서 딱히 쓸 말도 없네..
*/
let M = 1_000_000_000
let N = Int(readLine()!)!
var dp = [[[Int]]](repeating: [[Int]](repeating: [Int](repeating: 0, count: 1024), count: 10), count: (N+1))
for i in 1..<10 {
    dp[1][i][1<<i] = 1
}
if N > 1 {
    for n in 2...N {
        for i in 0..<10 {
            for j in 0..<1024 {
                if i > 0 { dp[n][i][j|(1<<i)] += dp[n-1][i-1][j] }
                if i < 9 { dp[n][i][j|(1<<i)] += dp[n-1][i+1][j] }
                dp[n][i][j|(1<<i)] %= M
            }
        }
    }
}
print(dp[N].reduce(0) { ($0 + $1[1023])%M })
