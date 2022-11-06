import sys
from collections import defaultdict
input = sys.stdin.readline
"""
1324번 젠장

수열 A와 B가 길이 N으로 주어집니다.
수열 A, B의 교집합이며, A와 B에서 모두 증가하는 수열인 집합의 최대크기를 구해주세요.

DP의 세계는 넓고, 저는 아무것도 모르겠습니다.
DP는커녕, 그냥 이분탐색에 완전탐색 섞은거같습니다.
숏코딩 봐도 뭔소린지 모르겠습니다.. 네..
"""
def upperbound(arr, x):
    l = 0; r = len(arr)-1
    while l < r:
        m = l + r >> 1
        if arr[m] > x: r = m
        else: l = m + 1
    return r if arr[r] > x else -1

n = int(input())
arr = list(map(int, input().split()))
brr = list(map(int, input().split()))
arridx = defaultdict(list)
for idx, i in enumerate(arr):
    arridx[i].append(idx)
dp = []
for idx, i in enumerate(brr):
    if i not in arridx: continue
    idxend = defaultdict(lambda: 1000)
    idxend[1] = arridx[i][0]
    for curN, cnt, curidx in dp:
        if curN >= i: continue
        if (tmp:=upperbound(arridx[i], curidx)) == -1: continue
        idxend[cnt+1] = min(idxend[cnt+1], arridx[i][tmp])
    for j, k in idxend.items():
        dp.append([i, j, k])
print(max(dp, key=lambda x: x[1])[1] if dp else 0)
