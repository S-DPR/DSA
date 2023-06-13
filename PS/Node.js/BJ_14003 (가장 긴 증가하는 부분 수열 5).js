const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
14003번 가장 긴 증가하는 부분 수열 5

LIS를 역추적해보자.

저번에 3초 정렬 할 때 했던 아이디어 그대로 들고오기.
그냥 한번 했던건데 난이도가 좀 높길래 아껴뒀다가 이제 꺼내먹습니다.
이번엔 그냥 1차원 배열 두 개로 하려다가 포기..
대신 하던대로 long배열을 2차원으로 하니까 바로 되네요.
이런 문제는 항상 재밌습니다.
*/
function lowerBound(arr, x) {
    let [lo, hi] = [0, arr.length]
    while (lo < hi) {
        let mid = (lo + hi) >> 1
        if (arr[mid][0] >= x)
            hi = mid
        else
            lo = mid + 1
    }
    return hi
}

let N = Number(input[0])
let arr = input[1].split(" ").map(Number)
let trace = []
let long = [[-1_000_000_001, -1]]
for (let i = 0; i < N; i++) {
    let idx = lowerBound(long, arr[i])
    trace[i] = long[idx-1][1]
    long[idx] = [arr[i], i]
}
let [stk, nxt] = [[], long[long.length-1][1]]
while (nxt !== -1) {
    stk.push(arr[nxt])
    nxt = trace[nxt]
}
console.log(stk.length)
let ret = ""
while (stk.length !== 0) ret += stk.pop() + " "
console.log(ret)
