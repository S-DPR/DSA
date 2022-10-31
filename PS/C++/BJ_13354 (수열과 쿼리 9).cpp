#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
#define MAX_SIZE 100001
using namespace std;
/*
13354번 수열과 쿼리 9

수열의 길이 n이 첫 줄에 주어집니다.
이후 수열 A와 수열 B가 두 줄에 걸쳐 주어집니다.
그다음 쿼리의 개수 Q가 주어집니다.
마지막으로 쿼리가 x, y, k의 꼴로 Q개 주어집니다.
각 쿼리에 대해 A(i)*B(j) <= k (x <= i, j <= y)인 i, j의 개수를 출력해주세요.
(1 <= n <= 100000, 1 <= x, y <= 100000, 1 <= A(i), B(i) <= 100000, 1 <= k <= 100000)

신이 지혜를 내리지 않으면 올라오지 못하는 오랜만의 C++ PS입니다.
제한시간이 6초인 기묘한 문제입니다.
2, 3, 5는 많이 봤는데 6초..?
뭐, 그렇다고요.
옛날엔 이게 왜 세그트리?? 어캐품?? 했었는데..

문제 풀이를 생각해보면,
A(i) * B(j) <= k를 조금 만지면,
B(j) <= k / A(i)가 되고,
결론적으로 k / A(i)가 정수부가 바뀌는 부분만 골라잡아서 배열을 하나 전처리 해두고 써먹으면 될겁니다.
A와 B에 대해선 그냥 Mo's 때리면서 모든 수를 그려넣어두면 되겠죠. 일단 위 일을 먼저 해결해야합니다.

어떤 수 x에 대해, sqrt(x)까지 x를 나누어봅시다.
x/1, x/2, x/3 ... x/sqrt(x) 이렇게요.
여기서, 나누었을때 정수부가 달라지는 부분을 골라냅시다.
만약에 x/1, x/2, x/3..이 각각 정수부가 달라진다면 1 2 3..을 골라내야 할겁니다.
추가로 (x/1) + 1, (x/2) + 1, (x/3) + 1.. 도 x를 나누었을 때 최초로 정수부가 달라지는 수입니다.
마지막으로, 만약에 현재 수가 다음 제곱수와의 차가 다음 제곱수의 sqrt값 이하라면,
현재 sqrt의 정수부+1의 값도 최초로 정수부가 달라지는 수입니다.
(파이썬 코드로 보면, 현재 수를 i라 할 때 [(int(i**.5)+1)**2 - i <= int(i**.5)]를 만족한다면.
이 부분은 init_share함수를 참고해주시길 바랍니다.

1부터 100000까지 위 식을 적용하면 O(NsqrtN)만에 1~100000까지의 나누었을 때 정수부가 나오는 부분의 배열을 얻을 수 있습니다.
(만약에 차례대로 push_back해서 sort까지 해야한다면 100% 시간초과납니다.)
이제 쉽습니다. Mo's를 적당히 적용하면 될겁니다.
나중을 위해 예시를 들어두면, 10의 경우 위 규칙을 적용하면 나오는 수는 1, 2, 3, 4, 6, 11입니다.
이제 i를 0부터 arr.size()-2까지, j는 arr.size()-1부터 0까지 돌립시다.
arr에 Mo's를 적용한 것에서는 arr[i], arr[i+1]-1을 l, r로 잡아 쿼리를 돌리고,
brr에 Mo's를 적용한 것에서는 1, brr[j]-1을 l, r로 잡아 쿼리를 돌린 뒤 둘을 곱합니다.
그러면 arr[i], arr[i+1]-1에 대해 A(i)*B(j)<=k인 i, j의 쌍을 arr.size()-1번만에 구할 수 있습니다.

시간복잡도는 O(QlgQ+NlgNsqrtN)이라는 모양이 나오게됩니다.
*/
struct Query {
    int s = 0, e = 0, n = 0, k = 0;
    Query(){}
    Query(int st, int en, int K, int N) {
        s = st; e = en; k = K; n = N;
    }
};

struct Fenwick {
    vector<ll> F;
    Fenwick(int size) {
        F.resize(size, 0);
    }
    void update(int idx, ll val) {
        for (idx; idx < MAX_SIZE; idx += idx & -idx)
            F[idx] += val;
    }
    ll query(int idx) {
        ll res = 0;
        for (idx; idx > 0; idx -= idx & -idx)
            res += F[idx];
        return res;
    }
    ll query(int l, int r) {
        return query(r) - query(l - 1);
    }
};

void init_share(vector<vector<int>> &k) {
    k.resize(100001);
    int total = 0;
    for (int i = 1; i < MAX_SIZE; i++) {
        double sqrtii = sqrt(i);
        if (sqrtii == (int)sqrtii) total += 2;
        int sqrti = (int)sqrtii + 1;
        k[i].resize(total, -1);
        if (sqrti * sqrti - i <= sqrti) {
            k[i].push_back(0);
            k[i][total / 2] = sqrti;
        }
        int now = -1, idx = 0;
        for (int j = 1; j < sqrti; j++) {
            if (now == i / j) continue;
            k[i][idx] = j;
            k[i][k[i].size() - idx - 1] = (i / j) + 1;
            now = i / j; idx++;
        }
    }
}

int n;
int n_sqrt;

bool comp(Query i, Query j) {
    int A = i.s / n_sqrt;
    int B = j.s / n_sqrt;
    if (A == B) return i.e < j.e;
    return A < B;
}

// 배열이나 구조체의 주솟값을 넘겨주게 하는것과 그렇지 않은것에는 속도에 엄청난 차이를 나게 합니다.
ll cnt(vector<int>& k, Fenwick &arrcnt, Fenwick &brrcnt) {
    ll res = 0;
    for (int i = 0, j = k.size() - 1; i < k.size() - 1; i++, j--)
        res += arrcnt.query(k[i], k[i + 1] - 1) * brrcnt.query(1, k[j] - 1);
    return res;
}

int main()
{
    fast;
    vector<vector<int>> k;
    init_share(k);

    cin >> n;
    n_sqrt = (int)sqrt(n);
    int arr[MAX_SIZE], brr[MAX_SIZE];
    for (int i = 1; i <= n; i++) cin >> arr[i];
    for (int i = 1; i <= n; i++) cin >> brr[i];

    int q;
    cin >> q;
    vector<Query> Q(q);
    for (int i = 0; i < q; i++) {
        cin >> Q[i].s >> Q[i].e >> Q[i].k; 
        Q[i].n = i;
    }
    sort(Q.begin(), Q.end(), comp);

    Fenwick arrcnt(MAX_SIZE);
    Fenwick brrcnt(MAX_SIZE);
    for (int i = Q[0].s; i <= Q[0].e; i++) {
        arrcnt.update(arr[i], 1);
        brrcnt.update(brr[i], 1);
    }
    int s = Q[0].s, e = Q[0].e;
    ll ans[MAX_SIZE] = { 0 };
    for (auto x : Q) {
        while (x.s < s) {
            s--;
            arrcnt.update(arr[s], 1);
            brrcnt.update(brr[s], 1);
        }
        while (x.s > s) {
            arrcnt.update(arr[s], -1);
            brrcnt.update(brr[s], -1);
            s++;
        }
        while (x.e < e) {
            arrcnt.update(arr[e], -1);
            brrcnt.update(brr[e], -1);
            e--;
        }
        while (x.e > e) {
            e++;
            arrcnt.update(arr[e], 1);
            brrcnt.update(brr[e], 1);
        }
        ans[x.n] = cnt(k[x.k], arrcnt, brrcnt);
    }
    for (int i = 0; i < q; i++) cout << ans[i] << endl;
}
// 여담으로 시험보다가 정수부 바뀌는 부분 규칙을 찾아내서 풀었습니다.