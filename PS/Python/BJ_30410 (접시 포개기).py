import sys, math
input = sys.stdin.readline
spl = lambda: map(int, input().split())
"""
30410번 접시 포개기

1과 2로 이루어진 배열이 있다.
인접한 같은 수는 더해 더한 값을 가진 하나의 원소로 있다.
최대 몇의 수를 만들 수 있을까?

보자마자 아하 구간dp! 했는데 이제보니 N이 2*10^5.
어어.. 하면서 배열 원소 범위를 보니 1 아니면 2..
아.. 그냥 1을 전부다 더하면 되겠구나.
그리고 반례 보니까 뒤집어야하는 경우도 있길래, 뒤집어서도 한번 더.
이거 한 다음 최댓값 구하면 됩니다. 가운데 1끼는경우만 처리하고.
2로 만들 수 있는 최댓값은 1<<(int(log2(N))+1). 쉽죠.
"""
def go(inp):
    A = []
    for i in inp:
        if A and A[-1] == 1 and i == 1:
            A.pop()
            A.append(2)
        else:
            A.append(i)
    if A[-1] != 1:
        A.append(1)
    prv, ret = 0, 1
    for idx, i in enumerate(A):
        if i != 1: continue
        cnt = idx-prv
        if cnt != 0:
            ret = max(ret, 1<<(int(math.log2(cnt))+1))
        prv = idx
    return ret

N = int(input())
inp = [*spl()]
print(max(go(inp), go(inp[::-1])))
