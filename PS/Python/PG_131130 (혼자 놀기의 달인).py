"""
131130 혼자 놀기의 달인

https://school.programmers.co.kr/learn/courses/30/lessons/131130
문제는 사이트를 참고해주세요.

점수의 최댓값을 구하는 문제입니다.
어차피 카드의 최대 개수가 100이라서 그냥 완전탐색 했습니다.
"""
def solution(cards):
    answer = 0
    def score(start, second):
        arr = [1]*len(cards)
        sc1 = 0
        while arr[start-1]:
            arr[start-1] = 0
            start = cards[start-1]
            sc1 += 1
        sc2 = 0
        while arr[second-1]:
            arr[second-1] = 0
            second = cards[second-1]
            sc2 += 1
        return sc1*sc2
    for i in range(1, len(cards)+1):
        for j in range(1, len(cards)+1):
            answer = max(answer, score(i, j))
    return answer
