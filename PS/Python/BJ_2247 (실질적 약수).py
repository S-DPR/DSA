"""
2247번 실질적 약수

N 이하 모든 수의 실질적 약수 합을 구하여라.
실질적 약수란, 1과 자기 자신을 제외한 약수이다.

무려 67B로 정리가 되는 골드문제입니다!
(골드 5도 일단 골드니까요.)
풀이가 두 개 있습니다. 하나는 O(N)입니다.
방법은, N//2 이하의 수 i를 i*N//i-i로 계산하면 그 실질적 약수를 모두 더한 효과가 난다는 겁니다.
100 이하의 2의 배수는 50개, 그중 자기 자신을 빼서 49개,
이 49개는 2를 가지니 49*2를 해서 98이라는 숫자를 얻는 식입니다.
이런 방식으로 이 문제는 풀 수 있습니다.
"""
n=int(input());print(sum(i*(n//i-1)for i in range(2,n//2+1))%10**6)
"""
O(sqrt(N))방법이 있습니다.
Harmonic Lemma라는 보조정리가 있는데요..
https://xy-plane.tistory.com/17
이 사이트에서 잘 정리해주고 있습니다.
대충 어.. 결론은..
S(N) = Sigma(i=1, i->t) (N//s(i)*(e(i)-s(i)+1))
.. 라네요. 사이트로 들어가서 수식으로 봅시다.
"""
"""
n = int(input())
res = 0
i = 1
while i <= n:
    j = n//(n//i)
    res += (n//i)*(j-i+1)*(i+j)//2
    i = j+1
print(res-(n-1)-(n*(n+1)//2))
"""
