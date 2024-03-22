#include <iostream>
#include <algorithm>
#include <string.h>
using namespace std;
/*
19553번 피자 배틀

A와 B가 피자를 먹으려 한다.
크기가 S인 피자를 먹는데는 S의 시간이 걸린다.
A와 B 모두의 손이 비어있으면 A가 먼저 피자를 고른다.
처음에는 아무 피자나 고를 수 있고, 이후에는 한 쪽이 먹힌 피자만 고를 수 있다.
이 때, A와 B가 모두 최선의 수를 둘 때 A가 먹을 수 있는 최대 피자조각의 크기의 합을 구하시오.

전체적으로 옛날옛적 1년전에 푼 케이크 자르기였나 그 문제 상위호환.
그때는 B가 하는 행동이 단순하게 유지됐는데 이젠 최선의 수를 둔다고 합니다.
그거때문에 이제 단순하게 1000*1000이 안되고 1000*1000*200을 세워야하고..
그거 외엔 다 똑같은데, 그거때문에 3티어 올라갔네요.

그나저나 풀이 보니 1000*1000*2로 200ms정도에 푼사람이 있더라고요.
내꺼는 3500ms인데..
한번 코드 분석이나 해봐야겠습니다.
*/
int N;
int A[1000], dp[1000][1000][201];

int loop(int l, int r, int f, int s) {
    int nxtL = (l-1+N)%N;
    int nxtR = (r+1)%N;
    if (nxtL == r) return 0;
    if (f != 0 && s != 0) {
        int kill_time = min(f, s);
        return loop(l, r, f-kill_time, s-kill_time);
    }
    int time_diff = f-s+100;
    int &cur = dp[l][r][time_diff];
    if (cur != -1) return cur;
    if (f == 0) {
        int eatL = loop(nxtL, r, A[nxtL], s) + A[nxtL];
        int eatR = loop(l, nxtR, A[nxtR], s) + A[nxtR];
        cur = max(eatL, eatR);
    } else {
        int eatL = loop(nxtL, r, f, A[nxtL]);
        int eatR = loop(l, nxtR, f, A[nxtR]);
        cur = min(eatL, eatR);
    }
    return cur;
}

int main() {
    scanf("%d", &N);
    for (int i = 0; i < N; i++) {
        scanf("%d", &A[i]);
    }
    memset(dp, -1, sizeof dp);
    int mx = 0;
    for (int i = 0; i < N; i++) {
        mx = max(mx, loop(i, i, A[i], 0)+A[i]);
    }
    printf("%d", mx);
}
