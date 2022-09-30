#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
#define MAX 100002
using namespace std;
/*
11012번 Egg

아니 뭐..
상처뿐인 승리를 얻었네요 네..
승리를 얻었으니 된건가..?

문제가 영어인데, 대충 요지만 보자면..
"0 <= x, y <= 100000인 (x, y)가 N개 주어지고,
[x1, x2, y1, y2] 꼴로 범위가 M번 주어진다.
범위 내에 몇 개의 점이 있는지, M번을 다 더한 값을 출력하여라."
문제 조건상, 무조건 (x1, y1)부터 (x2, y2)라는 직사각형 범위를 생각하시면 됩니다.

이 문제를 푸는 방법은 두 가지입니다.
쿼리문제답게 세그트리로 범벅이 되어있는데요,
1. 세그먼트 트리 + 스위핑 활용
2. 머지소트 트리 활용
3. 퍼시스턴트 세그먼트 트리 활용

저는 1번은 스위핑을 모르고, 2번도 모르니 3번밖에 못합니다..

생각해보면, PST의 원래 존재 의의는 서로 다른 수 따위를 세는게 아니라,
이런 2차원 쿼리문제의 해결입니다.
그래서 이 문제의 정해는 PST가 아니라고 하지만, PST 기초문제로도 볼 수 있습니다.

이 문제를 PST로 위해서는,
"X좌표를 루트에 넣고, 각 노드에는 Y좌표를 넣자"
라는 생각을 하면 됩니다.
*/
struct Node {
    int l=0, r=0, val=0;
    Node(int il = 0, int ir = 0, int ival = 0) {
        l = il; r = ir; val = ival;
    }
};

struct upd_node {
    int x = 0, y = 0;
    upd_node(int ix = 0, int iy = 0) {
        x = ix, y = iy;
    }
};

struct query_node {
    int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
    query_node(int ix1 = 0, int ix2 = 0, int iy1 = 0, int iy2 = 0) {
        x1 = ix1; x2 = ix2; y1 = iy1; y2 = iy2;
    }
};

class PST {
    vector<Node> seg = { Node() };
    int root[MAX] = {0};
    vector<int> root_idx = { 0 };
    int seg_size = 1, root_size = 1;
	
	// 평범한 PST 업데이트입니다.
    void internal_update(int prev, int cur, int s, int e, int idx) {
        seg_size++; seg.push_back(Node(seg[prev].l, seg[prev].r, seg[prev].val+1));
		if (s == e) return;
		int m = (s + e) >> 1;
		if (idx <= m) {
			seg[cur].l = seg_size;
			internal_update(seg[prev].l, seg[cur].l, s, m, idx);
		}
		else {
			seg[cur].r = seg_size;
			internal_update(seg[prev].r, seg[cur].r, m + 1, e, idx);
		}
    }
	
	// 같은 X좌표가 나올 수 있다고 대놓고 말하고있기에, 그 전용 업데이트 함수도 만들었습니다.
    // 다른분들을 봤을 때 아예 벡터에서 전처리 하고 업데이트 한번씩 집어넣던데,
	// 전 개인적으로 이렇게하는게 취향의 영역이네요.
	void internal_update(int cur, int s, int e, int idx) {
        seg[cur].val++;
		if (s == e) return;
		int m = (s + e) >> 1;
		if (idx <= m) {
			if (seg[cur].l == 0) {
				seg.push_back(Node());
				seg[cur].l = seg_size++;
			}
			internal_update(seg[cur].l, s, m, idx);
		}
		else {
			if (seg[cur].r == 0) {
				seg.push_back(Node());
				seg[cur].r = seg_size++;
			}
			internal_update(seg[cur].r, m + 1, e, idx);
		}
    }

    ll internal_query(int cur, int s, int e, int l, int r) {
        if (s > r || e < l) return 0;
        if (s >= l && e <= r) return seg[cur].val;
        int m = (s + e) >> 1;
        return internal_query(seg[cur].l, s, m, l, r) + \
            internal_query(seg[cur].r, m + 1, e, l, r);
    }

public:
	// x좌표는 같은애로 나올 수 있다 했었죠.
	// x좌표가 root의 기준점이 될겁니다.
	// 만약 업데이트 할 x좌표가 변하지 않았다면 따로 만들어둔 업데이트를 하게 됩니다.
    void update(int dim, int idx) {
        if (root_idx[root_size-1] != dim) {
            root[dim] = seg_size; root_size++;
            root_idx.push_back(seg_size);
            internal_update(root_idx[root_size - 2], root_idx[root_size - 1], 1, MAX, idx);
        }
        else {
            internal_update(root_idx[root_size - 1], 1, MAX, idx);
        }
    }
	
	// root에는 현재 중간중간 업데이트가 안된 부분이 있습니다.
	// 그부분을 쫙 해줄겁니다.
	// 이거 안하려고 몇시간을 썼는데 굴복해버렸습니다..
    void update_final() {
        for (int i = 1, cache = 0; i < MAX; i++) {
            if (cache > root[i]) root[i] = cache;
            else cache = root[i];
        }
    }

    ll query(int x1, int x2, int y1, int y2) {
        return internal_query(root[x2], 1, MAX, y1, y2) - \
            internal_query(root[x1 - 1], 1, MAX, y1, y2);
    }
};

// 오름차순 정렬입니다.
bool cmp(upd_node i, upd_node j) {
    if (i.x == j.x) return i.y < j.y;
    return i.x < j.x;
}

int main()
{
    fast;
    int tc;
    cin >> tc;
    while (tc--) {
        int n, m;
        cin >> n >> m;
        vector <int> arr(n);

        vector <upd_node> coor(n);
        for (int i = 0; i < n; i++) {
            int tmp1, tmp2;
            cin >> tmp1 >> tmp2;
            coor[i] = upd_node(++tmp1, ++tmp2);
        }
        sort(coor.begin(), coor.end(), cmp);
		// x좌표를 중심으로 정렬을 해주어 쿼리에 업데이트를 한번에 할겁니다.
        
        vector <query_node> query(m);
        for (int i = 0; i < m; i++) {
            int x1, y1, x2, y2;
            cin >> x1 >> x2 >> y1 >> y2;
            query[i] = query_node(++x1, ++x2, ++y1, ++y2);
        }

        PST t;
        for (auto x : coor) {
            t.update(x.x, x.y);
        }
        t.update_final();
        ll sum = 0;
        for (auto x : query) {
            sum += t.query(x.x1, x.x2, x.y1, x.y2);
        }

        cout << sum << endl;
    }
}