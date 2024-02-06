const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
2423번 전구를 켜라

\는 좌측 상단과 우측 하단을, /는 우측 상단과 좌측 하단을 잇는다.
각 칸을 90도 회전시킬 수 있다. 좌측 최상단에서 우측 최하단까지 가려고 할 때, 최단거리를 구해보자.

으에에
문제는 보자마자 파악했는데 그래프그리는데 1시간걸렸서 나 슬퍼

맵, 그니까 \ / 를 row/col이 모두 홀수인 곳에,
방문배열 업데이트는 row/col이 모두 짝수인 곳만 씁니다.
왜냐면, 이건 대각선을 봐야해서.
처음에 히히 10분컷 이랬다가 6배로 늘어나버렸네요..
*/
let [R, C] = ins()
let [M, T] = [[], []]
for (let r = 0; r <= R*2; r++) {
    M.push([])
    T.push([])
    for (let c = 0; c <= C*2; c++) {
        M[r].push(-1)
        T[r].push(1<<30)
    }
}
for (let r = 1; r <= R*2; r += 2) {
    let str = input[__idx++]
    let idx = 0
    for (let c = 1; c <= C*2; c += 2) {
        M[r][c] = str[idx++] == '/' ? 1 : 0
    }
}
T[0][0] = 0
let dr = [-1, 1, -1, 1]
let dc = [-1, 1, 1, -1]
let [Q, lQ] = [[], []]
Q.push([0, 0])
while (Q.length + lQ.length > 0) {
    if (Q.length == 0)
        while (lQ.length > 0)
            Q.push(lQ.pop())
    let [curR, curC] = Q.pop()
    for (let i = 0; i < 4; i++) {
        let [nxtR, nxtC] = [curR+dr[i], curC+dc[i]]
        let [goR, goC] = [nxtR+dr[i], nxtC+dc[i]]
        if (!(0 <= goR && goR <= R*2)) continue
        if (!(0 <= goC && goC <= C*2)) continue
        let forward = i < 2 && M[nxtR][nxtC] == 0
        let backward = i >= 2 && M[nxtR][nxtC] == 1
        let weight = (forward || backward) ? 0 : 1
        if (T[goR][goC] <= T[curR][curC]+weight) continue
        let q = weight == 0 ? Q : lQ
        T[goR][goC] = T[curR][curC] + weight
        q.push([goR, goC])
    }
}
console.log(T[R*2][C*2] == 1<<30 ? 'NO SOLUTION' : T[R*2][C*2])
