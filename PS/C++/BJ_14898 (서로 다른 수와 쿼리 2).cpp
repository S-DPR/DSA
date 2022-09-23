#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
#define MAX_NUM 1000002
using namespace std;
/*
14898번 서로 다른 수와 쿼리 2

읽는 사람이 Persistent Segment Tree 자료구조에 대해 이해했다고 가정합니다.
문제는 초등학생도 이해할만큼 간단합니다.
"수열 드림. 여기서 l번~r번 인덱스에 서로 다른 수가 몇개가있어??"
근데 푸는 방법은 어렵습니다. 누가 이런 아이디어를 생각해냈는진 모르지만, 그냥 대단해요.

사실 푸는 방법이 세개정도가 있는데, 초기 아이디어는 모두 똑같습니다.
초기 아이디어 떠올리기가 정말정말 어렵다고 생각돼요.
*/
struct Node {
    int l = 0; int r = 0; int val = 0;
    Node(int val = 0, int l = 0, int r = 0) { l = l; r = r; val = val; }
};

vector <Node> seg = { Node() };
vector <int> root = { 0 };
int seg_size = 1;
int root_size = 0;

// 업데이트에 val 변수는 필요 없습니다. 어차피 인덱스 기반 트리라 1씩 증가하니까요.
void internal_update(int prev, int cur, int s, int e, int idx) {
    seg.push_back(Node()); seg_size++;
    if (s == e) {
        seg[cur].val = seg[prev].val+1; return;
    }
    seg[cur] = seg[prev];
    seg[cur].val++;
    int m = s + e >> 1;
    if (idx <= m) {
        seg[cur].l = seg_size;
        internal_update(seg[prev].l, seg[cur].l, s, m, idx);
    }
    else {
        seg[cur].r = seg_size;
        internal_update(seg[prev].r, seg[cur].r, m + 1, e, idx);
    }
}

void update(int idx) {
    root.push_back(seg_size); root_size++;
    internal_update(root[root_size-1], root[root_size], 1, MAX_NUM, idx);
}


// 쿼리입니다.
int internal_query(int prev, int cur, int s, int e, int k) {
    if (k < s) return seg[cur].val - seg[prev].val; // k가 s보다 작은경우 값을 반환합니다.
    if (s == e || e <= k) return 0; // k가 e보다 크면, 이제 무의미한 값만 남을테니 갖다 버립니다.
    int m = s + e >> 1;
    return internal_query(seg[prev].l, seg[cur].l, s, m, k) + \
        internal_query(seg[prev].r, seg[cur].r, m + 1, e, k);
}

int query(int l, int r) {
    return internal_query(root[l - 1], root[r], 1, MAX_NUM, r);
}

int main()
{
    fast;
    int i, t, m, res = 0;
	
    vector <int> arr;
    vector <int> brr;
    cin >> i;
    arr.resize(i);
    brr.resize(i);
    for (int j = 0; j < i; j++) cin >> arr[j];
    brr = arr;
    sort(brr.begin(), brr.end());
    brr.erase(unique(brr.begin(), brr.end()), brr.end());
    for (int j = 0; j < i; j++) arr[j] = ++lower_bound(brr.begin(), brr.end(), arr[j]) - brr.begin();
    // 여기까지 좌표압축.
	
	int tmp[MAX_NUM] = { 0 };
    vector<int> crr(i); fill(crr.begin(), crr.end(), i + 1);
    int k = 1;
    for (int x : arr) {
        if (tmp[x] > 0) crr[tmp[x] - 1] = k;
        tmp[x] = k++;
    }
	// 이부분이 핵심 아이디어입니다.
	// 이 방법을 푸는 대부분의 방법은 이 아이디어를 거의 필수적으로 채용합니다.
	// 뭐냐면, 배열에서 각 인덱스에 수가 있을겁니다. arr[idx]를 그 수라 하면, 다음 arr[idx]가 나오는 인덱스를 집어넣는겁니다.
	// 예를들어 1 2 3 1 2 3이 있다면, [4, 5, 6, 7, 7, 7]로 설정하는거죠. 마지막에 나온 수는 현 배열보다 1 더 크게 설정했습니다.
	// 이 새 배열로 PST 업데이트를 진행합니다. 그러면 쿼리에서 리프노드까지 안내려가고 빠르게 풀 수 있게됩니다.
	
    for (auto x : crr) update(x);
    cin >> i;
    while (i--) {
        cin >> t >> m;
        t += res;
        res = query(t, m);
        cout << res << '\n';
    }
}