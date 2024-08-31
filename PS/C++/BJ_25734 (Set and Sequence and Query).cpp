#include <iostream>
#include <set>
#define ll long long
using namespace std;
/*
25734번 Set and Sequence and Query

두 쿼리가 주어진다. 각 쿼리 처리마다 연속된 자연수 배열의 총 개수를 구해보자.
1 x : x를 삽입한다.
2 x : x를 제거한다.

어떻게풀까.. 30만에 10^9네.. 하면서 생각해보다가,
개쩌는 트리셋이구나 하고 풀었습니다.
구간을 [l, r]로 잡아두고 그걸로 처리..
각 쿼리를 못해도 logN으로 처리할 수 있겠죠.
멋진아이디어..
*/
int Q, u, v;
set<pair<int, int>> s;
ll ret = 0;

ll cnt(pair<int, int> p) {
    ll len = p.second-p.first+1;
    return len*(len+1)/2;
}

int main() {
    scanf("%d", &Q);
    while (Q--) {
        scanf("%d %d", &u, &v);
        if (u == 1) {
            int lo = v, hi = v;
            auto lower = s.lower_bound({v, v});
            auto higher = s.upper_bound({v, v});
            if (lower != s.begin()) lower--;
            if (lower != s.end() && lower->second+1 == v) {
                lo = lower->first;
                ret -= cnt(*lower);
                s.erase(*lower);
            }
            if (higher != s.end() && higher->first-1 == v) {
                hi = higher->second;
                ret -= cnt(*higher);
                s.erase(*higher);
            }
            s.insert({lo, hi});
            ret += cnt({lo, hi});
        } else {
            auto find = s.lower_bound({v, v});
            if (!(find->first <= v && v <= find->second)) find--;
            int lo = find->first, hi = find->second;
            ret -= cnt(*find);
            if (lo != v) {
                s.insert({lo, v-1});
                ret += cnt({lo, v-1});
            }
            if (hi != v) {
                s.insert({v+1, hi});
                ret += cnt({v+1, hi});
            }
            s.erase(*find);
        }
        printf("%lld\n", ret);
    }
}
