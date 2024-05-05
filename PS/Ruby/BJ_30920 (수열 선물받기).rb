ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
30920번 수열 선물받기

순열에 아래 연산을 적용 할 수 있다.
i l r : A[i] = mex(A[l..r])
순열을 1부터 N까지 정렬하려한다. 어떻게 적용해야할까?
단, 연산은 3N//2번 이하로 써보자.

'순열'이기에 가능한 문제.
첨엔 2N번 생각했는데 생각해보니 저거 제한이 1.5N ㅋㅋ
해구성하기 혐오해서 잘 안풀었는데 코포에서 계속 쳐맞으니 어지러워서 풀어봤습니다.
항상 N번 이하로 가능함을 예측해야 쉽습니다.
현재 수 != 현재 인덱스가 아니면 그걸 0으로 하고 맞는 수 찾아나가면서 채워주면 끝.
=end
N = ini.()
A = [0] + ins.()
idx = [0]*(N+1)
(1..N).each do |i|
  idx[A[i]] = i
end
ret = []
(1..N).each do |i|
  cur = A[i]
  ret << [i, i, i] if idx[i] != i
  while idx[i] != i
    ret << [cur, 1, N]
    idx[cur] = cur
    cur = A[idx[cur]]
  end
end
puts ret.length
out = ""
ret.each do |i, l, r|
  out += "#{i} #{l} #{r}\n"
end
print out
