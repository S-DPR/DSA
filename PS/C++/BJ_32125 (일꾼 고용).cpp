#include <iostream>
#include <algorithm>
#include <string.h>
#include <map>
#include <set>
#include <ext/pb_ds/assoc_container.hpp>
#include <ext/pb_ds/tree_policy.hpp>
#define ll long long
using namespace __gnu_pbds;
using namespace std;
/*
32125번 일꾼 고용

N개의 원소를 가진 두 배열이 주어진다.
배열 A : 1과 2로만 이루어져있다.
배열 B : 0이상 10^9 이하의 원소로 이루어져있다.
이 때, 적절한 범위 [l, r]을 잡으려 한다.
배열 A가 1인 원소의 B의 합, 2인 원소의 B의 합의 차이가 K 이하가 되는 (l, r)의 개수를 구해보자.

"모르겠으면 PBDS나 세그트리에 기대볼 것"
세그트리로도 풀 수 있는데 굳이 세그트리로 하려니 난이도가 뻥튀기돼고..
그냥 뭐 multiset+distance로 하다가 TLE터져서 PBDS 짜줘 한다음 풀었습니다.
정해..는 이게 아니라고 하네요. 투포인터로 너무 깔끔하게 풀린다고합니다.
코드보러가야지 히히
*/
template<typename T>
using ordered_multiset = tree<T, null_type, less_equal<T>, rb_tree_tag, tree_order_statistics_node_update>;

int T, N, K, x;
int C[500000];
ll W[500000];
int main() {
    scanf("%d", &T);
    while (T--) {
        scanf("%d %d", &N, &K);
        map<int, ordered_multiset<ll>> h;
        for (int i = 0; i < N; i++) {
            scanf("%d", &x);
            C[i] = x == 1 ? -1 : 1;
        }
        for (int i = 0; i < N; i++) {
            scanf("%lld", &W[i]);
        }
        h[0].insert(0);
        int c = 0;
        ll w = 0;
        ll ret = 0;
        for (int i = 0; i < N; i++) {
            c += C[i];
            w += W[i]*C[i];
            ret += h[c].order_of_key(w + K + 1) - h[c].order_of_key(w - K);
            h[c].insert(w);
        }
        printf("%lld\n", ret);
    }
}
