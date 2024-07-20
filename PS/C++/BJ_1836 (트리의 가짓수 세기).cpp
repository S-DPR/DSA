#include <iostream>
#include <algorithm>
#include <string.h>
#define ll long long
using namespace std;
/*
1836번 트리의 가짓수 세기

노드 개수가 N개, 높이가 K, 모든 노드는 0개 또는 2개의 자식노드를 갖는 트리의 개수를 구해보자.

O(NK)로 푼 문제.
난이도 기여 보는데 딱 맞는 말이 있더라고요.
'높이가 K인 트리의 개수가 아니라, K가 아닌 트리의 개수 구하기가 핵심'
옛날엔 감도 안잡혔는데 오랜만에 보니 조금 눈이 트인거같기도하고..
한시간정도 써서 푼 것 같습니다. 60~90분?
*/
// dp[i][j][k] = 노드 n개로 현재 높이 j에서 K층까지 못가는/가는 경우의수
int N, K, dp[201][101][2];
int MOD = 9901;
pair<int, int> item[] = {{0, 1}, {1, 0}, {1, 1}};

int loop(int n, int k, int x) {
    if (n < 0) return 0;
    if (k == K) return n == 1 && x != 0;
    if (!x && n == 1) return 1;
    if (dp[n][k][x] != -1) return dp[n][k][x];
    int &cur = dp[n][k][x];
    cur = 0;
    for (int i = 1; i < n-1; i++) {
        if (!x) {
            cur += loop(i, k+1, 0) * loop(n-i-1, k+1, 0);
            cur %= MOD;
            continue;
        }
        for (auto &[u, v]: item) {
            cur += loop(i, k+1, u) * loop(n-i-1, k+1, v);
            cur %= MOD;
        }
    }
    return cur;
}

int main() {
    scanf("%d %d", &N, &K);
    memset(dp, -1, sizeof dp);
    printf("%d", loop(N, 1, 1));
}
