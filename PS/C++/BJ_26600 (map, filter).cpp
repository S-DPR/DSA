#include <iostream>
#include <algorithm>
#include <string.h>
#define ll long long
using namespace std;
/*
26600번 map, filter

6개의 연산이 있다. 각 연산에 대해 값을 출력해보자.
map + x : x를 배열의 모든 원소에 더한다.
map * x : x를 배열의 모든 원소에 곱한다.
map % x : 배열의 모든 원소를 x로 나눈 나머지로 바꾼다.
filter >= x : x 이상인 원소의 개수를 출력한다.
filter <= x : x 이하인 원소의 개수를 출력한다.
filter == x : x의 개수를 출력한다.
연산을 모두 해도 배열의 모든 원소는 2^63-1을 넘지 않는다.
map의 연산에서 x는 1이상 100이하,
filter의 연산에서 x는 0이상 2^63-1이하이다.

처음엔 filter의 x에 이전까지의 map연산을 처리하려했는데,
하다보니 멸망에 이르러버린 문제..
생각해보니 mod연산이 나온 순간부터 배열의 길이를 100으로 줄일 수 있음을 깨닫고,
이를 이용해 풀었습니다.

그리고 2^63-1이하면 +1이 치명적이라는 점도 새로 깨닫게 되었습니다..
*/
int N, Q;
ll A[100000], AA[100], tmp[100];
ll pr = 1, pl = 0, mod = -1, x, newV;
char cmd[100], op[100];

int bisect(ll v) {
    int lo = 0, hi = N;
    while (lo < hi) {
        int mid = (lo + hi) >> 1;
        if (A[mid]*pr+pl >= v) {
            hi = mid;
        } else {
            lo = mid + 1;
        }
    }
    return hi;
}

int upperBound(ll v) {
    int lo = 0, hi = N;
    while (lo < hi) {
        int mid = (lo + hi) >> 1;
        if (A[mid]*pr+pl > v) {
            hi = mid;
        } else {
            lo = mid + 1;
        }
    }
    return hi;
}

int main() {
    scanf("%d", &N);
    for (int i = 0; i < N; i++) {
        scanf("%lld", &A[i]);
    }
    sort(A, A+N);
    scanf("%d", &Q);
    while (Q--) {
        scanf("%s %s %lld", &cmd, &op, &x);
        if (!strcmp(cmd, "map")) {
            if (mod == -1) {
                if (!strcmp(op, "+")) {
                    pl += x;
                } else if (!strcmp(op, "*")) {
                    pr *= x;
                    pl *= x;
                } else {
                    for (int i = 0; i < N; i++)
                        AA[(A[i]*pr+pl)%x]++;
                    mod = x;
                    pr = 1;
                    pl = 0;
                }
            } else {
                if (!strcmp(op, "+")) {
                    pl += x;
                } else if (!strcmp(op, "*")) {
                    pr *= x;
                    pl *= x;
                } else {
                    memset(tmp, 0, sizeof tmp);
                    for (ll i = 0; i < 100; i++) {
                        newV = (i*pr+pl)%x;
                        tmp[newV] += AA[i];
                    }
                    for (int i = 0; i < 100; i++)
                        AA[i] = tmp[i];
                    mod = x;
                    pr = 1;
                    pl = 0;
                }
            }
        } else {
            if (mod == -1) {
                int lo = 0, hi = N;
                if (!strcmp(op, ">=")) {
                    lo = bisect(x);
                } else if (!strcmp(op, "<=")) {
                    hi = upperBound(x);
                } else {
                    lo = bisect(x);
                    hi = upperBound(x);
                }
                printf("%d\n", hi-lo);
            } else {
                int ret = 0;
                if (!strcmp(op, ">=")) {
                    for (ll i = 0; i < 100; i++) {
                        if (i*pr+pl < x) continue;
                        ret += AA[i];
                    }
                } else if (!strcmp(op, "<=")) {
                    for (ll i = 0; i < 100; i++) {
                        if (i*pr+pl > x) continue;
                        ret += AA[i];
                    }
                } else {
                    for (ll i = 0; i < 100; i++) {
                        if (i*pr+pl != x) continue;
                        ret += AA[i];
                    }
                }
                printf("%d\n", ret);
            }
        }
    }
}
