=begin
14705번 홍삼 게임 (Hard)

N명의 사람이 홍삼게임을 한다. 1번 사람의 왼쪽은 N번, N번 사람의 오른쪽은 1번이다.
다음규칙을 따를 때, 가장 빠르게 홍삼게임을 끝내는 횟수를 찾아보자. 끝낼 수 없다면 Evil Galazy를 출력하자.
1. 홍삼게임의 첫 시작은 A, B번이다. (이 둘은 지목되었음을 나타낸다.)
2. A는 자신과 DA만큼 떨어진 두 명중 한명을 지목한다.
3. 지목된 사람이 현재 지목되어있는 상태라면 게임은 끝난다.
4. B는 자신과 DB만큼 떨어진 두 명중 한명을 지목한다.
5. 지목된 사람이 현재 지목되어있는 사람이라면 게임은 끝난다.
6. 2로 돌아간다.

이왜골5 이러면서 때려쳤던 문제
그건 Easy였고, 푼건 Hard입니다.
N 범위가 1000이나 차이나서 그런가, 난이도 차도 크네요.

태그에 dp도 있긴한데 잘 모르겠습니다. 저는 규칙으로 푼거같습니다.
BFS로 각 사람이 지목권을 언제 받을 수 있는지 일단 정보를 얻어냅니다.
이후 모든 사람에 대해 공식을 집어넣고, 최솟값을 찾습니다. 공식은 코드에 있습니다.
솔직히 왜 작동하는진 잘 모르겠지만, 작동하더라고요?

일단 *2를 해주는건 어쨌든 둘이 같은 턴에 만나야함+'턴 횟수'가 아니라 '지목횟수'임이기 떄문이고,
+1 -1은.. 아마 A가 먼저 시작함에 따라 달라지는 경우같습니다.

푸는데 한시간 반.. 후..
=end
def bfs(start, d, visit)
  q = [[start, 0]]
  until q.empty?
    curN, curT = q.shift
    if visit[curN][curT&1] == @INF
      visit[curN][curT&1] = curT
      q << [(curN+d)%@N, curT+1] << [(curN+@N-d)%@N, curT+1]
    end
  end
end

@INF = 1_000_000_000
@N, A, B, Da, Db = gets.split.map &:to_i
Avisit = @N.times.map do [@INF, @INF] end
Bvisit = @N.times.map do [@INF, @INF] end
bfs(A-1, Da, Avisit)
bfs(B-1, Db, Bvisit)
res = @N.times.map do |i|
  a_even = Avisit[i][0]
  a_odd = Avisit[i][1]
  b_even = Bvisit[i][0]
  b_odd = Bvisit[i][1]
  [[a_even, b_even].max*2,
   [a_even, b_odd].max*2+(a_even > b_odd ? -1 : 1),
   [a_odd, b_even].max*2+(a_odd > b_even ? -1 : 1),
   [a_odd, b_odd].max*2].min
end.min
puts res < @INF ? res : "Evil Galazy"
