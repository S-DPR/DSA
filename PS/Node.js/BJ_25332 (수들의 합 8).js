const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let ini = (i) => Number(input[i])
let ins = (i) => input[i].split(" ").map(Number)
/*
25332번 수들의 합 8

두 수열에 대해, A[i]+...+A[j] = B[i]+...+B[j]를 만족하는 (i, j) 쌍의 개수를 구해보자.

처음엔 아예 몰랐는데 수들의 합 4 풀고 보니까 '이거 그 느낌이겠는데' 해서 그 방향으로 생각한 문제.
어느날 갑자기 '어 이거 누적합의 차를 맵에 넣어주면 되겠다' 라는 생각이 들어 그대로 구현했습니다.
흥미로운 문제였네요 이건..
*/
let N = ini(0)
let [arr, brr] = [ins(1), ins(2)]
let [apf, bpf] = [0, 0]
let ret = 0
let diff = {}
for (let i = 0; i < N; i++) {
    if (!diff[apf-bpf]) diff[apf-bpf] = 0
    diff[apf-bpf]++
    apf += arr[i]
    bpf += brr[i]
    ret += diff[apf-bpf] ? diff[apf-bpf] : 0
}
console.log(ret)
