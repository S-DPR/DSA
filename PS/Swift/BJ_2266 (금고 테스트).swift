/*
2266번 금고 테스트

K개의 금고가 있고, N층 빌딩이 있다.
금고는 어떤 층 F에서 떨어질경우 깨진다.
단, F층 미만에서 떨어뜨릴경우 꺠지지 않는다.
이 때, F가 0~N+1중 어떤 값이든 상관없이 최대 X번 떨어뜨려 F를 구하려 한다.
X의 최솟값을 구해보자.

대체 어떻게 푸는고, 하고 몇달 박아둔 문제.
그런데 역시 DP할당제탓인지, 이거도 DP네..

이분탐색인줄 알았는데 신기하게도 DP.
그런데 수가 작아서 DP가 O(N^3)으로 굴러가는거지, 이분탐색이 logN이 나오나봅니다.
매개변수는 대체 어떻게 하란건지 모르겠어서 도망쳤지만..

dp[i][j] = i가 N이고 j가 K일 때 가능한 최솟값.
이제 구간dp 잘 굴려주면 됩니다.
구현은 진짜 10분걸린거같네..
*/
let I = readLine()!.split(separator: " ").map {Int($0)!}
let N = I[0], K = I[1]
var dp = [[Int]](repeating: [Int](repeating: -1, count: K+1), count: N+1)

func loop(_ N: Int, _ K: Int) -> Int {
    if N == 0 { return 0 }
    if K == 0 { return 1<<30 }
    if dp[N][K] != -1 { return dp[N][K] }
    dp[N][K] = 1<<30
    for i in 1...N {
        dp[N][K] = min(dp[N][K], max(loop(i-1, K-1), loop(N-i, K))+1)
    }
    return dp[N][K]
}

print(loop(N, K))
