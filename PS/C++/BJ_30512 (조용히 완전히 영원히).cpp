#include <iostream>
#include <algorithm>
#include <queue>
#define ll long long
using namespace std;
/*
30512번 조용히 완전히 영원히

길이가 N인 수열이 있고, 쿼리가 Q개 주어진다.
 - L R X : L번째부터 R번째 수까지, min(A[i], X)를 적용한다.
쿼리가 모두 적용된 배열을 출력해보자.
그리고, 어느 순간부터 위 쿼리로 변하지 않는 인덱스가 있다.
각 쿼리가 마지막으로 바꾸는 원소의 개수를 누적하여 출력해보자.

생각 좀 해보면 스위핑 써야겠다는 생각은 드는데,
쿼리는 오프라인쿼리로 처리하고, 각 쿼리를 pq로 구간처리.
이런식으로 대충 슥슥 긁어대면 끝납니다.

sweep배열을 100001이 아니라 100000으로 잡아서 1시간 날렸네요. 아..
*/
int N, Q, u, v, w;
int A[100001], sweep[100001], R[100001], C[100001];
tuple<int, int, int, int> queries[100001];

int main() {
	scanf("%d", &N);
	for (int i = 0; i < N; i++) scanf("%d", &A[i]);
	scanf("%d", &Q);
	for (int i = 0; i < Q; i++) {
		scanf("%d %d %d", &u, &v, &w);
		queries[i] = {u-1, v-1, w, i+1};
	}
	sort(queries, queries+Q);
	priority_queue<tuple<int, int, int>> pq;
	for (int i = 0, idx = 0; i < N; i++) {
		while (idx < Q && get<0>(queries[idx]) <= i) {
			auto &[_, r, w, q] = queries[idx++];
			pq.push({-w, -q, r});
		}
		while (pq.size() && get<2>(pq.top()) < i) {
			pq.pop();
		}
		if (pq.size() && -get<0>(pq.top()) < A[i]) {
			sweep[i] = -get<1>(pq.top());
		}
		R[i] = pq.size() ? min(-get<0>(pq.top()), A[i]) : A[i];
	}
	for (int i = 0; i < N; i++) {
		printf("%d ", R[i]);
		C[sweep[i]]++;
	}
	printf("\n");
	for (int i = 1; i <= Q; i++) {
		C[i] += C[i-1];
		printf("%d ", C[i]);
	}
}
