"""
134239 우박수열 정적분

콜라츠 추측에 대해, 정적분을 실행하세요.
첫 값은 k로, 구할 정적분의 범위는 ranges에 [left, right]형식으로 주어집니다.
ranges의 left는 200이하의 음의정수가 아닌 정수, right는 양의정수가 아닌 정수입니다.
k = 5에 대해 우박수열은 5-16-8-4-2-1입니다.
이 때, left가 3이고 right가 -1이면 x=3부터 x=5까지 정적분하면 됩니다.
left가 1이고 right가 0이면 x=1부터 x=6까지 정적분하면 됩니다.
모든 ranges에 대해, 정적분을 구해주세요.
단, 범위를 벗어나는 ranges에 대해선 -1을 내보내주세요.

콜라츠추측이란,
1-1. 입력된 수가 짝수면 2로 나눈다.
1-2. 입력된 수가 홀수면 3을 곱하고 1을 더한다.
2. 결과로 나온 수가 1이 아니면 1번을 반복한다.

그냥 누적합 문제입니다. 긴 설명 필요 없습니다.
"""
def solution(k, ranges):
    answer = []
    arr = [k]
    while k > 1:
        if not k % 2: k //= 2
        else: k = k*3+1
        arr.append(k)
    area = [0]
    for i in range(len(arr)-1):
        area.append(area[-1]+(arr[i]+arr[i+1])/2)
    for a, b in ranges:
        if a >= len(area) or b-1 < -len(area): answer.append(-1)
        elif area[b-1]-area[a] < 0: answer.append(-1)
        else: answer.append(area[b-1]-area[a])
    return answer
