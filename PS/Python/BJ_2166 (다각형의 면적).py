import sys
input = sys.stdin.readline
spl = lambda: map(int, input().split())
"""
2166번 다각형의 면적

다각형을 이루는 점이 순서대로 주어진다.
다각형의 넓이를 구해보자.

기하학 시작 해야지.. 해서 스타트를 끊은 문제입니다.
옛날옛적, 고등학교시절이 기억나는 문제.
신발끈공식..이라고 이름이 특이해서 잊을래야 잊을 수 없는.

한개 놓쳤던게, 마지막 점과 시작 점을 이어주지 않은것.
왜 안되나 했는데, 그 중요한 점을 잊고있었더라고요.
다음부턴 골드이상이면 파이썬말고 다른거로 풀어야지..
"""
N = int(input())
v = []
for _ in ' '*N:
    v.append([*spl()])
v.append(v[0])
ret = 0
for i in range(N):
    ret += v[i][0]*v[i+1][1]
    ret -= v[i][1]*v[i+1][0]
print(f"{abs(ret/2):.01f}")
