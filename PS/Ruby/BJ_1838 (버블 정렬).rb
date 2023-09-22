=begin
1838번 버블 정렬

수열이 주어진다.
한번 버블정렬의 그것을 하는 행위를 몇 번 해야 이 수열이 정렬될까?

솔직히 그냥 답지 보고 푼 문제
아직도 왜 이러는지는 잘 모르겠습니다.
진짜 정렬 하나 달고 골드1 달고있는데는 그런 이유가 있는것같아요.
한번 생각날때마다 검색해서 찾아봐야할거같네요..
=end
N = gets.to_i
arr = gets.split.map &:to_i
brr = arr.sort
ret = N.times.map do |i|
  idx = brr.bsearch_index{|x| x >= arr[i]}
  i-idx
end.max
puts ret
