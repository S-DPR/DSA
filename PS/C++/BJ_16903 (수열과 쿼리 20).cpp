#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define MAX 1073741824
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
using namespace std;
/*
16903번 수열과 쿼리 20

숫자 0이 들어있는 수열 A가 있다.
다음 쿼리를 처리하는 프로그램을 작성하시오.
1 x : 수열에 x를 추가한다.
2 x : 수열에서 x를 제거한다. 두 개 이상의 원소가 있어도 하나만 제거한다.
3 x : 수열 A의 원소중 x와 XOR하여 가장 큰 값을 출력하시오.
모든 x는 1 이상 10의 9승 이하이다.

즐거운 쿼리시간
C++이 드디어 사용되었습니다.
이 문제는 원래는 Trie라는 자료구조를 사용하여 푸는거라는데,
저는 Dynamic Segment Tree로 풀었습니다.

쿼리처리에서, 1<<k와 비스마스킹하여 True라면 왼쪽으로, 아니라면 오른쪽으로 보냅니다.
k는 29면 10의 29승을 처리할 수 있습니다.
굳이 왜 비트마스킹으로 처리할까요?
세그먼트트리는 이진트리인데, 여기서 왼쪽으로 가면 1<<k가 비어있는 정수의 개수가되고,
오른쪽으로가면 1<<k가 차있는 정수의 개수가 됩니다.
이 규칙으로 쭉쭉 나아가자는거죠. 재밌는 문제입니다. Trie는 공부 안할거같지만..
*/
struct node {
    int l, r, val;
    node(int l_ = 0, int r_ = 0, int val_ = 0) {
        l = l_; r = r_; val = val_;
    }
};

struct DS {
    vector<node> T = {node(), node()};
    int seg_size = 1;
    void update(int n, int s, int e, int idx, int val) {
        T[n].val += val;
        if (s == e) return;
        int mid = (s + e) >> 1;
        if (idx <= mid) {
            if (!T[n].l) {
                T[n].l = ++seg_size;
                T.push_back(node());
            }
            update(T[n].l, s, mid, idx, val);
        }
        else {
            if (!T[n].r) {
                T[n].r = ++seg_size;
                T.push_back(node());
            }
            update(T[n].r, mid + 1, e, idx, val);
        }
    }
    int query(int n, int s, int e, int x, int rec = 29) {
        if (!T[n].val) return 0;
        if (s == e) return s;
        int mid = (s + e) >> 1;
        if (T[T[n].l].val && T[T[n].r].val) {
            if (x & (1 << rec)) return query(T[n].l, s, mid, x, rec - 1);
            else return query(T[n].r, mid + 1, e, x, rec - 1);
        }
        else if (!T[T[n].l].val)
            return query(T[n].r, mid + 1, e, x, rec - 1);
        else
            return query(T[n].l, s, mid, x, rec - 1);
    }
};

int main() {
    fast;
    int n; cin >> n;
    DS t;
    for (int i = 0; i < n; i++) {
        int x, y; cin >> x >> y;
        switch (x) {
        case 1:
            t.update(1, 0, MAX-1, y, 1);
            break;
        case 2:
            t.update(1, 0, MAX-1, y, -1);
            break;
        case 3:
            cout << max(t.query(1, 0, MAX-1, y)^y, 0^y) << endl;
            break;
        }
    }
}