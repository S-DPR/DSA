#include <iostream>
#include <string.h>
#include <vector>
#include <stack>
#include <queue>
#define ll long long
using namespace std;
/*
4013번 ATM

처음으로 i번 정점에 가면 점수를 A[i]점 얻을 수 있다.
이 때, 도착정점 중 하나에 도착했을 때 최대 점수를 구해보자.
간선을 원하는 만큼 탈 수 있고, 정점도 원하는만큼 방문이 가능하다.

SCC+DP+위상
진짜 오랜만에 푸는 플레2문제
저번에 비슷한거 하나 겪어봐서 보자마자 그거대로 풀면 되겠는데? 싶어 바로 풀었습니다.

그냥 SCC만들고 그거로 그래프를 다시 그리면 DAG가 되니까 위상정렬dp쓰면 되겠다, 했고 실제로 그랬습니다.
그런데 가만 보면 SCC로 그래프 다시그리기는 좋은 방법을 찾아봐야할거같아요..
너무 복잡해 이거..
*/
vector<vector<int>> scc;
vector<int> G[500001];
vector<int> sccG[500001];
stack<int> stk;
int sccN[500001];
int treeN, tree[500001];
int S, EN, E[500001];
int indep[500001];
ll dp[500001];
int P[500001];
bool isScc[500001];
bool isVis[500001];

int dfs(int x) {
    tree[x] = ++treeN;
    int ret = tree[x];
    stk.push(x);
    for (int nxtN: G[x]) {
        if (!tree[nxtN]) {
            ret = min(ret, dfs(nxtN));
        } else if (!isScc[nxtN]) {
            ret = min(ret, tree[nxtN]);
        }
    }
    if (ret == tree[x]) {
        vector<int> cScc;
        while (true) {
            int item = stk.top(); stk.pop();
            cScc.push_back(item);
            isScc[item] = true;
            if (item == x) break;
        }
        scc.push_back(cScc);
    }
    return ret;
}

int main() {
    int N, K;
    scanf("%d %d", &N, &K);
    while (K--) {
        int u, v;
        scanf("%d %d", &u, &v);
        G[u].push_back(v);
    }
    for (int i = 1; i <= N; i++) {
        scanf("%d", &P[i]);
    }
    scanf("%d %d", &S, &EN);
    while (EN--) {
        int x; scanf("%d", &x);
        E[x] = 1;
    }

    dfs(1);
    int sz = scc.size();
    for (int i = 0; i < sz; i++) {
        for (int j: scc[i]) {
            sccN[j] = i;
        }
    }
    for (int i = 0; i < sz; i++) {
        for (int j: scc[i]) {
            for (int k: G[j]) {
                if (sccN[k] == sccN[j]) continue;
                sccG[i].push_back(sccN[k]);
                indep[sccN[k]]++;
            }
        }
    }
    queue<int> q;
    for (int i = 0; i < sz; i++) {
        if (!indep[i]) {
            isVis[i] = sccN[S] == i;
            q.push(i);
        }
    }
    while (!q.empty()) {
        int curN = q.front(); q.pop();
        if (isVis[curN]) {
            for (int i: scc[curN]) {
                dp[curN] += P[i];
            }
        }
        for (int i: sccG[curN]) {
            indep[i]--;
            if (!indep[i]) q.push(i);
            dp[i] = max(dp[i], dp[curN]);
            isVis[i] = isVis[i] || sccN[S] == i || isVis[curN];
        }
    }
    ll ret = 0;
    for (int i = 0; i < sz; i++) {
        int isEnd = 0;
        for (int j: scc[i]) {
            isEnd |= E[j];
        }
        if (isEnd) ret = max(ret, dp[i]);
    }
    printf("%lld", ret);
}
