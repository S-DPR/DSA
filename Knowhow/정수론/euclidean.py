import random
R = lambda x: random.randint(1, x)

# https://rebro.kr/97
# https://rkm0959.tistory.com/179
# https://casterian.net/algo/ext-euclidean.html

# 유클리드 호제법을 구현해보자
# 1. 재귀
gcd = lambda a, b: gcd(b, a%b) if b else a
R1, R2 = R(500), R(500)
print(f"{R1}, {R2} : {gcd(R1, R2)}")
# 2. 반복
print(f'{R1}, {R2} : ', end = '')
while R2: R1, R2 = R2, R1%R2
print(R1)

# 확장 유클리드 알고리즘
# gcd(a, n) = 1 -> ax≡1(mod n)을 찾는 알고리즘
# ax + by = gcd(a, b)인 x, y를 찾는 알고리즘
# 아래 두 함수에 대해, 첫 값이 1이 아니면 그 방정식의 해는 없다.
def exEuclid_1(a, b):
    r1, r2 = a, b
    s1, s2 = 1, 0
    t1, t2 = 0, 1
    while r2:
        q = r1//r2
        r = r1 - (q*r2)
        s = s1 - (q*s2)
        t = t1 - (q*t2)
        r1, r2 = r2, r
        s1, s2 = s2, s
        t1, t2 = t2, t
    return r1, s1, t1

def exEuclid_2(a, b):
    if not b: return a, 1, 0
    g, x, y = exEuclid_2(b, a%b)
    return g, y, x-(a//b)*y

R1, R2 = R(500), R(500)
R1, R2 = 100, 20
print(f"{R1}, {R2} : {gcd(R1, R2)}")
print(f"{R1}, {R2} : {exEuclid_1(R1, R2)}")
print(f"{R1}, {R2} : {exEuclid_2(R1, R2)}")
