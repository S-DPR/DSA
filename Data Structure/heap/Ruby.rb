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
    if @arr_size == 0 then return 0 end
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

P = PriorityQueue.new(->(a, b) { a > b })
T = gets.to_i
T.times do
  query = gets.to_i
  case query
  when 0 then puts P.pop
  else P << query end
end