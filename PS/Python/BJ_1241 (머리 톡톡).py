import sys
from collections import defaultdict
input = sys.stdin.readline
"""
1241번 머리 톡톡

내 머리 텅텅

그래프고 뭐고 그냥 약수판정에 O(sqrt(N))걸리는 알고리즘을 사용하는겁니다.
개인적으로 이건 소수판정에만 쓸 수 있는줄 알았는데,
약수에도 이걸 쓸 수 있을줄은 몰랐습니다..

이걸 풀 때 한동안 몰라서 O(N/2)이 최대속인줄 알았습니다..
"""
n = int(input())
arr = [int(input()) for _ in ' '*n]
dic = defaultdict(int)
for i in arr:
    dic[i] += 1

ansPerNum = defaultdict(int)
for i in list(dic.keys()):
    dic[i] -= 1
    for j in range(1, int(i**.5)+1):
        if i % j: continue
        ansPerNum[i] += dic[j]
        if i//j != j: ansPerNum[i] += dic[i//j]
    dic[i] += 1

for i in arr:
    print(ansPerNum[i])
