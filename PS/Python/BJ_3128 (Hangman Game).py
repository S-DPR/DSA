import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
fini = lambda: int(input()[1:])
fins = lambda: [*map(int, input()[1:].split())]
inf = float('inf')
"""
3128번 Hangman Game

A로 시작해 왼쪽, 오른쪽으로 알파벳을 변경할 수 있다.
A에서 왼쪽은 Z, Z에서 오른쪽은 A이다.
또, 입력을 하려면 엔터를 한 번 눌러야한다.
입력된 문자는 문자열에서 해당 문자가 필요한 모든 곳에 나타난다.
문자열에 존재하는 모든 문자를 나타내기 위한 최소 버튼 클릭 횟수를 구해보자.
입력된 문자도 차례대로 출력해보자.

파이썬 이틀 쓴김에 이번주는 걍 파이썬만 써야겠다

아 이거 어떻게하지 어떻게하지 하다가 생각난 구간dp.
dp[i][l][r] = i번째를 잡고있을 때 l부터 r까지 완성한 경우의 수.
사전에 문자열에서 공백 없애고 A 없으면 맨 앞에 추가하고 중복없애고 정렬해야합니다.
A를 추가하는 이유는, A에서 시작하기 때문.
역추적할때는 이를 감안해서 해주면 됩니다.

난이도 자체는 간단한데, 이건 구간dp를 떠올렸을때 이야기고..
구간dp가 좀 잘 숨어있다고 생각합니다.
딱 골드2수준같아요. 골3 기본인 구간dp+역추적+떠올리기 까다로움(어렵진않음) 해서 G2 기여했습니다.
사실 제가 구간dp 오랜만에 하는거라 헷갈린거지 평소에 잘 해둔사람이면 금방 풀것같아요.
"""
def loop(ch, l, r):
    if (l-1+ln)%ln == r: return 0
    if dp[ch][l][r] != -1: return dp[ch][l][r]
    lch = N[(l-1+ln)%ln]
    rch = N[(r+1)%ln]
    ldist = min(abs(ch-lch), 26-abs(ch-lch))
    rdist = min(abs(ch-rch), 26-abs(ch-rch))
    left = loop(lch, (l-1+ln)%ln, r)+ldist
    right = loop(rch, l, (r+1)%ln)+rdist
    trace[ch][l][r] = [0, 1][left > right]
    dp[ch][l][r] = min(left, right)
    return dp[ch][l][r]

def backT(ch, l, r):
    if not (ch == 0 and 'A' not in st):
        print(chr(ch+ord('A')), end = "")
    if (l-1+ln)%ln == r: return
    if trace[ch][l][r]:
        backT(N[(r+1)%ln], l, (r+1)%ln)
    else:
        backT(N[(l-1+ln)%ln], (l-1+ln)%ln, r)

S = input().replace(" ", "")
st = set(S)
N = [*map(lambda x: ord(x)-ord('A'), sorted(st | {'A'}))]
ln = len(N)
dp = [[[-1]*ln for _ in ' '*ln] for _ in ' '*26]
trace = [[[0]*ln for _ in ' '*ln] for _ in ' '*26]
print(loop(0, 0, 0)+len(st))
backT(0, 0, 0)
