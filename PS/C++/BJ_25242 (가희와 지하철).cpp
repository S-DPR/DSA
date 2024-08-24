#include <iostream>
#include <algorithm>
#include <string.h>
#include <vector>
#include <queue>
#include <map>
#define ll long long
using namespace std;
/*
25242번 가희와 지하철

노선이 N개 있는 지하철이 있다.
지하철은 상행과 하행이 있으며, 지하철의 노선이 주어진다.
각 역은 모두 2분 간격일 때, u에서 v로 가는 최단거리를 Q번 구해보자.
단, 환승역은 20개 이하이다.

구아아악
진짜끔찍하다끔찍해

환승역이 20개 이하니까 u -> 환승 -> v 최단거리 20번 찾으면되고,
u -> v 직통은 둘 다 환승역이 아니라는 가정 하에 그냥 각 노선의 몇번째 역인지 체크해서 넣으면 됩니다.
그런데 구현이 좀.. 그.. 뭐랄까..
조금 복잡하네요..
로직 내에서 고민을 하는게 아니라 정보를 어떻게 저장할지에 대한 고민을 더 많이 한 것 같습니다.
*/
int N, Q; 
string s;
map<string, int> station;
map<int, int> line;
map<int, int> kth;
vector<int> G[200002];
bool V[200000];
int dist[200002][20];
vector<int> transfer;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    memset(dist, -1, sizeof dist);
    cin >> N >> Q;
    for (int i = 0; i < N; i++) {
        int t; cin >> t;
        int prv = -1;
        for (int j = 0; j < t; j++) {
            cin >> s;
            int num = station.size();
            if (station.find(s) != station.end()) {
                num = station[s];
                if (!V[num]) {
                    transfer.push_back(num);
                    V[num] = true;
                }
            } else {
                station[s] = num;
            }
            kth[num] = j;
            line[num] = i;
            if (prv != -1) {
                G[prv].push_back(num);
                G[num].push_back(prv);
            }
            prv = num;
        }
    }
    for (int i = 0; i < transfer.size(); i++) {
        queue<int> q;
        q.push(transfer[i]);
        dist[transfer[i]][i] = 0;
        while (q.size()) {
            int cur = q.front(); q.pop();
            int time = dist[cur][i];
            for (auto &nxt: G[cur]) {
                if (dist[nxt][i] != -1) continue;
                dist[nxt][i] = time+1;
                q.push(nxt);
            }
        }
    }
    while (Q--) {
        string u, v; cin >> u >> v;
        int un = station[u];
        int vn = station[v];
        int ret = 1<<30;
        for (int i = 0; i < transfer.size(); i++) {
            if (dist[un][i] == -1 || dist[vn][i] == -1) continue;
            ret = min(ret, dist[un][i]+dist[vn][i]);
        }
        if (line[un] == line[vn] && !V[un] && !V[vn]) {
            ret = min(ret, abs(kth[un]-kth[vn]));
        }
        cout << (ret < 1<<30 ? ret*2 : -1) << '\n';
    }
}
