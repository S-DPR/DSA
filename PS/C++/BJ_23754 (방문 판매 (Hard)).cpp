#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
#define INF 1000000000
using namespace std;
/*
23754번 방문 판매 (Hard)

N명의 고객과, M개의 고객 방문 순서와, 팔아야하는 목표치 X, Y가 주어진다.
이후 M개의 줄에 u, v의 형식으로 수 두 개가 주어진다. u를 방문해야 v를 방문할 수 있다는 의미이다.
이후 N개의 줄에 x, y의 형색으로 수 두 개가 주어진다. i번째 고객은 판매에 성공하면 x, y개를 구매한다는 뜻이다.
이 때, 적어도 몇 명에게 팔아야하는지, 가장 빠르게 목표치를 달성할 때 마지막으로 구매하는 사람이 누구인지 구해보자.

에엣날에 이거 그래프로구나 했다가 머리에 구멍 송송 뚫리고 도망친 문제.
그 때 태그봐서 DP라는건 알고있었는데, 배낭까지 있었을줄은 몰랐네요. 풀면서 배낭라이크문제인데? 하긴했는데.
플레 4라서 오랜만에 C++ 굴려봤습니다.

그런데 풀면서 문제를 제대로 안본 탓에 스무고개하듯이 문제를 풀었습니다..
앞으로는 문제 제대로 읽기로합시다.
푸는 방법은 그냥 dp[i][j] = X를 i개, Y를 j개 팔았을 때 몇 명에게 팔았는지+마지막으로 산 사람이 누구인지를 적어두면 됩니다.
고객의 순서는 위상정렬+우선순위 큐 굴리면 됩니다. 사실 위상정렬 특성상 그냥 큐 써서 해도 되지만..

오늘의 교훈 : C++의 기본 priority_queue는 최대힙이다.
*/
struct info {
    int x = 0, y = 0;
};
struct sellInfo {
    int cnt = INF, last = INF;
};
vector<vector<int>> G;
sellInfo sell[401][401];
int dep[401];
info inf[401];
int vis[401];
int n, m, x, y;
int retCnt = INF, last = INF;

int main() {
    fast;
    int visitCnt = 0;
    priority_queue<int> visit;
    cin >> n >> m >> x >> y;
    G.resize(n+1);
    while (m--) {
        int u, v; cin >> u >> v;
        G[u].push_back(v);
        dep[v]++;
    }
    for (int i = 1; i <= n; i++)
        cin >> inf[i].x >> inf[i].y;
    sell[0][0] = sellInfo{ 0, 0 };
    while (true) {
        if (visit.empty())
            for (int i = 1; i <= n; i++)
                if (!dep[i]) visit.push(-i), dep[i] = -1;
        if (visit.empty()) break;
        int cur = -visit.top(); visit.pop();
        int curX = inf[cur].x, curY = inf[cur].y;
        vis[cur] = ++visitCnt;
        for (int i : G[cur]) dep[i]--;
        for (int i = x; i >= 0; i--) {
            for (int j = y; j >= 0; j--) {
                if (sell[i][j].cnt == INF) continue;
                int nxtX = i + curX, nxtY = j + curY;
                int nxtCnt = sell[i][j].cnt+1;
                int prvCnt = sell[nxtX][nxtY].cnt;
                if (nxtCnt < prvCnt || (nxtCnt == prvCnt && vis[cur] < vis[sell[nxtX][nxtY].last]))
                    sell[nxtX][nxtY] = sellInfo{ nxtCnt, cur };
            }
        }
    }
    for (int i = x; i <= 400; i++) {
        for (int j = y; j <= 400; j++) {
            if (sell[i][j].cnt == INF) continue;
            if (sell[i][j].cnt > retCnt) continue;
            if (sell[i][j].cnt < retCnt) {
                retCnt = sell[i][j].cnt;
                last = sell[i][j].last;
            }
            else if (vis[sell[i][j].last] < vis[last]) {
                last = sell[i][j].last;
            }
        }
    }
    if (retCnt == INF || visitCnt != n) cout << -1;
    else cout << retCnt << endl << last;
}
