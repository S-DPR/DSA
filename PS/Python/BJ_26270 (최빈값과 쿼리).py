import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
26270번 최빈값과 쿼리

수 K와 수열이 주어진다. 아래 쿼리에 답해보자.
l r : l번째부터 r번째까지 부분배열 중, 같은 수가 K번 이상 나오는 최단 부분배열의 길이를 구해보자.

이제 블루도 찍었겠다 티어작하려고 플레32 뒤적뒤적하다가,
단순 2d스위핑인줄 알았는데 알고보니 레이지세그 스위핑이었던 플레2에 2시간 뜯기고,
다시 풀만한거 없나 뒤적뒤적하다가 나온 문제.

첨 볼땐 와 이거 PST각이다 PST각 이랬는데 정신차리고 더 쉬운 방법을 찾기로 했고..
세그트리의 각 노드를 'l이 처음일 때 가능한 최단길이'로 정하고..
쿼리는 r을 기준으로 정렬해줍니다.
이제 cnt 하나 만들어서 각 수가 1번부터 idx번까지 몇 번 나왔나 구해주고.
그게 K번 이상이 된다면 idx가 마지막일 때 최단 길이를 구해서 처리해주면됩니다.

플3이상 세그트리 오랜만이니까 감회가 새롭네
"""
def query(l, r):
    ret = inf
    l, r = l+N, r+N
    while l <= r:
        if l&1:
            ret = min(ret, segT[l])
            l += 1
        if ~r&1:
            ret = min(ret, segT[r])
            r -= 1
        l >>= 1
        r >>= 1
    return ret

def update(idx, val):
    idx += N
    segT[idx] = min(segT[idx], val)
    while idx > 1:
        segT[idx>>1] = min(segT[idx], segT[idx^1])
        idx >>= 1

N, K, Q = ins()
A = [0] + ins()
segT = [inf]*(N*2+1)
queries = [[*ins(), i] for i in range(Q)]
queries.sort(key = lambda x: x[1])
pos = [[] for _ in ' '*(N+1)]
ret = [0]*Q
idx = 1
for l, r, i in queries:
    while idx <= r:
        pos[A[idx]].append(idx)
        if len(pos[A[idx]]) >= K:
            update(pos[A[idx]][-K], idx-pos[A[idx]][-K]+1)
        idx += 1
    ln = query(l, r)
    ret[i] = [ln, -1][ln == inf]
print(*ret, sep='\n')
