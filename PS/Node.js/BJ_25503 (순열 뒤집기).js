const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
25503번 순열 뒤집기

1부터 N까지 한번씩 나오는 순열이 주어진다.
괄호를 사용해 수를 뒤집을 수 있을 때, 오름차순으로 만들 수 있을까?
괄호는 처음 한번에 쳐야하고, 제일 깊숙한 괄호부터 뒤집히게된다.

와
에디토리얼 먼소린지 몰랐는데 생각해보니 알것같기도
진짜 이런거 어떻게생각하는거지.. 쩐다..
*/
let N = ini()
let A = ins()
let stk = []
for (let i = 0; i <= N; i++) {
    if (i < N) stk.push([A[i], A[i]])
    while (stk.length > 1) {
        const first = stk.pop()
        const second = stk.pop()
        if (second[1]+1 == first[0] || first[1]+1 == second[0]) {
            const start = Math.min(first[0], second[0])
            const end = Math.max(first[1], second[1])
            stk.push([start, end])
            continue
        }
        stk.push(...[second, first])
        break
    }
}
if (stk.length == 1) {
    console.log("YES")
} else {
    console.log("NO")
}
