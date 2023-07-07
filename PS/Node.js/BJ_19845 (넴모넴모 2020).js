const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let ini = (i) => Number(input[i])
let ins = (i) => input[i].split(" ").map(Number)
/*
19845번 넴모넴모 2020

각 층에 놓여있는 물건의 개수가 주어진다.
예를들어, 3 3 1이라면 1층에 3개, 2층에 3개, 3층에 한개라는 의미이다.
이 물건들은 모두 한쪽으로 밀려있기 때문에 항상 내림차순 수열임이 보장된다.
각 물건은 x를 1만큼, y를 1만큼 차지하기때문에 (x, y)로 표현할 수 있다.
Q개의 쿼리가 주어진다. 이는 두 수로 주어진다.
각각 x, y좌표를 뜻하며, 오른쪽에 있는 물건, 위쪽에 있는 물건을 모두 가져간다는 의미이다.
(단, x좌표 혹은 y좌표가 동일해야 가져갈 수 있다.)
각 쿼리에 대해, 몇 개의 물건을 가져갈 수 있나 구하시오.

문제요약이 이상하긴한데 뭐 어때요
문제를 제대로 읽겠다고 한지 단 2주만에 문제 제대로 안읽어서 또 시간날렸네 하아..
어쩐지 논리는 완벽한거 치고 맞왜틀 계속 당하긴했어

그냥 내림차순일 때 쓸 수 있는 lowerBound 하나 만들어서 굴리면 됩니다.
골드 하위로 오니까 쉽긴 쉽다 그죠?
*/
function lowerBound(arr, x) {
    let [lo, hi] = [0, arr.length-1]
    while (lo <= hi) {
        let mid = (lo + hi) >> 1
        if (arr[mid] >= x) lo = mid+1
        else hi = mid-1
    }
    return hi
}
let [N, Q] = ins(0)
let arr = [1<<31].concat(ins(1))
let ret = ""
for (let i = 2; i <= Q+1; i++) {
    let [y, x] = ins(i)
    if (arr[x]-y < 0)
        ret += `${0}\n`
    else {
        let maxX = Math.max(0, lowerBound(arr, y)-x)
        let maxY = arr[x] - y + 1
        ret += `${Math.max(0, maxY+maxX)}\n`
    }
}
console.log(ret)
