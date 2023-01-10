const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
1188번 음식 평론가

N, M이 주어진다.
N개의 소시지가 있고, 이를 M등분 할거라는 의미이다.
소시지를 T번 잘라 M등분할건데, 예를들어 N=3, M=4인경우 아래와 같다.
[----]    [---][-] 
[----] -> [---][-]
[----]    [---][-]
3개의 소시지가 3번의 칼질로 4등분이 되었다.
최소의 T를 구해보자.

이해가 안되는 수학문제
N, M이 10^18이었다면 골드 4에 있지 않을 문제..
그냥 브루트포스 때려버렸는데..
어..
수학은어렵다..
*/
let gcd = function (a, b) {
    if (a < b) [a, b] = [b, a]
    if (b > 0) return gcd(b, a%b)
    return a
}
let lcm = function (a, b) {
    if (a < b) [a, b] = [b, a]
    return a/gcd(a, b)*b
}
let [A, B] = input[0].split(" ").map(Number)
A %= B
let cnt = 0
let all = A*B
while (all !== 0) {
    all -= A
    if (all % lcm(A, B) !== 0) cnt++
}
console.log(cnt)