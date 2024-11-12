const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
const inf = 1<<29
let __idx = 0
/*
2611번 자동차경주

1번 노드를 제외하면 사이클이 없는 방향 그래프가 주어진다.
모든 간선에는 점수가 있고, 해당 간선 사용시 그 점수만큼 점수를 얻을 수 있다.
1번 노드에서 출발해, 1번 노드로 돌아오는 최고점수는 몇일까?

으아아악
처음에 보고 아 이거 위상정렬이네, 했는데
풀다보니 1번 노드를 제외하면 사이클이라는 조건을 까먹어서..
40분 헤메고 10분만에 풀었습니다..

그냥 위상정렬할때 1번노드가 다음노드일경우를 제외하고 쭉 쓰면됩니다.
그 외 역추적이 조금 귀찮긴한데..
머 별 생각 안하고 쭉 하면 됩니다.
골2따리인데 조금 멍청하게 풀었네.
*/
const sort = (graph) => {
    let [Q, lQ] = [[1], []]
    let dist = Array(N+1).fill(-1)
    let indep = Array(N+1).fill(0)
    let trace = Array(N+1).fill(-1)
    for (let i = 1; i <= N; i++) {
        graph[i].forEach(([j, _]) => indep[j]++)
    }
    dist[1] = 0
    while (Q.length+lQ.length) {
        if (!Q.length) {
            while (lQ.length) Q.push(lQ.pop())
        }
        let curN = Q.pop()
        graph[curN].forEach(([nxtN, nxtW]) => {
            if (nxtN != 1) {
                if (dist[nxtN] < dist[curN]+nxtW) {
                    dist[nxtN] = dist[curN]+nxtW
                    trace[nxtN] = curN
                }
                if (!--indep[nxtN]) {
                    lQ.push(nxtN)
                }
            }
        })
    }
    return [dist, trace]
}

let [N, K] = [ini(), ini()]
let G = Array(N+1).fill(0).map(_ => [])
let RG = Array(N+1).fill(0).map(_ => [])
for (let i = 0; i < K; i++) {
    let [u, v, w] = ins()
    G[u].push([v, w])
    RG[v].push([u, w])
}
let [D, T] = sort(G)
let [RD, RT] = sort(RG)
let [mx, mxN] = [-1, -1]
for (let i = 1; i <= N; i++) {
    if (mx < D[i]+RD[i]) {
        mx = D[i]+RD[i]
        mxN = i
    }
}
let [trace, rtrace] = [[], []]
let cur = mxN
while (cur != 1) {
    rtrace.push(cur)
    cur = RT[cur]
}
cur = mxN
while (cur != 1) {
    trace.push(cur)
    cur = T[cur]
}
trace.push(1)
rtrace.push(1)
trace = trace.reverse()
trace.pop()
trace = trace.concat(rtrace)
console.log(mx)
console.log(trace.join(" "))
