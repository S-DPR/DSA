#include <iostream>
#include <vector>
#include <deque>
#define ll long long
using namespace std;
/*
30180번 재민이의 생일

R*C크기의 케이크가 있다.
넓이가 N인 직사각형으로 자를 때, 내부에 있는 가장 단 케이크와 가장 달지 않은 케이크의 차의 최대를 구해보자.
만약 자를 수 없다면, -1을 출력하자.

퍄
처음 볼때 2D세그인가.. 했는데 태그보니까 덱이네
실화냐

옛날옛날에 푼 최솟값 찾기 << 이 문제를 2D로 늘린겁니다.
근데 늘리면서 최대/최소 다 찾게 바뀌었고..
그냥.. 가로로 한번 해주고, 세로로 한번 하면 끝납니다.
근데 똑같은게 4번나와서 코드가 좀 기네..
*/
int R, C, N;
int M[500001];
pair<int, int> col[500001];

int pos(int r, int c) {
    return r*C+c;
}

int process(int r, int c) {
    if (R < r || C < c) return -1;
    // col
    for (int i = 0; i < R; i++) {
        deque<pair<int, int>> mx, mn;
        for (int j = 0; j < C; j++) {
            int v = M[pos(i, j)];
            while (mx.size() && mx.back().first <= v)
                mx.pop_back();
            while (mx.size() && j - mx.front().second >= c)
                mx.pop_front();
            
            while (mn.size() && mn.back().first >= v)
                mn.pop_back();
            while (mn.size() && j - mn.front().second >= c)
                mn.pop_front();

            mx.push_back({v, j});
            mn.push_back({v, j});

            if (j >= c-1) {
                col[pos(i, j-c+1)] = {mx.front().first, mn.front().first};
            }
        }
    }

    // row
    int ret = -1;
    for (int i = 0; i < C; i++) {
        deque<pair<int, int>> mx, mn;
        for (int j = 0; j < R; j++) {
            int cjiM = col[pos(j, i)].first;
            int cjim = col[pos(j, i)].second;
            if (cjiM == -1<<30) break;
            while (mx.size() && mx.back().first <= cjiM)
                mx.pop_back();
            while (mx.size() && j - mx.front().second >= r)
                mx.pop_front();
            
            while (mn.size() && mn.back().first >= cjim)
                mn.pop_back();
            while (mn.size() && j - mn.front().second >= r)
                mn.pop_front();
            
            mx.push_back({cjiM, j});
            mn.push_back({cjim, j});

            if (j >= r-1) {
                ret = max(ret, mx.front().first - mn.front().first);
            }
        }
    }
    return ret;
}

int main() {
    scanf("%d %d %d", &R, &C, &N);
    for (int r = 0; r < R; r++) {
        for (int c = 0; c < C; c++) {
            scanf("%d", &M[pos(r, c)]);
        }
    }
    int ret = -1;
    for (int i = 1; i <= N; i++) {
        if (N%i) continue;
        ret = max(ret, process(i, N/i));
    }
    printf("%d", ret);
}
