const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let ini = (i) => Number(input[i])
let ins = (i) => input[i].split(" ").map(Number)
/*
25428번 분필 도둑

각 노드에 가중치가 주어진 트리가 주어진다.
(고른 노드의 개수) * min(고른 노드의 가중치)의 최댓값을 구해보자.

와
와
와 진짜
히스토그램문제 분리집합으로 풀 수 있었어?
진짜 태그 빠르게 봐서 다행이라는 생각 거의 안했는데 이번엔 진짜 다행이란 생각이 든다

'큰쪽에서 범위를 넓혀간다'를 이용한 문제입니다.
태그보고 바로 방법 떠올랐어요..
와 그냥 정렬하고 분리집합 한번씩 긁어주면 되네..
진짜.. 와..
개쩐다..
*/
function union(x, y) {
    S[find(x)] += S[find(y)]
    U[find(y)] = U[find(x)]
}

function find(x) {
    if (U[x] !== x) U[x] = find(U[x])
    return U[x]
}

function dfs(x, s) {
    let ret = s*S[find(x)]
    G[x].forEach(i => {
        if (arr[i] >= s && find(i) !== find(x)) {
            union(x, i)
            ret = Math.max(ret, s*S[find(x)])
        }
    })
    return ret
}

let N = ini(0)
let arr = [0].concat(ins(1))
let size = []
let [G, U] = [[], []]
let S = Array(N+1).fill(1)
for (let i = 0; i <= N; i++) {
    G.push([])
    U[i] = i
    size[i] = [arr[i], i]
}
size.sort((i, j) => {
    return j[0]-i[0]
})
for (let i = 2; i <= N; i++) {
    let [u, v] = ins(i)
    G[u].push(v)
    G[v].push(u)
}
let ret = 0
size.forEach(i => {
    ret = Math.max(ret, dfs(i[1], i[0]))
})
console.log(ret)
