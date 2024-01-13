import sys
input = sys.stdin.readline
ini = lambda: int(input())
ins = lambda: map(int, input().split())
inf = float('inf')
"""
16156번 장애물 달리기

가장 왼쪽에서 가장 오른쪽으로 달릴 때, 최단거리로 가려 한다.
가장 왼쪽의 모든 경우에 대해 최단경로로 달리면 오른쪽 어디에 오게 되나 각각의 경우의 수를 구해보자.
모든 경우에 최단경로가 유일함을 보장한다.

문제 보고
아니 왜 플레4..?
음... 일반문젠 아닌거같은데..
근데 아닌데.. 다른방법있나..
하다가 MLE TLE 쳐맞고 답지보고 푼 문제.
어휴..

아니..
이게 처음엔 그냥 왼쪽 모든 행에서 다익스트라 돌리면 되지않나..? 했는데,
ㅋㅋ 어림도없지 MLETLEMLETLEMLETLE
억울해요 하면서 답지를 펼쳐보니, 오른쪽에서 왼쪽으로 가네 ㅋㅋ
ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
저거 코드 이해하는데만해도 상당히 긴 시간이 걸렸다..

이해하고나서 아 그렇네.. 역으로 가면 다익스트라 한번에 끝내네..
진짜.. 너무 당연한데 왜 생각을 못했을까..
"""
import heapq

dc = [1, -1, 0, 0]
dr = [0, 0, 1, -1]
def dij():
    dist = [[inf]*C for _ in ' '*R]
    ret = [0]*R
    h = [[M[i][-1], i, C-1, i] for i in range(R)]
    for i in range(R):
        dist[i][-1] = M[i][-1]
    heapq.heapify(h)
    while h:
        curW, curR, curC, curI = heapq.heappop(h)
        if dist[curR][curC] < curW: continue
        if curC == 0: ret[curI] += 1
        for i in range(4):
            nxtR = curR+dr[i]
            nxtC = curC+dc[i]
            if not (0 <= nxtR and nxtR < R): continue
            if not (0 <= nxtC and nxtC < C): continue
            if dist[nxtR][nxtC] <= curW+M[nxtR][nxtC]: continue
            dist[nxtR][nxtC] = curW+M[nxtR][nxtC]
            heapq.heappush(h, [dist[nxtR][nxtC], nxtR, nxtC, curI])
    return ret
R, C = ins()
M = [[*ins()] for _ in ' '*R]
print(*dij(), sep='\n')
