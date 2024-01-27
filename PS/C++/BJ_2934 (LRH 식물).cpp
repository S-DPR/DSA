#include <iostream>
#include <string.h>
#define ll long long
using namespace std;
/*
2934번 LRH 식물

빈 수열 A와 매 쿼리마다 L, R이 주어진다. 각 쿼리에 대해 아래 행위를 반복해보자.
1. A[L]+A[R]의 값을 출력한다.
2. L+1 ~ R-1번째 수에 1을 더한다.

어김없이 문제 뒤적뒤적하다가 튀어나온 문제.
대충 읽어보니 어떤걸 원하는지도 알겠고 해서 방법 생각해봤는데..
아무리 생각해도 lazy prop인데..

그런데 결국 구간 업데이트 + 한 점 쿼리네?
간단하게 펜윅트리로 풀었습니다.
https://www.acmicpc.net/blog/view/88
최근에 공부한 보람이 있네 ㅋㅋ
*/
const int MAX = 100000;
int N, L, R;
ll F[MAX+1];

void update(int idx, int val) {
	while (idx < MAX+1) {
		F[idx] += val;
		idx += idx & -idx;
	}
}

void update(int l, int r, int val) {
	update(l, val);
	update(r+1, -val);
}

ll query(int idx) {
	ll ret = 0;
	while (idx > 0) {
		ret += F[idx];
		idx -= idx & -idx;
	}
	return ret;
}

int main() {
	scanf("%d", &N);
	while (N--) {
		scanf("%d %d", &L, &R);
		ll lQ = query(L), rQ = query(R);
		printf("%lld\n", lQ+rQ);
		update(L, L, -lQ);
		update(R, R, -rQ);
		update(L+1, R-1, 1);
	}
}
