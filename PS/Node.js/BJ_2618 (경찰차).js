const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
2618번 경찰차

경찰차 두 대가 (1, 1), (N, N)에 있다.
경찰이 필요한 일이 총 C곳에서 일어났다고 하자.
경찰차의 이동거리를 최소화하며 모든 일을 처리하려 한다.
이 때, 그 최소 이동거리를 출력하고 이후 각 사건에 대해 움직여야하는 경찰차의 번호를 출력하자.

벌써 네번째 보는 유형
그냥 머풀까 끄적끄적하다가 이거 진짜 MST다 하고 쳐박고,
태그 확인하고 기하학인거 보고 그냥 이 문제로 도망왔습니다.

세번째까진 그냥 최소거리만 출력하면 됐는데..
이제 그냥 경로출력하라고하네?

1. 이 코드처럼 trace를 전부 때려박는다.
2. 어디로 이동해야하는지 인덱스만 적어둔다.

당연히 2번이 좋은데 저는 backT 함수 구현 귀찮아서 1번으로.
이제 이 유형도 구간dp처럼 풀게될듯하네요..
*/
let dist = (i, j) => {
    return Math.abs(i[0]-j[0]) + Math.abs(i[1]-j[1])
}

let loop = (idx, l, r) => {
    if (idx == K+1) return [0, ""]
    if (D[l][r][0] != -1) return D[l][r]
    let [left, lt] = loop(idx+1, idx, r)
    let [right, rt] = loop(idx+1, l, idx)
    let lxy = l == 0 ? [1, 1] : C[l]
    let rxy = r == 0 ? [N, N] : C[r]
    let ld = dist(lxy, C[idx]) + left
    let rd = dist(rxy, C[idx]) + right
    D[l][r] = ld < rd ? [ld, "1\n"+lt] : [rd, "2\n"+rt]
    return D[l][r]
}

let N = ini()
let K = ini()
let [D, C] = [[], []]
for (let i = 1; i <= K; i++)
    C[i] = ins()
for (let i = 0; i <= K; i++) {
    D[i] = []
    for (let j = 0; j <= K; j++)
        D[i][j] = [-1, ""]
}
let [ret, trace] = loop(1, 0, 0)
console.log(ret)
console.log(trace)
