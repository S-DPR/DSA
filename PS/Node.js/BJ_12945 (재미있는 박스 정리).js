const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
const inf = 1<<30
let __idx = 0
/*
12945번 재미있는 박스 정리

배열이 주어진다.
어떤 수 x를 2배 해도 어떤 수 y보다 작다면,
그 y는 x를 제거할 수 있다. 단, 해당 y는 단 한 번 이 행동을 할 수 있다.
이 때, 배열의 크기를 최소화해보자.

답 크기가 N/2가 최대기때문에 0번이랑 N/2번째랑 비교해보면서 처리해라!
..라니 하..
매개변수탐색도 할까 하다가 태그에 없어서 스읍 어쩔수없이 하고 미뤘는데,
매개변수탐색으로도 푼 사람 있나보네요.
푸는 방법이 많은 것 같아요.
*/
let N = ini()
let A = Array(N).fill(0).map(ini).sort((i, j) => i-j)
let [lo, hi] = [0, N>>1]
let cnt = 0
while (hi < N) {
    if (A[lo]*2 <= A[hi++]) lo++
    cnt++
}
while (lo++ < N>>1) cnt++
console.log(cnt)
