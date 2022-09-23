#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
#define MAX_SIZE 100001
using namespace std;

/*
13553번 수열과 쿼리 8

개인적으로 수열과 쿼리 문제를 정말 좋아해서 옛날에 푼 문제를 주석 달아서 다 올릴생각인데요,
수열과 쿼리류 문제에 단골손님수준으로 나오는 자료구조가 있습니다.
바로 세그먼트 트리입니다. 그 아종으로 펜윅트리도 있습니다.
세그먼트 트리의 친구수준인 Mo's알고리즘도 있습니다.
이 알고리즘과 자료구조들에 대해 알고있다고 가정하겠습니다.

아, 그리고 눈물나게도 PyPy3로는 안되고 C++로만 가능한 알고리즘도 있습니다.
그래서, 아마 많은 쿼리 문제는 C++로 올라올것같습니다.
아무래도 쿼리랑 이제 거리가 좀 멀어져야 할 것 같지만요..

각설하고, 수열과 쿼리 8은 어떤 문제냐.
"수열 A랑 자연수 K를 드릴게. 그리고 내가 l이랑 r을 줄게.
l과 r 사이에 있고, 서로 다른 두 수 (i, j) (단, i < j)를 골라봐.
이 때, abs(Ai - Aj) <= K인 경우는 총 몇 개일까?"
라는 문제입니다.
*/

// Mo's알고리즘에 사용할 node입니다.
struct node {
    int s = 0, e = 0, n = 0;
    node(int s = 0, int e = 0, int n = 0) {
        s = s; e = e; n = n;
    }
};

int f[MAX_SIZE] = {0};
int n;

// 이 문제는 세그먼트트리로 하면 시간초과가 나고, 펜윅트리로 하면 시간초과가 나지 않는다고 합니다.
// 둘 다 쿼리 및 업데이트에 걸리는 시간복잡도는 O(lgN)인데, 상수시간의 차이가 있나봅니다.
void update(int idx, int val) {
    for (; idx < MAX_SIZE; idx += idx & -idx) f[idx] += val;
}

ll int_query(int idx) {
    int res = 0;
    for (; idx > 0; idx -= idx & -idx) res += f[idx];
    return res;
}

ll query(int l, int r) {
    return int_query(r) - int_query(l-1);
}

// Mo's알고리즘에 사용할 comp 함수입니다.
// 정렬에 사용되죠. Mo's알고리즘이 뭔지 아신다면 알거라 생각합니다.
bool comp(node a, node b) {
    int D = sqrt(n);
    int A = a.s / D;
    int B = b.s / D;
    if (A == B) return a.e < b.e;
    return A < B;
}

int main()
{
    fast;
    int k, arr[MAX_SIZE], arr_m[MAX_SIZE], arr_p[MAX_SIZE];
    cin >> n >> k;
	/*
	자, 생각을 다시 해봅시다.
	|Ai - Aj| <= K라고 했습니다.
	그러면 -K <= Ai - Aj <= K 맞나요?
	그러면, Aj - K <= Ai <= K + Aj 맞겠죠.
	이 부분을 이용할겁니다.
	arr_m은 arr_minus를 뜻하고, arr_p는 arr_plus를 뜻합니다.
	어떻게 쓰이는지는, 첫 쿼리 처리때 한번 봅시다.
	*/
    for (int i = 0; i < n; i++) {
        cin >> arr[i];
        arr_m[i] = max(1, arr[i] - k);
        arr_p[i] = min(MAX_SIZE-1, arr[i] + k);
    }

	// 여긴 Mo's를 안다면 이해가 되는 부분이시겠죠.
	// Fenwick-Tree는 0-Based니까 tmp에서 1을 빼서 넣어줍시다.
    node Q[MAX_SIZE];
    int m, tmp1, tmp2;
    cin >> m;
    for (int i = 0; i < m; i++) {
        cin >> tmp1 >> tmp2;
        Q[i].s = --tmp1; Q[i].e = --tmp2; Q[i].n = i;
    }
    sort(Q, Q + m, comp);
	
	/*
	이제 생각의 시간입니다.
	arr_m과 arr_p를 이용해 쿼리를 처리할겁니다.
	왜 둘을 이용하냐?
	arr[i]를 기준으로, arr_m[i]부터 arr_p[i]까지의 수는 조건에 부합하는 수입니다. 일단 이 두 수가 쿼리에 사용됩니다.
	그리고 문제에선 i, j를 주고 i < j라고 했지만, i > j로 잡고 i와 j를 스왑해도 문제가 없습니다.
	즉, 순서는 사실상 별 상관이 없고 "i != j"라는 점, "i,j와 j,i는 같다"라는 것을 알려주기 위한 조건이었습니다.
	결정적으로 abs(x - y)와 abs(y - x)는 같습니다.
	arr[i] 쿼리 처리 후, arr[i]를 업데이트 합니다.
	arr[i+1] 쿼리를 처리 한 뒤, arr[i+1]을 업데이트 합니다.
	.. 이런방식으로 가면 결국에는 해당 범위 내에 있는 수의 조합을 모두 고를 수 있겠죠.
	현재 펜윅트리는 인덱스 기반 펜윅트리이니 앞뒤 모두 볼 수 있습니다.
	이 방법을 채택할겁니다. 쿼리 처리 후, 업데이트 방식으로 진행하겠습니다.
	*/
    int s = Q[0].s, e = Q[0].e;
    ll res = 0, ans[MAX_SIZE];
    for (int i = s; i <= e; i++) {
        res += query(arr_m[i], arr_p[i]);
        update(arr[i], 1);
    }
	
	// 위 방식을 십분 활용한 Mo's입니다. Mo's 종료 시, ans를 모두 출력하면 끝납니다.
    for (int i = 0; i < m; i++) {
        node x = Q[i];

        while (x.s < s) {
            s--;
            res += query(arr_m[s], arr_p[s]);
            update(arr[s], 1);
        }
        while (x.s > s) {
            update(arr[s], -1);
            res -= query(arr_m[s], arr_p[s]);
            s++;
        }
        while (x.e < e) {
            update(arr[e], -1);
            res -= query(arr_m[e], arr_p[e]);
            e--;
        }
        while (x.e > e) {
            e++;
            res += query(arr_m[e], arr_p[e]);
            update(arr[e], 1);
        }

        ans[x.n] = res;
    }

    for (int i = 0; i < m; i++) cout << ans[i] << ' ' << endl;
}