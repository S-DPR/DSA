#include <iostream>
#include <string.h>
using namespace std;
/*
29818번 효율적으로 과제하기

과제를 하는데 아래 네 조건을 따른다.
0. 과제를 하는데는 T시간이 소요된다.
1. D시간 이하의 시간에 과제를 완료하면 P점을 받는다.
2. D+24시간 이하의 시간에 과제를 완료하면 P/2점을 받는다.
3. 그 이외의 경우 0점을 받는다.
과제가 N( <= 20)개 있다. 이 때, 받을 수 있는 최대 점수를 구해보자.

난 진짜 바보야
아..

그냥 dp배열이랑 시간배열 따로 놔서 둘이 갱신 같이해주면 끝나는 bit dp..
아..
나 진짜 왜 이렇게 할 생각을 못했지..
4달동안..
*/
struct info {
    int t, d, p;
};

int N, D[1<<20], T[1<<20];
info A[20];

pair<int, int> loop(int vis, int time) {
	if (D[vis] != -1) return {D[vis], T[vis]};
	if (vis+1 == 1<<N) return {0, 0};
    D[vis] = 0;
	for (int i = 0; i < N; i++) {
		if (vis&(1<<i)) continue;
		int t = A[i].t, d = A[i].d, p = A[i].p;
		if (time+t > d) {
			if (time+t <= d+24) p >>= 1;
            else p = 0;
		}
		auto x = loop(vis|(1<<i), time+t);
		int nd = x.first+p, nt = x.second+t;
		if (D[vis] < nd) {
			D[vis] = nd;
			T[vis] = nt;
		} else if (D[vis] == nd) {
			T[vis] = min(T[vis], nt);
		}
    }
	return {D[vis], T[vis]};
}

int main() {
    memset(D, -1, sizeof D);
    scanf("%d", &N);
    for (int i = 0; i < N; i++)
        scanf("%d %d %d", &A[i].t, &A[i].d, &A[i].p);
	auto x = loop(0, 0);
	int d = x.first, t = x.second;
	printf("%d %d", d, t);
}
