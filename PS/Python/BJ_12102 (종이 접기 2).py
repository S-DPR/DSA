import sys
input = sys.stdin.readline
"""
12102번 종이 접기 2

각 칸마다 숫자가 써져있는 격자판이 있습니다.
이 격자판을 접을 수 있습니다. 이 때 겹쳐지는 부분의 숫자는 더해집니다.
이 격자판을 적절하게 접었을 때 최댓값을 찾아주세요.
각 칸에는 절댓값 100을 넘지 않는 정수가 쓰여있으며,
격자판의 최대 크기는 가로 6 세로 6입니다.

보기보다 훨씬 까다로운 문제입니다.
일단 반복에는 한계가 보이니 재귀를 써야하고,
종이를 반씩만 접는게 아니라 격자판 한칸만 접든지, 두칸만 접든지도 가능하기에
규칙성을 잘 잡아서 풀어야합니다.
"""
def loop(arr):
    global res
    # 모든 경우에 대해 최댓값을 계산해 집어넣어줍니다.
    res = max(res, max(map(max, arr)))

    # 세로로 접기
    for i in range(1, len(arr)):
        C = [j[:] for j in arr[i:]]
        for j in range(i):
            while len(C) <= j: C.append([0]*len(C[0]))
            for k in range(len(C[j])):
                C[j][k] += arr[i-1-j][k]
        loop(C)

    # 가로로 접기
    for i in range(1, len(arr[0])):
        C = [j[i:] for j in arr]
        for j in range(i):
            # 4칸짜리 종이인데 왼쪽 3칸을 잡아 접을지도 모르죠.
            while len(C[0]) <= j:
                for k in range(len(C)):
                    C[k].append(0)
            for k in range(len(C)):
                C[k][j] += arr[k][i-1-j]
        loop(C)

n, m = map(int, input().split())
M = list(list(map(int, input().split())) for _ in ' '*n)
res = max(map(max, M))
loop(M)
print(res)
