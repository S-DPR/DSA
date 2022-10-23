import sys
input = sys.stdin.readline
inf = 10**9
"""
16958번 텔레포트

텔레포터가 설치되어있는 특별한 도시가 있습니다. 특별한 도시는 서로 텔레포트를 할 수 있습니다.
도시의 개수 N과 텔레포트 시 걸리는 시간 T가 첫째줄에 주어지고,
2번째줄부터 한줄마다 특별한 도시 여부 (True = 1, False = 0), 도시의 x좌표, 도시의 y좌표가 N줄 주어집니다.
이후 쿼리의 개수 Q가 주어집니다. 그 다음 Q개의 줄에 출발도시와 도착도시가 주어집니다.
각 쿼리마다 최단거리를 출력해주세요. 각 도시의 거리는 (x거리의 차) + (y거리의 차) 입니다.

최단거리라고 하길래 무지성으로 다익스트라를 생각했다가 시간초과맞고,
모든 도시에서의 거리를 얻어내는데는 O(EQlgV)보다 O(E^3) (E<=1000)이 더 낫다는 사실을 깨닫고 플로이드워셜 썼다가 시간초과맞고,
C언어로 풀었다가 와 이게 되네 ㅋㅋ 파이썬 억까네 하고,
'텔레포트는 한번만 하면 된다'라는 힌트를 solved.ac에서 얻고 제대로 풀었습니다.

정해는 플로이드-워셜이 아니라 아래 방법인것 같습니다. 
"""
def dist(x1, x2, y1, y2):
    return abs(x1-x2) + abs(y1-y2)

n, t = map(int, input().split()); n+=1
coor = [[]]
S = list() # 특별한 도시 경우, 이 리스트에 도시 번호가 들어가게 될겁니다.

# 입력을 받습니다.
for i in range(1, n):
    x, *k = map(int, input().split())
    coor.append(k)
    if x: S.append(i)

# 각 도시에서 특별한 도시와의 최단거리를 얻어냅니다. 특별한 도시인 경우, 그 거리는 0입니다.
teleport_shortest = [0]
for i in range(1, n):
    teleport_shortest.append(min(dist(coor[i][0], coor[j][0], coor[i][1], coor[j][1]) for j in S))

# 쿼리를 처리합니다.
for _ in ' '*int(input()):
    x, y = map(int, input().split())
    ans = dist(coor[x][0], coor[y][0], coor[x][1], coor[y][1])
    ans = min(ans, t+teleport_shortest[x]+teleport_shortest[y])
    print(ans)

""" 플로이드-워셜 알고리즘 C++ (952ms)
#include <bits/stdc++.h>
#include <unordered_set>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
#define inf 1000000000
using namespace std;

struct coor {
    int x = 0, y = 0;
    coor(){}
    coor(int x_, int y_) {
        x = x_; y = y_;
    }
};

int main()
{
    fast;
    int n, t; cin >> n >> t; n++;
    vector<coor> C = { coor() };
    int G[1001][1001] = { 0 };
    unordered_set<int> S;
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            G[i][j] = inf;
    for (int i = 1; i < n; i++) {
        int s, x, y; cin >> s >> x >> y;
        C.push_back(coor(x, y));
        if (s) S.insert(i);
    }

    for (int i = 1; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int tmp = abs(C[i].x - C[j].x) + abs(C[i].y - C[j].y);
            if (S.find(i) != S.end() && S.find(j) != S.end())
                tmp = min(tmp, t);
            G[i][j] = tmp; G[j][i] = tmp;
        }
    }

    for (int i = 1; i < n; i++)
        for (int j = 1; j < n; j++)
            for (int k = 1; k < n; k++)
                G[j][k] = min(G[j][k], G[j][i] + G[i][k]);

    int Q; cin >> Q;
    for (int i = 0; i < Q; i++) {
        int x, y; cin >> x >> y;
        cout << G[x][y] << endl;
    }
}
"""