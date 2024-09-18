#include <iostream>
#include <algorithm>
#include <numeric>
#include <string.h>
#define ll long long
using namespace std;
/*
24397번 말해 xor NO!

수열 A와 B가 주어진다.
각 수열에서 수를 하나씩 가져와 두 수를 xor한 값이 K 미만인 경우의 수는 몇개일까?

트라이인건 뭐 XOR이기도하고 해서 알았는데,
쿼리를 못짜서 틀렸네..
살짝 이건 트라이보다는 다이나믹 세그트리느낌이긴 하네요.
여하튼.. 뭐 트라이 기초인데 약간 강화한문제..같네요.
그나저나 트라이 오랜만에써보네
*/
int N, M, K, x;
ll ret;

struct trie {
    int cnt = 0;
    trie* child[2] = { nullptr, nullptr };

    int query(int x, int kth = 30) {
        if (kth == -1) return 0;
        int idx = (x>>kth)&1;
        if ((K>>kth)&1) {
            int ret = child[idx^1] ? child[idx^1]->query(x, kth-1) : 0;
            ret += child[idx] ? child[idx]->cnt : 0;
            return ret;
        }
        return child[idx] ? child[idx]->query(x, kth-1) : 0;
    }

    void update(int x, int kth = 30) {
        cnt++;
        if (kth == -1) return;
        trie* &nxt = child[(x>>kth)&1];
        if (!nxt) nxt = new trie();
        nxt->update(x, kth-1);
    }
};

int main() {
    trie root;
    scanf("%d %d %d", &N, &M, &K);
    while (N--) {
        scanf("%d", &x);
        root.update(x);
    }
    while (M--) {
        scanf("%d", &x);
        ret += root.query(x);
    }
    printf("%lld", ret);
}
