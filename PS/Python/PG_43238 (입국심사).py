"""
프로그래머스 43238 입국심사

백준문제 두개 풀고 심심해서 프로그래머스 갔다가
이분탐색이다 히히 하고 푼 문제입니다.
처음엔 이분탐색은 모르겠고 우선순위큐 가자~ 했다가 시간초과 맞고,
이분탐색으로 제대로 풀었습니다.

정확힌 이분탐색이라기보단 매개변수탐색입니다.
레벨 3치고 쉬웠네요. 분류 알고 풀어서 그런가.
"""
def solution(n, times):
    def cnt(times, obj):
        res = 0
        for i in times:
            res += obj//i
        return res
    l = min(times); r = max(times)*n
    while l < r:
        m = l + r >> 1
        if cnt(times, m) >= n:
            r = m
        else:
            l = m + 1
    answer = r
    return answer
