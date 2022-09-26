#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
#define MAX_SIZE 2001
using namespace std;

/*
1615번 교차개수세기

교차개수를 센다? 이거 냄새가 벌써 납니다.
문제는 쭉쭉 길게 써져있는데 결론적으로..
"i > j이고 arr[i][1] < arr[j][1] 혹은 i < j이고 arr[i][1] > arr[j][1]인 개수를 구해라."
그냥 Inversion Counting입니다.

저같은 경우 Inversion Counting은 세그먼트트리를 사용해서 세는데요,
PyPy로 했더니 뭔짓을 해도 메모리초과 시간초과 지옥에 걸려서 C++로 도망왔습니다.
세그먼트 트리 종류중, 펜윅트리라는애가 있는데. 그친구를 썼거든요. 좀 더 빨라서.
지금 보니 PyPy도 펜윅트리 써서 맞춘사람이 있는데 왜 저만 시간초과나는지 모르겠습니다. 네.

펜윅트리에 대한 설명은 생략하겠습니다.
*/

ll seg[MAX_SIZE] = {0};

void update(int idx) {
    for (idx; idx < MAX_SIZE; idx += idx & -idx) seg[idx] += 1;
}

ll int_query(int idx) {
    ll res = 0;
    for (idx; idx > 0; idx -= idx & -idx) res += seg[idx];
    return res;
}

ll query(int l, int r) {
    return int_query(r) - int_query(l - 1);
}

// sort할겁니다. 오름차순으로요.
bool comp(pair<int, int> i, pair<int, int> j ) {
    if (i.first == j.first) return i.second < j.second;
    return i.first < j.first;
}

int main()
{
    fast;
    int n, m;
    cin >> n >> m;
    vector<pair<int, int>> arr(m); // 여기에 이차원배열 입력받을거고요..
    for (int i = 0; i < m; i++) cin >> arr[i].first >> arr[i].second;
    sort(arr.begin(), arr.end(), comp); // sort해주고요.
	
	/*
	이 sort에는 큰 의미가 있는데요,
	문제의 의미가
	"i > j이고 arr[i] < arr[j] 혹은 i < j이고 arr[i] > arr[j]인 개수를 구해라."
	라고 했습니다.
	
	이걸 오름차순으로 만들어버리면 모든 i, j에 대해 i <= j가 됩니다.
	또, 모든 arr[i][1], arr[j][1]에 대해 arr[i][1] < arr[j][1]이 됩니다.
	중복된 간선은 안주니까요.
	
	결론적으로 문제가 어떻게 줄어드느냐,
	"i < j이고 arr[i][1] > arr[j][1]인 개수를 구해라."
	가 됩니다.
	*/
	
    ll ans = 0;
    for (pair<int, int> x : arr) {
        ans += query(x.second+1, MAX_SIZE); // arr[j][1]을 넘는 쿼리를 세주고
        update(x.second);
		// 업데이트를 해줍니다.
		// x.first가 바뀌지 않는 한 x.second는 오름차순이기에, 중복해서 셀 일은 없습니다.
    }
    cout << ans;
	// 시간복잡도 : O((N+M)lgN)
	// 공간복잡도 : O(N)
}