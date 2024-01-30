#include <iostream>
#include <stack>
#include <vector>
#include <algorithm>
#include <string.h>
#define ll long long
using namespace std;
/*
17226번 묘수풀이: 모독

내 유닛이 N개, 상대 유닛이 M개 있다.
각 유닛은 생명력, 공격력이 있으며, 격투시 상대에게 공격력만큼의 데미지를 입힌다. (자신도 상대 공격력만큼 데미지를 입는다.)
그리고 나에겐 모독이란 카드가 있는데, 처음에 1데미지를 주지만 이 카드로 인해 죽은 유닛이 있을경우 재발동하게된다.
재발동한 모독도 처음 쓰는 모독과 같은 효과를 가진다.
이 때, 모독과 유닛을 적절히 사용하여 상대의 모든 유닛을 처리해보자.
단, 처리할 수 없을경우 -1을 출력하자.

아..
진짜 당연한건데 놓쳐버렸어..

처음에 loop에서 2중for문을 썼었는데..
그러면 (N*M)^N이 터져서 시간초과나고.
생각해보니 그러면 같은거를 여러번 반복하게 되더라고요. 그래서 그냥 kth유닛을 쓰도록 하게 했습니다.
그리고 atk가 낮은거 먼저 공격하도록..
.. 아 이거 답지 안보고 생각해봤으면 될거같기도한데 너무 당연해서 절대 못 떠올릴것같기도하고..
*/
struct minion {
	int hp, atk, idx;
};

int N, M;
vector<pair<int, int>> trace;
bool loop(vector<minion> &oppo, vector<minion> &me, int kth, bool modok_used) {
	bool is_all_kill = true;
	for (auto &i: oppo) {
		is_all_kill = is_all_kill && i.hp <= 0;
	}
	if (is_all_kill) return true;
	if (kth == N+1) return false;

	if (kth < N && me[kth].hp > 0) {
		for (int j = 0; j < M; j++) {
			if (oppo[j].hp <= 0) continue;
			me[kth].hp -= oppo[j].atk;
			oppo[j].hp -= me[kth].atk;
			trace.push_back({me[kth].idx, j+1});
			if (loop(oppo, me, kth+1, modok_used)) {
				return true;
			}
			me[kth].hp += oppo[j].atk;
			oppo[j].hp += me[kth].atk;
			trace.pop_back();
		}
	}
	if (!modok_used) {
		vector<int> hp;
		for (auto &i: oppo) if (i.hp > 0) hp.push_back(i.hp);
		for (auto &i: me) if (i.hp > 0) hp.push_back(i.hp);
		sort(hp.begin(), hp.end());
		int dmg = 1;
		for (auto &i: hp) {
			if (i > dmg) break;
			if (i == dmg) dmg++;
		}
		for (auto &i: oppo) i.hp -= dmg;
		for (auto &i: me) i.hp -= dmg;
		trace.push_back({-1, -1});
		if (loop(oppo, me, kth, true)) {
			return true;
		}
		for (auto &i: oppo) i.hp += dmg;
		for (auto &i: me) i.hp += dmg;
		trace.pop_back();
	}
	return loop(oppo, me, kth+1, modok_used);
}

int main() {
	int x, y;
	vector<minion> oppo;
	vector<minion> me;
	scanf("%d %d", &N, &M);
	for (int i = 0; i < N; i++) {
		scanf("%d %d", &x, &y);
		me.push_back({y, x, i+1});
	}
	for (int i = 0; i < M; i++) {
		scanf("%d %d", &x, &y);
		oppo.push_back({y, x, i+1});
	}
	sort(me.begin(), me.end(), [](auto &i, auto &j) {
		return i.atk < j.atk;
	});
	if (loop(oppo, me, 0, false)) {
		printf("%d\n", trace.size());
		for (auto &i: trace) {
			if (i.first == -1) {
				printf("use modok\n");
			} else {
				printf("attack %d %d\n", i.first, i.second);
			}
		}
	} else {
		printf("-1");
	}
}
