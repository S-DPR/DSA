// C언어로 풀 때 사용했던 템플릿. 백준에서 fhrmdls2로 검색할 경우 해당 템플릿으로 푼 흔적이 나옵니다.
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <malloc.h>
#include <string.h>
#define ll long long
#define INFI (2139062143) // set 0x7f using memset
#define INFL (9223372036854775807) // set 0x6f using memset
#define min(a, b) ((a) < (b) ? (a) : (b))
#define max(a, b) ((a) < (b) ? (b) : (a))
#define abs(a) ((a) < 0 ? (-(a)) : (a))

///////////////////////// Structure Define
//////////////////// type example
typedef struct typeExample {
	int item;
} typeExample;

// 비교함수 예시
int cmpExample(typeExample* i, typeExample* j) {
	return i->item < j->item;
}
//////////////////// type example end

//////////////////// default data structure
typedef struct linkedList {
	int size, LP, RP;
	void** arr;
	struct linkedList* prv;
	struct linkedList* nxt;
} linkedList;

typedef struct deque {
	int size, cnt;
	struct linkedList* left;
	struct linkedList* right;
} deque;

typedef struct heap {
	int size, extra_size;
	int used_max_size, max_size;
	int (*cmp) (void*, void*);
	void* arr;
} heap;
//////////////////// default data structure end

//////////////////// RBTree
typedef struct treeNode {
	int color; // 0 = BLACK, 1 = RED
	void *key, *val;
	struct treeNode* parent;
	struct treeNode* left;
	struct treeNode* right;
} treeNode;

// Map은 Set처럼 사용할 수 있으므로 Set은 따로 구현하지 않음.
typedef struct treeMap {
	int size;
	// 정렬조건
	int (*cmp) (treeNode*, treeNode*);
	struct treeNode* root;
	struct treeNode* NIL;
} treeMap;
//////////////////// RBTree End

//////////////////// graph Structure
// 그래프 노드 정보
typedef struct graphNodeInfo {
	ll d, w;
} graphNodeInfo;

typedef struct graphNode {
	int size, pointer;
	int extra_size;
	graphNodeInfo* arr;
} graphNode;

typedef struct graph {
	int size;
	graphNode** graph;
} graph;
//////////////////// graph Structure End
///////////////////////// Structure Define End

///////////////////////// Functions
linkedList* linkedListInit(int size, int LP, int RP);
void linkedListLink(linkedList* left, linkedList* right);
void linkedListRemove(linkedList* target);

deque* dequeInit(int size);
int dequeIsEmpty(deque* deq);
void dequePush(deque* deq, void* item);
void* dequePop(deque* deq);
void* dequePopLeft(deque* deq);
void* dequePeek(deque* deq);
void* dequePeekLeft(deque* deq);
int dequeSize(deque* deq);

heap* heapInit(int default_size, int (*cmp) (void*, void*));
heap* heapify(void** arr, int size, int (*cmp)(void*, void*));
void heapPush(heap* h, void* item);
void* heapPop(heap* h);
int heapIsEmpty(heap* h);
void _heapExtend(heap* h); // CALLED BY heapPush

graph* graphInit(int size);
graphNode* _graphNodeInit(int size); // CALLED BY graphInit
void graphNodePush(graph* g, int start, graphNodeInfo info);
ll* dijkstra(graph* g, int s);

// treeMap~ : 실제로 사용
// RBT~ : 내부적으로 사용
treeMap* treeMapInit(void* defaultV, int (*cmp)(treeNode*, treeNode*));
treeNode* treeNodeInit(treeMap* h, void* key, void* val);
treeNode* treeMapInsert(treeMap* h, void* key, void* val, void (*oper)(treeNode*, treeNode*)); // 리턴값 : 넣은 노드
void* treeMapDelete(treeMap* h, void* key);
treeNode* treeMapFindKey(treeMap* h, void* key);
treeNode* treeMapGetMin(treeMap* h);
treeNode* treeMapGetMax(treeMap* h);
void _RBTLeftRotate(treeMap* h, treeNode* x);
void _RBTRightRotate(treeMap* h, treeNode* x);
void _RBTInsertFix(treeMap* h, treeNode* z);
void _RBTTransplant(treeMap* h, treeNode* u, treeNode* v);
treeNode* _RBTGetTreeMinimumFromNode(treeMap* h, treeNode* item);
void _RBTDeleteFix(treeMap* h, treeNode* x);

int bisect(void** arr, int l, int r, void* x, int (*cmp) (void*, void*));
void swap(void** i, void** j);
///////////////////////// Functions End

///////////////////////// MAIN FUCTION
int main() {
}
///////////////////////// MAIN FUCTION END

///////////////////////// linkedlist
linkedList* linkedListInit(int size, int LP, int RP) {
	linkedList* ret = malloc(sizeof(linkedList));
	ret->arr = malloc(sizeof(void*) * size);
	ret->LP = LP; ret->RP = RP;
	ret->size = size;
	ret->prv = NULL;
	ret->nxt = NULL;
	return ret;
}

void linkedListLink(linkedList* left, linkedList* right) {
	if (left) left->nxt = right;
	if (right) right->prv = left;
}

void linkedListRemove(linkedList* target) {
	linkedListLink(target->prv, target->nxt);
	free(target->arr);
	free(target);
}
///////////////////////// linkedlist

///////////////////////// deque
deque* dequeInit(int size) {
	deque* ret = malloc(sizeof(deque));
	ret->size = size;
	ret->cnt = 0;
	ret->right = linkedListInit(size, 0, 0);
	ret->left = ret->right;
	return ret;
}

int dequeIsEmpty(deque* deq) {
	return !deq->cnt;
}

void dequePush(deque* deq, void* item) {
	deq->cnt++;
	linkedList* r = deq->right;
	if (r->size == r->RP) {
		linkedList* nxt = linkedListInit(deq->size, 0, 0);
		linkedListLink(r, nxt);
		deq->right = nxt;
		r = deq->right;
	}
	r->arr[r->RP++] = item;
}

void dequePushLeft(deque* deq, void* item) {
	deq->cnt++;
	linkedList* l = deq->left;
	if (l->LP == 0) {
		linkedList* nxt = linkedListInit(deq->size, deq->size, deq->size);
		linkedListLink(nxt, l);
		deq->left = nxt;
		l = deq->left;
	}
	l->arr[--l->LP] = item;
}

void* dequePop(deque* deq) {
	if (dequeIsEmpty(deq)) return NULL;
	if (deq->cnt >= 1 && !deq->right->RP) {
		deq->right = deq->right->prv;
		linkedListRemove(deq->right->nxt);
	}
	void* ret = dequePeek(deq);
	deq->right->RP--;
	deq->cnt--;
	return ret;
}

void* dequePopLeft(deque* deq) {
	if (dequeIsEmpty(deq)) return NULL;
	if (deq->cnt >= 1 && deq->left->LP == deq->size) {
		deq->left = deq->left->nxt;
		linkedListRemove(deq->left->prv);
	}
	void* ret = dequePeekLeft(deq);
	deq->left->LP++;
	deq->cnt--;
	return ret;
}

void* dequePeek(deque* deq) {
	if (dequeIsEmpty(deq)) return NULL;
	linkedList* l = deq->right->prv;
	linkedList* r = deq->right;
	return !r->RP ? l->arr[l->RP - 1] : r->arr[r->RP - 1];
}

void* dequePeekLeft(deque* deq) {
	if (dequeIsEmpty(deq)) return NULL;
	linkedList* l = deq->left;
	linkedList* r = deq->left->nxt;
	return l->LP == l->size ? r->arr[r->LP] : l->arr[l->LP];
}

int dequeSize(deque* deq) {
	return deq->cnt;
}
///////////////////////// deque end

///////////////////////// heap
heap* heapInit(int default_size, int (*cmp) (void*, void*)) {
	heap* ret = malloc(sizeof(heap));
	ret->size = 0;
	ret->max_size = default_size;
	ret->used_max_size = 0;
	ret->arr = malloc(sizeof(void*) * default_size);
	ret->extra_size = 1000;
	ret->cmp = cmp;
	return ret;
}

void _heapExtend(heap* h) {
	h->arr = realloc(h->arr, sizeof(void*) * (h->max_size + h->extra_size));
	h->max_size += h->extra_size;
}

heap* heapify(void** arr, int size, int (*cmp)(void*, void*)) {
	for (int j = size / 2 - 1; j >= 0; j--) {
		int i = j;
		while (1) {
			int root = i;
			int left = i * 2 + 1;
			int right = i * 2 + 2;
			if (left < size && cmp(arr[left], arr[root]))
				root = left;
			if (right < size && cmp(arr[right], arr[root]))
				root = right;
			if (root != i) {
				swap(&arr[i], &arr[root]);
				i = root;
			}
			else break;
		}
	}
	heap* h = heapInit(0, cmp);
	free(h->arr);
	h->max_size = size;
	h->size = size;
	h->arr = arr;
	return h;
}

void heapPush(heap* h, void* item) {
	int idx = h->size++;
	if (idx >= h->max_size) _heapExtend(h);
	void** arr = h->arr;
	while (idx != 0 && h->cmp(item, arr[(idx-1) >> 1])) {
		arr[idx] = arr[(idx-1) >> 1];
		idx = (idx-1) >> 1;
	}
	arr[idx] = item;
	h->used_max_size = max(h->used_max_size, idx);
}

void* heapPop(heap* h) {
	if (heapIsEmpty(h)) return NULL;
	int idx = 0;
	void** arr = h->arr;
	void* ret = arr[0];
	void* item = arr[--h->size];
	while ((idx << 1)+1 <= h->size) {
		int child = (idx << 1)+1;
		if (child + 1 < h->size && h->cmp(arr[child + 1], arr[child]))
			child++;
		if (h->cmp(item, arr[child])) break;
		arr[idx] = arr[child];
		idx = child;
	}
	arr[idx] = item;
	return ret;
}

void* heapPeek(heap* h) {
	if (heapIsEmpty(h)) return NULL;
	void** arr = h->arr;
	void* ret = arr[0];
	return ret;
}

int heapIsEmpty(heap* h) {
	return !h->size;
}

void heapFree(heap* h) {
	free(h->arr);
	free(h);
}
///////////////////////// heap end

///////////////////////// graph
graphNode* _graphNodeInit(int size) {
	graphNode* ret = malloc(sizeof(graphNode));
	ret->arr = malloc(sizeof(graphNodeInfo) * size);
	ret->size = size;
	ret->pointer = 0;
	ret->extra_size = size;
	return ret;
}

graph* graphInit(int size) {
	graph* ret = malloc(sizeof(graph));
	ret->size = size + 1;
	ret->graph = malloc(sizeof(graphNode*) * ret->size);
	for (int i = 0; i <= size; i++)
		ret->graph[i] = _graphNodeInit(max(30, size / 500));
	return ret;
}

void graphNodePush(graph* g, int start, graphNodeInfo info) {
	graphNode* s = g->graph[start];
	s->arr[s->pointer++] = info;
	if (s->pointer == s->size) {
		s->arr = realloc(s->arr, sizeof(graphNodeInfo) * (s->size + s->extra_size));
		s->size += s->extra_size;
	}
}

typedef struct dijkstraNode {
	ll d, w;
} dijkstraNode;

int dijkstraNodeCmp(dijkstraNode* i, dijkstraNode* j) {
	return i->w < j->w;
}

ll* dijkstra(graph* g, int start) {
	ll* dist = malloc(sizeof(ll) * g->size);
	memset(dist, 0x6f, sizeof(ll) * g->size);
	heap* h = heapInit(1000, dijkstraNodeCmp);
	dijkstraNode* st = malloc(sizeof(dijkstraNode));
	st->d = start; st->w = 0;
	heapPush(h, st);
	dist[start] = 0;
	while (!heapIsEmpty(h)) {
		dijkstraNode* cur = heapPop(h);
		ll curN = cur->d, curW = cur->w;
		free(cur);
		if (dist[curN] < curW) continue;
		for (int i = 0; i < g->graph[curN]->pointer; i++) {
			ll nxtN = g->graph[curN]->arr[i].d;
			ll nxtW = g->graph[curN]->arr[i].w;
			if (curW + nxtW < dist[nxtN]) {
				dist[nxtN] = curW + nxtW;
				dijkstraNode* item = malloc(sizeof(dijkstraNode));
				item->d = nxtN; item->w = dist[nxtN];
				heapPush(h, item);
			}
		}
	}
	heapFree(h);
	return dist;
}
///////////////////////// graph end

///////////////////////// treeMap
#define RBT_B 0
#define RBT_R 1

treeMap* treeMapInit(void* defaultV, int (*cmp)(treeNode*, treeNode*)) {
	treeMap* ret = malloc(sizeof(treeMap));
	ret->NIL = malloc(sizeof(treeNode));
	ret->NIL->color = RBT_B;
	ret->NIL->left = ret->NIL;
	ret->NIL->right = ret->NIL;
	ret->NIL->parent = ret->NIL;
	ret->NIL->val = defaultV;
	ret->root = ret->NIL;
	ret->cmp = cmp; ret->size = 0;
	return ret;
}

treeNode* treeNodeInit(treeMap* h, void* key, void* val) {
	treeNode* ret = malloc(sizeof(treeNode));
	ret->key = key; ret->val = val; ret->color = RBT_R;
	ret->left = h->NIL;
	ret->right = h->NIL;
	ret->parent = h->NIL;
	return ret;
}

void treeNodeFree(treeNode* n) {
	if (!n) return;
	free(n->key);
	free(n->val);
	free(n);
}

void _RBTLeftRotate(treeMap* h, treeNode* x) {
	treeNode* y = x->right;
	x->right = y->left;
	if (y->left != h->NIL) y->left->parent = x;
	y->parent = x->parent;
	if (x->parent == h->NIL)
		h->root = y;
	else if (x == x->parent->left)
		x->parent->left = y;
	else x->parent->right = y;
	y->left = x;
	x->parent = y;
}

void _RBTRightRotate(treeMap* h, treeNode* x) {
	treeNode* y = x->left;
	x->left = y->right;
	if (y->right != h->NIL) y->right->parent = x;
	y->parent = x->parent;
	if (x->parent == h->NIL)
		h->root = y;
	else if (x == x->parent->right)
		x->parent->right = y;
	else x->parent->left = y;
	y->right = x;
	x->parent = y;
}

void _RBTInsertFix(treeMap* h, treeNode* z) {
	treeNode* y;
	while (z->parent->color == RBT_R) {
		if (z->parent == z->parent->parent->left) {
			y = z->parent->parent->right;
			if (y->color == RBT_R) {
				z->parent->color = RBT_B;
				y->color = RBT_B;
				z->parent->parent->color = RBT_R;
				z = z->parent->parent;
			}
			else {
				if (z == z->parent->right) {
					z = z->parent;
					_RBTLeftRotate(h, z);
				}
				z->parent->color = RBT_B;
				z->parent->parent->color = RBT_R;
				_RBTRightRotate(h, z->parent->parent);
			}
		}
		else {
			y = z->parent->parent->left;
			if (y->color == RBT_R) {
				z->parent->color = RBT_B;
				y->color = RBT_B;
				z->parent->parent->color = RBT_R;
				z = z->parent->parent;
			}
			else {
				if (z == z->parent->left) {
					z = z->parent;
					_RBTRightRotate(h, z);
				}
				z->parent->color = RBT_B;
				z->parent->parent->color = RBT_R;
				_RBTLeftRotate(h, z->parent->parent);
			}
		}
	}
	h->root->color = RBT_B;
}

treeNode* treeMapInsert(treeMap* h, void* key, void* val, void (*oper) (treeNode*, treeNode*)) {
	treeNode* item = treeNodeInit(h, key, val);
	treeNode* y = h->NIL;
	treeNode* x = h->root;
	while (x != h->NIL) {
		y = x;
		if (h->cmp(item, x))
			x = x->left;
		else if (h->cmp(x, item))
			x = x->right;
		else {
			oper(x, item);
			free(item);
			return x;
		}
	}
	item->parent = y;
	if (y == h->NIL)
		h->root = item;
	else if (h->cmp(item, y))
		y->left = item;
	else
		y->right = item;
	item->left = h->NIL;
	item->right = h->NIL;
	item->color = RBT_R;
	_RBTInsertFix(h, item);
	h->size++;
	return item;
}

void _RBTTransplant(treeMap* h, treeNode* u, treeNode* v) {
	if (u->parent == h->NIL) h->root = v;
	else if (u == u->parent->left) u->parent->left = v;
	else u->parent->right = v;
	v->parent = u->parent;
}

treeNode* _RBTGetTreeMinimumFromNode(treeMap* h, treeNode* item) {
	treeNode* r = item;
	if (r == h->NIL) return r;
	while (r->left != h->NIL)
		r = r->left;
	return r;
}

void _RBTDeleteFix(treeMap* h, treeNode* x) {
	treeNode* w;
	while ((x != h->root) && x->color == RBT_B) {
		if (x == x->parent->left) {
			w = x->parent->right;
			if (w->color == RBT_R) {
				w->color = RBT_B;
				x->parent->color = RBT_R;
				_RBTLeftRotate(h, x->parent);
				w = x->parent->right;
			}
			if (w->left->color == RBT_B && w->right->color == RBT_B) {
				w->color = RBT_R;
				x = x->parent;
			}
			else {
				if (w->right->color == RBT_B) {
					w->left->color = RBT_B;
					w->color = RBT_R;
					_RBTRightRotate(h, w);
					w = x->parent->right;
				}
				w->color = x->parent->color;
				x->parent->color = RBT_B;
				w->right->color = RBT_B;
				_RBTLeftRotate(h, x->parent);
				x = h->root;
			}
		}
		else {
			w = x->parent->left;
			if (w->color == RBT_R) {
				w->color = RBT_B;
				x->parent->color = RBT_R;
				_RBTRightRotate(h, x->parent);
				w = x->parent->left;
			}
			if (w->right->color == RBT_B && w->left->color == RBT_B) {
				w->color = RBT_R;
				x = x->parent;
			}
			else {
				if (w->left->color == RBT_B) {
					w->right->color = RBT_B;
					w->color = RBT_R;
					_RBTLeftRotate(h, w);
					w = x->parent->left;
				}
				w->color = x->parent->color;
				x->parent->color = RBT_B;
				w->left->color = RBT_B;
				_RBTRightRotate(h, x->parent);
				x = h->root;
			}
		}
	}
	x->color = RBT_B;
}

void* treeMapDelete(treeMap* h, void* key) {
	treeNode* z = treeMapFindKey(h, key);
	if (z == h->NIL) return NULL;
	treeNode* y = z;
	treeNode* x;
	int yC = y->color;
	if (z->left == h->NIL) {
		x = z->right;
		_RBTTransplant(h, z, z->right);
	}
	else if (z->right == h->NIL) {
		x = z->left;
		_RBTTransplant(h, z, z->left);
	}
	else {
		y = _RBTGetTreeMinimumFromNode(h, z->right);
		yC = y->color;
		x = y->right;
		if (y->parent == z)
			x->parent = y;
		else {
			_RBTTransplant(h, y, y->right);
			y->right = z->right;
			y->right->parent = y;
		}
		_RBTTransplant(h, z, y);
		y->left = z->left;
		y->left->parent = y;
		y->color = z->color;
	}
	if (yC == RBT_B)
		_RBTDeleteFix(h, x);
	void* ret = z->val;
	free(z);
	h->size--;
	return ret;
}

treeNode* treeMapFindKey(treeMap* h, void* key) {
	treeNode* dummy = treeNodeInit(h, key, NULL);
	treeNode* cur = h->root;
	while (cur != h->NIL && (h->cmp(cur, dummy) || h->cmp(dummy, cur))) {
		if (h->cmp(cur, dummy))
			cur = cur->right;
		else cur = cur->left;
	}
	free(dummy);
	return cur;
}

treeNode* treeMapGetMin(treeMap* h) {
	treeNode* cur = h->root;
	while (cur->left != h->NIL)
		cur = cur->left;
	return cur;
}

treeNode* treeMapGetMax(treeMap* h) {
	treeNode* cur = h->root;
	while (cur->right != h->NIL)
		cur = cur->right;
	return cur;
}
///////////////////////// treeMap end

///////////////////////// Utils
void swap(void** i, void** j) {
	void* tmp = *i;
	*i = *j;
	*j = tmp;
}

int bisect(void** arr, int l, int r, void* x, int (*cmp) (void*, void*)) {
	while (l < r) {
		int m = (l + r) >> 1;
		if (cmp(x, arr[m]))
			r = m;
		else
			l = m + 1;
	}
	return r;
}
///////////////////////// Utils End
