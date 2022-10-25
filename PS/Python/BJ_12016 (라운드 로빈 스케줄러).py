import sys
input = sys.stdin.readline
MAX_INDEX = 100000
"""
12016번 라운드 로빈 스케줄러

수열의 길이 n과 수열 A가 주어진다.
i를 0 1 2 .. n-1 0 1 2 .. n-1 0 1 2 .. 이런식으로 반복할 때,
i가 A[i]번 나올 때 몇 번째에 나오는지 모든 수열에 대하여 구하여라.
단, A[i]번 나온 i는 더이상 반복하지 않는다.

상당히 참신하고 재밌는 문제입니다.
마지막줄때문에 난이도가 급상승한 케이스입니다.
마지막줄 없었으면 실버따리문제라고 생각합니다.

이분탐색도 되고, 세그트리도 되는 문제라고 합니다.
저는 세그트리로 풀었습니다.
문제 특성상 반드시 배열 순서대로 풀 필요는 없습니다.

앞부분에 대하여, 정렬을 내림차순으로 한 뒤 맨 뒤부터 pop하며,
  이미 완료한 것들의 시간 합
+ (현재 처리할 인덱스에 대응하는 값) * ((현재인덱스) - (앞쪽중 완료 안된것의 개수))
+ ((현재 처리할 인덱스에 대응하는 값)-1) * ((배열의길이)-(현재인덱스)-(뒤쪽중 완료안된것의 개수))
의 값을 구하면 원하는 값이 나옵니다.

세그트리는 각 루트노드를 인덱스로 보고 완료 안된것의 개수를 세는데 쓰면 됩니다.
"""
def update(f, idx, val):
    while idx < MAX_INDEX:
        f[idx] += val
        idx += idx & -idx

def int_query(f, idx):
    res = 0
    while idx > 0:
        res += f[idx]
        idx -= idx & -idx
    return res

def query(f, l, r):
    return int_query(f, r) - int_query(f, l-1)

n = int(input())
arr = sorted([[i, idx] for idx, i in enumerate(map(int, input().split()), 1)], reverse=True)
f = [0]*(MAX_INDEX+1)
ans = [0]*n
t = 0
while arr and (i:=arr[-1][0]):
    cnt = []
    while arr and arr[-1][0] == i:
        _, idx = arr.pop()
        front = i*(idx-query(f, 1, idx))
        back = (i-1)*(n-query(f, idx, MAX_INDEX)-idx)
        ans[idx-1] = front+back+t
        cnt.append(idx)
    t += i*len(cnt)
    while cnt: update(f, cnt.pop(), 1)
for i in ans:
    print(i)
# 북마크에 오랫동안 박혀있던 문제였는데 드디어 풀었네요.
