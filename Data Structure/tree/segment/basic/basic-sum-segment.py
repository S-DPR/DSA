import sys
input = sys.stdin.readline

def init(node, start, end):
    if start == end:
        seg[node] = arr[start]
    else:
        mid = start + end >> 1
        seg[node] = init(node*2, start, mid) + init(node*2+1, mid+1, end)
    return seg[node]

def query(node, start, end, left, right):
    if start > right or end < left:
        return 0
    if start >= left and end <= right:
        return seg[node]
    mid = start + end >> 1
    return query(node*2, start, mid, left, right) + query(node*2+1, mid+1, end, left, right)

def update(node, start, end, idx, value):
    if not start <= idx <= end: return
    seg[node] = value
    if start != end:
        mid = start + end >> 1
        update(node*2, start, mid, idx, value)
        update(node*2+1, mid+1, end, idx, value)
        seg[node] = seg[node*2] + seg[node*2+1]

n = int(input())
arr = list(map(int, input().split()))
seg = [0]*(n*4)
init(1, 0, n-1)
while 1:
    print("쿼리적용: 1, 업데이트적용: 2")
    match int(input()):
        case 1:
            print("수 두개 띄어쓰기로 구별해 입력")
            print(query(1, 1, n, *map(int, input().split())))
        case 2:
            print("몇번째를 뭘로 바꿀건지 띄어쓰기로 구별해 입력")
            update(1, 1, n, *map(int, input().split()))
        case _:
            break
