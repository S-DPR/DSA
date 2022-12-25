let input = require("fs").readFileSync("/dev/stdin").toString().trim().split("\n")
//let input = require("fs").readFileSync("./input.txt").toString().trim().split("\n")

/*
3216번 다운로드

수열의 수열의 길이 N이 첫 줄에 주어진다.
이후 N줄동안 두 정수가 공백으로 구분되어 주어진다. 이것을 x, y라고 하자.
어떤 값에 수열의 각 인덱스에서 y를 빼고, x를 더하는 행위를 수열이 끝날 때 까지 해보자.
이 때, 이 값은 음의 정수가 되어선 안된다.
'어떤 값'의 최솟값을 구하시오.

대충 태그 받은걸 보니 그리디, DP, 매개변수탐색 모두로 풀리는듯 합니다.
저는 매개변수탐색을 처음에 했고, 그리디 태그를 보고나서 그리디로 풀었습니다.
와 근데 이게 그리디네.. 진짜..
코드는 간단해서 보자마자 원리를 바로 알 수 있을 정도입니다.
*/
let n = Number(input[0])
let ans = 0, cur = 0
for (let i = 1; i <= n; i++) {
    let [x, y] = input[i].split(' ').map (Number)
    cur -= y
    if (cur < 0) {
        ans -= cur
        cur = 0
    }
    cur += x
}
console.log(ans)

/*
매개변수 탐색 풀이

function test(x) {
    for (let i = 1; i <= n; i++) {
        x -= arr[i][1]
        if (x < 0) return false
        x += arr[i][0]
    }
    return true
}

let n = Number(input[0])
let arr = []
let left = 0, right = 0
for (let i = 1; i <= n; i++) {
    let [x, y] = input[i].trim().split(' ').map (Number)
    arr[i] = [x, y]
    right += Number(y)
}

while (left < right) {
    let mid = (left + right) >> 1
    if (test(mid)) right = mid
    else left = mid+1
}
console.log(right)
*/