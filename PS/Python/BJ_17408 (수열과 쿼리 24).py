import sys
input = sys.stdin.readline
"""
17408번 수열과 쿼리 24

낮은 난이도 쿼리문제입니다.
수열 A를 주고, 아래 쿼리중 하나를 줍니다.
1 i v : Ai를 v로 바꾼다.
2 l r : l <= i < j <= r을 만족하는 모든 Ai + Aj중 최댓값을 출력한다.

세그먼트트리의 특성을 이용한 낮은 난이도 쿼리문제입니다.
세그먼트트리 특성상 루트노드까지 가면 인덱스와 그 값까지 알아낼 수 있는데,
값은 따로 저장해두고 인덱스를 이용하여 다음으로 큰 최댓값을 얻어내는 방식입니다.

예를들어 2 l r에서 제일 큰 수의 인덱스가 k가 나왔다면,
l ~ k-1과 k+1~r중 최댓값을 찾아 저장해뒀던 가장 큰 값과 더하여 출력하면 됩니다.
"""
class Node: # 사실 이 노드 필요 없습니다.. 세그먼트트리에 익숙해지기 전에 짠 코드라..
    def __init__(self, val, idx):
        self.val = val; self.idx = idx

def query(l, r):
    res = Node(-1,-1)
    l += n - 1; r += n - 1
    while l <= r:
        if l & 1:
            if seg[l].val > res.val:
                res = seg[l]
            l += 1
        if not r & 1:
            if seg[r].val > res.val:
                res = seg[r]
            r -= 1
        l >>= 1; r >>= 1
    return res

def update(idx, val):
    idx += n - 1; seg[idx].val = val
    while idx > 1:
        if seg[idx].val >= seg[idx^1].val:
            seg[idx>>1] = Node(seg[idx].val, seg[idx].idx)
        else:
            seg[idx>>1] = Node(seg[idx^1].val, seg[idx^1].idx)
        idx >>= 1

n = int(input())
arr = tuple(map(int, input().split()))
seg = [0]*(n*4)

for idx, i in enumerate(arr):
    seg[idx+n] = Node(i, idx)
for i in range(n-1,0,-1):
    if seg[i<<1].val >= seg[i<<1|1].val:
        seg[i] = Node(seg[i<<1].val, seg[i<<1].idx)
    else:
        seg[i] = Node(seg[i<<1|1].val, seg[i<<1|1].idx)
        
for _ in ' '*int(input()):
    c, i, v = map(int, input().split())
    if c == 1: update(i, v)
    else:
        tmp = query(i, v) # 최댓값
        tmp2 = query(i, tmp.idx).val if i <= tmp.idx else -1 # 왼쪽 최댓값
        tmp3 = query(tmp.idx+2, v).val if tmp.idx+2 <= v else -1 # 오른쪽 최댓값
        if tmp2 > tmp3:
            print(tmp.val + tmp2)
        else:
            print(tmp.val + tmp3)
