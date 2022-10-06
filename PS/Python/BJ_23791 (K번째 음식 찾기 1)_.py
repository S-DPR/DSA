import sys
from bisect import bisect_left
input = sys.stdin.readline
"""
23791번 K번째 음식 찾기

계속 생각해보다가 새로운 방법을 생각해냈습니다.
PST가 2796ms에 644036KB메모리를 사용했는데,
이진탐색 제대로 쓰면, 그리고 구현에 함수호출 말고 반복문만 쓴다면
944ms에 141088KB메모리를 사용합니다.

파이썬으로도 돌아갑니다. 걸리는 시간은 2176ms입니다.
숏코딩때문에 들여쓰기를 안한 부분이나 억지로 줄인부분이 다소 있습니다.
더 제대로 줄일 수는 있는데 귀찮아서..

방식은 이겁니다.
배열 A에서 가운데값을 들고 배열 B에서 lowerbound를 사용합니다.
그 때 lowerbound 반환값이 A보다 작은 수의 개수인데,
가운데 인덱스 + lowerbound반환값이 k-1이라면 그 수보다 작은 수가
배열 모두 통틀어서 k-1개 있다는 뜻이므로 결국 그 수가 K번째 수가 됩니다.

만약에 더 작다면 left값을 m+1로 설정하고,
더 크다면 right값을 m으로 설정하고 다시 돌립니다.
그렇게해서 배열 A에서 값이 나오면 그 값을 쓰고,
나오지 않는다면 배열 B에서 같은 함수를 한번 더 돌려 찾아내면 됩니다.
"""
def find_K(arr, brr, alen, blen):
  l = 0; r = blen
  while l < r:
    m = l + r >> 1
    back = bisect_left(arr, brr[m], hi=alen)
    if m + back == k-1: return m+1
    elif m + back < k-1: l = m+1
    else: r = m
  return 0

n = int(input())
arr = list(map(int, input().split()))
brr = list(map(int, input().split()))
for _ in ' '*int(input()):
  i, j, k = map(int, input().split())
  print(*([1,t] if (t:=find_K(brr, arr, j, i)) else [2, find_K(arr, brr, i, j)]))
