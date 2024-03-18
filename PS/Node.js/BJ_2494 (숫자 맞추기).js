const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
2494번 숫자 맞추기

숫자 N개가 있는 다이얼이 있다.
이 다이얼은 현재 S의 상태인데 E상태로 바꾸려 한다.
다이얼은 왼쪽, 오른쪽으로 돌려 맞출 수 있다.
왼쪽으로 돌리면 현재 다이얼 아래의 모든 다이얼도 같이 왼쪽으로 돈다.
다이얼을 E로 만들 때 필요한 최소 횟수를 구하시오.

간단한 dp문제
이거 단계별에 있던거같은데?

여기서 제일 중요한건 '왼쪽으로 얼마나 돌렸냐'
위쪽부터 천천히 맞추면서, 왼쪽으로 얼마나 돌렸나만 매개변수로 굴려주면 됩니다.
10번 돌리면 똑같으니까 모듈러 10 하면 10000*10상태가 나오고,
dp적용하면 AC 되겠죠.

역추적이 더 귀찮은데 그냥 trace배열 하나 만들어서 따라가주면 끝납니다.
*/
let loop = (idx, left) => {
    if (idx == N) return 0
    if (dp[idx][left] != 1<<30) return dp[idx][left]
    let cur = (Number(S[idx])+left)%10
    let dst = Number(E[idx])
    let leftDist = (dst-cur+10)%10
    let rightDist = (cur-dst+10)%10

    let leftCost = leftDist+loop(idx+1, (left+leftDist)%10)
    let rightCost = rightDist+loop(idx+1, left)
    if (leftCost < rightCost) {
        dp[idx][left] = leftCost
        trace[idx][left] = leftDist
    } else {
        dp[idx][left] = rightCost
        trace[idx][left] = -rightDist
    }
    return dp[idx][left]
}

let traceRet = ""
let tracing = (idx, left) => {
    if (idx == N) return traceRet
    if (trace[idx][left] != 0) {
        traceRet += `${idx+1} ${trace[idx][left]}\n`
    }
    if (trace[idx][left] >= 0) {
        tracing(idx+1, (left+trace[idx][left])%10)
    } else {
        tracing(idx+1, left)
    }
    return traceRet
}

let N = ini()
let S = input[__idx++]
let E = input[__idx++]
let [dp, trace] = [[], []]
for (let i = 0; i < N; i++) {
    dp.push(Array(10).fill(1<<30))
    trace.push(Array(10).fill(0))
}
console.log(loop(0, 0))
console.log(tracing(0, 0))
