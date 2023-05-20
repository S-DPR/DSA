=begin
14848번 정수 게임

1부터 N까지 있는 배열과 1이상 100이하의 수가 K개 적힌 배열 A가 있다.
A 원소의 모든 배수를 앞 배열에서 제거하면 몇 개의 수가 남을까?

포함배제 아시는구나!!!!!!!!!
정말 개 날먹입니다!!!!!!

루비는 신이고 나는 무적이다
그냥 대충 포함배제 돌리면 AC.
과제도 많은데 쉬운 골드3 하나 주워서 다행입니다.
=end
N,K=gets.split.map &:to_i
A=gets.split.map &:to_i
p(N-(1..K).map do |i|
  cnt = A.combination(i).map do |j|
    prod = 1
    j.each do |k| prod = prod.lcm(k) end
    N/prod
  end.sum
  cnt * (-1)**(i-1)
end.sum)
=begin
숏코딩입니다. (137B)
N,K=gets.split.map &:to_i;A=gets.split;p(N-(1..K).map{|i|A.combination(i).map{|j|N/j.inject(1){|p,k|p.lcm(k.to_i)}}.sum*(-1)**(i-1)}.sum)
=end