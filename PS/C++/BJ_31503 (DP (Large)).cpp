#include <iostream>
#include <algorithm>
#include <string.h>
#include <vector>
#define ll long long
using namespace std;
/*
31503번 DP (Large)

수열이 주어진다. K번째 원소를 포함한 LIS의 길이는 몇일까?
한 번만 하면 너무 쉬우니 Q번 해결해보자.

핵심은 잘 짚었는데 깔끔하게 풀진 못한 문제
일단 답은 간단하게 수열 두고 LIS 한번, 수열 뒤집고 LDS 한번 하기.
그리고나서 i번째 인덱스라면 LIS[i]+LDS[i]-1 이 답이 됩니다.
1을 빼는 이유는 K번째 원소가 중복으로 세져서.

그런데 음..
이분탐색으로는 i번째 원소의 길이를 알 수 없지 않나? 해서 세그트리로 했네요.
세그트리 LIS는 처음해보긴했는데..
역시 이분탐색이야, 성능 확실하구만. 생각 들더라고요.
다른사람 푼거 보니까 이분탐색 깔끔하고.
*/
const int MAX = 300000;
int seg[MAX*2+2], lis[MAX], lds[MAX], exist[MAX];
int N, Q, x, sz;
pair<int, int> A[MAX];
vector<int> comp;

void update(int idx, int val) {
    idx += sz;
    seg[idx] = max(seg[idx], val);
    while (idx > 1) {
        seg[idx>>1] = max(seg[idx], seg[idx^1]);
        idx >>= 1;
    }
}

int query(int l, int r) {
    l += sz;
    r += sz;
    int ret = 0;
    while (l <= r) {
        if (l&1) ret = max(ret, seg[l++]);
        if (~r&1) ret = max(ret, seg[r--]);
        l >>= 1;
        r >>= 1;
    }
    return ret;
}

int main() {
    scanf("%d %d", &N, &Q);
    for (int i = 0; i < N; i++) {
        scanf("%d", &x);
        comp.push_back(x);
        A[i] = {x, i};
    }
    sort(comp.begin(), comp.end());
    comp.erase(unique(comp.begin(), comp.end()), comp.end());
    sz = comp.size();
    for (int i = 0; i < N; i++) {
        x = A[i].first;
        x = lower_bound(comp.begin(), comp.end(), x) - comp.begin() + 1;
        int len = query(1, x-1) + 1;
        update(x, len);
        lis[i] = len;
    }
    memset(seg, 0, sizeof seg);
    memset(exist, 0, sizeof exist);
    for (int i = N-1; i >= 0; i--) {
        x = A[i].first;
        x = lower_bound(comp.begin(), comp.end(), x) - comp.begin() + 1;
        int len = query(x+1, sz) + 1;
        update(x, len);
        lds[i] = len;
    }
    while (Q--) {
        scanf("%d", &x);
        printf("%d\n", lis[x-1]+lds[x-1]-1);
    }
}
