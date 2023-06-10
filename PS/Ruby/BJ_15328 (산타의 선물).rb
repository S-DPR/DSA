require 'bigdecimal'
require 'bigdecimal/util'
=begin
15328번 산타의 선물

(0, 0, 0)에서 (x1, y1, z1), ..., (x4, y4, z4)를 순서대로 방문할 때,
거리의 합이 R을 넘는지 구해보자.

거리의합..sqrt..실수..윽..머리가..
진짜 다행히도 Rust나 Kotlin이나 Java가 아닌 Ruby에서 걸려서 다행인 문제.
Python도 임의정밀도를 지원하는데, Ruby도 지원합니다.
BigDecimal로 그냥 갈아버렸습니다.

4개를 계산하는데 시간제한 5초. 맞출테면 맞춰봐라죠 이건.
=end
T = gets.to_i
output = []
T.times do
  sec = BigDecimal(gets)
  cur = [[0, 0, 0]]
  4.times do
    x, y, z = gets.split.map &:to_i
    cur << [cur[0][0]+x, cur[0][1]+y, cur[0][2]+z]
  end
  x, y, z = cur.shift
  need = BigDecimal("0")
  until cur.empty?
    nx, ny, nz = cur.shift
    nnx = BigDecimal("#{(nx-x).abs**2}")
    nny = BigDecimal("#{(ny-y).abs**2}")
    nnz = BigDecimal("#{(nz-z).abs**2}")
    x, y, z = nx, ny, nz
    need = need.add((nnx+nny+nnz).sqrt(10), 64)
  end
  output << (need > sec ? "NO\n" : "YES\n")
end
output.each do |i| print i end