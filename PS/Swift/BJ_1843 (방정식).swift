/*
1843번 방정식

1 <= x <= y <= N
1 <= z <= N
N이 주어진다. 위 조건과 아래 조건에 맞는 방정식의 해의 개수를 각각 구해보자.
A : X != Y != Z
B : X, Y, Z는 모두 N의 양의 약수
C : X, Y, Z는 모두 소수
A, B, C 각각에 대해 구해보자.

그냥 소수판정하면 끝나는 문제.
이제 슬슬 이런 흔한 정수론문제는 쉬워졌습니다.
막 BitXOR섞거나 순수수학 나오거나 하면 어질어질하긴한데..
*/
let N = Int64(readLine()!)!
var A = Int64(0)
var B = Int64(0)
var C = Int64(0)

var prime = [Int64]()
var arr = [Int64](0...N)
if 1 < N {
  for i in Int64(2)...N {
    if arr[Int(i)] == i {
      prime.append(i)
    }
    for j in prime {
      if i*j > N { break }
      arr[Int(i*j)] = j
      if i%j == 0 { break }
    }
  }
}

if 3 <= N {
  for i in 3...N {
    A += (i-1)/2
  }
}
print(A)

var div = Set<Int64>()
var divv = [Int64]()
for i in 1...N {
  if i*i > N { break }
  if N%i != 0 { continue }
  div.insert(i)
  divv.append(i)
  if N/i != i {
    div.insert(N/i)
    divv.append(N/i)
  }
}
for i in 0..<divv.count {
  for j in i..<divv.count {
    if div.contains(divv[i]+divv[j]) {
      B += 1
    }
  }
}
print(B)

var primeSet = Set(prime)
for i in 0..<prime.count {
  if primeSet.contains(prime[i]+2) {
    C += 1
  }
}
print(C)