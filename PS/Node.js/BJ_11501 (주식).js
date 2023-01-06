const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")

/*
11501번 주식

수열 A가 주어진다.
이 A에서 원하는만큼 수를 하나 가져올 수 있다.
그리고 그 수들을 가져온 수들의 위치보다 더 뒤쪽에 있는 수 하나로 바꿀 수 있다.
이 새로 만들어진 수열을 B라고 하자. (B의 합)-(A의 합)의 최댓값을 구하시오.

그리디문제지만, 스택처럼 풀 수 있는 쉬운 문제입니다.
저는 스택처럼 풀었습니다.
*/
let TestCase = Number(input[0])
for (let i = 1; i <= TestCase*2; i+=2) {
    let N = Number(input[i])
    let arr = input[i+1].split(" ").map(Number)
    let result = 0, MAX = 0
    while (arr.length > 0) {
        let cur = arr.pop()
        if (cur > MAX) MAX = cur
        else result += MAX-cur
    }
    console.log(result)
}