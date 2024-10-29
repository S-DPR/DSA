def print_complete_binary_tree(based_1_tree):
    if not based_1_tree: return
    tree = based_1_tree[1:]
    t = len(str(max(tree)))
    cnt = 1
    while (1 << cnt) - 1 < len(tree):
        cnt += 1
    dist = (1 << (cnt-1))*t
    print(f"{' '*(dist-t)}{tree[0]:0{t}d}", end = '')
    idx = 1; cnt = 0; pls = 2
    while idx < len(tree):
        if cnt < idx:
            dist >>= 1
            cnt += pls
            pls += pls
            bonus = 0
            print()
        else:
            bonus = dist
        print(f"{' '*(dist+bonus-t)}{tree[idx]:0{t}d}", end = '')
        idx += 1
    print()
    print()

def push(x):
    idx = len(items)
    items.append(x)
    print_complete_binary_tree(items)
    while idx != 1 and items[idx>>1] > x:
        items[idx] = items[idx>>1]
        items[idx>>1] = x
        print_complete_binary_tree(items)
        idx >>= 1
    if idx != len(items)-1:
        print_complete_binary_tree(items)

items = []
q = [[0, 20], [0, 12], [0, 3], [0, 2], [1], [0, 5], [0, 16], [1], [0, 1]]
import heapq
for Q in q:
    if len(Q) == 1:
        heapq.heappop(items)
    else:
        heapq.heappush(items, Q[1])
items.insert(0, 0)
print_complete_binary_tree(items)
