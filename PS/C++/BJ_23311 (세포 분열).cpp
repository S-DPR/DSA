#include <iostream>
#include <algorithm>
#include <string.h>
#define ll long long
#define endl "\n"
using namespace std;
/*
23311번 세포 분열

시작 문자가 있다. 이 문자가 최종 문자열로 변환될 수 있나 생각해보자.
i번째 알파벳은 M[i]번째 알파벳 혹은 자기 자신을 자신의 왼쪽 아니면 오른쪽에 복제할 수 있다.

아니 뭐 dp인거야 그렇다 치겠는데
이게 구간dp인건 조금 많이 놀라운데..

뭔가 구간dp의 새로운 점을 알게 해준것같은 문제.
처음에 개판 좀 치면서 몇시간 갈아넣다가 결국 답지를 보았는데,
이야 이게 되네
이건 조금 더 두고 생각을 많이 해봐야 할 것 같습니다. 너무 놀라워서..
*/
int N;
char M[500];
char s, e[501];
bool dp[501][501][26];
bool vis[501][501][26];

bool loop(int l, int r, int c) {
	if (l == r) return c == e[l]-'a';
	if (vis[l][r][c]) return dp[l][r][c];
	vis[l][r][c] = true;
	for (int i = l; i < r; i++) {
		dp[l][r][c] |= loop(l, i, c) && loop(i+1, r, c);
		dp[l][r][c] |= loop(l, i, c) && loop(i+1, r, M[c]);
		dp[l][r][c] |= loop(l, i, M[c]) && loop(i+1, r, c);
	}
	return dp[l][r][c];
}

int main() {
	ios::sync_with_stdio(0);
	cin.tie(nullptr), cout.tie(nullptr);
	cin >> N;
	for (int i = 0; i < N; i++) {
		cin >> M[i];
		M[i] -= 'a';
	}
	cin >> s >> e;
	cout << (loop(0, strlen(e)-1, s-'a') ? "YES" : "NO");
}
