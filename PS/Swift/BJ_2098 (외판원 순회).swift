/*
2098번 외판원 순회

도시 1에서 출발해서 N개의 모든 도시를 방문하고 다시 도시 1로 돌아오는 최단거리를 구해보자.
단, 방문한 도시는 다시 방문할 수 없다.

비트마스킹DP 근-본넘치는 문제
그냥 보면 그래프같아보이는게 묘미.

지금 북마크에 비트마스킹DP가 5개쯤 쌓여있어서 풀어봤습니다. 아닌가? 한갠가? 어쨌든요.
단어..어쩌구하는 비트마스킹 dp 푼적은 있는데 좀 생각해보며 활용한건 이게 처음이네요.
시간복잡도는 O(N*2^N), 공간복잡도는 O(2^N)입니다.

dp[i][j] : i번째 도시에서 j의 비트중 켜져있는 것들을 방문했을 때 앞으로 가야하는 최소거리.
즉 dp[0][0]은 0번 도시에서 모든 도시를 방문했을 때 가야하는 최소거리가 되어, 이게 답이 됩니다.
*/
let INF = 1_000_000_000
func loop(_ G: [[Int]], _ dp: inout [[Int]], _ visit: Int, _ cur: Int) -> Int {
    let N = G.count
    if visit == (1<<N)-1 { return G[cur][0] == 0 ? INF : G[cur][0] }
    if dp[cur][visit] != -1 { return dp[cur][visit] }
    dp[cur][visit] = INF
    for i in 0..<N {
        if visit&(1<<i) != 0 { continue }
        if i == 0 && visit != (1<<i)-2 { continue }
        if G[cur][i] == 0 { continue }
        let nxtV = visit|(1<<i)
        dp[cur][visit] = min(dp[cur][visit], loop(G, &dp, nxtV, i) + G[cur][i])
    }
    return dp[cur][visit]
}
let N = Int(readLine()!)!
var G = [[Int]]()
for _ in 0..<N {
    G.append(readLine()!.split(separator: " ").map {Int($0)!})
}
var dp = [[Int]](repeating: [Int](repeating: -1, count: 1<<N), count: N)
print(loop(G, &dp, 1, 0))
