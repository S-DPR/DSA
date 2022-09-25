#pragma GCC optimize("03")
#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
using namespace std;

/*
14897번 서로 다른 수와 쿼리 1

"아니 서로 다른 수와 쿼리 2랑 머가달라요?"
"왜 쟤는 다이아인데 얘는 플레따리에요?"
"하다못해 배열이나 쿼리의 개수도 같은데?"

라고 할 수 있겠지만, 문제를 읽어보면 살짝 이질감이 느껴질텐데요,
얘는 정말 정직하게 답만 순서에 맞춰 출력하면 되지만,
서로 다른 수와 쿼리 2는 뭐 쿼리에 이상한짓을 해놨죠.

차이점은 단 하나, Offline-Query의 가능여부입니다.
얘는 제가 쿼리를 멋대로 처리해도 별 문제가 없죠.
하지만, 전 쿼리의 정답이 현재 내가 처리할 쿼리에 영향을 준다면 달라집니다.
'반드시' 순서대로 처리해야하죠.

저도 처음엔 왜 쿼리에 이런 이상한 짓거리를 한 문제가 있나 했는데,
풀다보니 이유를 알게 되더군요.
'서로 다른 수와 쿼리 2'같은 문제는 Online-Query라고 합니다.
각 쿼리에 대해 순서대로 답을 내야한다는 거겠죠.

Online-Query는 Offline-Query에 비해 '반드시' 어려워집니다.
연산의 수가 증가해서가 아니에요, Offline-Query에선 
Mo's라는 기적의 알고리즘으로 편하게 풀 수 있는 문제가 많지만,
Online-Query는 그런거 사용 못합니다. (사실 쿼리 처리 속도도 대부분 온라인쿼리가 더 빨라요.)

Online-Query로 처리 가능한 모든 쿼리는 오프라인 쿼리 문제에서도 사용 가능합니다.
어쩌면 당연한거지만요.. 그래서 서로 다른 수와 쿼리 2 문제의 정답을 살짝 수정해서 여기에 붙여넣어도
정답처리가 됩니다.
걸리는 시간은, Mo's가 4572ms, PST가 1644ms입니다.

서론이 길었습니다.
어쨌든, 이 문제는 '수열과 쿼리 5'처럼 풀면 금방 풀리는 문제입니다.
좌표압축만 곁들이면요.
*/
struct node {
    int s, e, n;
};

int n;
int D;

bool compare(node a, node b) {
    int adiv = a.s / D;
    int bdiv = b.s / D;
    if (adiv == bdiv) {
        return a.e < b.e;
    } return adiv < bdiv;
}

int main()
{
    fast;
    int k, idx = 0;
    vector<int> arr;
    vector<int> brr;
    node Q[1000000];

    cin >> n;
    D = (int)sqrt(n);
    arr.resize(n); brr.resize(n);
    for (int i = 0; i < n; i++) cin >> arr[i];
    brr = arr;
    sort(brr.begin(), brr.end());
    brr.erase(unique(brr.begin(), brr.end()), brr.end());
    for (int i = 0; i < n; i++) arr[i] = lower_bound(brr.begin(), brr.end(), arr[i]) - brr.begin();
	//여기까지 좌표압축
	
    cin >> k;
    for (int i = 0; i < k; i++) {
        cin >> Q[i].s >> Q[i].e;
        Q[i].s--; Q[i].e--;
        Q[i].n = i;
    }
    sort(Q, Q + k, compare);
	//여기까지 Mo's
	
    int s = Q[0].s, e = Q[0].e;
    int cnt[1000000] = {0}, res = 0;
    int ans[1000000];
    for (int i = s; i <= e; i++) if (++cnt[arr[i]] == 1) res += 1;
    ans[Q[0].n] = res;
	// 여기까지 첫 쿼리 (이 문제를 볼때쯤 되면, 이 쿼리가 어떤식으로 처리가 되고있는건지 알거라 생각합니다.)
	
    for (int i = 0; i < k; i++) {
        node x = Q[i];
        while (x.s < s) if (++cnt[arr[--s]] == 1) ++res;
        while (x.s > s) if (--cnt[arr[s++]] == 0) --res;
        while (x.e > e) if (++cnt[arr[++e]] == 1) ++res;
        while (x.e < e) if (--cnt[arr[e--]] == 0) --res;
        ans[x.n] = res;
    }
	// 여기는 Mo's 처리,
	
    for (int i = 0; i < k; i++) cout << ans[i] << endl;
	// 마지막으로 정답 처리.
}