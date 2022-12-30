=begin
23254번 나는 기말고사형 인간이야

기말고사까지 남은 날짜 수 N과 과목의 개수 M이 주어진다.
두번째줄에는 공부 안하면 얻는 점수 M개가 음이 아닌 정수의 형태로 M개 주어지고,
세번째줄에는 1시간 공부마다 얻는 점수가 음이 아닌 정수의 형태로 M개 주어진다.
최대 점수는 100점이라하고, 잠을 자지 않아 남은 시간 모두 공부할 수 있을 때 (즉, 24*N시간)
얻을 수 있는 최대 점수를 구하시오. 단, 반드시 1시간 단위로 공부해야 점수가 오른다.

바로 알 수 있듯이 그리디 문제입니다.
그런데 풀다보면, 우선순위큐가 필요해지는데..
Ruby님은 우선순위큐같은거 몰라서, 구현해줘야합니다 ㅠㅠ
그래서 그거 구현하고 그리디하게 풀면 풀립니다.

1. 세번째줄로 받은거를 기준으로 정렬해줍니다.
2. 큰거부터 100점에 최대한 가깝게 되도록 시간을 투자합시다.
2-1. 만약에 100점이 아닌 것에 대해,
     1시간 더 투자하면 100점이 된다면, 100-(직전점수)를 우선순위큐에 넣습니다.
2-2. 지금 1시간 투자하면 얻는 점수보다 우선순위큐에 있는 가장 큰 값이 더 크다면,
     시간을 거기에 투자합니다.
3. 2번의 합을 출력합니다.
=end
class PriorityQueue
  def initialize
    @arr = [0]*500
    @arrSize = 0
  end
  def push(x)
    @arrSize += 1
    @arr[@arrSize] = x
    idx = @arrSize
    while idx != 1 and @arr[idx>>1] < x do
      @arr[idx] = @arr[idx>>1]
      idx >>= 1
    end
    @arr[idx] = x
  end
  def size
    @arrSize
  end
  def peek
    if @arrSize != 0 then @arr[1] else 0 end
  end
  def pop
    if @arrSize == 0 then return 0 end
    result = @arr[1]
    idx, value = 1, @arr[@arrSize]
    @arrSize -= 1
    while idx<<1 <= @arrSize
      bigger = idx<<1
      if bigger < @arrSize and @arr[bigger] < @arr[bigger+1] then bigger+=1 end
      if @arr[bigger] <= value then break end
      @arr[idx] = @arr[bigger]
      idx = bigger
    end
    @arr[idx] = value
    result
  end
end

n, m = gets.split.map &:to_i
arr = gets.split.map &:to_i
brr = gets.split.map &:to_i
unite = m.times.map do |i|
  [arr[i], brr[i]]
end
unite.sort_by! do |_, j| -j end

time = 24*n
upperLimit = PriorityQueue.new
ans = unite.map do |i, j|
  plus = 0
  while upperLimit.size > 0 and j < upperLimit.peek and time > 0
    time -= 1
    plus += upperLimit.pop
  end
  study = [(100-i)/j, time].min
  if time > 0 then time -= study end
  final = i+study*j
  if final != 100 and final+j > 100 then upperLimit.push(100-final) end
  final+plus
end.sum
ans += time.times.map do
  upperLimit.pop
end.sum
print ans