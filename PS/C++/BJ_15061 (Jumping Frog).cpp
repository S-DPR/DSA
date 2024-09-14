#include <iostream>
#include <algorithm>
#include <numeric>
#include <string.h>
using namespace std;
/*
15061번 Jumping Frog

길이가 N인 문자열이 주어진다.
1~N-1만큼의 점프력으로 점프를 하려한다. 즉, 현재 위치가 s면 다음 위치는 (s+j)%N이다.
이 때, P를 밟지 않고 시작접으로 돌아올 수 있는 점프력은 몇 개일까?
단, 시작점은 어디든지 상관없다.

음..
플레4가 너무 쉽네?

10만 이하에서 약수가 제일 많은수가 200개정도 됩니다.
그래서 10만*200이 된다는점을 감안하고..
주기성이 점프력과 문자열 길이의 gcd라는점에 착안.
gcd로 처리해주고 방문한건 재방문하지 않게 메모이제이션해주면 됩니다.
*/
char S[100001];
int dp[100001];

int main() {
    scanf("%s", &S);
    int N = strlen(S);
    memset(dp, -1, sizeof dp);
    int ret = 0;
    for (int i = 1; i < N; i++) {
        int g = gcd(i, N);
        if (dp[g] != -1) {
            ret += dp[g];
            continue;
        }
        dp[g] = 0;
        for (int s = 0; s < i; s++) {
            if (S[s] == 'P') continue;
            int returned = 0;
            int res = 1;
            int cur = s;
            while (true) {
                res &= S[cur] == 'R';
                if (returned && cur == s) {
                    ret += res;
                    break;
                }
                cur = (cur + i) % N;
                returned |= cur == s;
            }
            if (res) {
                dp[g] = 1;
                break;
            }
        }
    }
    printf("%d", ret);
}
