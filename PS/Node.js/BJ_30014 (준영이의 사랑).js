const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
30014번 준영이의 사랑

수열이 주어진다. 수열을 적절히 재배열하여,
A[0]A[1]+A[1]A[2]+...+A[N-2]A[N-1]+A[N-1]A[0]를 최대화해보자.
그 재배열된 수열까지 출력해보자.

골3에 있는 그리디랑 같은문제. 아니, 오히려 쉽지.
골4 기여했습니다.
*/
let N = ini()
let A = ins().sort((i, j) => j-i)
let ret = []
A.forEach(i => {
    if (ret.length == 0) ret.push(i)
    else if (ret[0] > ret[ret.length-1])
        ret.unshift(i)
    else
        ret.push(i)
})
let s = 0
for (let i = 0; i < N; i++) {
    s += ret[i]*ret[(i+1)%N]
}
console.log(s)
console.log(ret.join(" "))
