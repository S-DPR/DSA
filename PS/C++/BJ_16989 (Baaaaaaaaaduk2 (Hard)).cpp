#include <iostream>
#include <algorithm>
#include <string.h>
#include <vector>
#include <map>
#define ll long long
using namespace std;
/*
16989번 Baaaaaaaaaduk2 (Hard)

0, 1, 2로 이루어진 맵이 있다.
2는 주변이 모두 1로 둘러싸이면 죽게된다. 인접한 2는 하나의 2로 생각한다.
2개의 0을 1로 변형시킬 수 있을 때, 잡을 수 있는 2의 최대 개수를 구해보자.

옛날에 Easy는 완탐으로 가볍게 했었는데 이건 어떻게할까.. 고민을 좀 해봤습니다.
그래서 나온게..
1. 1개만 놔도 되는 곳을 체크
2. 2개를 놔야만 하는 곳을 체크
3. 2번의 결과로 (a, b), (c, d)에 놔야한다는 결과가 나올텐데, 각각을 1번에서 가져온 답으로 체크
4. 그중 가장 큰 값 반환.

그런데 이제 2번을 하려면 배열이 1000*1000*1000*1000이 되어야하므로,
가볍게 map을 써서 처리.
의외로 시간초과 안났네요.

한 해, 아마도 잘 끝낸거같네요..
*/
int R, C, ret;
int M[1000][1000];
int S[1000][1000];
int V[1000][1000];
int dr[] = {1, -1, 0, 0};
int dc[] = {0, 0, 1, -1};

int dfs(int r, int c, vector<pair<int, int>> &empty) {
    V[r][c] = 1;
    if (M[r][c] != 2) {
        if (!M[r][c])
            empty.push_back({r, c});
        return 0;
    }
    int sz = 1;
    for (int i = 0; i < 4; i++) {
        int nr = r+dr[i];
        int nc = c+dc[i];
        if (!(0 <= nr && nr < R)) continue;
        if (!(0 <= nc && nc < C)) continue;
        if (V[nr][nc]) continue;
        sz += dfs(nr, nc, empty);
    }
    return sz;
}

int main() {
    scanf("%d %d", &R, &C);
    for (int r = 0; r < R; r++)
        for (int c = 0; c < C; c++)
            scanf("%d", &M[r][c]);

    vector<int> single_sz(2, 0);
    for (int r = 0; r < R; r++) {
        for (int c = 0; c < C; c++) {
            if (M[r][c] != 2) continue;
            if (V[r][c]) continue;
            vector<pair<int, int>> empty;
            int sz = dfs(r, c, empty);
             for (auto x: empty)
                V[x.first][x.second] = 0;
            if (empty.size() != 1) continue;
            S[empty[0].first][empty[0].second] += sz;
        }
    }
    for (int r = 0; r < R; r++)
        for (int c = 0; c < C; c++)
            if (S[r][c]) single_sz.push_back(S[r][c]);
    sort(single_sz.begin(), single_sz.end());
    int ssz = single_sz.size();
    ret = single_sz[ssz-1]+single_sz[ssz-2];

    memset(V, 0, sizeof V);
    map<pair<pair<int, int>, pair<int, int>>, int> m;
    for (int r = 0; r < R; r++) {
        for (int c = 0; c < C; c++) {
            if (M[r][c] != 2) continue;
            if (V[r][c]) continue;
            vector<pair<int, int>> empty;
            int sz = dfs(r, c, empty);
            for (auto x: empty)
                V[x.first][x.second] = 0;
            if (empty.size() != 2) continue;
            sort(empty.begin(), empty.end());
            m[{empty[0], empty[1]}] += sz;
        }
    }
    for (auto x: m) {
        int pf = x.second;
        pair<int, int> fc = x.first.first;
        pair<int, int> sc = x.first.second;
        pf += S[fc.first][fc.second];
        pf += S[sc.first][sc.second];
        ret = max(ret, pf);
    }
    printf("%d", ret);
}
