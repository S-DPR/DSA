"""
118667 두 큐 합 같게 만들기

길이가 같은 큐 두개가 주어집니다. 큐의 길이는 300000 미만의 자연수입니다.
큐중 한곳을 pop하고 다른 한곳에 push하는 작업을 1회라 할 때,
두 큐의 합을 같게 하는 최소의 횟수를 구해주세요.
불가능한 경우 -1을 출력해주세요.

굳이 deque 소환 안해도 list만으로 깔끔하게 처리 가능합니다.
한바퀴를 돌아돌아 인덱스를 재확인 할 필요는 없으니 원래 길이의 2배 이상이 된다면,
그 곳은 pop-push작업을 중단해도 됩니다.
"""
def solution(queue1, queue2):
    answer = 0
    origin_len = len(queue1)
    s1, s2 = sum(queue1), sum(queue2)
    idx1, idx2 = 0, 0
    while s1 != s2:
        if idx1 < origin_len*2 and s1 > s2:
            s1 -= queue1[idx1]
            s2 += queue1[idx1]
            queue2.append(queue1[idx1])
            idx1 += 1
        elif idx2 < origin_len*2 and s1 < s2:
            s1 += queue2[idx2]
            s2 -= queue2[idx2]
            queue1.append(queue2[idx2])
            idx2 += 1
        else: return -1
        answer += 1
    return answer
