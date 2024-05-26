ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
18892번 가장 긴 증가하는 부분 수열 ks

수열이 주어진다. K번째 LIS를 찾아보자. 없으면 -1을 출력하자.

캬 문제 간단한거봐
그런데 난이도는 어쨰서?????????????

흑흑
dp를 3단계로 푸는게 정해인데 (LIS 길이 구하기, 각 항목에서 끝나는 LIS 개수 구하기, K번째 구하기),
저처럼 랭크도 낮고 PS를 하기에 지능이 조금 낮은 족속은 굳이굳이 세그트리 갖다써서 N^2logN을 갖다 씁니다..
심지어 정렬도 매번함 ㅋㅋ 안할 방법 있는데 분명

머 어쨌든 그냥.. 그냥.. 하..
네.. dp[i][j] = i번째에 대해 길이가 j인 lis 길이.
근데 이거 하다보니까 누적합..비슷한게 필요하네?
어 근데 매번 업데이트하네?
ㅋㅋ 세그트리 드가자~
.. 가 결론.
어휴..
=end
def update(f, idx, val)
  while idx < N+1
    f[idx] += val
    idx += idx&-idx
  end
end

def int_query(f, idx)
  ret = 0
  while idx > 0
    ret += f[idx]
    idx -= idx&-idx
  end
  ret
end

def query(f, l, r)
  int_query(f, r) - int_query(f, l-1)
end

N, k = ins.()
A = ins.().reverse
# dp[i][j] = 현재 인덱스가 i이고 길이가 j인 is 개수
dp = N.times.map do [0]*(N+1) end
F = (N+1).times.map do [0]*(N+1) end
N.times do |i|
  dp[i][1] = 1
  update(F[i], 1, 1)
end
N.times do |i| # 현재 인덱스
  N.times do |ln| # 이전 길이 순회
    dp[i][ln+1] += query(F[ln], A[i]+1, N)
    update(F[ln+1], A[i], dp[i][ln+1])
  end
end
lis = [[-INF, N+1]]
lis_len = -1
(1..N).each do |ln|
  exist = false
  items = N.times.map do |i|
    exist = exist || dp[i][-ln] != 0
    [A[i], i, dp[i][-ln]]
  end.sort
  lis_len = N-ln+1 if lis_len == -1 && exist
  while !items.empty?
    val, idx, cnt = items.shift
    next if val <= lis[-1][0] || lis[-1][1] <= idx
    if cnt < k
      k -= cnt
    else
      lis << [val, idx]
      break
    end
  end
end
if lis.length == lis_len+1
  lis.shift
  ret = lis.map do |i, _| i end
  print ret.join(" ")
else
  print -1
end
