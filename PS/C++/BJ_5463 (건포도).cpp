#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
using namespace std;
/*
5463번 건포도

초콜릿을 쪼갤 때, 쪼개기 전 해당 조각에 있는 건포도의 개수만큼의 비용이 든다.
모든 초콜릿을 1*1크기로 쪼개려한다. 이 때 최소비용은 얼마일까?

"시간복잡도가 애매하다면 혼자 이상한 언어로 삽질하지 말고 C++로 갈아탈것"
어디선가 얻은 교훈. 그때도 O(NsqrtN)에 N이 200만이 돌아가는 세상 억울한 상황이었는데.
저번엔 Ruby라 그러려니 했는데 이번엔 Swift에 당했네. Rust는 어제 썼고.

어쨌든 그냥 파일합치기입니다. 그런데 그게 2차원이고, 시간복잡도가 O(N^6)이라는 세상 무서운.
파일합치기처럼 반복문으로 굴리려고 했었는데, 머리가 쪼개지는 엄청난 경험을 하고나서 재귀로 도망쳤습니다.

아 몰라. 역시 재귀가 최고야!
*/
int INF = 1 << 29;
int Y, X;
int pf[51][51];
int dp[51][51][51][51];

int sum(int sx, int sy, int ex, int ey) {
    return pf[ey][ex] - pf[ey][sx - 1] - pf[sy - 1][ex] + pf[sy - 1][sx - 1];
}

int loop(int sx, int sy, int ex, int ey) {
    if (sx > X || sy > Y || ex > X || ey > Y) return 0;
    if (sx == ex && sy == ey) return 0;
    if (dp[sy][sx][ey][ex]) return dp[sy][sx][ey][ex];
    dp[sy][sx][ey][ex] = INF;
    int S = sum(sx, sy, ex, ey);
    for (int i = sy; i < ey; i++)
        dp[sy][sx][ey][ex] = min(dp[sy][sx][ey][ex], S + loop(sx, sy, ex, i) + loop(sx, i+1, ex, ey));
    for (int i = sx; i < ex; i++)
        dp[sy][sx][ey][ex] = min(dp[sy][sx][ey][ex], S + loop(sx, sy, i, ey) + loop(i+1, sy, ex, ey));
    return dp[sy][sx][ey][ex];
}

int main() {
    fast;
    cin >> Y >> X;
    for (int i = 1; i <= Y; i++) {
        for (int j = 1; j <= X; j++) {
            cin >> pf[i][j];
            pf[i][j] = pf[i][j] + pf[i - 1][j] + pf[i][j - 1] - pf[i - 1][j - 1];
        }
    }
    cout << loop(1, 1, X, Y);
}
