import sys
input = sys.stdin.readline
inf = 10**9
"""
1999번 최대최소

쓸데없이 난이도가 높게 책정되어있습니다.
솔직히 정해도 잘 모르겠고..
DP로 O(N^4+K) 해서 푼 사람,
아얘 브루트포스 때려박은 사람도 풀 수 있는 문제입니다.
저같은경우 그렇게하긴싫러요 하면서 세그트리 때려박긴했는데..
.. 아니 뭔 dict에 들어올 수 있는 입력값 저장해두니까 바로 통과되나..

일단 O(N^4)보단 빨라지려면,
그냥 최대최소 세그트리 각 가로줄마다 하나씩 만들어서 2중for문 돌리면 됩니다.
아...
근데 그거만 하면 시간초과나고 dict에 입력값이랑 결과값 저장해서,
중복된 입력이 들어오면 O(1)로 바로 보내줘야합니다.
"""
def query(seg, l, r, command):
    res = inf if command == min else -inf
    l += N-1; r += N-1
    while l <= r:
        if l&1:
            res = command(res, seg[l])
            l += 1
        if not r&1:
            res = command(res, seg[r])
            r -= 1
        l >>= 1; r >>= 1
    return res

N, B, K = map(int, input().split())
M = [list(map(int, input().split())) for _ in ' '*N]
mseg = [[0]*(N*2) for _ in ' '*N]
Mseg = [[0]*(N*2) for _ in ' '*N]
for i in range(N):
    for j in range(N):
        Mseg[i][j+N] = mseg[i][j+N] = M[i][j]
    for j in range(N-1, 0, -1):
        mseg[i][j] = min(mseg[i][j<<1], mseg[i][j<<1|1])
        Mseg[i][j] = max(Mseg[i][j<<1], Mseg[i][j<<1|1])

dic = dict()
for _ in ' '*K:
    i, j = map(int, input().split())
    if f"{i},{j}" not in dic: # 이거 없을때는 놀랍게도 시간초과
        mres = inf; Mres = -inf
        for y in range(i-1, i+B-1):
            mres = min(mres, query(mseg[y], j, j+B-1, min))
            Mres = max(Mres, query(Mseg[y], j, j+B-1, max))
        dic[f"{i},{j}"] = Mres - mres
    print(dic[f"{i},{j}"])
