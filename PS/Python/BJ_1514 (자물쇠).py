import sys
input = lambda: sys.stdin.readline().strip("\n")
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
1514번 자물쇠

자물쇠를 풀려고 한다. 현재 자물쇠 상태는 S이고 비밀번호는 T이다.
자물쇠를 1회 바꾸는데 다음과 같은 행위를 할 수 있다.
 - 인접한 서로 다른 위아래 칸을 최대 3칸 잡는다.
 - 왼쪽 혹은 오른쪽으로 최대 3칸 회전시킨다.
두 행위는 동시에 하여도 1회로 취급한다. 최소 몇 회 움직여야할까?

플레3 탐방하다가 되게 할만해보여서 건들고 3시간 박은 문제
그래도 뭐..
못풀진 않았으니..

그냥 하다가 이것저것 놓치면 안되는 사실을 싹 다 놓치는 기적을 일으켜버려서 오래 걸렸네요.
확실히 사람 적게 푼 문제보단 훨씬 인간적인 문제네..
"""
from functools import lru_cache
@lru_cache(None)
def loop(idx, first_left, second_left):
    if idx == N: return 0
    cur_number = (S[idx]+first_left)%10
    ret = inf
    left_turn = (T[idx]-cur_number+10)%10
    right_turn = (cur_number-T[idx]+10)%10
    for idxx, first in enumerate([left_turn, right_turn]):
        sign = [1, -1][idxx]
        for sec in range(first+1):
            for third in range(sec+1):
                arr = [0, third, sec, first]
                dist = sum((arr[i]-arr[i-1]+2)//3 for i in [1, 2, 3])
                ret = min(ret, loop(idx+1, (second_left+sec*sign+10)%10, (third*sign+10)%10)+dist)
    return ret

N = ini()
S = [*map(int, input())]
T = [*map(int, input())]
print(loop(0, 0, 0))
