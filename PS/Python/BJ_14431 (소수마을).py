import sys, heapq
input = sys.stdin.readline
inf = 1e10

"""
14431번 소수마을

문제를 살살 읽다보면 알 수 있는 정보 두 개가 있습니다.
"... A마을로 갈 수 있는 최단의 길을 찾는 것을 도와주자."
"...마을 간의 거리는..."
즉, 가중치가 있고 최단거리를 찾는 문제, 그중에서도 가중치는 양수인 문제.
다익스트라 문제입니다.
조금 까다로운 점이라면, 직접 가중치를 구해 그래프를 표현해야 한다는 것이겠죠.
"""

# Lazy-Propagate 세그먼트 트리에서 걸려온 고질병인데,
# class로 변수관리하는걸 제가 참 좋아합니다.
# 하려면 배열 2개정도 만들어서 할 수 있겠지만, 이게 더 좋아요.
class coor:
    def __init__(self, x, y, idx):
        self.x = x
        self.y = y
        self.idx = idx

# 소수의 절댓값은 3000을 넘지 않습니다.
# 대충 어.. 6000*sqrt(2)정도를 고려해야하는데요.
# 8485.2813.. 이렇게 나아갑니다. 대충 넉넉하게 10000정도까지의 소수를 구합시다.
def prime():
    arr = [1] * 10000
    arr[0] = 0; arr[1] = 0
    for i in range(10000):
        for j in range(2, int(i**(1/2)+1)):
            if arr[j] and not i % j:
                arr[i] = 0
    return arr

# 거리계산 함수입니다.
def dist_calc(x1, y1, x2, y2):
    xdist = abs(x1 - x2)
    ydist = abs(y1 - y2)
    dist = int((xdist**2 + ydist**2)**(1/2))
    return dist

# 다익스트라입니다. 그냥 n을 받아서 n.idx를 초반에 써준다는점 빼고는 다를게 없습니다.
def dij(n):
    priority = []
    heapq.heappush(priority, (0, n.idx))
    distance = [inf] * (1+len(N)); distance[0] = -1
    distance[n.idx] = 0
    while priority:
        weight, cur_node = heapq.heappop(priority)
        for new_weight, new_node in M[cur_node]:
            if new_weight + weight < distance[new_node]:
                distance[new_node] = weight + new_weight
                heapq.heappush(priority, (weight+new_weight, new_node))
    return distance

# 메인함수입니다. 시작부터 P는 소수배열을 받고요.
P = prime()
sx, sy, ex, ey = map(int, input().split()) # 시작점, 끝점을 받읍시다.
N = [coor(sx, sy, 1), coor(ex, ey, 2)] # 시작점의 idx는 1, 끝점은 2입니다.
for i in range(3, int(input())+3):
    N.append(coor(*map(int, input().split()), i))
    # 이후 들어오는 경유가능한 점도 3, 4, 5... 이렇게 인덱스를 할당받습니다.
M = [[] for _ in ' '*(1+len(N))] # M은 간선을 입력받을 배열입니다.
for i in N:
    for j in N: # 각 각선의 가중치를 계산할건데요.. 그러기 위해 N을 두번 돌리고
        if i.idx == j.idx: continue # 같은 노드는 그냥 패스
        if P[(w:=dist_calc(i.x, i.y, j.x, j.y))]: # 거리를 계산해서 소수라면..
            M[i.idx].append([w, j.idx]) # 가중치와 인덱스를 간선의 배열에 넣어줍니다.
print(k if (k:=dij(N[0])[2]) != inf else -1)
# 마지막으로, 다익스트라를 돌려 inf라면 -1을, 아니라면 그 결과값을 출력해줍니다.
