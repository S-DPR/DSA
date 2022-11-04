from collections import defaultdict
"""
131127 할인 행사

배열 A의 10개 원소를 가지는 연속된 부분배열중,
want가 각각 number번 나오는 경우를 세주세요.

투포인터를 쓰거나, Counter을 쓰거나.
Counter은 잊고있다가 오늘 다른사람 풀이보고 생각났네요.
저는 투포인터 비슷하게 풀었습니다.
"""
def solution(want, number, discount):
    def test_disc(now):
        for i, j in zip(want, number):
            if now[i] < j: return 0
        return 1
    answer = 0
    discount = discount[::-1]
    now = defaultdict(int)
    for i in range(-1, -11, -1):
        now[discount[i]] += 1
    answer += 1 if test_disc(now) else 0
    while len(discount) > 10:
        now[discount.pop()] -= 1
        now[discount[-10]] += 1
        answer += 1 if test_disc(now) else 0
    return answer
