import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: map(int, input().split())
inf = float('inf')
"""
20553번 다오와 디즈니의 데이트

다음 두 행동 중 하나를 할 수 있다.
1. 자신의 왼쪽이나 오른쪽 칸이 있으면, 거기로 이동한다. 이동한 곳에 있는 수만큼의 점수를 얻는다.
2. 자신의 위치에 그대로 있는다. 0점을 얻는다.
T번 행동해 얻을 수 있는 최대점수를 구해보자.
(1 <= T <= 10^9)

첫 여행의 시작에 풀었던 문제,
첫 여행의 종점에 제출한 문제ㅡ
즐거운 꿈을 꾸었습니다.

모든 칸에 대해, 좌->우->좌...를 반복했을때 얻는 값을 계산하면 됩니다.
누적합 쓰면 O(1)로 계산이 돼서 총 O(N).
이걸 대충 식으로 표현하는게 더 어렵더라고요.
찜질방에서, 자기 직전에 푼 문제였습니다.
"""
N, T = ins()
A = [*ins()]
pf, ret = 0, 0
for i in range(N-1):
	if 2*i > T: break
	cur = pf*2+A[i]+(A[i]+A[i+1])*((T-2*i)//2)
	ret = max(ret, cur)
	pf += A[i]
print(ret)
