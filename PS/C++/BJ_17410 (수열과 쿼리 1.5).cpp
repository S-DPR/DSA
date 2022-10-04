#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
using namespace std;
/*
17410번 수열과 쿼리 1.5

10의 4승을 1000이라고 착각을 2시간동안 해서 틀렸습니다 세례를 받았습니다.
하아..

문제는 정말 간단합니다. 배열을 일단 받고요.
수열과 쿼리 1에서 연산만 하나 추가한건데,
쿼리 1은 업데이트 연산입니다. 1 i v로 주어지며 i번째 원소를 v로 바꿉니다.
쿼리 2는 쿼리 연산입니다. 2 l r k로 주어지며 l부터 r까지 원소중 k보다 큰 수의 개수를 출력하면 됩니다.

Mo's를 쓰자니 업데이트 연산때문에 전부 망가질 가능성이 높고 (애초에 k가 매번 바뀌어서 적합하지 않음)
PST를 쓰자니 역시 업데이트 연산떄문에 업데이트를 진행할 수 없습니다.
머지소트트리를 쓰자니 역시 업데이트 연산떄문에 전부 망가집니다.
그렇다고 일반 세그트리로 이분탐색을 하자니 l부터 r까지라는 범위가 너무 거슬립니다.

이 문제는 평방분할이라는 방법을 이용하여 푸는 문제입니다.
저도 개념만 대충 알고있었는데 직접 써보는건 처음이었습니다.
개념설명은 넘어가겠습니다.
*/

// k보다 큰 수를 세는데에는 펜윅트리를 사용했습니다.
// 새로 배열을 만들어서 하거나 정렬을 계속 하려니까 시간이 너무 느린데,
// 세그트리 쓰면 결국 O(lgN)걸리니까 시간이 충분하더라고요.
// 시간이 까다로워보이니 세그트리말고 펜윅트리로 바꾸었습니다. 구현도 귀찮고..
void update(vector<int>& arr, int idx, int val) {
    for (idx; idx < arr.size(); idx += idx & -idx)
        arr[idx] += val;
}

int query(vector<int>& arr, int idx) {
    int res = 0;
    for (idx; idx > 0; idx -= idx & -idx)
        res += arr[idx];
    return res;
}

int main()
{
    fast;
    vector<int> arr;
    int n = 0; cin >> n; arr.resize(n);
    for (int i = 0; i < n; i++)
        cin >> arr[i];

	// 평방분할 부분입니다. 여기서 10001을 1001로 써서 여덟번은 틀렸습니다..
    int sqrtn = sqrt(n);
    vector<vector<int>> t(n/sqrtn, vector<int>(10001, 0));
    for (int i = 0; i < (n / sqrtn); i++)
        for (int j = 0; j < sqrtn; j++)
            update(t[i], arr[j + i * sqrtn], 1);

    int Q; cin >> Q;
    while (Q--) {
        int command; cin >> command;
        switch (command) {
        case 1: {
            int idx, val; cin >> idx >> val; idx--;
            int tidx = idx / sqrtn;
			// 업데이트 처리에선, 평방분할이 된 곳은 현재 수 제거 -> 새로운 수 업데이트 방식을 사용했습니다.
			// O(1) + O(2lgN), 업데이트연산엔 O(lgN)이 걸립니다.
            if (tidx < t.size()) {
                update(t[tidx], arr[idx], -1);
                arr[idx] = val;
                update(t[tidx], arr[idx], 1);
            }
            else arr[idx] = val; // 종종 나오는 sqrt(N)개 미만 떨거지는 그냥 배열업데이트만 했습니다.
            break;
        }
        case 2: {
            int res = 0, l, r, k; cin >> l >> r >> k;
            for (int i = l; i <= r; i++) {
				// 만약 범위 내라면, 그리고 sqrtn > 1이라면 (sqrtn == 1이려면 2, 3인데 그냥 완전탐색 하는편이..)
				// 평방분할로 전처리해둔걸 이용하여 O(lgN)만에 sqrt(N)범위를 처리해줍니다.
				// 총 O(sqrt(N)lgN)의 시간복잡도에다가,
                if ((i-1) % sqrtn == 0 && i+sqrtn <= r+1 && sqrtn > 1) {
                    res += sqrtn - query(t[i / sqrtn], k);
                    i += sqrtn-1;
                } // 떨거지를 더하여 쿼리는 O(sqrt(N)lg(N))의 시간복잡도를 갖게됩니다.
                else if (arr[i - 1] > k) res++;
            }
            cout << res << endl;
            break;
            }
        }
    }
}