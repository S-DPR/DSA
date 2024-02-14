#include <iostream>
#include <string.h>
#define ll long long
using namespace std;
/*
1200번 기상예측

N*M 맵을 세로선을 R개, 가로선을 C개 선택해 잘라 잘린 각 조각의 합의 최대가 최소가 되게 해보자.

솔직히 어렵다..라는 생각은 잘 안드는데,
누가봐도 이건 매개변수탐색이다, 하고있는데.
N이랑 M이 18 이하라는 점도 감안해서 브루트포스까지 잡았는데.. (2^20이 100만정도니까)
구현..구현이 살짝 쉽지않다..
대충 최악의경우 계산해보니 연산량 좀 많길래 비장의 C++꺼냈는데 괜히했나..
*/
int R, C, Rc, Cc;
ll M[18][18], pf[18];
int pos[18];

bool chk(ll mx) { 
    memset(pf, 0, sizeof pf);
    int row_cut = 0;
    for (int r = 0; r < R; r++) {
        int idx = 0;
        bool cur_cut = false;
        for (int c = 0; c < C; c++) {
            idx += pos[c];
            pf[idx] += M[r][c];
            cur_cut = cur_cut || pf[idx] > mx;
        }
        if (!cur_cut) continue;
        cur_cut = false;
        idx = 0;
        row_cut++;
        memset(pf, 0, sizeof pf);
        for (int c = 0; c < C; c++) {
            idx += pos[c];
            pf[idx] += M[r][c];
            cur_cut = cur_cut || pf[idx] > mx;
        }
        if (cur_cut) return false;
    }
    return row_cut <= Rc;
}

bool loop(ll mx, int cut, int idx) {
    if (cut == Cc) {
        return chk(mx);
    }
    bool ret = false;
    for (int i = idx; i < C; i++) {
        pos[i] = 1;
        ret = ret || loop(mx, cut+1, i+1);
        pos[i] = 0;
    }
    return ret;
}

int main() {
    scanf("%d %d %d %d", &R, &C, &Rc, &Cc);
    for (int r = 0; r < R; r++)
        for (int c = 0; c < C; c++)
            scanf("%lld", &M[r][c]);
    ll lo = 0, hi = 1<<30;
    while (lo < hi) {
        ll mid = (lo + hi) >> 1;
        if (loop(mid, 0, 1)) {
            hi = mid;
        } else {
            lo = mid + 1;
        }
    }
    printf("%lld", hi);
}
