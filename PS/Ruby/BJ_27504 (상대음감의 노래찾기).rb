=begin
27504번 상대음감의 노래찾기

길이가 N인 수열이 T개 주어진다. 이후 길이가 M인 수열 B가 주어진다.
T개의 수열중, B의 모든 원소에 x만큼 더하거나 뺀 배열이 있는 경우를 찾아보자.

KMP 몰라서 그냥 북마크에 박아둔 문제
KMP 개념만 알고있는데도 이건 나 KMP쓰면 풀린다고 광고하고있어서 태그야 짐작하고있었습니다.

그냥 인접한 수끼리 빼서 새 수열 만든뒤 KMP돌리면 됩니다.
그나저나 C++에서 KMP짠거 보니까 엄청 깔끔하던데 루비는 살짝 드럽네..
원리? 당장은 그냥 그러려니하고 넘어갔는데 쓰다보면 알게되지않을까요?
=end
def get_fail(find)
  fail = [0]*M
  j = 0
  (1...M).each do |i|
    j = fail[j-1] while j.positive? && find[i] != find[j]
    if find[i] == find[j]
      j += 1
      fail[i] = j
    end
  end
  fail
end

def kmp(str, find, fail)
  j = 0
  str.length.times do |i|
    j = fail[j-1] while j.positive? && str[i] != find[j]
    next if str[i] != find[j]
    return true if j == M-1
    j += 1
  end
  false
end

N = gets.to_i
arr = N.times.map do
  _, *items = gets.split.map &:to_i
  items.each_cons(2).map do |i, j| i-j end
end
M = gets.to_i-1
find = gets.split.map(&:to_i).each_cons(2).map do |i, j| i-j end
fail = get_fail(find)
ret = N.times.map do |i|
  i+1 if kmp(arr[i], find, fail)
end.compact
print ret.length > 0 ? ret.join(" ") : -1
