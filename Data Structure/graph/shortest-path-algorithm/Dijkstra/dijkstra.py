import heapq
inf = 10**9

# G means Graph, x means standard node
def dij(G: list[[int, int], ...], x: int) -> list:
    priority = []
    dist = [inf]*len(G)
    dist[x] = 0
    heapq.heappush((0, x))
    while priority:
        curWeight, curNode = heapq.heappop(priority)
        for nextWeight, nextNode in G[curNode]:
            if curWeight + nextWeight < dist[nextNode]:
                dist[nextNode] = curWeight + nextWeight
                heapq.heappush(priority, (dist[nextNode], nextNode))
    return dist
