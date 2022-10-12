#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
using namespace std;
/*
17070번 파이프 옮기기

PyPy로 완전탐색을 했더니 시간초과나서 C++로 풀었습니다.
PyPy는 안되더니 C++로는 120ms정도밖에 안걸리네요.
아무래도 DP로도 해봐야할것같네요..
파이프 옮기기 2번은 DP로만 풀 수 있다고 합니다.
어떻게 해야할지 계속 생각해봐야할것같습니다.
*/
struct node {
    int x = 0, y = 0, c = 0;
    node(int x_ = 0, int y_ = 0, int c_ = 0) {
        x = x_; y = y_; c = c_;
    }
};

int main()
{
    fast;
    int n; cin >> n;
    int M[16][16];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            cin >> M[i][j];
    vector<node> d;
    int cnt = 0;
    d.push_back(node(1, 0, 1));
    while (!d.empty()) {
        node tmp = d.back(); d.pop_back();
        int x = tmp.x, y = tmp.y, pos = tmp.c;
        if (x == n - 1 && y == n - 1) cnt++;
		// 가만보면 대각선은 어느 방향으로 있어도 둘 수 있고,
        if (x + 1 < n && y + 1 < n && M[y + 1][x] + M[y + 1][x + 1] + M[y][x + 1] == 0)
            d.push_back(node(x + 1, y + 1, 3));
		// 세로인 경우에는 가로로 못두고요,
        if (pos != 2 && x + 1 < n && M[y][x + 1] == 0)
            d.push_back(node(x + 1, y, 1));
		// 가로인 경우에는 세로로 못두더라고요.
        if (pos != 1 && y + 1 < n && M[y + 1][x] == 0)
            d.push_back(node(x, y + 1, 2));
    }
    cout << cnt;
}