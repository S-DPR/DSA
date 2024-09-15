ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<63
=begin
21201번 Interview Queue

수열이 주어진다.
여기서 모든 수를 한번씩 선택해보자. 그리고 그 양 옆에 이 수보다 큰 수가 있다면, 이 수는 지울 예정인 수가 된다.
모든 수를 다 한번씩 확인해봤다면, 이제 지울 예정인 수를 모두 지운다.
이 과정을 변화가 없을 때까지 반복할 것이다.
각 과정에서 사라지는 수를 순서대로 출력해보자.

나 연결리스트 너무싫어

솔직히 연결리스트인건 금방 알았는데..
어? 이거 오큰수처럼 하면 안되나? 했습니다.
근데 그러면 왼쪽에 6 3 3 8처럼 양쪽에서 맞는경우 바로 6 8이 이어져야하는데,
그게 처리가 안돼서 아쉽게도 에러가 나더라고요.
관련 테스트케이스는 지워서 까먹었지만.. 어쨌든 그렇더라고요.

그래서 연결리스트 열심히 만들고 구현..
테크닉 자체는 딱히 없지만 그냥 연결리스트가 너무 화가나네요.
=end
N = ini.()
A = ins.()
L = N.times.map do |i| [i-1, i+1] end
L[-1][1] = -1
ret = []
change = [*(0...N)]
total_del = [false]*N
until change.empty?
  new_change = []
  delete = []
  change.each do |i|
    next unless [0, 1].map do |k| (L[i][k] != -1 && A[i] < A[L[i][k]]) end.any?
    delete << i if !total_del[i]
    total_del[i] = true
  end
  delete.each do |i|
    new_change << L[i][0] if !total_del[L[i][0]] && L[i][0] != -1
    new_change << L[i][1] if !total_del[L[i][1]] && L[i][1] != -1
    L[L[i][1]][0] = L[i][0] if L[i][1] != -1
    L[L[i][0]][1] = L[i][1] if L[i][0] != -1
  end
  ret << delete.map do |i| A[i] end.join(" ") unless delete.empty?
  change = new_change
end
mx = A.max
ret << A.filter do |i| i == mx end.join(" ")
p ret.size-1
puts ret
