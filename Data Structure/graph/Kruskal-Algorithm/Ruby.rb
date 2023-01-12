def union(x, y)
  xP = find(x)
  yP = find(y)
  U[xP] = U[yP]
end

def find(x)
  U[x] = find(U[x]) if U[x] != x
  U[x]
end

result = 0
V, E = gets.split.map &:to_i
G = E.times.map do
  a, b, c = gets.split.map &:to_i
  [c, a, b]
end.sort!
U = Array(0..V)
G.each do |c, a, b|
  if find(a) != find(b)
    union(a, b)
    result += c
  end
end
puts result