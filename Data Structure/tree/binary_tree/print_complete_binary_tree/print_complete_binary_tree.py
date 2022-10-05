def print_complete_binary_tree(based_1_tree):
    if not based_1_tree: return
    tree = based_1_tree[1:]
    t = len(str(max(tree)))
    cnt = 1
    while (1 << cnt) - 1 < len(tree):
        cnt += 1
    dist = (1 << cnt)*t
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

arr = list(range(1, 6))
n = len(arr)
seg = [0]*(n*2)
for i in range(n):
    seg[i+n] = arr[i]
for i in range(n-1, 0, -1):
    seg[i] = seg[i<<1] + seg[i<<1|1]
print_complete_binary_tree(seg)
