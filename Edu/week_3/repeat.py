import sys
input = sys.stdin.readline

arr = [10, 20, 30, 40, 50]
for i in range(5):
    print(i, end = ' ')
# 출력값 : 0 1 2 3 4 
print()

for i in arr:
    print(i, end = ' ')
# 출력값 : 10 20 30 40 50
print()

for i in range(5):
    print(i, arr[i], end = ' | ')
# 출력값 : 0 10 | 1 20 | 2 30 | 3 40 | 4 50 | 
print()

for idx, i in enumerate(arr):
    print(idx, i, end = ' | ')
# 출력값 : 0 10 | 1 20 | 2 30 | 3 40 | 4 50 |
print()

brr = [15, 25, 35, 45, 55]
for i, j in zip(arr, brr):
    print(i, j, end = ' | ')
# 출력값 : 10 15 | 20 25 | 30 35 | 40 45 | 50 55 | 
print()

for idx, (i, j) in enumerate(zip(arr, brr)):
    print(f"{idx} : {i} {j}", end = ' | ')
# 출력값 : 0 : 10 15 | 1 : 20 25 | 2 : 30 35 | 3 : 40 45 | 4 : 50 55 |
print()

crr = [[10, 15], [20, 25], [30, 35], [40, 45], [50, 55]]
for i in crr:
    print(i, end = ' | ')
# 출력값 : [10, 15] | [20, 25] | [30, 35] | [40, 45] | [50, 55] |
print()

for i, j in crr:
    print(f"{i} {j}", end = ' | ')
# 출력값 : 10 15 | 20 25 | 30 35 | 40 45 | 50 55 |
print()

for i, j in enumerate(crr):
    print(f"{i} : {j}", end = ' | ')
# 출력값 : 0 : [10, 15] | 1 : [20, 25] | 2 : [30, 35] | 3 : [40, 45] | 4 : [50, 55] |
print()

for idx, (i, j) in enumerate(crr):
    print(f"{idx} : {i} {j}", end = ' | ')
# 출력값 : 0 : 10 15 | 1 : 20 25 | 2 : 30 35 | 3 : 40 45 | 4 : 50 55 |
print(); print()

dic = {1 : 10, 2 : 20, 3 : 30}
for i in dic:
    print(i, end = ' ')
# 출력값 : 1 2 3
print()

for i, j in dic.items():
    print(i, j, end = ' | ')
# 출력값 : 1 10 | 2 20 | 3 30 |
print()