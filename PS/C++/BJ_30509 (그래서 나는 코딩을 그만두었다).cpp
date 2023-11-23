#include <iostream>
#include <algorithm>
#include <string.h>
#define ll long long
using namespace std;
/*
30509번 그래서 나는 코딩을 그만두었다

내 현재 코딩 실력은 S이고, 현재 멘탈은 M이다.
다른 친구들의 코딩 실력과 멘탈이 주어진다.
아래 규칙에 따라 대결을 할 때, 최적의 경우 최대 몇 번의 대결을 할 수 있나 구해보자.
 - 대결시 코딩실력+멘탈이 더 큰쪽이 이긴다.
 - 내 코딩실력보다 더 높은 사람을 이기면 멘탈이 그 차이만큼 상승한다.
 - 코딩실력이 같거나 낮은 상대를 이길 경우 아무런 변화가 일어나지 않는다.
 - 내 코딩실력보다 낮은 상대에게 지면 그 차이만큼 멘탈이 감소한다.

오랜만에 태그예측 성공하고 푼 P4
그리디+매개변수탐색이었습니다. 지금까지 본 그 조합은 아니고.
매개변수탐색을 주로 하되, 내부에서 그리디를 굴리는 방식이었습니다.

check함수가 관건인데, 정렬을 두 번 해야합니다.
한 번은 코딩실력+멘탈을 오름차순으로,
한 번은 코딩실력을 내림차순으로.
그리고 매개변수탐색은 초기에 몇 번을 쉴지를 처리하는 용도입니다.
쉬는걸 초반에 다 쉬는게 제일 이득이거든요.

그렇게해서 처리하면 끝납니다. 와~
*/
ll N, L, R, S, M;
pair<ll, ll> A[100000];

bool check(ll rest) {
    sort(A, A+N, [](pair<ll, ll> i, pair<ll, ll> j) {
        return i.first+i.second < j.first+j.second;
    });
    ll curS = S, curM = M + R*rest;
    ll battle = 0;
    while (battle+rest < N && A[battle].first+A[battle].second < curS+curM) {
        curM += max(0LL, A[battle].first-curS);
        battle++;
    }
    while (battle+rest < N && A[battle].first+A[battle].second == curS+curM) {
        battle++;
    }
    sort(A+battle, A+N, [](pair<ll, ll> i, pair<ll, ll> j) {
        return i.first > j.first;
    });
    while (battle+rest < N) {
        curM -= L + max(0LL, curS-A[battle].first);
        battle++;
    }
    return curM > 0;
}

int main() {
    scanf("%lld %lld %lld", &N, &L, &R);
    scanf("%lld %lld", &S, &M);
    for (int i = 0; i < N; i++) {
        scanf("%lld %lld", &A[i].first, &A[i].second);
    }
    ll left = 0, right = N;
    while (left < right) {
        ll mid = (left + right) >> 1;
        if (check(mid))
            right = mid;
        else
            left = mid + 1;
    }
    printf("%lld", N-right);
}
