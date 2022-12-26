=begin
21870번 시철이가 사랑한 GCD

길이가 n인 배열 arr이 주어진다.
이중 왼쪽에서 [n/2]개의 원소를 고르고, 오른쪽에서 남은 원소를 고르자.
([x]는 x보다 크지 않은 최대의 정수이다.)
이를 각각 l, r이라고 하고, l, r중에 하나를 고르자.
이제, 고르지 않은 쪽에 대해 위 작업을 반복하자. 만약 원소가 하나라면 그 원소를 고르고 멈추도록 하자.
위 과정 중에서, 고른 배열의 최대공약수를 모두 구한 뒤 더하자.
그리고, 이 값의 최댓값을 구해보자.

추가시간 없이 1초라는거를 보고, 시간초과날까봐 gcd 세그트리를 짜서 했습니다.
근데 필요 없나봐요? 그냥 해도 파이썬이랑 루비, node.js로도 되나?
뭐 세그트리 쓰는편이 시간적으로 손해는 아닐테니 그냥 그려려니 합시다.
답은 그냥 분할정복하면 나오게 됩니다. N이 최대 20만이긴 한데, 어차피 이분탐색하듯이 가기때문에 재귀횟수가 정말 적어요.
=end
def gcd(x, y)
  if x < y then x, y = y, x end
  while y > 0 do x, y = y, x%y end
  x
end

def query(l, r, n, segT)
  res = 0
  l += n-1
  r += n-1
  while l <= r do
    if l&1==1
      res = gcd(segT[l], res)
      l+=1
    end
    if r&1==0
      res = gcd(segT[r], res)
      r-=1
    end
    l >>= 1
    r >>= 1
  end
  res
end

def loop(l, r, acc, n, segT, arr)
  if l == r then return acc+arr[l-1] end
  length = (r - l + 1) / 2 - 1
  [loop(l, l+length, acc+query(l+length+1, r, n, segT), n, segT, arr),
    loop(l+length+1, r, acc+query(l, l+length, n, segT), n, segT, arr)].max
end

n = gets.to_i
arr = gets.split.map &:to_i
segT = [0]*(n*2)
n.times.each do |i|
  segT[i+n] = arr[i]
end
(1...n).each do |i|
  i = n-i
  segT[i] = gcd(segT[i<<1], segT[i<<1|1])
end

puts loop(1, n, 0, n, segT, arr)