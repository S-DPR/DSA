import sys
from collections import defaultdict
input = sys.stdin.readline
"""
1253번 좋다

문제 이름이 좋다입니다.
내용은
"이 배열에서 두 수를(arr[i]와 arr[j]는 다른 수) 골라 만들 수 있는 배열 내의 수는 몇개일까?"
입니다.
예를들어, 1 2 3 이 배열이라 한다면 3은 1+2니까 되고, 1과 2는 만들 수 없으니 1이 답이겠죠.
1 2 3 4는 1+2=3, 3+1=4니까 답이 2일겁니다.
0 1 3의 경우, 0이 답이 됩니다. 3을 제외한 0과 1로 3을 만들 수 없으니까요.

참고로 이 풀이는 비효율적 풀이입니다.
정렬과 이분탐색을 이용한 풀이가 정해입니다.
"""
n = int(input())
arr = list(map(int, input().split()))
dic = defaultdict(int) # 저는 defaultdict를 활용했습니다.
for i in arr:
    dic[i] += 1 # dict에 전부 저장해주고
cnt = 0
for idx, i in enumerate(arr):
    dic[i] -= 1
    # i는 "이 수가 다른 수의 합으로 만들어질 수 있나?" 입니다.
    # 자기 자신을 더할 수는 없으므로 임시로 dic[i]에서 1을 빼줍니다.
    # 단, 0 3 3의 경우 0+3이 가능하니 1만 빼주는겁니다.
    for jidx, j in enumerate(arr): # j는 더할 수 1입니다.
        # 두 수를 더한다 하면, a+b = c가 됩니다.
        # a나 b가 정해지면 다른 b, a는 자동으로 정해지겠죠.
        # 이 j를 a나 b로 생각하면 편합니다.
        if idx == jidx: continue # 예외케이스 1. 자기 자신으로 시도하는 경우
        if not dic[i-j]: continue # 예외케이스 2. a+b=c에서 a, c가 정해졌는데 b가 불가능한 해인경우
        if i-j == j and not dic[j]-1: continue # 예외케이스 3. a+b=c에서 a==c/2인데 a가 2개 이상 있지 않은경우
        cnt += 1 # 예외가 아니라면 i는 가능한 수입니다. 1을 더해줍니다.
        break # 이후 더 반복할 필요가 없습니다.
    dic[i] += 1 # 지웠던 i를 복구해줍니다.
print(cnt)
