const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
30976번 사랑의 큐피드

여자는 자신의 기준보다 작은 남자를, 남자는 자신의 기준보다 큰 여자를 선호한다고 하자.
각 여자의 키와 선호기준, 남자의 키와 선호기준이 주어질 때 최대 몇 쌍의 커플을 만들 수 있을까?

보자마자 '이건 진짜 이분매칭' 하면서 이분매칭 검색하고.. 풀었습니다.
이분매칭은 간단해서 몇 번 풀면 외울거같긴하네요.
그런데 그래프 잘못만들어서 7틀 ㅋㅋ..
아..
*/
let [N, M] = ins()
let FL = ins()
let ML = ins()
let FW = ins()
let MW = ins()
let G = []

for (let i = 0; i < N+M; i++) {
    G[i] = Array(N+M).fill(0)
}

for (let i = 0; i < N; i++) {
    for (let j = 0; j < M; j++) {
        if (FW[i] > ML[j]) G[i][j+N] = 1
    }
}

for (let i = N; i < N+M; i++) {
    for (let j = 0; j < N; j++) {
        if (MW[i-N] < FL[j]) G[i][j] = 1
    }
}

let dfs = (vis, x) => {
    vis[x] = true
    for (let i = N; i < N+M; i++) {
        if (G[x][i]+G[i][x] < 2) continue
        if ((right[i] == -1) || (!vis[right[i]] && dfs(vis, right[i]))) {
            left[x] = i
            right[i] = x
            return true
        }
    }
    return false
}

let ret = 0
let left = Array(N+M).fill(-1)
let right = Array(N+M).fill(-1)
for (let i = 0; i < N; i++) {
    if (left[i] != -1) continue
    if (!dfs(Array(N+M).fill(false), i)) continue
    ret++
}
console.log(ret)
