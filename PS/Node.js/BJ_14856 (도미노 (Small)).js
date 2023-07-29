const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let ini = (i) => Number(input[i])
let ins = (i) => input[i].split(" ").map(Number)
let idx = 0
/*
14586번 도미노 (Small)

각 도미노의 위치 x와 그 길이 h가 주어진다.
i번째 도미노의 위치와 길이와 길이를 x, y라고 하자.
이 도미노를 왼쪽으로 넘어뜨리면 위치가 (x-y)이상이고 x 이하인 도미노가 연쇄적으로 왼쪽으로 쓰러지고,
오른쪽으로 넘어뜨리면 x 이상이고 (x+y) 이하인 도미노가 연쇄적으로 오른쪽으로 쓰러진다.
도미노를 최소로 넘어뜨려 모든 도미노를 넘어뜨리려 한다. 적어도 몇 개를 넘어뜨려야할까?

이야..
진짜 골드 2까진 그렇다치는데 골드 1 이상은 맨날 감탄하면서 문제풀게된다..

dp를 3개 굴렸습니다.
dpL, dpR, dp라고 합시다.
dpL에는 각 도미노를 왼쪽으로 넘어뜨렸을 때 어디까지 넘어지는지,
dpR은 똑같은데 오른쪽으로,
dp는 dpL과 dpR을 이용해 구간dp를 굴리면 됩니다.

와.. 신기해.. 이런문제도 만들수가있네..
왜 Small인데 골드1인가 했어..
*/
function loop(l, r) {
    if (l > r) return 0
    if (dp[l][r] !== 1 << 10) return dp[l][r]
    for (let i = l; i <= r; i++) {
        dp[l][r] = Math.min(dp[l][r], loop(l, dpL[i]-1)+loop(i+1, r) + 1)
        dp[l][r] = Math.min(dp[l][r], loop(l, i-1)+loop(dpR[i]+1, r) + 1)
    }
    return dp[l][r]
}

let N = ini(idx++)
let items = [[1<<31, 0]]
while (idx <= N) {
    let [x, h] = ins(idx++)
    items.push([x, h])
}
items.push([~(1<<31), 0])
items.sort((i, j) => {return i[0]-j[0]})
let dpL = Array(N+1).fill(0)
let dpR = Array(N+1).fill(0)
let j
for (let i = 1; i <= N; i++) {
    let [curX, curH] = items[i]
    for (j = i; j > 0; j--) {
        let [nxtX, nxtH] = items[j-1]
        if (nxtX < curX-curH) break
        if (curX-curH > nxtX-nxtH)
            [curX, curH] = [nxtX, nxtH]
    }
    dpL[i] = j
}
for (let i = N; i >= 1; i--) {
    let [curX, curH] = items[i]
    for (j = i; j <= N; j++) {
        let [nxtX, nxtH] = items[j+1]
        if (nxtX > curX+curH) break
        if (curX+curH < nxtX+nxtH)
            [curX, curH] = [nxtX, nxtH]
    }
    dpR[i] = j
}
let dp = []
for (let i = 0; i <= N; i++)
    dp.push(Array(N+1).fill(1<<10))
console.log(loop(1, N))