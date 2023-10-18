spl = ->() { gets.split.map &:to_i }
=begin
3013번 부분 수열의 중앙값

1부터 N으로 이루어진 순열이 주어진다.
수 B를 중앙값으로 하는 부분수열의 개수를 구해보자.
단, 부분수열은 순열의 끝을 줄이는 방식으로 만들어야한다.

이런 류 문제는 오랜만에 푸네

깔끔하게 누적합+해시 때려서 풀었습니다.
이런류 문제 많았잖아요, 수들의 합이라던가..
그거 푼 생각이 나서 그냥 쭉 풀었습니다.
처음에 중앙값이 여러개면 어떻하지.. 생각했는데.
=end
N, B = gets.split.map &:to_i
A = gets.split.map &:to_i
F = A.index(B)
L, R = Hash.new(0), Hash.new(0)
A[F] = 0
(0...F).reverse_each do |i|
    A[i] = A[i] > B ? 1 : -1
    A[i] += A[i+1]
    L[A[i]] += 1
end
(F+1...N).each do |i|
    A[i] = A[i] > B ? 1 : -1
    A[i] += A[i-1]
    R[A[i]] += 1
end
p(L.each_key.map do |i|
    R[-i]*L[i]
end.sum+L[0]+R[0]+1)