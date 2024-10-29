import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
11585번 속타는 저녁 메뉴

문자열 A, B가 주어진다. 둘의 길이는 N이다.
문자열 B를 회전시킬 수 있을 때, 문자열 A가 몇 번이나 나타날까?

'회전이 나왔다면 일단 배열을 두배로 박고 슬라이딩 윈도우를 써볼 것'
옛날에 어느 문제에선가 얻어온 아이디어입니다.
K512어쩌구였던것같네요.
암튼 그렇게하면.. 이제 문자열이 일치하는지 세든 말든 해야하는데,
일단 문자열 두배로 한다음 거기서 KMP로 count하는 방법이 있고,
두번째로는 그냥 문자열의 희망 라빈카프를 갖다 박는겁니다.
이중해싱 안했다가 저격TC에 고통받은적이 있으니 이중해싱까지.

오늘도 맛있는 날먹플레
"""
from math import gcd
from collections import defaultdict
MOD1 = 1_000_000_007
MOD2 = 1_000_000_009
BASE = 1_000_019
def mod(n, mod):
    if n >= 0: return n%mod
    return ((-n%mod+1)*mod+n)%mod

def hashing(chars):
    ret = defaultdict(int)
    h = (0, 0)
    p = (1, 1)
    for i in range(len(chars)-N+1):
        if i == 0:
            for j in range(N):
                first = mod(h[0]+mod(p[0]*chars[N-j-1], MOD1), MOD1)
                second = mod(h[1]+mod(p[1]*chars[N-j-1], MOD2), MOD2)
                h = (first, second)
                if j < N-1:
                    first = mod(p[0]*BASE, MOD1)
                    second = mod(p[1]*BASE, MOD2)
                    p = (first, second)
        else:
            first = mod(BASE*mod(h[0]-mod(p[0]*chars[i-1], MOD1), MOD1) + chars[i+N-1], MOD1)
            second = mod(BASE*mod(h[1]-mod(p[1]*chars[i-1], MOD2), MOD2) + chars[i+N-1], MOD2)
            h = (first, second)
        if i != N: ret[h] += 1
    return ret

N = ini()
A = [*map(lambda x: ord(x), input().split())]
B = [*map(lambda x: ord(x), input().split())]*2
HA = hashing(A)
HB = hashing(B)
key = [*HA.keys()][0]
up = HB[key]
down = N
g = gcd(up, down)
print(f"{up//g}/{down//g}")
