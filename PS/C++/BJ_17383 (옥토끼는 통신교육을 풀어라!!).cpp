#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
#include <string.h>
#define ll long long

using namespace std;
/*
17383번 옥토끼는 통신교육을 풀어라!!

N개의 작업 x를 하는데 필요한 각 시간이 주어진다.
각 작업을 최대 두 개까지 병렬로 할 수 있을 때,
여기서 우리가 볼 것은 각 작업이 끝나는 시간이다.
적절하게 작업을 나열한 뒤,
작업이 끝나는 시간을 나열했을 때 그 텀의 최대가 최소를 구하시오.

음
아이디어는 금방 알았는데,
multiset써서 logN(NlogN)은 tle때리고 NlogN은 68ms로 맞은 문제
그리서 으윽 어떻게하지 하면서 열심히 NlogN으로 갈았네요..
솔직히 아이디어가 P4치곤 어렵진 않았다.
구현이 흐음 생각이 들 뿐..
*/
int N;
ll lo = 1, hi = 0;
ll A[100000];
int main() {
    scanf("%d", &N);
    for (int i = 0; i < N; i++) scanf("%lld", &A[i]), hi += A[i];
    while (lo < hi) {
        ll mid = (lo + hi) >> 1;
        ll mn = 0, cnt = 0;
        ll time = 0;
        for (int i = 0; i < N; i++) {
            mn += A[i] <= mid;
            cnt += max(0ll, (A[i]+mid-1)/mid-2);
        }
        if (cnt < mn) hi = mid;
        else lo = mid + 1;
    }
    printf("%lld", hi);
}
