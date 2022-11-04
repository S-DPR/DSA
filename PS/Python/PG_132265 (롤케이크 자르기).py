"""
132265 롤케이크 자르기

수열 A가 주어집니다.
A를 적절하게 두개로 나누어 서로 다른 수의 개수가 같게 하는 경우의 수를 세주세요.

https://www.acmicpc.net/problem/13547
수열과 쿼리 5 아시는구나!!!!
전체적으로 저 문제 풀 때 풀었던 알고리즘으로 풀었습니다.
이제생각해보니 그냥 딕셔너리 쓰면 되는 쉬운문제였지만,
머릿속에서 수열과 쿼리 5가 떠나질 않았네요..
"""
def solution(topping):
    answer = 0
    a, b = [0]*10001, [0]*10001
    acnt, bcnt = 1, 0
    a[topping[0]] += 1
    for i in range(1, len(topping)):
        if not b[topping[i]]: bcnt += 1
        b[topping[i]] += 1
    if acnt == bcnt: answer += 1
    for i in range(1, len(topping)):
        if not a[topping[i]]: acnt += 1
        if b[topping[i]] == 1: bcnt -= 1
        a[topping[i]] += 1
        b[topping[i]] -= 1
        if acnt == bcnt: answer += 1
    return answer
