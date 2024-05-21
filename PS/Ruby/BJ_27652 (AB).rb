ini = ->() { gets.to_i }
ins = ->() { gets.split.map &:to_i }
INF = 1<<64
=begin
27652번 AB

빈 집합 A, B와 쿼리가 Q개 주어진다. 모두 처리해보자.
add A/B S : A 혹은 B에 S를 추가한다.
delete A/B S : A 혹은 B에 S를 제거한다.
find S : A의 모든 요소의 접두사와 B의 모든 요소의 접미사를 합칠 때 S가 되는 경우의 수를 출력한다.
         이 때, 접두사(접미사)가 다르더라도 다른 요소에서 가져왔거나, 가져온 방식이 다르다면 다른 경우로 취급한다.
add와 delete의 A/B는 둘 중 하나로 주어진다.

트라이말고 해싱하려다가 멸망 ㅋㅋ
그래도 해싱 연습 가능한 문제인거같긴한데..

그냥 트라이를 쓰는데, B에 저장할때는 거꾸로 저장합시다.
그리고 B에서 접미사 가져올때는 애초에 맨 밑에꺼 가져온다음 parent를 사용해 올려줍시다.
이러면 가볍게 AC.
=end
class Trie
  attr_accessor :child, :cnt, :parent
  def initialize(parent)
    @parent = parent
    @child = [nil]*26
    @cnt = 0
  end
  def insert(str, idx)
    return if str.length == idx
    ch = str[idx].ord-'a'.ord
    @child[ch] = Trie.new(self) unless @child[ch]
    @child[ch].insert(str, idx+1)
    @child[ch].cnt += 1
  end
  def delete(str, idx)
    return if str.length == idx
    ch = str[idx].ord-'a'.ord
    @child[ch].delete(str, idx+1)
    @child[ch].cnt -= 1
  end
  def find(str, idx)
    return @cnt if str.length == idx
    ch = str[idx].ord-'a'.ord
    return 0 unless @child[ch]
    return @child[ch].find(str, idx+1)
  end
  def get(str, idx)
    return self if str.length-1 == idx
    ch = str[idx].ord-'a'.ord
    @child[ch] = Trie.new(self) unless @child[ch]
    return @child[ch].get(str, idx+1)
  end
end

ret = []
A = Trie.new(nil)
B = Trie.new(nil)
ini.().times do
  q, *args = gets.strip.split
  if q == 'find'
    s = args[0]
    a, b = A, B.get(s.reverse, 0)
    cnt = 0
    (0...s.length).each do |i|
      a_ch = s[i].ord-'a'.ord
      a = a.child[a_ch]
      break if !a || !b
      cnt += a.cnt*b.cnt
      b = b.parent
    end
    ret << cnt
  else
    t, s = args
    target = t == 'A' ? A : B
    s.reverse! if t != 'A'
    q == 'add' ? target.insert(s, 0) : target.delete(s, 0)
  end
end
puts ret
