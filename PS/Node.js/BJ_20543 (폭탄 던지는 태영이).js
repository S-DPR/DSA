const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
20543번 폭탄 던지는 태영이

폭탄이 터지면 폭탄을 중심으로 한 K*K 구역이 1씩 깎인다.
각 구역이 얼마나 깎였는지 주어진다. 각 구역에서 몇 개의 폭탄이 터졌는지 구해보자.

처음엔 '머야 그냥 좌측상단부터 시작해서 느긋하게 내려가면 되겠네'
일단 아이디어는 맞았는데.. '어어 이거 누적합느낌나네'
이후 '아.. 이런거 식세우기 개빡센데..'

어느 느낌으로 접근했냐면, 1차원 누적합 배열이 주어질 때 2차원 누적합을 만드는 방향으로 했습니다.
좌측상단부터 시작하는건 그냥 당연한 이야기고..
누적합 잘 쓰면 각 칸을 O(1)로 처리할 수 있게 되어 시간초과가 안납니다.

휴 한시간만에 풀었다..
*/
let [N, K] = ins()
let M = []
let [R, P] = [[], []]
for (let i = 0; i < N; i++) {
    M[i] = ins()
    R[i] = Array(N).fill(0)
    P[i] = Array(N).fill(0)
}
let Kh = K >> 1

for (let r = Kh; r+Kh < N; r++) {
    for (let c = Kh; c+Kh < N; c++) {
        let rpf = c > 0 ? P[r][c-1] : 0
        let cpf = 0
        if (r > 0)  {
            cpf += P[r-1][c]
            let rKh = r-Kh*2-1
            let cKh = c-Kh*2-1
            if (rKh >= 0) cpf -= P[rKh][c]
            if (cKh >= 0) cpf -= P[r-1][cKh]
            if (rKh >= 0 && cKh >= 0) cpf += P[rKh][cKh]
        }
        if (c-Kh*2-1 >= 0) rpf -= P[r][c-Kh*2-1]
        let cur = -M[r-Kh][c-Kh] - rpf - cpf
        R[r][c] = cur
        P[r][c] = R[r][c]
        if (c-1 >= 0) P[r][c] += P[r][c-1]
    }
    for (let c = 0; c < N; c++)
        if (r > 0) P[r][c] += P[r-1][c]
}

let Rs = ""
for (let r = 0; r < N; r++)
    Rs += R[r].join(" ") + "\n"
console.log(Rs)