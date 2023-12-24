#include <iostream>
#include <algorithm>
#include <string.h>
#include <vector>
#define ll long long
using namespace std;
/*
16221번 모독

'모독'이라는 카드는 아래 효과를 갖는다.
1. 수열의 모든 값에서 1을 뺀다.
2. 0이 된 값을 모두 제거한다. 제거에 성공했을 경우, 1로 돌아간다.
다음 쿼리가 주어진다.
1 x : x를 수열에 추가한다.
2 x : x를 수열에서 하나 제거한다.
이 때, 각 쿼리마다 '모독'을 사용시 몇 개의 원소가 제거되는지 체크해보자.

또그먼트 또리
'그' 트리

그냥.. 세그트리를 두개 구축하고 처리하면 됩니다.
하나는 수 K 이하의 자연수가 모두 있는지 확인하기 위해,
하나는 그 K 이하의 자연수가 몇 개나 들어있는지 확인하기 위해.

펜윅으로 할거면 이분탐색 하나 섞어주면 되겠죠.
금방 풀어버렸네..
*/
int MAX = 1'000'000;
int Q;
int q, x;

struct Fenwick {
//    int F[1'000'001];
    vector<int> F;
    int size = 1'000'001;
    Fenwick() {
        F = vector<int>(size, 0);
    }

    void update(int idx, int val) {
        while (idx < size) {
            F[idx] += val;
            idx += idx&-idx;
        }
    }

    int query(int idx) {
        int ret = 0;
        while (idx > 0) {
            ret += F[idx];
            idx -= idx&-idx;
        }
        return ret;
    }

    int query(int s, int e) {
        return query(e) - query(s-1);
    }
};

int main() {
    scanf("%d", &Q);
    Fenwick s, ms;
    while (Q--) {
        scanf("%d %d", &q, &x);
        if (q == 1) {
            ms.update(x, 1);
            int exist = ms.query(x, x);
            if (exist == 1)
                s.update(x, 1);
        } else {
            ms.update(x, -1);
            int exist = ms.query(x, x);
            if (!exist)
                s.update(x, -1);
        }
        int lo = 0, hi = MAX;
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            if (s.query(1, mid) != mid) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        printf("%d\n", ms.query(1, hi));
    }
}
