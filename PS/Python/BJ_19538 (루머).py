import sys
from collections import deque
input = sys.stdin.readline
"""
19538번 루머

"어떤 사람의 주변인 수를 N이라 하자.
그중 N/2명 이상이 어떤 루머를 믿으면 그 사람도 반드시 루머를 믿게된다고 한다.
그리고 루머는 1초마다 한번씩 전파되려고 시도한다고 하자.
사람들의 주변인 관계와 최초유포자가 주어질 때, 각 사람이 루머를 믿게되는 시간을 출력하라.
최초유포자는 0초이고, 평생 안믿는 사람은 -1초이다."

대충 문제를 보면 그래프문제인게 보입니다.
시간제한도 10초라서 무서운데,
입력에 '양방향 주변인 관계(즉, 간선)은 100만개 이하이다.'가 있어 생각보다 빠르게 됩니다.
저같은경우는 3.2초에서 1332ms까지 줄였습니다.

대충 주변인 믿는 사람 배열과 시간초를 세는 배열을 만들면 답이 될듯합니다.
한번 짜봅시다.
"""
n = int(input())+1
G = [[] for _ in ' '*n]
for i in range(1, n):
    G[i].extend(map(int, input().split()))
    G[i].pop()
input() # M값은 파이썬에선 필요가 없습니다.
d = deque(map(int, input().split()))
ans = [-1]*n # ans의 최초값은 -1입니다. 영영 듣지 믿지 못하는 사람이 -1초라서요.
for i in d:
    ans[i] = 0 # 최초유포자는 0으로 초기화.
believe = [0]*n
while d:
    x = d.popleft()
    for i in G[x]:
        if ans[i] != -1: continue # 이미 전파한 사람에게는 또 전파할 이유가 없죠.
        believe[i] += 1
        if believe[i] >= len(G[i])/2:
            ans[i] = ans[x]+1 # 주변인중 절반 이상이 믿으면 전파된 초 +1을 해줍니다.
            d.append(i)
print(*ans[1:]) # 출력.
