require 'set'
=begin
14658번 하늘에서 별똥별이 빗발친다

점의 최대 Y범위 N, 최대 X범위 M, 정사각형의 한 변의 길이 L, 점의 개수 K가 주어진다.
이후 K개의 줄에 점의 좌표가 x y의 형태로 주어진다.
정사각형으로 최대한의 별을 담아냈을 때, 정사각형 밖의 점의 개수를 세어보자.
(1 <= N, M <= 500_000, 1 <= 100_000 <= L , 1 <= K <= 100)

아니 이게 좌표압축이 아니야?????
심지어 브루트포스야?????????
아니 어이가없네

저는 좌표압축 하고 브루트포스 때려서 풀었습니다.
아니 근데 좌표압축도 안하고 그게 되나..?
..했더니 생각해보니까 되네요.
각 별의 x, y좌표가 겹치는 모든 부분에 대해서만 계산해주면 됩니다.
사실상 제가 좌표압축해서 푼거와 100% 같은 이치입니다.
=end
N, M, L, K = gets.split.map &:to_i
star, numX, numY = [], Set.new, Set.new
K.times do
  x, y = gets.split.map &:to_i
  star.push([x, y])
  numX.add(x)
  numY.add(y)
end
numX, numY = numX.to_a.sort, numY.to_a.sort

compMap = numY.length.times.map do numX.length.times.map do 0 end end
star = star.map do |x, y|
  compX = numX.bsearch_index do |k| x <= k end
  compY = numY.bsearch_index do |k| y <= k end
  compMap[compY][compX] += 1
  [compX, compY]
end

leftX, leftY = L, L
result = 0
numY.length.times do |i|
  numX.length.times do |j|
    cur = 0
    (i...numY.length).each do |y|
      break if numY[y]-numY[i] > L
      (j...numX.length).each do |x|
        break if numX[x]-numX[j] > L
        cur += compMap[y][x]
      end
    end
    result = [cur, result].max
  end
end

puts K-result

=begin
브루트포스 코드입니다.

N, M, L, K = gets.split.map &:to_i
star = []
K.times do
  x, y = gets.split.map &:to_i
  star.push([x, y])
end

result = 0
star.each do |_, j|
  star.each do |i, _|
    cnt = 0
    star.each do |x, y|
      cnt += 1 if i <= x and x <= i+L and j <= y and y <= j+L
    end
    result = [cnt, result].max
  end
end

puts K-result
=end