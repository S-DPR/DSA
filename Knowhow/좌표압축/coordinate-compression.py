import random, time, bisect
# 파이썬의 좌표압축은 세 가지 방법이 가능합니다.
# 첫번째는 C처럼 lowerbound를 활용하는 방법
# 두번째는 dictionary가 C의 unordered_map보다 하자(?)가 적다는 점을 이용한 방법입니다.
# 세 번째는 모듈을 사용하는 방법입니다.

def lowerbound(arr, x):
    l = 0; r = len(arr)-1
    while l < r:
        m = l+r >> 1
        if arr[m] >= m:
            r = m
        else:
            l = m + 1
    return r

def a(t):
    arr = t[:]
    s = time.time()
    arr.sort() # arr을 정렬하고
    dic = dict() # dictionary를 만들어줍니다.
    idx = 1 # 이게 하나하나 압축될 좌표고요,
    for i in arr: # arr을 반복하는데
        if i not in dic: # dic에 i가 없다면
            dic[i] = idx # i를 idx라는 값으로 딕셔너리에 저장하고
            idx += 1 # idx를 1 올립니다.
    arr = [dic[i] for i in arr]
    # 그리고 한번 더 반복하는데, 이제 arr에 그 수를 넣어줍니다.
    return time.time()-s

def b(t):
    arr = t[:]
    s = time.time()
    brr = sorted(set(arr)) # brr을 만들어 arr에서 중복된 수를 제거하고 sort해줍니다.
    for i in range(len(arr)):
        arr[i] = lowerbound(brr, arr[i]) # lowerbound로 arr[i]의 값을 수정합니다.
    return time.time()-s

def c(t):
    arr = t[:]
    s = time.time()
    brr = sorted(set(arr))
    for i in range(len(arr)):
        arr[i] = bisect.bisect_left(brr, arr[i]) # 사실 b(t)와 같습니다.
    return time.time()-s

T = [0, 0, 0]
for i in range(10):
    k = [random.randint(1, 10**6) for _ in ' '*10**6]
    T[0] += a(k)
    T[1] += b(k)
    T[2] += c(k)
    if not i%100: print(i)
for i in T:
    print(i / 1000)
# 첫 시도 결과 (수의 범위 1~10**6, 개수 10**6, 10번반복)
# 0.0045...
# 0.0155...
# 0.0091...
# 전체적으로 dictionary를 활용한 방법이 제일 빠른걸 볼 수 있습니다.
input()
