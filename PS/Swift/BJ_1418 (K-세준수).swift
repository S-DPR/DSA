/*
1418번 K-세준수

첫째줄에 N, 둘째줄에 K가 주어진다.
N 이하의 자연수 중 모든 소인수가 K 이하인 수의 개수를 출력하자.

딱 입출력까지만 배우고 돌격 앞으로! 했습니다.
수학문제는 언어 문법 잘 몰라도 풀 수 있어서 너무 좋아요.
물론 나중에는 수학을 몰라서 못풀지만..

어쨌든 그냥 linear-sieve를 구현해보고 그거로 죽 풀었습니다.
*/
let N = Int(readLine()!)!
let K = Int(readLine()!)!
var sieve = Array<Int>()
var prime = Array<Int>()
for i in 0..<(N+1) {
    sieve.append(i)
}
for i in 2..<(N+1) {
    if sieve[i] == i {
        prime.append(i)
    }
    for j in prime {
        if N < i*j {
            break
        }
        sieve[i*j] = j
        if (i%j == 0) {
            break
        }
    }
}
var ans = 0
for i in 1..<(N+1) {
    var t = i
    while t != 1 {
        if sieve[t] > K {
            break
        }
        t = t / sieve[t]
    }
    if t == 1 {
        ans += 1
    }
}
print(ans)