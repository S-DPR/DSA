const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
const inf = 1<<30
let __idx = 0
/*
29119번 Задача для Альфа

양의 정수 수열이 주어진다.
이를 적절히 재배열하고 그대로 이어붙여 나온 수를 최대로 해보자.

웰-논
솔직히 증명은 아직도 어렵지만 방법만큼은 알고있다
그냥 랜덤마라톤에 나와서 풀었습니다.
*/
let N = ini()
let A = ins()
A.sort((i, j) => {
    return BigInt(i+"" + j) > BigInt(j+"" + i) ? -1 : 1
})
console.log(A.join(" "))
