const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
3050번 집들이

X를 포함하지 않는 직사각형 중, 둘레가 가장 긴 직사각형의 둘레 - 1 의 값을 구해보자.

보고 이것은 BFS인가 dp인가 고민하다가,
하아 모르겠다 하고 태그 열어보니 누적합.
그래도 잘 모르겠어서 아예 풀이 펴보니..
예에에에에에에에에에에엣날 4월 5일에 풀었던 땅따먹기 느낌의 누적합이더라고요.
그니까, 이게 지금 가능한 부분인지 여부를 누적합으로 체크하는 방식.

그래서 보고 방법은 알겠어서 바로 냅다 구현했습니다.
잊었던 테크닉인데 다시 부활해버렸다..
*/
let [R, C] = ins()
M = [Array(C+1).fill(0)]
for (let i = 0; i < R; i++) {
    let row = input[__idx++]
    M.push(Array(C+1).fill(0))
    for (let j = 0; j < C; j++)
        if (row[j] != ".") M[i+1][j+1] = 1
}
for (let r = 1; r <= R; r++)
    for (let c = 1; c <= C; c++)
        M[r][c] += M[r-1][c]+M[r][c-1]-M[r-1][c-1]
let pf = (c1, r1, c2, r2) => M[r2][c2]-M[r1-1][c2]-M[r2][c1-1]+M[r1-1][c1-1]
let ret = 0
for (let r1 = 1; r1 <= R; r1++) {
    for (let c1 = 1; c1 <= C; c1++) {
        if (pf(c1, r1, c1, r1) !== 0) continue;
        let c2 = C;
        for (let r2 = r1; r2 <= R; r2++) {
            while (c2 >= c1 && pf(c1, r1, c2, r2) !== 0) c2--;
            if (c2 < c1) break;
            if (pf(c1, r1, c2, r2) === 0) {
                ret = Math.max(ret, ((c2 - c1 + 1) + (r2 - r1 + 1)) << 1);
            }
        }
    }
}
console.log(Math.max(0, ret-1))
