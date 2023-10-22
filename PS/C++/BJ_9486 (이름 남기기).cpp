#include <iostream>
#include <algorithm>
#include <string.h>
#define ll long long
using namespace std;
/*
9486번 이름 남기기

어떤 문자열을 쓰려 한다. 아래 네가지 규칙이 있다.
1. 현재 커서의 왼쪽에 글자가 삽입된다. : 비용 1
2. 현재 커서를 왼쪽 혹은 오른쪽 글자로 옮긴다 : 비용 1
3. 현재 설정된 글자를 바꾼다. A, B, C, ..., Z, A, B, ... 순으로 가능하다. : 비용 1
4. 현재 설정된 글자를 바꾼다. A, Z, X, ..., A, Z, X, ... 순으로 가능하다. : 비용 1
쓰려는 문자열이 주어질 때, 최소비용을 구해보자.

이야 이게 비트dp네
진짜 dp의세계는 언제봐도 새롭다..

현재 커서의 왼쪽에 글자가 삽입된다는 말은, i번째 글자를 골랐으면 i+1번째에 글자가 삽입된다는 의미.
그리고 왼쪽, 오른쪽으로 이동하는데는 vis에서 이동하려는데까지 글자가 몇개있나를 세면 됩니다.
이런거 고려해서 두 분이 플레5로 책정한거같네요. 적절한 난이도 같기도 하고..

아직도 역추적 매달려서 못푸는 플레5 비트dp가 있어서.. 이거라도 열심히 때려잡았습니다..
*/
char s[20];
int len;
int dp[18][1<<18];

int loop(int cur, int vis, int isFirst) {
    if (vis+1 == 1<<len) return 0;
    if (dp[cur][vis] != -1) return dp[cur][vis];
    dp[cur][vis] = 1<<30;
    int curC = s[cur] - 'A';
    if (isFirst) curC = 0;
    for (int i = 0; i < len; i++) {
        if (vis&(1<<i)) continue;
        int move = 0;
        for (int j = cur+1; j < i; j++)
            if (vis&(1<<j)) move++;
        for (int j = cur; j > i-1; j--)
            if (vis&(1<<j)) move++;
        int nxtC = s[i] - 'A';
        int dist = abs(curC - nxtC);
        dist = min(dist, 26-dist);
        dp[cur][vis] = min(dp[cur][vis], loop(i, vis|(1<<i), 0)+move+dist+1);
    }
    return dp[cur][vis];
}

int main() {
    while (true) {
        scanf("%s", &s);
        if (!strcmp(s, "0")) break;
        len = strlen(s);
        memset(dp, -1, sizeof dp);
        printf("%d\n", loop(0, 0, 1));
    }
}
