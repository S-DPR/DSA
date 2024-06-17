const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
10800번 컬러볼

공 색깔 C와 그 공의 크기 S가 N개 주어진다.
색깔이 다르고 자신보다 작은 공의 개수는 몇개인지 모든 공에 대해 구해보자.

그냥 누적합+이분탐색 박았는데,
사람들 어떻게 풀었나 보니까 누적합만 쓴사람도 있고.
저는 사이즈가 2000밖에 안된다는 점에 착안, pf+bsearch로 끝냈습니다.
그리고 골드4기여.. 이게 왜 골드2야..
*/
let bisect = (arr, x) => {
    let [lo, hi] = [1, arr.length-1]
    while (lo <= hi) {
        let mid = (lo + hi) >> 1
        if (arr[mid]-arr[mid-1] >= x) {
            hi = mid - 1
        } else {
            lo = mid + 1
        }
    }
    return hi
}

let N = ini()
let P = [...Array(N+1)].map(_ => [0])
let AP = [0]
let A = []
for (let i = 0; i < N; i++) {
    let [c, s] = ins()
    AP.push(s)
    P[c].push(s)
    A.push([c, s])
}
P = P.map(i => i.sort((i, j) => i-j))
AP = AP.sort((i, j) => i-j)
for (let i = 1; i <= N; i++) {
    AP[i] += AP[i-1]
    for (let j = 1; j < P[i].length; j++) {
        P[i][j] += P[i][j-1]
    }
}
let ret = ""
A.forEach(i => {
    let [c, s] = i
    let cnt = AP[bisect(AP, s)]
    cnt -= P[c][bisect(P[c], s)]
    ret += `${cnt}\n`
})
console.log(ret)
