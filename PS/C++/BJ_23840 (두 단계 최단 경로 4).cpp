#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define INFI (1<<30)
#define INFL (1LL<<60)
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
using namespace std;
/*
23840번 두 단계 최단 경로 4

그래프가 주어진다.
x에서 출발해, 주어지는 모든 P개의 정점을 거친 후 z로 도착하는 최단경로를 구해보자.

다익스트라 P+1번, 그리고 O((2^P)(P^2))이라는 기적적인..
P가 최대 20인데, 21로 비트DP돌렸다가 시간초과로 엄청 맞았습니다.
이게 재귀가 문제인가했는데, 초기화방법을 for에서 memset으로 바꾸니까 4초정도로 통과.
하아.. 며칠전에 골드3만 풀때는 세상이 밝아보였는데 요즘은 세상이 밉다 진짜
*/
vector<pair<ll, ll>> G[100001];
int pass[21];
int N, M, P, s, e;
ll u, v, w;
ll dist[21][100001];
ll dp[21][1 << 21];

void dij(int idx) {
    priority_queue<pair<ll, ll>> pq;
    pq.push(pair(0, pass[idx]));
    for (int i = 0; i <= N; i++)
        dist[idx][i] = INFL;
    dist[idx][pass[idx]] = 0;
    while (!pq.empty()) {
        ll curN = pq.top().second, curW = -pq.top().first; pq.pop();
        if (dist[idx][curN] < curW) continue;
        for (auto x : G[curN]) {
            ll nxtN = x.first, nxtW = x.second;
            if (nxtW + curW < dist[idx][nxtN]) {
                dist[idx][nxtN] = nxtW + curW;
                pq.push(pair(-dist[idx][nxtN], nxtN));
            }
        }
    }
}

ll loop(int c, int v) {
    if (v + 1 == 1 << P) return dist[c][e];
    if (dp[c][v] != -1) return dp[c][v];
    dp[c][v] = INFL;
    for (int i = 0; i < P; i++) {
        if (v & (1 << i)) continue;
        dp[c][v] = min(dp[c][v], loop(i, v | (1 << i)) + dist[c][pass[i]]);
    }
    return dp[c][v];
}

int main() {
    fast; cin >> N >> M;
    while (M--) {
        cin >> u >> v >> w;
        G[u].push_back(pair(v, w));
        G[v].push_back(pair(u, w));
    }
    cin >> s >> e;
    cin >> P;
    pass[P] = s;
    for (int i = 0; i < P; i++) cin >> pass[i];
    for (int i = 0; i <= P; i++) dij(i);
    memset(dp, -1, sizeof dp);
    ll ret = loop(P, 0);
    cout << ((ret == INFL) ? -1 : ret);
}
