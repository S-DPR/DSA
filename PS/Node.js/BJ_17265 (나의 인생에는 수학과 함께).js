const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
17265번 나의 인생에는 수학과 함께

숫자와 연산자가 번갈아가며 나오는 맵이 있다.
(0, 0)에서 출발하여 최단거리로 (N-1, N-1)로 가려한다.
이 때, 연산순서를 무시하고 연산을 하며 가려한다. 최솟값과 최댓값을 출력해보자.

오늘도 플레에 쳐맞다가 또 골드5풀기
어쨌든 eval은 신입니다. 편하게 풀었네요.
*/
let N = Number(input[0])
let M = []
for (let i = 1; i <= N; i++) {
    M[i-1] = input[i].split(" ")
}
let inf = 1_000_000_000
let [max, min] = [-inf, inf]
let Q = [[0, 0, M[0][0], ""]]
let dx = [1, 0]
let dy = [0, 1]
while (Q.length > 0) {
    let [x, y, calc, oper] = Q.shift()
    if (x === N-1 && y === N-1) {
        max = Math.max(max, Number(calc))
        min = Math.min(min, Number(calc))
        continue
    }
    for (let i = 0; i < 2; i++) {
        let [nx, ny] = [x+dx[i], y+dy[i]]
        if (!(0 <= nx && nx < N && 0 <= ny && ny < N)) continue
        if (isNaN(Number(M[ny][nx]))) {
            Q.push([nx, ny, calc, M[ny][nx]])
        } else {
            Q.push([nx, ny, eval(calc+oper+M[ny][nx]), ""])
        }
    }
}
console.log(max + " " + min)