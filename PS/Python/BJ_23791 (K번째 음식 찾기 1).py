import sys
input = sys.stdin.readline
n = 0
"""
23791번 K번째 음식 찾기 1

K번째 << 이 글자는 정말 마법의 단어인데요,
저거 있으면 무조건 이분탐색입니다.
그래서 이 문제도 이분탐색하는 문제인데..
일단 정말 사파로 풀었..습니다. 네.

아무리 생각해봐도 진짜 모르겠어서 그냥 직관적으로 생각나는 방법인 PST를 때려박았습니다..
그래서 결론적으로 풀긴 했는데 다시풀어봐야만 한다..가 결론.
PST 두개를 만들어 각각 A, B배열을 전담하게 하고,
쿼리에선 둘을 합쳐 이진탐색을 하면 되긴 합니다.

사실 문제 조건상 비효율적인 방법인거지, 이 방법이 훨씬 조건이 없긴합니다.
문제 조건에는 '정렬된 두 정렬이 주어지고' 'A배열은 [1:i], B배열은 [1:j]까지'라고 되어있는데,
이 방법은 쿼리 조금만 손보면 저 두 조건이 없어도(2번 조건은 A[x:i], B[y:j]로)
충분히 AC가 나오는 방법이 됩니다.

정해는 이진탐색입니다. 머 당연하죠 K번째수 찾는게 이진탐색이지..
참고로 이 방법도 이진탐색이긴 합니다. 단지 이상한 자료구조를 달고올 뿐.
"""
class Node:
    def __init__(self, v = 0, l = 0, r = 0):
        self.v = v; self.l = l; self.r = r

def update(seg, root, idx):
    root.append(len(seg))
    internal_update(seg, root[-2], root[-1], 1, n*2, idx)

def internal_update(seg, prev, cur, s, e, idx):
    if s == e:
        seg.append(Node(seg[prev].v+1))
    else:
        seg.append(Node(seg[prev].v+1, seg[prev].l, seg[prev].r))
        m = s + e >> 1
        if idx <= m:
            seg[cur].l = len(seg)
            internal_update(seg, seg[prev].l, seg[cur].l, s, m, idx)
        else:
            seg[cur].r = len(seg)
            internal_update(seg, seg[prev].r, seg[cur].r, m+1, e, idx)

def query(i, j, k):
    return internal_query(aroot[i], broot[j], 1, n*2, k)

# 어차피 root[0]가 prev라 굳이 두 prev를 변수로 받을 가치가 없죠.
def internal_query(acur, bcur, s, e, k):
    if s == e:
        r = unpress[s]
        if aseg[acur].v:
            return (1, adic[r])
        else:
            return (2, bdic[r])
    else:
        m = s + e >> 1
        p = aseg[aseg[acur].l].v + bseg[bseg[bcur].l].v
        if k <= p:
            return internal_query(aseg[acur].l, bseg[bcur].l, s, m, k)
        else:
            return internal_query(aseg[acur].r, bseg[bcur].r, m+1, e, k-p)

n = int(input())
arr = list(map(int, input().split()))
brr = list(map(int, input().split()))
crr = sorted(arr+brr)
aseg = [Node()]
bseg = [Node()]
aroot = [0]
broot = [0]
press = dict(); unpress = dict()
for idx, i in enumerate(crr, 1):
    press[i] = idx
    unpress[idx] = i
adic = dict(); bdic = dict()
for idx, (i, j) in enumerate(zip(arr, brr)):
    arr[idx] = press[i]
    brr[idx] = press[j]
    adic[i] = idx+1; bdic[j] = idx+1
    update(aseg, aroot, arr[idx])
    update(bseg, broot, brr[idx])
for _ in ' '*int(input()):
    print(*query(*map(int, input().split())))
