import sys, math
input = sys.stdin.readline
lcm = lambda x, y: abs(x*y)//math.gcd(x, y)
"""
10253번 헨리

어떤 진분수가 주어진다.
이 진분수보다 작은 단위분수중 가장 큰 것을 x라고 하자.
진분수에서 x를 빼면, 0 혹은 어떤 수가 나올 것이다.
0이 나왔다면 x를 출력후 프로그램을 종료하고,
아니라면 위 행위를 반복한다.
출력되는 x를 구하시오.

무려 진분수를 단위분수로 나타내기! 인데
알고리즘을 다 던져줘서 그냥 그거 받아먹기만 하면 됩니다.
1/x <= a/b를 만족하는 가장 큰 x는 b/a이지만,
b/a가 정수라는 보장이 없죠. 일단 x에 b/a를 저장은 합니다.
여기서 b%a가 존재한다면 x에 1을 더합니다.
왜냐면, 예를들어 1/1.5보단 1/2가 더 작기 때문이죠. 1/1은 1/1.5보다 크니까요.
이후 분수의 뺄셈을 lcm을 써서 구현해준 뒤 문제에서 나온걸 그대로 반복하면 됩니다.
"""
for _ in ' '*int(input()):
	ans = 0
	a, b = map(int,input().split())
	while a:
		x = math.ceil(b/a)
		tmp = lcm(x, b)
		a, b = a*(tmp//b)-(tmp//x), tmp
	print(x)