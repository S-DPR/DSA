import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
Round 939 (Div 2) D : Nene and the Mex Operator

길이가 N인 수열이 주어진다.
아래 연산을 50만번 이하로 사용해 배열의 합을 최대로 만들어보자.
l r : l부터 r까지 값을 mex(A[l..r])로 바꾼다.
가능한 최댓값과 그 연산을 아무거나 출력해보자.

코포 대회때 푼건 아니라도 업솔빙정도는 올려보는게 좋겠죠.
어.. 솔직히 이건 정말 어렵던데, 해 구성하기라서..
이거 처음 봤을 때 어버버만 하다가 터졌던 것 같습니다.
한번 이야기를 해보면..

N이 좀 작아서 모든 가능한 부분수열에 대해 완탐을 한다고 합니다.
솔직히 생각도 못했는데.. 이게 가능했구나.
모든 0 1 부분수열을 만드는건 비트마스킹으로 가볍게 가능합니다.

그럼 이제 어느 구간 [l, r]에 대해서 이 값의 최대는 무엇이냐..
일단 모두 0이라고 가정할 때, (r-l+1)^2이 최댓값입니다.
최종적으로 0 1 2 ... r-1로 만들 수 있거든요. 
마지막에 [l, r] 써주면 모두 r r r ... r이 되니, 길이의 제곱이 최댓값.
만약에 이게 원래 배열보다 수가 작다면 연산을 만들면 안되겠죠.

마지막으로 남은 문제는 그래서 어떻게 만들어? 인데.
0 0 0을 기준으로 보면..
1 0 0
2 2 0
0 2 0
1 2 0
3 3 3
이런 흐름이 됩니다. go(i) = i번째 수를 i로 만드는데 필요한 연산을 만드는 경우.
라고 이야기하면 편해지겠네요. 이건 재귀로 구현할 수 있습니다.
하.. 쉽지않다.. 난이도 2000이던데 이게 D인가..
"""
def get_segment(n):
    ret = []
    for i in range(n.bit_length()):
        if not n&(1<<i): continue
        if ret and ret[-1][1]+1 == i:
            s, _ = ret.pop()
            ret.append([s, i])
        else:
            ret.append([i, i])
    return ret

def sol():
    cal, oper = S, []
    for i in range(1<<N):
        cur, cur_oper = S, []
        rng = get_segment(i)
        for s, e in rng:
            cur -= sum(A[s:e+1])
            cur += (e-s+1)**2
            for j in range(s, e+1):
                if A[j]: cur_oper.append([j, j])
        if cur <= cal: continue
        for s, e in rng:
            cur_oper.extend(create_oper(s, e))
        cal, oper = cur, cur_oper
    return cal, oper

def create_oper(l, r):
    def go(k):
        if k == l: return [[k, k]]
        ret = go(k-1)
        for i in range(k-2, l-1, -1):
            ret.append([l, i])
            ret.extend(go(i))
        ret.append([l, k])
        return ret
    return go(r)

N = ini()
A = ins()
S = sum(A)
cal, oper = sol()
print(cal, len(oper))
for s, e in oper:
    print(s+1, e+1)
