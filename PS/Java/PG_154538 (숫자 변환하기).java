/*
PG_154538 숫자 변환하기

아래 3개 연산을 최소한으로 사용해 x를 y로 바꾸려고 합니다.
최소 연산 횟수를 구해주세요.
 - k에 3을 곱합니다.
 - k에 2를 곱합니다.
 - k에 n을 더합니다.

아 1로만들기 아시는구나!!!!!!!!!!!!!
정말정말어렵습니다!!!!!!!!!!!!!!
*/
class Solution {
    public int solution(int x, int y, int n) {
        int INF = 1 << 30;
        int[] dp = new int[y+1];
        for (int i = 0; i < y; i++)
            dp[i] = INF;
        for (int i = y; i >= x; i--) {
            if (i%3 == 0) dp[i/3] = Math.min(dp[i/3], dp[i]+1);
            if (i%2 == 0) dp[i/2] = Math.min(dp[i/2], dp[i]+1);
            if (i >= n) dp[i-n] = Math.min(dp[i-n], dp[i]+1);
        }
        return dp[x] == INF ? -1 : dp[x];
    }
}