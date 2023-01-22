=begin
19623번 회의실 배정

회의실이 하나 있고, 회의 일정이 N개가 있다.
각 회의 일정은 S, E, W로 이루어져있으며 각각 시작시간, 끝시간, 사람의 수이다.
이 때, 누적사용자가 최대가 되도록 일정을 짰을 때, 그 수를 구해보자.

처음으로 분류와 완전히 다른 방식으로 푼 문제입니다.
저는 그리디+우선순위 큐로 풀었는데, 분류 정해는 이분탐색+DP(+좌표압축)같네요.
골드5찾다가 회의실문제라길래 쫄래쫄래 따라가서 풀어본 문제였습니다.

알고리즘은 다음과 같습니다.
1. 입력받은 것을 시작시간으로 정렬합니다.
2. 우선순위 큐에 끝시간이 더 작은 값이 있으면 그걸 가져옵니다.
2-1. 이를 반복합니다. 그렇게해서 이전의 누적 최댓값을 구합니다. 이는 이후에도 계속 사용됩니다.
3. 우선순위 큐에 이번 일정을 넣는데, [끝시간, 누적사람+이번사람]으로 넣습니다.
4. 이를 모두 반복하고, 일정을 모두 체크해본 뒤에도 우선순위 큐가 비지 않았다면 전부 탈탈 털어 사람의 최댓값을 얻습니다.
5. 답을 출력합니다.
=end
class PriorityQueue
  def initialize
    @arr = []
    @arr_size = 0
  end

  def <<(x)
    @arr_size += 1
    idx = @arr_size
    while idx != 1 && @arr[idx >> 1][0] > x[0]
      @arr[idx] = @arr[idx >> 1]
      idx >>= 1
    end
    @arr[idx] = x
  end

  def pop
    result = @arr[1]
    idx = 1
    item = @arr[@arr_size]
    @arr_size -= 1
    while idx << 1 <= @arr_size
      child = idx << 1
      child += 1 if child < @arr_size && @arr[child][0] > @arr[child + 1][0]
      break if @arr[child][0] >= item[0]
      @arr[idx] = @arr[child]
      idx = child
    end
    @arr[idx] = item
    result
  end

  def peek
    @arr[1]
  end

  def isEmpty
    @arr_size == 0
  end
end

N = gets.to_i
days = []
N.times do
  x, y, w = gets.split.map &:to_i
  days.push([x, y, w])
end

cur = PriorityQueue.new
prev = 0
days.sort_by! do |i, _, _| i end
days.each do |i, j, w|
  prev = [prev, cur.pop[1]].max while !cur.isEmpty && cur.peek[0] <= i
  cur << [j, prev+w]
end

result = prev
result = [result, cur.pop[1]].max until cur.isEmpty
puts result
