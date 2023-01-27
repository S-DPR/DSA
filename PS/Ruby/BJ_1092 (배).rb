=begin
1092번 배

크레인을 N개 조종하여 상자를 M개 모두 옮기려 한다.
크레인마다 옮길 수 있는 최대 무게가 있고, 상자도 각각 무게가 있다.
모두 옮길 수 없으면 -1을, 모두 옮길 수 있으면 최소시간을 출력해보자.

딱 보자마자 느껴지는 이 그리디의 강력한 기운
하지만 코드로 짜내기는 까다로웠네요. 내 구현능력에 문제가 있나 진지하게 고민함.
어쨌든.. 정말로 어려운문제는 아니었습니다.
그냥 에라 예제 다맞는데 맞겠지~ 하고 냈더니 맞았는데,
다른사람 Ruby코드 보니까 시간도 덜먹고 메모리도 덜먹고.

사실 제가 한건 엄청 나이브한 편입니다.
그래도 O(max(N, M))정도라서 되게 할만한정도라고 생각은하지만..

일단, 크레인과 박스는 정렬합니다.
1. '현재 최대 무게인 박스를 옮길 수 있는' 크레인들을 모두 골라냅니다.
1-2. 골라낼때마다, 지금까지 걸린 시간만큼 박스에서 꺼냅니다.
2. 골라내고나서 박스가 남아있나 확인합니다. 없으면 걸린 시간을 출력하고 끝냅니다.
3. 현재 일할 수 있는 애들만큼 박스를 꺼냅니다.
4. 1로 돌아갑니다.
=end
N = gets.to_i
arr = (gets.split.map &:to_i).sort
M = gets.to_i
brr = (gets.split.map &:to_i).sort

if arr[-1] < brr[-1]
  puts '-1'
else
  time = 0
  working = 0
  until brr.empty?
    while !arr.empty? and !brr.empty? and arr[-1] >= brr[-1]
      working += 1
      time.times do brr.pop end
      arr.pop
    end
    break if brr.empty?
    time += 1
    working.times do brr.pop end
  end
  puts time
end
