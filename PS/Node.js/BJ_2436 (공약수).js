const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
2436 공약수

두 자연수의 최대공약수와 최소공배수 A, B가 주어진다.
두 자연수를 구하시오. 만약 그것이 여러개라면, 합이 가장 작은 두 자연수를 구하시오.

또, 정수정수한 정수론문제입니다.
저는 아래와 같은 과정을 이용했습니다.
gcd(x, y) = A, lcm(x, y) = B이므로
xy/A = B
xy = AB
그러므로, x와 y는 AB의 두 약수. 이걸 O(sqrt(N))동안 O(lgN) 2번을 이용해 A, B가 맞다면 결과에 넣기..
로했는데.

더 나은 풀이가 있습니다.
그냥 B를 A로 나누네요..
*/
let gcd = function (a, b) {
    if (a < b) [a, b] = [b, a]
    while (b) [a, b] = [b, a%b]
    return a
}
let lcm = function (a, b) {
    return a/gcd(a, b)*b
}

let [A, B] = input[0].split(" ").map(Number)
let prod = A*B
let resA = 100_000_000, resB = 100_000_000
for (let i = 1; i*i <= prod; i++) {
    if (prod%i) continue
    if (gcd(i, prod/i) === A && lcm(i, prod/i) === B && resA+resB > i+prod/i)
        [resA, resB] = [i, prod/i]
}
console.log(resA + " " + resB)