#include <iostream>
#include <string.h>
using namespace std;
/*
24916번 용암 점프

크기가 N인 정렬된 집합 A가 주어진다.
i번쨰에서 시작해 모든 수를 한 번씩 밟을 수 있는지 알고싶다.
단, 이번에 이동한 거리가 x라면 다음 이동거리는 2x이상이어야한다.
모든 가능한 i에 대하여 가능 여부를 출력하자.
한 번만 하면 재미없으니 T번 하자.

수열 원소가 -10^6~10^6임을 알아채고 실제 가능한 N은 크게 작다는걸 간파해야하는 문제.
그다음은 dp인데 이건 뭐 잘해야죠..
저는 dp[i][j][k] = i~j까지 잡고있고 현재가 i위치면 k=0, j위치면 k=1로 잡아서 했습니다.
와.. 이게되네..
*/
int N;
int A[100000];
int dp[30][30][2];

int loop(int l, int r, int k) {
    if (l == 0 && r == N-1) return 1<<30;
    if (dp[l][r][k] != -1) return dp[l][r][k];
    int &x = dp[l][r][k];
    int cur = k ? A[r] : A[l];
    int goL = -1, goR = -1;
    if (0 <= l-1) {
        int ldst = abs(A[l-1]-cur);
        goL = loop(l-1, r, 0) >= ldst*2 ? ldst : -1;
    }
    if (r+1 < N) {
        int rdst = abs(A[r+1]-cur);
        goR = loop(l, r+1, 1) >= rdst*2 ? rdst : -1;
    }
    if (goL == -1 && goR == -1) x = -2;
    else if (goL != -1 && goR != -1) x = min(goL, goR);
    else if (goL != -1) x = goL;
    else x = goR;
    return x;
}

int main() {
    int T; scanf("%d", &T);
    while (T--) {
        scanf("%d", &N);
        for (int i = 0; i < N; i++) {
            scanf("%d", &A[i]);
        }
        if (N > 30) {
            while (N--) printf("NO\n");
            continue;
        }
        memset(dp, -1, sizeof dp);
        for (int i = 0; i < N; i++) {
            if (loop(i, i, 0) != -2) printf("YES\n");
            else printf("NO\n");
        }
    }
}
