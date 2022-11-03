#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
#define MAX 524288
using namespace std;
/*
16910번 mex와 쿼리

mex(arr)은 arr에 들어있지 않은 가장 작은 자연수를 반환합니다.
예를들어 arr = [10,1,4,5,2,4]일 때 mex(arr)은 3입니다.
이제, 쿼리가 N개 주어집니다. 각 쿼리는 Q, L, R 세 자연수로 이루어져있습니다.
Q = 1이면, L부터 R까지의 수를 배열에 추가합니다. 단, 이미 있는 수는 추가하지 않습니다.
Q = 2이면, L부터 R까지의 수를 배열에서 제거합니다. 단, 이미 없는 수는 제거하지 않습니다.
Q = 3이면, L부터 R까지의 수중 있는것은 제거하고, 없는것은 추가합니다.
각 쿼리를 진행했을 때 mex(arr)의 값을 구해주세요. 초기 arr에는 어떤 수도 존재하지 않습니다.
(1 <= N <= 100000, 1 <= Q <= 3, L <= R이고 1 <= L, R <= 10^18)

쿼리문제주제에 10^9가 최대가 아닌, 10^18이 최대인 변종입니다.
첨엔 10^18 보고 다이나믹 세그트리 쓰면 되겠다 했는데, 그래도 안되더라고요.
태그 오픈하고 본 값/좌표압축은 생각도 못했는데, 머 어떻게 하다보니까 되긴하네요..?
별개로, 세그트리 구현 자체에 상당히 시간을 쏟은 문제입니다.
그닥 효율적이진 않게, 750ms나 나왔지만..
MAX값 반토막내면 650ms까지 줄더군요.
*/
struct Node {
    ll l = 0, r = 0, v = 0;
    int lazy = 0;
};

struct QueryNode {
    ll q, s, e;
    QueryNode() {}
    QueryNode(ll q_, ll s_, ll e_) {
        q = q_; s = s_; e = e_;
    }
};

struct DS {
    vector<Node> S = { Node(), Node() };
    ll seg_size = 1;
    // kind 1 is query 1 (ADD ALL), kind 2 is query 2 (RM ALL), kind 3 is query 3 (reversal)
    // lazy 0 means "No Behavior"
	// propa함수는 현재 노드에 대해 어떤 쿼리가 내려왔는지 판단하고, 현재 노드의 lazy값을 바꾸어줍니다.
    void propa(ll n, int kind) {
        switch (S[n].lazy) {
        case 0: {
            S[n].lazy = kind;
            break;
        }
        case 1: {
            switch (kind) {
            case 2: case 3:
                S[n].lazy = 2; break;
            }
            break;
        }
        case 2: {
            switch (kind) {
            case 1: case 3:
                S[n].lazy = 1; break;
            }
            break;
        }
        case 3: {
            switch (kind) {
            case 1: S[n].lazy = 1; break;
            case 2: S[n].lazy = 2; break;
            case 3: S[n].lazy = 0; break;
            }
            break;
        }
        }
    }
	
	// reset_value함수는 n에 있는 lazy의 값을 보고 n의 값을 바꾸어줍니다.
    void reset_value(ll n, ll s, ll e) {
        // Reset value of Node n
        ll tmp = sum_query(1, 1, MAX, s, e);
        switch (S[n].lazy) {
        case 1:
            S[n].v += (e - s + 1) - tmp;
            break;
        case 2:
            S[n].v -= tmp;
            break;
        case 3:
            S[n].v -= tmp - ((e - s + 1) - tmp);
            break;
        }
    }
	
	// reset_value에 사용됩니다.
    ll sum_query(ll n, ll s, ll e, ll l, ll r) {
        if (r < s || e < l) return 0;
        if (l <= s && e <= r) return S[n].v;
        ll m = (s + e) >> 1;
        return sum_query(S[n].l, s, m, l, r) + sum_query(S[n].r, m + 1, e, l, r);
    }
	
	// propagate는 현재 노드가 좌, 우 노드가 있는지 확인한 뒤 없다면 만들고,
	// lazy가 있는지 판단해 reset_value함수를 호출하여 값을 수정한뒤,
	// 현재 lazy값을 좌, 우에 전달해주고 현재 노드의 lazy를 초기화합니다.
    void propagate(ll n, ll s, ll e) {
        // When n don't have left or right, make them
        if (!(S[n].l)) {
            S[n].l = ++seg_size;
            S.push_back(Node());
        }
        if (!(S[n].r)) {
            S[n].r = ++seg_size;
            S.push_back(Node());
        }
        if (S[n].lazy) {
            // Reset value of Node n
            reset_value(n, s, e);
            if (s != e) {
                propa(S[n].l, S[n].lazy);
                propa(S[n].r, S[n].lazy);
            }
            S[n].lazy = 0;
        }
    }
	
	// update입니다. lazy범위의 공통조상을 찾아 lazy값을 업데이트한 뒤, 값을 업데이트하고,
	// 왼쪽과 오른쪽에 업데이트 lazy를 전달해준 뒤 현재 lazy값을 초기화합니다.
    void update(ll n, ll s, ll e, ll l, ll r, int kind) {
        propagate(n, s, e);
        ll m = (s + e) >> 1;
        if (r < s || e < l) return;
        if (l <= s && e <= r) {
            propa(n, kind);
            reset_value(n, s, e);
            if (s != e) {
                propa(S[n].l, kind);
                propa(S[n].r, kind);
            }
            S[n].lazy = 0;
            return;
        }
        update(S[n].l, s, m, l, r, kind);
        update(S[n].r, m + 1, e, l, r, kind);
        S[n].v = S[S[n].l].v + S[S[n].r].v;
    }
	
	// mex를 찾는 쿼리입니다.
    ll query(ll n, ll s, ll e) {
        propagate(n, s, e);
        ll m = (s + e) >> 1;
        propagate(S[n].l, s, m);
        if (s == e) return s;
        if (S[S[n].l].v < (e - s + 1) / 2) return query(S[n].l, s, m);
        else return query(S[n].r, m + 1, e);
    }
	
	// update는 밖에서 이 함수로 호출하고,
    void update(ll l, ll r, int kind) {
        update(1, 1, MAX, l, r, kind);
    }

	// 최종 mex를 찾는 쿼리는 이 함수로 호출합니다.
    ll query() {
        return query(1, 1, MAX);
    }
};

int main()
{
    fast;
    int Q; cin >> Q;
    vector<QueryNode> Query(Q);
    vector<ll> comp;
    comp.push_back(1);
    for (int i = 0; i < Q; i++) {
        ll q, l, r; cin >> q >> l >> r;
        Query[i] = QueryNode(q, l, r);
        comp.push_back(l);
        comp.push_back(r);
        comp.push_back(r + 1);
    }

    sort(comp.begin(), comp.end());
    comp.erase(unique(comp.begin(), comp.end()), comp.end());
    vector<ll> uncomp = comp; DS t;
    for (QueryNode& x : Query) {
        ll pres_s = ++lower_bound(comp.begin(), comp.end(), x.s) - comp.begin();
        ll pres_e = ++lower_bound(comp.begin(), comp.end(), x.e) - comp.begin();
        t.update(pres_s, pres_e, x.q);
        cout << uncomp[t.query() - 1] << endl;
    }
}