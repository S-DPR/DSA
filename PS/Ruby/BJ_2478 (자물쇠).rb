ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
2478번 자물쇠

1부터 N까지 정렬되어있는 순열이 있다.
이것을 왼쪽으로 K번 돌리자.
그 후, L부터 R까지 뒤집자.
마지막으로, 왼쪽으로 X번 돌리자.
마지막까지 수행한 순열이 주어진다. K, L, R, X를 구하시오.
단, K, X는 1 이상 N 미만, 1 <= L < R <= N이다.

주는 순열을 수정한다 생각하면 그 순간 멸망합니다. 너무 어려워져요.
대신, 차례차례 봅시다.
먼저 마지막 X를 고정합시다. 마지막에 0회나 N회를 돌릴 수가 없어서요.
반드시 가능한 경우만 주어지니, 맘 편하게 1~N-1 돌려줍니다.

이후 모든 L, R을 잡아 돌려봅시다. 그렇게 한 뒤 1의 위치를 찾습니다.
이제 그 왼쪽부분 + 1 포함하는 오른쪽부분을 딱 정렬하면 정렬된 순열이 나와야합니다.
그럼 AC.


어..? 잠시만.. X 잡고.. L, R잡고.. 벌써 O(N^3).. 그다음에 A[L:R] 직접 돌리는데 O(N)..
N은 최대 500.. TLE..

네, 최적화를 한 번 합시다. 모든 L, R을 볼 필요 없죠.
원래 순열을 두 번 붙인 뒤에, 몇개나 뒤집혔나 세어봅시다.
그러면 이제 모든 L, R대신 L, L+revLen을 보면 됩니다.
이제 O(N^3)으로 줄었죠. AC.
=end
N = ini.()
A = ins.()*2
origin = [*(1..N)]
rev, cur = 0, 0
(N*2-1).times do |i|
  cur = A[i]-1 == A[i+1] || (A[i] == 1 && A[i+1] == N) ? cur+1 : 1
  rev = [rev, cur].max
end
rev = [rev, N].min

ans = [*(1..N)]
flg = false
(1...N).each do |third|
  break if flg
  A.unshift(A.pop)
  N.times do |left|
    break if flg
    right = left + rev
    newA = A[0...left] + A[left...right].reverse + A[right...N]
    pos = newA.index(1)
    if pos != 0 && newA[pos...N] + newA[0...pos] == ans
      print N-pos, "\n", left+1, " ", right, "\n", third
      flg = true
    end
  end
end
