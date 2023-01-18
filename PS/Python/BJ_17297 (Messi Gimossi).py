"""
17297번 Messi Gimossi

다음과 같은 규칙을 가진 함수가 있다.
F(1) = Messi
F(2) = Messi Gimossi
F(3) = Messi Gimossi Messi
F(4) = Messi Gimossi Messi Messi Gimossi
F(5) = Messi Gimossi Messi Messi Gimossi Messi Gimossi Messi
...
피보나치 수열과 매우매우 비슷한 함수인듯 하다.
N은 F에 충분히 큰 수가 들어갔을 때, N번째 문자를 뜻한다.
예를들어 N이 10이라면 문자열의 길이가 10이상인 F(2)의 10번째 문자인 'o'가 나올 것이다.
N이 2의 30승 미만의 자연수일 때, 어떤 문자가 출력되는지 구해보자.
단, 그 문자가 공백일경우 Messi Messi Gimossi를 대신 출력하자.

GOAT Messi
일단 문제 자체는 피보나치 변형 그 자체입니다. 그거로 골드 3인게 대단해요.
뭐 엄청나게 큰 수도 아니고 2의 30승 미만의 자연수인데..

풀이의 핵심은 이겁니다.
'어차피 반복은 잘라내면 된다'
N에서 N보다 작고 가장 큰 피보나치수를 뺍니다.
그 전에, 이 피보나치수의 첫 값과 두번째 값은 5와 13입니다. 각각 F(1)의 길이와 F(2)의 길이입니다.
어쨌든 빼고나서 다시 위 작업을 반복합니다.
N이 14 이하가 되었다면 이제 멈추는데,
그냥 이제는 "Messi Gimossi "에서 골라내면 됩니다.

인터넷에서 로직은 참고했지만, 이게 왜 되는지는 아직도 참 신기하네요..
"""
n = int(input())
S = "Messi Gimossi "
fib = [5, 13]
while fib[-1] <= n:
	fib.append(fib[-1]+fib[-2]+1)
while n > 14:
	for i in range(len(fib)):
		if fib[i] > n:
			n -= fib[i-1]+1
			break
print(S[n-1] if S[n-1]!=' ' else "Messi Messi Gimossi")