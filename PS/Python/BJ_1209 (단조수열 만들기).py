import sys
input = sys.stdin.readline
sys.setrecursionlimit(100001)
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
1209번 단조수열 만들기

길이가 N인 수열 A가 주어진다. 단조수열 B를 하나 만들어 |A_i-B_i| (0 <= i < N)를 최소화해보자.

캬
N^3밖에 안나와서 어떻게하지.. 하다가 갑자기 생각난 누적최솟값
바로 구현해주니까 AC. 와~
난이도기여창에선 B의 모든 원소가 A의 원소여야한다는걸 착안하는게 힘들었다고 하네요.
저는 그냥 느낌적인 느낌으로 알아차렸는데..
증명을 어떻게 하는진 모르겠습니다. 알아봐야게따
"""
N = ini()
A = [ini() for _ in ' '*N]
S = sorted(set(A))

def sol(arr):
    prv = [0]*len(S)
    for i in range(N):
        cur = [inf]*len(S)
        mn = inf
        for idx, j in enumerate(S):
            mn = min(mn, prv[idx])
            cur[idx] = mn+abs(arr[i]-j)
        prv = cur
    return min(prv)

print(min(sol(A), sol(A[::-1])))
