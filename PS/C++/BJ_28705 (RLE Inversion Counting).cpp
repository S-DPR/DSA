#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define INFI (1<<30)
#define INFL (1LL<<60)
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
using namespace std;
/*
28705번 RLE Inversion Counting

빈 배열 A가 있다.
여기에 길이가 N인 수열을 K번 반복한 것을 M번 추가하려한다.
모든 연산을 끝낸 후, 모든 0 <= i < j < len(A) 에 대해
A[i] > A[j]를 만족하는 모든 (i, j)쌍의 개수를 구해보자.

이야~
세그트리 이악물고 안풀려고 했는데 대회에 딱 나왔네~
그런데 플레티넘 3이라서 이제 진짜 세그트리 점수가 하늘을 뚫고 올라가네..

인버전카운팅 유명하죠
너무너무 유명해서 대회에 여기에 2시간 떄려박았는데,
눈물나게도 compV에 매개변수 보낼 때 vector<ll> comp만 써서 시간초과..
vector<ll>& comp 이렇게했어야했는데..
아.............
아,.....
아...

여하튼 입력 다 받고, 단계를 세 번 정도로 나눠야하는데요.
1. 받은 배열에 대한 Inversion Counting 세기
2. 1번의 정보로 Inversion Counting 세기
3. 이전에 입력받은 배열에 대해 Inversion Counting 세기
이 세 방법으로 나누어 풀었습니다.

1번은 그냥 하면 되고..
2번은 K번 반복됨을 이용해 1부터 K-1까지 더한 값에 대해 그 수를 연속해 세어주면 됩니다.
[1 2 3]이 반복된다면, 이후에 [1 2 3] [1 2 3] ... 이렇게될텐데..
두번째 1부터 기준으로 보면 앞에 2 3이 하나 있고.. 세번째 1에서 다시 2 3 2 3 나올거고..
그래서 첫번째는 따로 처리해주고, 두번째부터 일괄적으로 처리하는겁니다.

세번째 단계때문에 세그트리를 두개로 나눠서 써야하는데요.
연산 한 번 정리 하고나서 새 세그트리에 다 넣어줘야합니다.
K = 5, N = 3, arr = [1, 2, 3]이면.. 세그트리에는 1을 5개, 2를 5개, 3을 5개.
이렇게 넣어주는식으로..

아 진짜 아쉽네 대회 시간 내에 못푼거
문제점을 하필 끝나고 2분뒤에 알았어
*/
struct info {
    ll K, N;
    vector<ll> arr;
};

ll segT[1'000'001];
ll localSegT[1'000'001];
ll ret;
ll MOD = 1e9 + 7;
int S;

ll query(ll* segT, int l, int r) {
    ll ret = 0;
    for (l += S, r += S; l <= r; l >>= 1, r >>= 1) {
        if (l & 1) ret += segT[l++];
        if (~r & 1) ret += segT[r--];
        ret %= MOD;
    }
    return ret%MOD;
}

void update(ll* segT, int idx, ll val) {
    for (idx += S; idx > 0; idx >>= 1)
        segT[idx] += val, segT[idx] %= MOD;
}

ll compV(vector<ll> &comp, ll x) {
    return lower_bound(comp.begin(), comp.end(), x) - comp.begin();
}

int main() {
    fast;
    int T; cin >> T;
    ll K, N, x;
    vector<info> Q;
    vector<ll> comp;
    for (int i = 0; i < T; i++) {
        Q.push_back(info{});
        cin >> Q[i].K >> Q[i].N;
        for (int j = 0; j < Q[i].N; j++) {
            cin >> x;
            Q[i].arr.push_back(x);
            comp.push_back(x);
        }
    }
    sort(comp.begin(), comp.end());
    comp.erase(unique(comp.begin(), comp.end()), comp.end());
    S = comp.size();
    for (int i = 0; i < T; i++) {
        ll Kth = (Q[i].K * (Q[i].K - 1) / 2) % MOD;
        for (int j = 0; j < Q[i].N; j++) {
            Q[i].arr[j] = compV(comp, Q[i].arr[j]) + 1;
            ret += Q[i].K * query(localSegT, Q[i].arr[j] + 1, S);
            ret %= MOD;
            ret += Q[i].K * query(segT, Q[i].arr[j] + 1, S);
            ret %= MOD;
            update(localSegT, Q[i].arr[j], 1);
        }
        for (int j = 0; j < Q[i].N; j++) {
            ret += query(localSegT, Q[i].arr[j] + 1, S) * Kth;
            ret %= MOD;
        }
        for (int j = 0; j < Q[i].N; j++) {
            update(segT, Q[i].arr[j], Q[i].K);
            update(localSegT, Q[i].arr[j], -1);
        }
        ret %= MOD;
    }
    cout << ret % MOD;
}