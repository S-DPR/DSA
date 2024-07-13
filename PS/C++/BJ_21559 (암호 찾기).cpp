#include <iostream>
#include <vector>
#include <string.h>
#include <set>
#define ll long long
using namespace std;
/*
21559번 암호 찾기

문자열 A, B가 주어진다.
공통된 서로다른 부분연속문자열의 개수를 구해보자.

아..
해싱이 진짜 까다롭구나..

이중해싱-카빈라프.
아니 근데 진짜 쉽지않네요?
카빈라프야 뭐 본지 몇번 안됐으니 보면서 구현하자! 했는데,

근데 이중해싱??
솔직히 진짜 써본건 처음이었는데,
진짜 말 그대로 해싱이 두번;
바로 뚫어버리는거보고 놀랐습니다..
*/
ll MOD1 = 1'000'000'007;
ll MOD2 = 1'000'000'009;
ll BASE = 1'000'019;
int N, K;
char A[1000001];
char B[1000001];

ll mod(ll n, ll MOD) {
    if (n >= 0) return n%MOD;
    return ((-n%MOD+1)*MOD+n)%MOD;
}

pair<ll, ll> mod(pair<ll, ll> n) {
    return {mod(n.first, MOD1), mod(n.second, MOD2)};
}

set<pair<ll, ll>> hashing(char *A) {
    set<pair<ll, ll>> ret;
    pair<ll, ll> hash = {0, 0};
    pair<ll, ll> power = {1, 1};
    for (int i = 0; i < N-K+1; i++) {
        if (i == 0) {
            for (int j = 0; j < K; j++) {
                hash.first = mod(hash.first + mod(power.first*A[K-j-1], MOD1), MOD1);
                hash.second = mod(hash.second + mod(power.second*A[K-j-1], MOD2), MOD2);
                if (j < K-1) {
                    power.first = mod(power.first * BASE, MOD1);
                    power.second = mod(power.second * BASE, MOD2);
                }
            }
        } else {
            hash.first = mod(BASE * mod(hash.first - mod(power.first * A[i - 1], MOD1), MOD1) + A[i + K - 1], MOD1);
            hash.second = mod(BASE * mod(hash.second - mod(power.second * A[i - 1], MOD2), MOD2) + A[i + K - 1], MOD2);
        }
        ret.insert(hash);
    }
    return ret;
}

int main() {
    scanf("%d %d", &N, &K);
    scanf("%s %s", A, B);
    set<pair<ll, ll>> a = hashing(A);
    set<pair<ll, ll>> b = hashing(B);
    int cnt = 0;
    for (auto &i: a) {
        if (b.find(i) != b.end()) cnt++;
    }
    printf("%d", cnt);
}
