#include <iostream>
#include <string.h>
#define ll long long
using namespace std;
/*
28020번 Attention

두 수열 A B가 있다.
여기서 수 세개를 골랐을 때, A, B에서 위치가 모두 증가하는 형태인 경우의 수가 몇개일까?

세상이밉다정말

NEW한 아이디어를 GET했습니다.
A에서 각 수가 어디에 나오는지 적어둡시다. 이걸 C라고 하자고요.
그럼 이제 B의 각 요소가 C에서 어디에 나오는지 D에 적습니다.
아니?? 그러면 이제 D에 A B가 압축이 된 형태가 됩니다!
D에서 증가하면 A에서도 증가해요! 와?

그럼 이제 세그트리 두개 써서 하나는 길이가 1인 개수, 하나는 길이가 2인 개수로 하고,
ret에는 길이가 2인거에서 쿼리써서 처리하면 됩니다.
*/
int N;
int A[100001];
int B[100001];
int C[100001];
int D[100001];

struct Fenwick {
    int F[100002] = {0};

    void update(int idx, int val) {
        while (idx <= N) {
            F[idx] += val;
            idx += idx&-idx;
        }
    }

    ll query(int idx) {
        ll ret = 0;
        while (idx > 0) {
            ret += F[idx];
            idx -= idx&-idx;
        }
        return ret;
    }

    ll query(int l, int r) {
        return query(r)-query(l-1);
    }
};

int main() {
    scanf("%d", &N);
    for (int i = 1; i <= N; i++) {
        scanf("%d", &A[i]);
        C[A[i]] = i;
    }
    for (int i = 1; i <= N; i++) {
        scanf("%d", &B[i]);
        D[i] = C[B[i]];
    }
    Fenwick f, s;
    ll ret = 0;
    for (int i = 1; i <= N; i++) {
        f.update(D[i], 1);
        s.update(D[i], f.query(1, D[i]-1));
        ret += s.query(1, D[i]-1);
    }
    if (ret) {
        printf("My heart has gone to paradise\n%lld", ret);
    } else {
        printf("Attention is what I want\n");
    }
}
