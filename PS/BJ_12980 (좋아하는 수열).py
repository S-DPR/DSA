import sys
from itertools import permutations
input = sys.stdin.readline
n, m = map(int, input().split())

"""
12980번 좋아하는 수열
오름차순이 몇개 일어나는가, 그를 찾는 문제입니다.
흔히 이런 문제를 Inversion Counting문제라고 합니다.
저같은 경우 좀 복잡하지만 높은 난이도에서도 통용 가능한 방법으로 풀었습니다.

사용 자료구조는 세그먼트 트리입니다.
이 방법을 쓰면 P5 '버블정렬'도 같은 방법으로 풀 수 있습니다.
(버블정렬 문제는 "버블정렬을 할 때 몇 번의 교환이 일어나는가?를 찾는 문제입니다."
"""

# 이 두 함수는 세그먼트트리의 기초함수입니다. 별 설명 없이 넘어가겠습니다.
def update(idx, val = 1, n = 1, s = 1, e = n):
    if not s <= idx <= e: return
    if s == e: seg[n] = val
    else:
        m = s + e >> 1
        update(idx, val, n*2, s, m)
        update(idx, val, n*2+1, m+1, e)
        seg[n] = seg[n*2] + seg[n*2+1]

def query(r, l = 1, n = 1, s = 1, e = n):
    if s >= l and e <= r: return seg[n]
    if s > r or e < l: return 0
    m = s + e >> 1
    return query(r, l, n*2, s, m) + query(r, l, n*2+1, m+1, e)

N = {i for i in range(1, n+1)} # 이게 없는 수를 저장할 set자료형이고..
arr = list(map(int, input().split())) # 이게 배열을 입력받을곳입니다.
void = [] # 여기는 0을 입력받은 인덱스를 입력받을겁니다.
for idx, i in enumerate(arr):
    if i == 0: void.append(idx)
    else: N.remove(i)
cnt = 0
for i in permutations(N, len(N)): # permutations 묘듈을 사용했습니다.
    k = 0
    brr = arr[:] # arr을 복사하고
    for j, v in zip(i, void): 
        brr[v] = j # permuations로 만든 순열을 빈곳에 집어넣은 뒤
    seg = [0]*(n*4) # 세그트리를 만들고
    for i in brr:
        k += query(i) # Inversion Counting을 찾아냅니다.
        update(i) # 세그트리의 대표적인 활용형입니다.
    if k == m: cnt += 1 # 만약에 k가 m과 같다면 답이므로, cnt를 1 올려줍니다.
print(cnt)
