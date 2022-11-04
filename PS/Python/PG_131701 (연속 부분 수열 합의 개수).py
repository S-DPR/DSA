"""
131701 연속 부분 수열 합의 개수

원형수열 A가 주어집니다.
A의 모든 부분수열의 합의 개수를 구해주세요.

그냥 2중반복문에 for문 때리면 됩니다.
차라리 set 직접 만들기가 더 빡세겠네요..
"""
def solution(elements):
    answer = 0
    s = set()
    element_length = len(elements)
    elements += elements
    for i in range(1, element_length+1):
        for j in range(element_length+1):
            s.add(sum(elements[j:j+i]))
    answer = len(s)
    return answer
