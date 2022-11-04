import sys
input = sys.stdin.readline
find = 'MOLA'
"""
17351번 3루수는 몰라

알파벳 대문자가 N*N에 널브러져있습니다.
(1,1)에서 시작해서, 우측과 아래로만 적절히 이동해 (N,N)으로 이동할 때
부분문자열 'MOLA'를 최대한 많이 가지는 문자열의 'MOLA' 개수를 출력해주세요.

보자마자 BFS로구나 하고 두번 냈다가 메모리초과 두번맞았습니다.
BFS가 안돼?? 그럼 DP네 아... 이생각으로 풀었는데,
이게 또 문자열DP, 제가 싫어하는 두개가 합쳐진 모양새라..
뭐 우짭니까. 이미 손 댄 문제, 못풀거같지도 않고. 풀어야죠.
난이도는 골드4긴 한데, 개인적으로 진짜 까다롭게 풀었습니다.
전체적으로 정수삼각형 느낌이네요..
"""

# 사실 생각해보면 굳이굳이 문자열 전체를 저장한다음 .count쓰겠다는
# 고집이 체감난이도를 더 높인걸지도 모르겠습니다.
def put_char(string, char):
    if not len(string):
        if char == 'M': return char
        return ""
    for i in range(4):
        if string[-1] == find[i] and char == find[(i+1)%4]:
            return string+char
    if len(string)%4: mod = -(len(string)%4)
    # 다음 문자가 MOLA를 만드는데 방해한다면, 이렇게 MOLA가 생기기 전으로 꺾어버립시다.
    else: mod = len(string)
    return string[:mod] + ('M' if char == 'M' else "")
n = int(input())
M = list(list(input().rstrip()) for _ in ' '*n)
if M[0][0] != 'M':
    M[0][0] = ''
for i in range(1, n):
    M[i][0] = put_char(M[i-1][0], M[i][0])
    M[0][i] = put_char(M[0][i-1], M[0][i])
for i in range(1, n):
    for j in range(1, n):
        tmp1 = put_char(M[i-1][j], M[i][j])
        tmp2 = put_char(M[i][j-1], M[i][j])
        M[i][j] = max(tmp1, tmp2, key=len)
print(M[-1][-1].count(find))

