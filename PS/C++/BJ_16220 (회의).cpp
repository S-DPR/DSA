#define _CRT_SECURE_NO_WARNINGS
#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
using namespace std;
/*
16220번 회의

N개의 시작점과 끝점이 주어진다.
어떤 한 점에서 두개 이상의 선이 겹친다면 그중 두개의 선을 지울 수 있는데,
그 횟수를 최대로 하려 한다. 최댓값을 구하시오.

입력이 50만줄이라 그런지 swift java 전부 터져버린 문제.
똑같은 논리였는데 터져서 그냥 C++ 들고 풀어버렸습니다.
역시 결전병기야.

쉬운 플레 5였습니다. 보자마자 이거 그리디구나~ 하고있었고.
우선순위큐도 당연히 생각나고.
골드2에서 그리디+우선순위 많이 봤는데 그거 아종이었고.
아마 사람들이 더 많이 풀면 골2에 머무르지 않을까 하는 문제였네요.

한 번 틀렸는데, 이유는 C++ 우선순위큐는 최대힙인거 까먹어서..
- 두 개 붙이고 바로 AC.
저는 구조체를 썼는데, 생각해보니 Pair라는게 C++에도 있더라고요. 그거쓸걸그랬네.
그냥.. 그리디답게 코드가 간단해서 로직은 보면 금방 알 수 있을겁니다.
*/
struct info {
    int s, e;
};

bool cmp(info i, info j) {
    return i.s == j.s ? i.e < j.e : i.s < j.s;
}

int main() {
    //freopen("C:/Users/users/Desktop/codeGarbage/py/input.txt", "r", stdin);
    fast;
    int N; cin >> N;
    vector<info> items;
    for (int i = 0; i < N; i++) {
        int s, e; cin >> s >> e;
        items.push_back(info{ s, e });
    }
    sort(items.begin(), items.end(), cmp);
    priority_queue<int> pq;
    int ret = 0;
    for (auto i : items) {
        while (!pq.empty() && -pq.top() < i.s) {
            pq.pop();
            if (!pq.empty()) {
                pq.pop();
                ret++;
            }
        }
        pq.push(-i.e);
    }
    cout << ret + pq.size() / 2;
}