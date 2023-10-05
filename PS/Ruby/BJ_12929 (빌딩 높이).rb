spl = ->() { gets.split.map &:to_i }
=begin
12929 빌딩 높이

빌딩을 세우려 하는데, M개의 건물에는 제한을 걸려 한다.
제한은 "X[i]번째 건물이 T[i] 이하의 높이를 가져야 한다."이다.
모든 빌딩은 이전 건물보다 최대 K까지의 높이차를 가질 수 있으며,
1번째 빌딩의 높이는 0이다.
이 규칙으로 빌딩을 세울 때, 가장 높게 세울 수 있는 빌딩의 높이를 구해보자.

대충 보다가 이야 매개변수각이다 매개변수각 이러면서 푼 문제.
그런데 질문게시판 대충보다가 한번 틀렸습니다..

일단 매개변수탐색으로 굴리고, 그리고 X 높이들에 대해 식 세워서 가능한곳이 한 곳이라도 있다면,
left를 올리고 아니면 right를 내리고.
이거를 쭉 반복한는게 기본 로직입니다.
이 때, 현재위치->mid->다음위치 까지 가는걸 구하는게 핵심. 저는 여기 코드에 있는거로 했습니다.

그런데 복병이 있는데, 저거만 하면 이제 X[i]의 최댓값에서 X[i+1]의 최댓값으로 못가는 경우가 있습니다.
이거는 아예 처음에 T[i]를 쭉 보면서 전처리해줘야합니다. 이거때매 틀렸네요.
=end
N, K = spl.()
M = gets.to_i
X, T = M.positive? ? [spl.(), spl.()] : [[], []]
X.unshift(1)
T.unshift(0)
X << N
T << N*K
X.length.times.reverse_each do |i|
    next if i.zero?
    T[i-1] = [(X[i]-X[i-1])*K+T[i], T[i-1]].min
end
left, right = 0, N*K
while left < right
    mid = (left + right) >> 1
    flg = false
    curH = 0
    (1...X.length).each do |i|
        toMid = ([mid-curH, 0].max+K-1)/K
        toNxt = ([mid-T[i], 0].max+K-1)/K
        flg |= toMid+toNxt <= X[i]-X[i-1]
        curH = [(X[i]-X[i-1])*K+curH, T[i]].min
    end
    !flg ? right = mid : left = mid + 1
end
p right-1