"""
133501 야간 전술보행

https://school.programmers.co.kr/learn/courses/30/lessons/133501#
문제는 직접 보시길 바랍니다.

평범한 모듈러연산 문제인데..
삽질을 좀 많이 했습니다.
"""
def solution(distance, scope, times):
    answer = distance
    for i in range(len(scope)):
        start, end = sorted(scope[i])
        startT, endT = times[i]
        all = startT+endT
        enter = k if (k:=start%all) else all
        # 들어갈 때 startT 이하인지 봅니다.
        # 즉, 근무중인지 확인하는겁니다.
        if enter <= startT:
            answer = min(answer, start)
        # 잘 들어갔어도 중간에 걸릴 수 있겠죠.
        # 현재는 쉬고있으니, 쉬는 시간 + 1만큼 이동할 수 있습니다.
        # 그동안 범위를 못빠져나갔으면 잡혀야죠 뭐.
        elif start+(all-enter+1) <= end:
            answer = min(answer, start+(all-enter+1))
    return answer
