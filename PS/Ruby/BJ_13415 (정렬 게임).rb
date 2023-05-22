=begin
13415번 정렬 게임

수열이 있다. 이후 Q개의 쿼리가 주어진다.
각 쿼리는 l, r로 구성되어있으며 0..l까지 오름차순 정렬 한 뒤 0..r까지 내림차순 정렬을 하라는 뜻이다.
모든 쿼리를 처리한 뒤 배열의 상태를 출력해보자.

와! 세번째로 보는 덱문제! (이제 5달품)
골드3이었는데 골드1로 올라와진 문제입니다. 난이도 기여 보니까 골드3은 아래 쳐박혀있더만..
사?실?골드1인지는?모르겠?지만 뭐 사람들이 그렇다카니 그렇다합시다

뒤쪽에서부터 보면서 큰 인덱스를 주워오면 됩니다.
단 l이 더 커도 r도 같이 넣어줘야합니다. 그 반대는 l은 안넣습니다.
그리고 arr을 한번 정렬한 뒤 적절하게 넣어주면 AC입니다.
적절하게 넣으려면 어느방향에서 빼야하는지 넣어줘야겠죠..
max와 rev배열이 그 역할입니다.
=end
N = gets.to_i
arr = gets.split.map &:to_i
Q = gets.to_i.times.map do gets.split.map &:to_i end
max = [0]
rev = [true]
until Q.empty?
  curL, curR = Q.pop
  where = curL <= curR
  get_max = where ? curR : curL
  if !where && max[-1] < curR
    max << curR
    rev << true
  end
  if max[-1] < get_max
    max << get_max
    rev << where
  end
end
sorted_arr = arr[0...max[-1]].sort
ret = arr[max[-1]..]
while max[-1].positive?
  curN = max.pop
  nextN = max[-1]
  curR = rev.pop
  (curN-nextN).times do
    ret.unshift(curR ? sorted_arr.shift : sorted_arr.pop)
  end
end
print ret.join(" ")