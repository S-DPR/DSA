import sys
input = lambda: sys.stdin.readline().strip()
ini = lambda: int(input())
ins = lambda: [*map(int, input().split())]
inf = float('inf')
"""
Round 940 (Div 2) C : How Does the Rook Move?

룩을 서로 공격당하지 않게 배치하려고 한다. 룩이 두어지는 규칙은 아래와 같다.
 - 백색 룩을 공격받지 않는 위치에 둔다. 그 위치를 (r, c)라고 하자.
 - r != c라면 상대는 (c, r)에 흑색 룩을 둔다.
 - r == c라면 상대는 아무 말도 두지 않는다.
초기에 이미 K번의 룩을 두었고, 위와 같은 작업이 일어났다.
이제 최종적으로 나올 수 있는 룩 배치의 경우의 수를 구해보자.
수가 너무 커질 수 있으니 1_000_000_007로 나눈 나머지를 구해보자.

하? 이게 dp라고?
뭐 경우의 수라고 하는데 dp든 조합론이든 둘 중 하나 성공시켰어야했는데 못한 내 잘못이지..

에디토리얼 보고 그냥 따라 구현했습니다.
우선 에디토리얼 말대로 정의하면,
dp[i] = i*i 그리드에서 아무 룩도 배치되지 않았을 경우 룩을 둘 수 있는 경우의 수.
라고 합니다.

그리고 이게 제가 발견하지 못하고 이상하게만 생각했던 부분인데,
룩을 둘 때마다 크기가 1 혹은 2씩 그리드 크기가 깎여버린다고 생각할 수 있습니다.
r == c면 1, 아니면 2가 깎여버리겠죠.
아니 이게 왜 이렇게? 라고 생각한다면,
어차피 그 행과 열에는 룩을 둘 수 없으므로 고려를 할 수 없기 때문입니다.
여기에 추가로, 뭘 선택하든 저거만 영향을 받으니 어떤 열을 선택해서 두냐는 관련이 없어집니다.

그러면 이제 dp정의와 연관이 생깁니다. dp[i]는 일단 dp[i-1]과 dp[i-2]에 연관성을 갖겠네요.
dp[i]에서 r = c인 곳에 아무데나 던져뒀다고 생각해봅시다.
우선 이 부분은 흑색과 자리를 바꿔 둘 수 도 없으므로 2를 곱할 일이 없고,
그냥 이제 남은 부분에 룩을 배치해야합니다. 더이상 뭔가를 할 수 없으니 dp[i-1]과 같은 상태겠네요.
그런데 그러면 열이 i개 있으니까 i라도 곱해줘야하지않나? 싶겠지만,
dp[i-1] 내에 그게 처리되어있습니다.

이제 dp[i-2]에 연관성을 이어봅시다.
이 부분은 두면 흑색도 같이 두므로 2를 곱해야합니다. 위치 바꿀 수 있으니까요.
근데 이거는 보세요, 같은 i열이라도 무려 i-1개의 칸에 둘 수가 있습니다!
그러니까, 어떤 열 r을 하나 잡으면 (r, 1), (r, 2), ..., (r, i)에 배치가 가능해요.
근데 저중에서 (r, r)은 1번의 행위니까 제외해줘서 i-1.
그러므로 2(i-1)dp[i-2]...

이 두 개를 더하면 됩니다. 간단하죠.
아 근데 이건 내가 절대 시간내에 생각 못하겠는데.. 경우의수는 이렇게 들어가야하나..
"""
for _ in ' '*ini():
    N, K = ins()
    for _ in ' '*K:
        r, c = ins()
        N -= 1 + (r != c)
    dp = [1]*(N+1)
    for i in range(2, N+1):
        dp[i] = dp[i-1] + dp[i-2]*2*(i-1)
        dp[i] %= 1_000_000_007
    print(dp[-1])
