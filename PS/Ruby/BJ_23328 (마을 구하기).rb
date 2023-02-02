=begin
23328번 마을 구하기

직선으로 있는 알파벳이 있다.
이중 알파벳 하나는 터지면서 양 옆에 피해를 주기에, 이 피해를 최소화하려한다.
그 알파벳은 대문자임이 보장되며, 그 알파벳의 소문자는 그 피해를 막는 실드이다.
예를들어, A가 폭탄이고 'aAb'라면 A가 왼쪽은 실드에 의해 피해를 입지 않지만 오른쪽은 아니기때문에 피해를 입는다.
이 때, 피해를 최소화하는 방법 중 사전순으로 가장 앞서는 경우를 출력해보자.

이?게?골드?1?
제생각엔 한 골드 3까지 격추당하겠는데요?

짜증나는 그리디문제..라고 생각합니다.
실제론 애드혹과 조건분기만있지만.
조건을 분기하며 그리디식으로 처리하는 문제입니다.

일단, 폭탄이 터지는 자리는 무조건 피해가 간다는 사실을 직관적으로 알 수 있습니다.
폭탄이 N개 있다면 적어도 N개의 자리는 피해를 입고요.
피해입은 자리는 다시 피해를 입지 않을테니 (사실 입어도 상관없지만) 폭탄은 몰아두는게 좋다는 것이 직관적으로 보입니다.

폭탄을 모아서 하나로 취급하면 이제 실드를 배치할 차례인데요,
어차피 실드 둘 자리는 두 곳밖에 없죠.
그래서 놓거나/안놓거나, 왼쪽오른쪽에 아래 조건으로 맞춰 넣어주면 됩니다.

1. 폭탄이 사전순으로 제일 앞에 오는경우
 - 폭탄이 제일 앞에 오고, 그 실드까지 바로 뒤에 올 수 있는경우 -> [폭탄 실드]로 맨 앞에 배치
 - 폭탄이 제일 앞에 오지만 바로 뒤에 실드가 오지 못하는 경우 -> 폭탄을 몰아서 맨 앞에 설치
2. 1번이 아닌경우
 - 실드가 두 개 이상인 경우 -> [실드 폭탄 실드]형식으로 실드차례에 배치
 - 실드가 한 개 인경우 -> [실드 폭탄]형식으로 맨 뒤에 배치
 - 실드가 없는경우 -> 폭탄을 몰아서 맨 뒤에 설치

살짝?화나는? 그런문제류네요 그냥..
풀면서 저거적는다고 메모장까지 꺼낼줄은..
=end
_, BOOM = gets.split
M = gets.rstrip.split('')
cnt = {}
cnt[BOOM.downcase] = 0
M.each do |i| cnt[i] ? cnt[i] += 1 : cnt[i] = 1 end
cnt_sort = cnt.sort

result = ''
tnt_pack = BOOM*cnt[BOOM]
cnt[BOOM] = 0
if cnt_sort[0][0] == BOOM
  if cnt[BOOM.downcase] >= 1
    tnt_pack += BOOM.downcase
    cnt[BOOM.downcase] -= 1
  end
else
  if cnt[BOOM.downcase] >= 2
    tnt_pack = BOOM.downcase + tnt_pack + BOOM.downcase*(cnt[BOOM.downcase]-1)
    cnt[BOOM.downcase] = 0
  elsif cnt[BOOM.downcase] == 1
    tnt_pack = BOOM.downcase + tnt_pack
    cnt[BOOM.downcase] -= 1
  end
end

result = tnt_pack if cnt_sort[0][0] == BOOM
cnt_sort.each do |char, _|
  is_true =  char == BOOM.downcase && tnt_pack[0] + tnt_pack[-1] == char*2
  result += is_true ? tnt_pack : char*cnt[char]
end
result += tnt_pack if cnt_sort[0][0] != BOOM && result.count(BOOM).zero?

print result
