"""
20160번 야쿠르트 아줌마 야쿠르트 주세요

첫 줄에 V, E가 주어진다. 각각 정점의 개수와 도로의 개수이다.
이후 E줄간 각 노드에 대한 경로가 주어진다. 도로는 양방향이며 u, v, e로 주어진다.
u와 v를 잇는 가중치가 w인 도로를 뜻한다.
야쿠르트 아줌마가 정점 10개를 돌고, 이 정점은 공백으로 구분되어 E+2번쨰 줄에 주어진다.
야쿠르트 아줌마는 E+2번째 줄에 첫 번째로 주어진 노드에서 출발하며,
i번째에서 i+1번째로 갈 수 있다면 그곳으로 최단거리로 간다.
갈 수 없다면, i+2, i+3.. 으로 가려고 시도한다.
E+3번째 줄에는 내가 출발할 정점이 주어진다.
어느 노드에서 만날 수 있는지 출력하라. 그런 노드가 여러개라면 가장 작은 번호의 노드를 출력하라.

Ruby로 풀다가 시간초과나서 때려치고,
Node.js로 갔다가 시간초과나서 때려치고,
Go로 갔다가 시간초과나길래 이거 어디선가 내가 실수했다는걸 깨달았고,
Python으로 와서 정답코드 뜯으면서 잘못된 점을 깨달았습니다.

구현은 완벽한데, 단 하나,
다익스트라에서 "if curW > dist[curN]: continue"구문을 빼먹었습니다..
최근 몇개월 간 전혀 깨닫지 못한 점이었습니다.. 아예 몰랐어요..
결국 이거 구문 하나 문제였다는 점에 현타가 와서,
언어의 마술사가 되었습니다.. Ruby, Node.js, Go, Python으로 모두 풀었..
우선순위 큐를 구현하는 방법은 확실하게 깨달았습니다.. 단시간만에 3개나 만들어서..
"""
import sys, heapq
input = sys.stdin.readline
INF = 10**9

def dij(x):
    P, dist = [], [INF]*(V+1)
    heapq.heappush(P, [0, x])
    dist[x] = 0
    while P:
        curW, curN = heapq.heappop(P)
        if curW > dist[curN]: continue
        for newN, newW in G[curN].items():
            if dist[newN] > curW+newW:
                dist[newN] = curW+newW
                heapq.heappush(P, [dist[newN], newN])
    return dist

V, E = map(int, input().split())
G = [dict() for _ in ' '*(V+1)]
for _ in ' '*E:
    u, v, w = map(int, input().split())
    if v in G[u]: G[u][v] = min(G[u][v], w)
    else: G[u][v] = w
    if u in G[v]: G[v][u] = min(G[v][u], w)
    else: G[v][u] = w
path = list(map(int, input().split()))

answer = INF
takeTime = [-1]*10
takeTime[0] = 0
accTime, current = 0, path[0]
startPath = dij(int(input()))
dic = {}
for i in range(1, 10):
    if not current in dic: dic[current] = dij(current)
    dist = dic[current]
    if dist[path[i]] == INF: continue
    accTime += dist[path[i]]
    takeTime[i] = accTime
    current = path[i]
    
for i in range(10):
    if startPath[path[i]] <= takeTime[i] and path[i] < answer:
        answer = path[i]
print([-1, answer][answer != INF])
