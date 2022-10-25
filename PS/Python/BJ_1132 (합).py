import sys
input = sys.stdin.readline
"""
1132번 합

길이가 최대 12인 문자열이 최대 50개 주어진다.
각 문자열은 A~J로 이루어져있으며, 각 문자는 숫자 하나에 대응한다.
이 때, 각 문자에 적절한 수를 대입하여 최대 값을 찾아 출력해라.
단, 0으로 시작하는 숫자는 없다.

테스트케이스의 개수와 길이를 보면 알 수 있는데,
'시간은 신경쓰지말고 구현이나 해라' 라는 문제입니다.
저는 상당히 난해하게 해버린 편인데..
아래 방법대로 했습니다.

0. 각 문자열을 입력받고, 각 문자가 몇번째 자리 어디에 있는지 체크할 수 있는 배열을 만듭니다.
1. 각 문자열을 입력받고, 문자열의 자릿수에 대응하여 배열에 채워넣습니다.
2. (각 인덱스에 대응하는 자리수)*(해당 인덱스에 있는 값)를 기준으로 내림차순 정렬합니다.
3. 하나도 포함되지 않은 문자는 제거합니다.
4-1. 그럼에도 문자가 10개 있다면, 0이 될 수 있는 모든 문자에 0을 대입해보고 그리디하게 처리하여 그중 최댓값을 취합니다.
4-2. 문자가 9개 이하라면, 그리디하게 문자에 값을 대입합니다.
"""
arr = [input().rstrip() for _ in ' '*int(input())]
NOZERO = set(i[0] for i in arr)
alpha = [[[0]*12, chr(ord('A')+i)] for i in range(10)]
for i in arr:
    for idx, j in enumerate(i):
        alpha[ord(j)-ord('A')][0][-len(i)+idx] += 1
alpha.sort(key = lambda x: (sum(i*10**(12-idx) for idx, i in enumerate(x[0]))), reverse=True)
ans = 0
while alpha[-1][0] == [0]*12: alpha.pop()
if len(alpha) == 10:
    for idx_, (_, a) in enumerate(alpha):
        if a in NOZERO: continue
        res = 0
        for (i, j), n in zip(alpha[:idx_]+alpha[idx_+1:], range(9, 0, -1)):
            for idx, k in enumerate(i):
                if k: res += (k*n)*(10**(11-idx))
        ans = max(ans, res)
else:
    for (i, j), n in zip(alpha, range(9, 0, -1)):
        for idx, k in enumerate(i):
            if k: ans += (k*n)*(10**(11-idx))
print(ans)
