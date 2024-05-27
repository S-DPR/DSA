const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
24461번 그래프의 줄기

트리가 주어진다.
리프노드부터 서서히 자른다고 생각해보자.
어느 순간, 리프 노드가 2개가 될텐데 이때 멈추자.
이 때 남는 노드의 번호를 구해보자.

그냥 위상정렬 슥 긁으면 끝.
그런데 아이디어를 조금 생각해봐야합니다.
진짜 보자마자 푼 골드2는 오랜만이네
*/
let N = ini()
let [G, D] = [[], Array(N).fill(0)]
for (let i = 0; i < N; i++) G.push([])
for (let i = 1; i < N; i++) {
    let [u, v] = ins()
    G[u].push(v)
    G[v].push(u)
    D[u]++
    D[v]++
}
let ret = Array(N).fill(true)
while (true) {
    let Q = []
    for (let i = 0; i < N; i++) {
        if (D[i] == 1 && ret[i]) Q.push(i)
    }
    if (Q.length <= 2) break
    while (Q.length != 0) {
        let x = Q.pop()
        ret[x] = false
        G[x].forEach(i => D[i]--)
    }
}
let str = ""
for (let i = 0; i < N; i++) {
    if (ret[i]) str += `${i} `
}
console.log(str)
