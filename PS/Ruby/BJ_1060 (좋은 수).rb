=begin
1060번 좋은 수

먼저, 좋은 구간과 좋은 수에 대한 정의를 하겠습니다.
좋은 구간이란, 구간 [A, B]에 대하여
 1. A < B를 만족합니다.
 2. A 이상 B 이하의 모든 수가 집합 S에 포함되지 않습니다.
의 조건을 만족하면 좋은 구간입니다.

좋은 수란, 좋은 구간 내에 있는 수를 의미합니다.
좋은 수의 우선순위는 '작은 수일수록', '더 적은 좋은 구간에 포함될수록' 높아집니다.

집합 S가 주어집니다. 이후 정수 N이 주어집니다. N개의 좋은 수를 정렬하여 출력해주세요.
단, 정렬 기준은 '좋은 수' 기준으로 정렬하여야 합니다.
(S원소는 1_000_000_000을 넘지 않음, 1 <= N <= 100)

일단 깡구현은 이쪽으로. 여기에 쓰기엔 너무 길어져서(1436B) 분리.
깡구현에 대한 서술도 같이 할겁니다.
http://boj.kr/d10e194b01674034aae6b4d4e898a5df

요즘은 이상하게 우선순위 큐 문제가 많이 잡히네요.
깡구현이 처음 푼 방법인데, 우선순위큐는 푼 뒤 태그보고 알았습니다.

깡구현이든 우선순위큐든 일단 알아채야할 규칙이 있습니다.

만약에 [A, B, C, D, E]라는 연속된 배열이 있을 때,
A를 포함하는 연속한 부분배열의 개수는 몇 개 일까요?
[A], [A, B], [A, B, C], [A, B, C, D, E]로 5개입니다.

B인경우는,
[A, B], [A, B, C], [A, B, C, D], [A, B, C, D, E]
[B], [B, C], [B, C, D], [B, C, D, E]
로 무려 8개가 됩니다.

C는
[A, B, C, D, E], [A, B, C, D], [A, B, C]
[B, C, D, E], [B, C, D], [B, C]
[C, D, E], [C, D], [C]
로 9개죠.

보면, 길이는 L, 임의의 인덱스를 K라 할 때,
왼쪽으로 K개의 선택지와 오른쪽으로 (L-K+1)개의 선택지가 있음을 확인할 수 있습니다.
왼쪽에서 무엇을 선택하는가와 오른쪽에서 무엇을 선택하는가는 관련이 없으니 독립사건이고,
이는 길이가 L인 배열에서 인덱스 K를 포함하는 부분배열의 개수는 K(L-K+1)개임을 알 수 있습니다.

일단 최대길이의 좋은구간을 모두 구합시다.
구한 다음에 길이 갈리는데요,

우선순위큐의 경우에는 N이 100 이하임을 이용해
모든 구간에 대해 N개 이하의 원소를 모두 뽑아냅니다.
물론 규칙은 위와 같고, 우선순위큐에는 [(부분배열의 개수), (현재 수)]로 넣습니다.
이후 N개만큼 출력을 하면 AC가 나옵니다.

깡구현을 하려면, 위에서 좋은구간을 모두 구할 때 [시작숫자, 끝숫자]가 아닌,
[시작숫자, 끝숫자, 좌측/우측여부(0or1), 뽑아낸횟수]로 적습니다.
그리고 모든 구간에 대하여
 0. 뽑아낸 횟수가 구간 길이의 중간을 넘지 않는 구간에서,
 1. 뽑아내려는 수는 (좌측/우측여부에 따른 경계선에 있는 수)(좌측우측에 따라, +or-)((뽑아낸횟수)-1) 입니다.
 2-1. 현재 가중치(부분수열의 개수)가 현재 뽑아내려는 수보다 작다면
 2-2. 같다면, 현재 수가 뽑아내려는 수보다 작다면
 3. 그걸 바꿉니다.
이후, 좌측/우측여부는 XOR1처리를 하고,
뽑아낸 횟수는 다시 좌측으로 갔을 때 1씩 올리면 처리가 됩니다.

뽑아낸 뒤에, 현재 인덱스에 대하여 그것이 완벽히 중앙값인지 파악하고,
중앙값이라면 뽑아낸 횟수에 1을 더해 더 못뽑게 합니다.
만약 그렇게 다 뽑았음에도 부족하다면 제일 우측(~INF 범위)에서 계속 뽑아내 값을 얻어냅니다.
=end
class PriorityQueue
  def initialize(compare)
    @arr = []
    @arr_size = 0
    @compare = compare
  end
  def <<(x)
    @arr_size += 1
    idx = @arr_size
    while idx != 1 and @compare.call(@arr[idx>>1], x)
      @arr[idx] = @arr[idx>>1]
      idx >>= 1
    end
    @arr[idx] = x
  end
  def pop
    if @arr_size.zero? then return 0 end
    result = @arr[1]
    idx, val = 1, @arr[@arr_size]
    @arr_size -= 1
    while idx<<1 <= @arr_size
      child = idx<<1
      if child < @arr_size and @compare.call(@arr[child], @arr[child+1]) then child += 1 end
      if @compare.call(@arr[child], val) then break end
      @arr[idx] = @arr[child]
      idx = child
    end
    @arr[idx] = val
    result
  end
end

inf = 1_000_000_001 * 1_000_000_001
P = PriorityQueue.new(->(a, b) { a[0] != b[0] ? a[0] > b[0] : a[1] > b[1] })

L = gets.to_i
S = (gets.split.map &:to_i).sort
N = gets.to_i
segment = []
prev = 1
L.times do |i|
  segment.push([prev, S[i]-1]) if (S[i]-prev) >= 1
  prev = S[i]+1
end
S.each do |i| P << [0, i] end
segment.each do |i|
  if (i[1]-i[0]).zero?
    P << [0, i[0]]
    next
  end
  l, r, lt, rt = i[0], i[1], 1, i[1]-i[0]+1
  100.times do
    break if l > r
    P << [lt*rt, l]
    P << [lt*rt, r] if l != r
    l, r = l+1, r-1
    lt, rt = lt+1, rt-1
  end
end
r = S[-1]+1
100.times do
  P << [inf, r]
  r += 1
end

N.times do print P.pop[1], " " end