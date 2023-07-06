#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
using namespace std;
/*
25123번 좋은 단순 경로

트리가 있다. 각 트리에는 문자열이 하나씩 할당되어있다.
트리의 한 정점에서 시작해 간선을 하나씩 따라간다고 해보자.
단, 지나온 노드의 할당된 문자열을 지나온 순서대로 합쳤을 때 연속으로 두 번 나오는 문자가 없어야한다.
이것을 '좋은 단순 경로'라고 한다.
가장 긴 좋은 단순 경로의 길이와 그 개수를 출력해보자.

정답률 40% 넘었던거같은데 내가 맞추니까 25%가 됐네..
이건 파이썬잘못이라고 봅니다..
아니 근데 진짜 억울한게 파이썬 로직 그대로 C++ 들고오니까 맞춘다니까?
입출력 파일에 문제가있나봐요.
C++에는 진짜 뭔가 있는게 확실해
아 몰라 세상이 밉다 그냥

로직은.. 두 단계로 나뉘는데,
먼저 dp[i] = [현재 노드를 루트로 했을때 좋은단순경로의 최대길이, 그 개수]로 정의하고.
dfs를 돌립니다. 이건 간단하죠.

2번단계가 문제인데, 이는 위 dp를 이용해서
'현재 노드를 다리삼아 두 노드를 이었을 때 좋은단순경로의 최대길이와 그 개수'를 구해야합니다.
그니까.. 노드 i번을 고르고 그 i번의 자식노드 중 제일 긴거 두 개를 골라서 이을때 개수요.

이게 또 두 종류로 나뉘는데,
i노드의 자식노드 a, b, c, ... 가 있다고 합시다.
여기서 a, b가 제일 길게 뻗어있어요. 그러면 a, b를 선택해야하는데.
여기서 c도 a랑 길이가 같으면 c도 선택을 해야하거든요?
그래서 sum(a개수, b개수, c개수...) 이렇게 한 값을 S라 하면.
(a(S-a))+(b(S-a-b))+(c(S-a-b-c))+...
이걸 더해야합니다. 이건 조합론이야기고요.

다른 하나는 제일 긴거 하나 그다음 긴거 하나 있는거에요.
그냥 자식노드중에 있는거 그 길이들 다 합쳐서 둘이 곱하면 끝입니다.

하아.. 이상한문제였어..
*/
struct info {
    ll l, c;
};

int N, p, r;
char S[100001];
vector<int> G[100001];
info dp[100001];

info dfs(int cur) {
    for (int x : G[cur]) {
        info item = dfs(x);
        ll l = item.l, c = item.c;
        if (S[x] == S[cur]) continue;
        if (dp[cur].l < l + 1)
            dp[cur] = info{ l + 1, c };
        else if (dp[cur].l == l + 1)
            dp[cur].c += c;
    }
    return dp[cur];
}

int main() {
    fast;
    int T; cin >> T;
    string s;
    while (T--) {
        cin >> N >> s;
        for (int i = 0; i <= N; i++) {
            G[i] = vector<int>();
            S[i + 1] = s[i];
        }
        for (int i = 1; i <= N; i++) {
            cin >> p; G[p].push_back(i);
            dp[i] = info{ 1, 1 };
            if (!p) r = i;
        }
        dfs(r);
        ll rL = 0, rC = 0;
        for (int i = 1; i <= N; i++) {
            map<int, vector<ll>> items;
            for (int j : G[i]) {
                ll l = dp[j].l, c = dp[j].c;
                if (S[i] == S[j]) continue;
                items[l].push_back(c);
            }
            items[0] = vector<ll>();
            items[0].push_back(1);
            int largest = items.rbegin()->first;
            auto largest_item = items[largest];
            items.erase(largest);
            items[0] = vector<ll>();
            items[0].push_back(1);
            int sec_large = items.rbegin()->first;
            auto sec_largest_item = items[sec_large];
            if (largest_item.size() >= 2) {
                if (rL > largest * 2 + 1) continue;
                if (rL < largest * 2 + 1) {
                    rL = largest * 2 + 1;
                    rC = 0;
                }
                ll sum = accumulate(largest_item.begin(), largest_item.end(), 0);
                for (int x : largest_item) {
                    sum -= x;
                    rC += sum * x;
                }
            }
            else {
                if (rL > largest + sec_large + 1) continue;
                if (rL < largest + sec_large + 1) {
                    rL = largest + sec_large + 1;
                    rC = 0;
                }
                ll left = accumulate(largest_item.begin(), largest_item.end(), 0);
                ll right = accumulate(sec_largest_item.begin(), sec_largest_item.end(), 0);
                rC += left * right;
            }
        }
        cout << rL << " " << rC << endl;
    }
}