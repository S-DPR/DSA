const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
20439번 계획왕

0부터 1440으로 이루어져있는 시간의 단위가 있다.
N개의 [l, r] 범위가 주어진다. 이 시간은 스케줄이 있어 사용할 수 없는 시간이다.
K개의 T가 주어진다. 연속하여 이 시간을 사용하는 스케줄이 있다는 뜻이다.
0 <= l < r <= 1440, sum(T) <= 1440 일 때, K개의 T를 적절하게 시간에 끼워넣을 수 있는지 구해보자.

어제 내가 이거 풀려다가 깃허브 빵꾸냈어..
그런데 지금 생각해봐도 못풀만했네요. 적어도 지금 제가 푼 방법으론.

먼저 가능한 시간의 범위를 먼저 모두 구합니다. 이걸 T라고 합시다.
이제 각 T에 대하여 K개의 시간을 최대한 대입해봅니다.
비트마스킹형식으로 dp에 저장해줍니다.
dp[i][j] = i번째 T에서, j를 2진수로 나타냈을 때 1인 비트를 커버칠 수 있음
그니까.. K번째에서 어떻게 넣어야 i번째 T에 다 우겨넣을 수 있나 입니다.

이제 ret을 만들어줍니다. ret[i][j] = i번째 T에서 j를 2진수로 나타냈을 때 1인 비트를 커버칠 수 있음 (누적)
그니까.. ret[i][(1<<K)-1]이 하나라도 true라면 GOOD을, 아니면 BAD를 나타내야합니다.
dp랑 ret의 차이는 누적여부인데..음..
이거 어렵네..
*/
function loop(dp, T, V, use, cur) {
    let K = use.length
    if (dp[cur][V]) return true
    dp[cur][V] = true
    for (let j = 0; j < K; j++) {
        if (T[cur] >= use[j] && (V&(1<<j)) === 0) {
            T[cur] -= use[j]
            loop(dp, T, V|(1<<j), use, cur)
            T[cur] += use[j]
        }
    }
    return true
}

function collect(ret, dp, V, K, cur) {
    if (V === (1<<K)-1) return 1
    if (cur === dp.length) return 0
    if (ret[cur][V] !== -1) return ret[cur][V]
    ret[cur][V] = 0
    for (let i = dp[cur].length-1; i >= 0; i--)
        if (dp[cur][i] && (V & i) === 0) ret[cur][V] |= collect(ret, dp, V | i, K, cur + 1)
    return ret[cur][V]
}

let [N, K] = input[0].split(" ").map(Number)
let cur = 0
let T = []
for (let i = 1; i <= N; i++) {
    let [nxtS, nxtE] = input[i].split(" ").map(Number)
    T[i-1] = nxtS-cur
    cur = nxtE
}
T[N] = 1440-cur
let use = input[N+1].split(" ").map(Number)
let dp =[]
for (let i = 0; i < T.length; i++)
    dp.push(Array(1<<K).fill(false))
for (let i = 0; i < T.length; i++)
    loop(dp, T, 0, use, i)
let ret = []
for (let i = 0; i < T.length; i++)
    ret.push(Array(1<<K).fill(-1))
console.log(collect(ret, dp, 0, K, 0) === 1 ? "GOOD" : "BAD")
