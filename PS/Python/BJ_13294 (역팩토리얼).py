"""
13294번 역팩토리얼

n!값이 주어질때 n을 구해보자.
주어지는 n!값은 최대 100만자리수이다.

이상하게 이런문제만 풀면 코드가 개박살이나요
5가 곱해질때마다 잘 처리해주자.. 가 관건인 문제입니다.
예를들어 뒤에 0이 4개있으면 20~24범위의 팩토리얼만 확인하면 됩니다.
숏코딩은 먼소린지 잘 모르겠네요..
"""
import math
s=input()
m=0
cnt=0
for i in s[::-1]:
    if i != '0': break
    cnt+=1
while cnt:
    m += 5
    t = m
    while not t % 5:
        cnt -= 1
        t //= 5
        if cnt < 0:
            m -= 5
            break
    else: continue
    break
for i in range(max(1, m), m+5):
    if str(math.factorial(i)) == s:
        print(i)
