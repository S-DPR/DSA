=begin
11000번 강의실 배정

S에 시작해서 T로 끝나는 수업이 N개 있다.
필요한 최소 강의실의 개수를 구해보자.

과제하느라 못할뻔한거 문제 보자마자 1분컷.
거기에 한번에 해버리는 숏코딩. 키야~
그냥 해시 스위핑입니다. 쉬워요.
=end
d=Hash.new(0);gets.to_i.times{x,y=gets.split;d[x.to_i]+=1;d[y.to_i]-=1};c=0;p(d.keys.sort.map{|i|c+=d[i]}.max)