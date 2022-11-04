"""
131704 택배상자

수열 A가 주어집니다. 이 수열 A에는 1부터 자신의 길이까지의 숫자가 하나씩 정렬되어 있습니다.
1부터 수열 A 길이까지의 모든 수가 있는 수열 B도 있습니다. 수열 B는 정렬되어있지 않습니다.
수열 B의 인덱스 i~j를 한번에 뒤집을 수 있습니다.
이 작업을 최소로 하여 수열 B를 수열 A로 만들 때, 그 횟수를 출력해주세요.

문제가 많이 다르다고요?
하다보면 동치인걸 알 수 있습니다.
그리고 저게 차라리 나아요, 이거 문제 이해에 20분 넘게걸렸어요..
"""
def solution(order):
    answer = 0
    order = order[::-1]
    buf = []
    curN = list(range(len(order), 0, -1))
    while order:
        find_tax = order[-1]
        if curN and curN[-1] == find_tax:
            answer += 1
            curN.pop()
            order.pop()
        elif buf and buf[-1] == find_tax:`
            answer += 1
            buf.pop()
            order.pop()
        elif curN:
            buf.append(curN.pop())
        else:
            break
    return answer

""" 이건 딴사람이 짠거 보고 와 이게 더 낫네 하고 만든거
def solution(order):
    answer = 0
    order = order[::-1]
    buf = []
    curN = list(range(len(order), 0, -1))
    while curN:
        buf.append(curN.pop())
        while buf and buf[-1] == order[-1]:
            answer += 1
            buf.pop()
            order.pop()
    return answer
"""
