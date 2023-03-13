#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
using namespace std;
/*
11003번 최솟값 찾기

N개의 수와 L이 주어진다.
인덱스 max(0, i-L+1) ~ i까지의 수중 최솟값을 찾아보자.

C++은 신이 맞다...
Ruby로 했다가 이거 뭐 답이없네 하고 갖다 버리고,
Go로 했는데 뭐 입출력최적화 엄청 해야하는거같아서 포기하고,
오랜만에 C++이나 해볼까 하니까 된 문제

옛날에 비재귀세그트리 Python으로 풀었던 문제입니다.
갑자기 생각나서 그냥 덱으로 한번 더 풀었습니다.
모노큐(Mono Queue)라는 자료구조라네요.

어떤거냐면, 이 큐는 항상 단조증가하거나 단조감소하는 큐입니다.
그래서 새로운 요소를 넣을 때 자기 앞에 수가 자기보다 크거나(단조증가) 작으면(단조감소) 다 빼버리고 이 값을 넣습니다.
이거를 쭉 해주면 O(N)으로 처리할 수 있게 됩니다.
세그트리처럼 우선순위큐도 된다고 합니다. 그래도 덱이 정해라고하네요.
*/
struct node {
    int idx, num;
    node(int idx, int num) {
        this->idx = idx;
        this->num = num;
    }
};

int main() {
    fast;
    int N, L, t;
    string res = "";
    cin >> N >> L;
    deque<node> d;
    for (int i = 0; i < N; i++) {
        cin >> t;
        while (d.size() && i - d.front().idx >= L)
            d.pop_front();
        while (d.size() && d.back().num >= t)
            d.pop_back();
        d.push_back(node(i, t));
        cout << d.front().num << " ";
    }
}