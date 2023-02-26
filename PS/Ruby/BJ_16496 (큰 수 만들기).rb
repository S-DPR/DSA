=begin
16496번 큰 수 만들기

주어지는 N개의 자연수를 마음대로 이어 만들 수 있는 가장 큰 수를 출력하라.

답지보고 한번 풀고,
내 방식대로 한번 푼 문제.
저는 그냥 무식하게 둘이 길이가 같아질때까지 원래 수를 더한다음,
수가 같으면 원래 길이를 비교하여 작은쪽을, 수가 다르면 int화해서 큰쪽을 더 앞에 오게 배치했습니다.

답지는.. 엄청 간결한데,
i+j, j+i중 더 큰값을 그냥 리턴합니다..
... 진짜 보고 내가 그동안 문제 헛풀었나 생각하면서 다시풀었습니다.
=end
N = gets.to_i
arr = gets.split
arr.sort! do |i, j|
  origin_i, origin_j = i, j
  i.length < j.length ? i += origin_i : j += origin_j while i.length != j.length
  i != j ? -i.to_i <=> -j.to_i : origin_i.length <=> origin_j.length
end
p arr.join('').to_i

=begin
정답코드입니다.
N = gets.to_i
arr = gets.split
arr.sort! do |i, j| -(i+j).to_i <=> -(j+i).to_i end
p arr.join('').to_i
=end