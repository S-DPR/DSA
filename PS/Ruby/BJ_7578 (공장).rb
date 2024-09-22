ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<63
=begin
7578번 공장

Inversion Counting을 구해보자.

대충 문제 압축압축하면 위 문장인 문제.
Inversion Counting 자체가 꽤 멋진 아이디어라서 무조건 알아둬야한다고 생각하고..
세그트리도 세그트리고, pbds도 pbds인데 개인적으로 무조건 머지소트 아이디어가 제일 멋지다고 생각해요.
특별한 자료구조 없이, 평범한 정렬 알고리즘을 구현하는것만으로도 구현이 되는..
세그트리 참 좋아하지만 오랜만에 머지소트로 Inversion Counting 처리해봤습니다.
=end
def int_merge(left, right)
  ret = []
  while !left.empty? && !right.empty?
    if left[0] < right[0]
      ret << left.shift
    else
      @ret += left.length
      ret << right.shift
    end
  end
  ret.concat(left).concat(right)
end

def merge(arr)
  return arr if arr.length == 1
  mid = arr.length / 2
  left = merge(arr[0...mid])
  right = merge(arr[mid...arr.length])
  int_merge(left, right)
end

N = ini.()
A = ins.().each_with_index.to_h
B = ins.().map do |i| A[i] end
@ret = 0
merge(B)
p @ret
