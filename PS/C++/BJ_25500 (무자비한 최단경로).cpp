#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
#include <string.h>
#define ll long long
using namespace std;
/*
25500번 무자비한 최단경로

N개의 마을이 각각 x, y, z좌표에 있다.
각 마을은 min(|xi-xj|, |yi-yj|)의 양방향 도로로 이루어져있고,
zi+zj가 K의 배수라면 zi+zj만큼의 거리를 가진 양방향 도로로 연결되어있다.
이 때, 첫 번째 마을에서 모든 마을의 최단거리를 구해보자.

이거때문에 행성터널이랑 환승 문제 풀었던건데..
어림도없지 환승 업그레이드버전이라서 난이도 확올라버렸네..

행성터널은 그냥 만들면 됩니다. 저건 거의 고정수준같아요.
그런데 문제는, 환승 문제. 환승문제는 모든 가중치가 1이라 그냥 vis로 처리해도 됐는데,
여긴 진짜 더미노드 만들어서 거리관리를 해줘야합니다.
그거 하나때문에 둘을 합친거보다 난이도가 확 올라버렸어요..
하긴 P5에 G2 살짝 묻혀봐야 P5 탈출 힘든데 이러니까 P4지..
*/
struct Node {
  ll i, x, y, z;
};

int N, K;
Node G[200000];
vector<pair<int, ll>> TG[400000];
ll dist[400000];

void dij() {
  memset(dist, 0x6f, sizeof dist);
  priority_queue<pair<ll, int>> pq;
  dist[0] = 0;
  pq.push({0, 0});
  while (!pq.empty()) {
    auto cur = pq.top(); pq.pop();
    int curN = cur.second;
    ll curW = -cur.first;
    if (dist[curN] < curW) continue;
    for (auto &nxt: TG[curN]) {
      int nxtN = nxt.first;
      ll nxtW = nxt.second;
      if (curW+nxtW < dist[nxtN]) {
        dist[nxtN] = curW+nxtW;
        pq.push({-dist[nxtN], nxtN});
      }
    }
  }
}

bool xcmp(const Node& x, const Node& y) {
  return x.x < y.x;
}

bool ycmp(const Node& x, const Node& y) {
  return x.y < y.y;
}

int main() {
  scanf("%d %d", &N, &K);
  for (int i = 0; i < N; i++) {
    ll x, y, z;
    scanf("%lld %lld %lld", &x, &y, &z);
    G[i] = {i, x, y, z};
    TG[i].push_back({N+(K-z%K)%K, z});
    TG[N+z%K].push_back({i, z});
  }
  sort(G, G+N, xcmp);
  for (int i = 1; i < N; i++) {
    Node *first = &G[i-1];
    Node *second = &G[i];
    TG[first->i].push_back({second->i, second->x-first->x});
    TG[second->i].push_back({first->i, second->x-first->x});
  }
  sort(G, G+N, ycmp);
    for (int i = 1; i < N; i++) {
    Node *first = &G[i-1];
    Node *second = &G[i];
    TG[first->i].push_back({second->i, second->y-first->y});
    TG[second->i].push_back({first->i, second->y-first->y});
  }
  dij();
  ll INF = 9223372036854775807;
  for (int i = 0; i < N; i++) {
    printf("%lld\n", dist[i] == INF ? -1 : dist[i]);
  }
}
