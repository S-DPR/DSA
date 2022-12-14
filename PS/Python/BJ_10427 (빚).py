"""
10427번 빚

문제에 스토리텔링이 쭉 있는데요, 어쨌든 요건은..
"N의 길이를 가진 배열 arr이 있을 때, arr에서 수를 K개 (K <= N) 고르자.
그리고 고른 K개의 수 중 가장 큰 수를 M이라 할 때,
M * K - (고른 K개의 수의 합)을 S(K)라고 하자.
마지막으로, S(1) + S(2) + ... + S(N-1) + S(N)의 합을 구해보자."

문제 이해에 시간 상당히 썼습니다. 이거 왜 안돼?! 하면서 되는 경우의수 다 때려박아서 찾았네요..
어쨌든 문제에 예시를 보면 쉽게 알 수 있지만, "가능하면 차가 제일 작은 수들을 고르자"가 결론입니다.
대충 문제 예시 보고 20초의 생각의 시간을 가진 뒤 알아낸 사실인데,
이거 모르면 상당히 고생할 문제입니다.

하지만 K개의 수를 골라야하죠.
M보다 가깝지만 작은 수를 끼워넣어서 K개의 수를 만들면 됩니다.
절댓값이 큰걸 뺄수록 수는 작아집니다. 이건 진리죠.
그러면 고른 K개의 수의 합을 최대화해야합니다. 그러면서 최댓값은 늘리면 안돼요.
(생각해보면, 곱셈연산은 덧셈연산보다 훨씬 빠르게 커지죠.)
결국 K보다 작고 가장 가까운 수 K-1개를 골라야한다는 결론이 나옵니다.

서론이 길었습니다. 최종 결론은
1. 받은 배열을 정렬한다.
2. 누적합 알고리즘을 사용한다.
입니다.
"""
for _ in ' '*int(input()):
    n, *arr = map(int, input().split())
    arr.sort() # 배열을 정렬해주고요
    K = [0]
    for i in arr:
        K.append(K[-1]+i) # K는 누적합이 들어있는 배열입니다. O(1)이죠.
    s = 0
    for i in range(1, n):
        m = 10**9 # 실질적 inf값으로 설정해주고
        for j in range(n-i):
            m = min(m, (i+1)*arr[j+i]-(K[j+i+1]-K[j])) # 문제에 나온걸 그대로 구현해줍니다.
        s += m # 나온 최솟값은 s에 더해주고요.
    print(s) # 출력합니다.

# 시간복잡도 : O(N^2)
