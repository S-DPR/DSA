#include <iostream>
#include <vector>
#include <queue>
#include <cstdio>
#include <map>
using namespace std;
/*
30294번 Flea

URDL로 이루어진 R*C 맵이 주어진다.
각 방향으로 K칸 점프할 수 있다 할 때, 최종적으로 맵 밖으로 나갈 수 있는 칸의 개수를 구해보자.

크
에디토리얼 보고 머리가 띵했던 문제
왜 생각을 못했지?

한 곳에서 갈 수 있는 칸을 체크합니다.
각 알파벳 별로요. 그러면서 못가는 칸은 {-1, -1}로 둡니다.
그럼 이제 어떤 r, c에 대해 '이 칸으로 올 수 있는 가장 가까운 RLUD'를 O(1)로 구할 수 있게 됩니다.
이제 초기 큐에 밖으로 다이렉트로 나갈 수 있는 칸을 구하고, BFS를 굴리면 됩니다.

코드스타일이 평소랑 조금 다른건 Kotlin으로 풀었다가 TLE나서 C++로 GPT한테 바꿔달라고 했기 떄문.
와, 근데 되게 간단한 원리인데 생각 못해서 좀 분하네..
*/
const int MAX = 1e9;

int main() {
    int R, C, K;
    scanf("%d %d %d", &R, &C, &K);
    
    vector<vector<int>> M(R, vector<int>(C));
    map<char, int> dic = {{'U', 0}, {'R', 1}, {'D', 2}, {'L', 3}};
    for (int i = 0; i < R; ++i) {
        for (int j = 0; j < C; ++j) {
            char ch;
            scanf(" %c", &ch); // 공백을 두어 다음 문자를 제대로 읽게 함
            M[i][j] = dic[ch];
        }
    }

    vector<vector<pair<int, int>>> up(R, vector<pair<int, int>>(C, {-1, -1}));
    vector<vector<pair<int, int>>> right = up, down = up, left = up;

    for (int r = 0; r < R; ++r) {
        pair<int, int> cur = {-1, -1};
        for (int c = 0; c < C; ++c) {
            if (cur.first != -1 && abs(cur.second - c) > K) cur = {-1, -1};
            right[r][c] = cur;
            if (M[r][c] == 1) cur = {r, c};
        }
        cur = {-1, -1};
        for (int c = C-1; c >= 0; --c) {
            if (cur.first != -1 && abs(cur.second - c) > K) cur = {-1, -1};
            left[r][c] = cur;
            if (M[r][c] == 3) cur = {r, c};
        }
    }

    for (int c = 0; c < C; ++c) {
        pair<int, int> cur = {-1, -1};
        for (int r = 0; r < R; ++r) {
            if (cur.first != -1 && abs(cur.first - r) > K) cur = {-1, -1};
            down[r][c] = cur;
            if (M[r][c] == 2) cur = {r, c};
        }
        cur = {-1, -1};
        for (int r = R-1; r >= 0; --r) {
            if (cur.first != -1 && abs(cur.first - r) > K) cur = {-1, -1};
            up[r][c] = cur;
            if (M[r][c] == 0) cur = {r, c};
        }
    }

    queue<pair<int, int>> Q;
    vector<vector<int>> V(R, vector<int>(C, 0));
    for (int r = 0; r < R; ++r) {
        for (int c = 0; c < C; ++c) {
            bool ret = false;
            if (M[r][c] == 0 && r-K < 0) ret = true;
            if (M[r][c] == 1 && c+K >= C) ret = true;
            if (M[r][c] == 2 && r+K >= R) ret = true;
            if (M[r][c] == 3 && c-K < 0) ret = true;
            if (ret) {
                Q.push({r, c});
                V[r][c] = 1;
            }
        }
    }

    while (!Q.empty()) {
        pair<int, int> front = Q.front();
        int r = front.first;
        int c = front.second;
        Q.pop();

        auto checkAndPush = [&](pair<int, int> next) {
            if (next.first != -1 && V[next.first][next.second] == 0) {
                Q.push(next);
                V[next.first][next.second] = 1;
            }
        };

        checkAndPush(up[r][c]);
        checkAndPush(right[r][c]);
        checkAndPush(down[r][c]);
        checkAndPush(left[r][c]);
    }

    int total = 0;
    for (int i = 0; i < R; ++i) {
        for (int j = 0; j < C; ++j) {
            total += V[i][j];
        }
    }

    printf("%d\n", total);

    return 0;
}
