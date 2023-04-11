import sys, heapq
from collections import defaultdict
input = sys.stdin.readline
"""
CodeTree 코드트리 채점기

첫 입력으로 쿼리의 개수 Q가 주어진다.
두번째 줄에 100 N url순으로 주어진다. 이는 채점기가 N개가 있으며, 처음에 채점 준비 큐에 url이 들어있다는 뜻이다.
이 때 입력받은 url의 우선순위는 1이고, 입력받은 시간은 0초이다.
2번째 줄을 포함하여 이하 받는 모든 url은 (주소)/(숫자)로 이루어져있다.

이후 Q-1개의 줄에 아래 4개의 쿼리가 주어진다.
 - 200 t p url
  : t초에 채점 준비 큐에다가 우선순위가 p인 url이 주어진다.
  : 만약 url이 완벽히 같은 것이 채점 준비 큐에 이미 있다면 추가하지 않는다.
 - 300 t
  : t초에 채점 준비 큐에서 우선순위가 가장 높은 것을 채점큐로 옮긴다.
  : 우선순위가 높아도 아래 조건에 해당한다면 뽑히지 않는다.
   I. 해당 주소(url아님)가 이미 채점중이다.
   II. 해당 주소가 끝났지만, 현재가 해당 주소가 채점을 시작한 시간에 대하여
       (시작 시간)+(시작시간-끝시간)*3 미만의 시간이다.
  : p가 작을수록, p가 같다면 t가 작을수록 우선순위가 높다.
  : 유휴 채점기 중 번호가 가장 작은 채점기가 할당된다.
 - 400 t id
  : t초에 id번 채점기가 채점하던게 끝났음을 알리는 쿼리이다.
  : 애초에 채점 안하던 놈이면 그냥 무시하면 된다.
 - 500 t
  : t초에 채점 준비 큐에 몇 개의 원소가 존재하는지 출력한다.

지옥의 우선순위큐 문제
삼성 기출이래서 그냥 풀어봤는데, 플레5는 아니고 골드 1~2.. 그 사이..?
300, 400번 쿼리가 많이 짜증나는데 못 풀수준은 아닙니다.
오히려 짜증났다면 이거보다 메이즈 탈출인가.. 그게 더 짜증나네요. 걔는 아직도 못풀었는데..
"""
def ask(t, p, url):
    global cnt
    if url in exist: return
    t, p = int(t), int(p)
    exist.add(url)
    heapq.heappush(wait, [p, t, url])
    cnt += 1

def evaluate(t):
    global cnt
    t = int(t)
    if not workingIdx: return
    while wait:
        item = heapq.heappop(wait)
        _, _, fullurl = item
        url, _ = fullurl.split("/")
        if url in nextT:
            time, isWork = nextT[url]
            if isWork:
                failExist[url].append(item)
            elif t < time:
                newItem = [time, item]
                heapq.heappush(failT, newItem)
            if isWork or t < time: continue
        break
    else: return
    cnt -= 1
    workRoom = heapq.heappop(workingIdx)
    working[workRoom] = fullurl
    nextT[url] = [t, True]
    exist.remove(fullurl)

def evaluate_end(t, id):
    t, id = int(t), int(id)
    if id in workingIdx: return
    fullurl = working[id]
    heapq.heappush(workingIdx, id)
    url, _ = fullurl.split("/")
    time, _ = nextT[url]
    nextT[url] = [time+(t-time)*3, False]
    working[id] = ""
    for urls in failExist[url]:
        heapq.heappush(wait, urls)
    if url in failExist:
        del failExist[url]

def printQ(t):
    print(cnt)

Q = int(input())-1
_, N, url = input().split()
N = int(N)
working = [""]*(N+1)
workingIdx = [i for i in range(1, N+1)]
heapq.heapify(workingIdx)
exist = set([url])
nextT = {}
failT = []
failExist = defaultdict(list)
# 우선순위, 들어온 t, url
wait = [[1, 0, url]]
cnt = 1
querys = {
    '200': ask,
    '300': evaluate,
    '400': evaluate_end,
    '500': printQ
}
for _ in ' '*Q:
    query, *item = input().split()
    while failT and int(item[0]) >= failT[0][0]:
        _, Qlist = heapq.heappop(failT)
        heapq.heappush(wait, Qlist)
    querys[query](*item)
