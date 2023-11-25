const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
2881번 산책길

점이 N개 주어진다. 이후 직사각형의 왼쪽아래, 오른쪽위 좌표가 Q개 주어진다.
각 직사각형에 대해, 변 위에 점이 몇 개나 있나 구해보자.

귀찮은 이분탐색문제.
히히 오늘은 날먹해야지~ 하고 왔다가 하필 언어가 js라서 아..

저같은경우 이런류 문제가 언제나 그랬듯이 map써서 풀었습니다.
그런데 찾아보니 그냥 x y 따로따로 구하면서 하는 방법도 있네요. 신기해..
어쨌든, 네, 그냥 풀면됩니다.
가장 귀찮은건 꼭짓점에서 겹친 점 빼내기. 사실 이거도 그냥 이분탐색하면돼요.
*/
let bisect = (arr, x) => {
    if (arr == null) return 0
    let [lo, hi] = [0, arr.length]
    while (lo < hi) {
        let mid = (lo + hi) >> 1
        if (arr[mid] >= x)
            hi = mid
        else
            lo = mid + 1
    }
    return hi
}

let N = ini()
let [Tx, Ty] = [{}, {}]
for (let i = 0; i < N; i++) {
    let [x, y] = ins()
    if (Tx[x] == null) Tx[x] = []
    if (Ty[y] == null) Ty[y] = []
    Tx[x].push(y)
    Ty[y].push(x)
}
Object.keys(Tx).forEach(i => Tx[i].sort((i, j) => i-j))
Object.keys(Ty).forEach(i => Ty[i].sort((i, j) => i-j))
let Q = ini()
let R = ""
for (let i = 0; i < Q; i++) {
    let [x1, y1, x2, y2, ret] = [...ins(), 0]
    ret += bisect(Ty[y1], x2+1) - bisect(Ty[y1], x1)
    ret += bisect(Ty[y2], x2+1) - bisect(Ty[y2], x1)
    ret += bisect(Tx[x1], y2+1) - bisect(Tx[x1], y1)
    ret += bisect(Tx[x2], y2+1) - bisect(Tx[x2], y1)
    let arr = [x1, x2]
    arr.forEach(x => {
        [y1, y2].forEach(y => {
            if (Ty[y] != null && Ty[y][bisect(Ty[y], x)] == x) ret--
        })
    })
    R += `${ret}\n`
}
console.log(R)
