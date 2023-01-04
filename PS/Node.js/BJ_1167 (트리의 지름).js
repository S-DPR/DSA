const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
1167번 트리의 지름

정점이 있는 트리가 주어진다.
이 트리의 임의의 두 점을 잡아, 가장 긴 길이를 구해보자.

단계별에 있는 문제인데 생각없이 질문게시판 갔다가 알고리즘을 봐버린 케이스..
아.. 이게 난이도가 골드 2나 될줄은 진짜 몰랐지...
*/
function dfs(x, w, nextW, G, visit) {
    if (visit[x]) return [x, w-nextW]
    visit[x] = true
    let result = w
    let farthest = x
    for (let i = 0; i < G[x].length; i++) {
        let [nextN, nextW] = G[x][i]
        let [node, tmp] = dfs(nextN, nextW+w, nextW, G, visit)
        if (result < tmp) {
            result = tmp
            farthest = node
        }
    }
    return [farthest, result]
}

let N = Number(input[0])
let G = []
for (let i = 0; i <= N; i++)
    G[i] = []
for (let i = 1; i <= N; i++) {
    let arr = input[i].split(" ").map(Number)
    for (let j = 1; arr[j] !== -1; j+=2)
        G[arr[0]].push([arr[j], arr[j+1]]) // 정점, 가중치
}


let visit = []
for (let j = 0; j <= N; j++)
    visit.push(false)
let [farthestNode, _] = dfs(1, 0, 0, G, visit)

visit = []
for (let j = 0; j <= N; j++)
    visit.push(false)
console.log(dfs(farthestNode, 0, 0, G, visit)[1])