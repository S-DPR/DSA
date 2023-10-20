const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
15317번 동방 보수

비용이 C인 동방이 N개, 동방을 원하는 동아리가 각각 K의 돈을 갖고 M개가 기다리고있다.
최대 몇 개의 동방을 할당해줄 수 있을까?
단, 최대 X원을 지원해 줄 수 있다.

아 까먹고 어제 안올렸네..
그래도 어제 풀었다..

상당히 재미있던 매개변수문제.
정렬하고 언제나 그랬듯이 그리디하개 매개변수 굴려주면 됩니다.
은근 재미있단말이지..
*/
let check = (x) => {
    let need = 0
    for (let i = 0; i < x; i++) {
        need += Math.max(0, C[i]-S[M-x+i])
        if (need > X) return false
    }
    return true
}
let cmp = (i, j) => { return i-j }
let [N, M, X] = ins()
let C = ins().sort(cmp)
let S = ins().sort(cmp)
let [l, r] = [0, Math.min(N, M)+1]
while (l < r) {
    let m = (l + r) >> 1
    if (!check(m)) r = m
    else l = m + 1
}
console.log(r-1)
