#include <iostream>
#include <vector>
#include <set>
using namespace std;
/*
25549번 트리의 MEX

N개의 정점으로 이루어진 트리가 있다.
각 정점에는 0 이상 N 미만의 수가 적혀있다.
주어진 트리에서 모든 정점에 대해 mex(i)를 구해보자.
mex(i)란, i번째 정점을 루트로 하는 모든 서브트리에서 나오는 정점 배열에 적혀있지 않은 수 중 최솟값이다.

Small To Large트릭 처음 배워본 문제.
음..
set 두개에 따로따로 박다가 MLE에 당해 멸망해버렸습니다.

암튼 이런식으로 짜는구나~ 하게되네요.
신기하네.
*/
int N, root, x, R[200001];
set<int> A[200001];
vector<int> G[200001];

void dfs(int x) {
    for (int &i: G[x]) {
        dfs(i);
        R[x] = max(R[x], R[i]);
        if (A[x].size() < A[i].size()) A[x].swap(A[i]);
        A[x].insert(A[i].begin(), A[i].end());
        A[i].clear();
    }
    while (A[x].find(R[x]) != A[x].end()) R[x]++;
}

int main() {
    scanf("%d", &N);
    for (int i = 1; i <= N; i++) {
        scanf("%d", &x);
        if (x == -1) {
            root = i;
            continue;
        }
        G[x].emplace_back(i);
    }
    for (int i = 1; i <= N; i++) {
        scanf("%d", &x);
        A[i].emplace(x);
    }
    dfs(root);
    for (int i = 1; i <= N; i++) {
        printf("%d\n", R[i]);
    }
}
