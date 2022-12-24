let input = require("fs").readFileSync("/dev/stdin").toString().trim().split("\n")
//let input = require("fs").readFileSync("./input.txt").toString().trim().split("\n")

/*
14888번 연산자 끼워넣기

수의 개수 N이 첫줄에, 수열이 두번째 줄에 주어지고 (2 <= n <= 11, 1 <= A_i <= 100),
합이 N-1인 크기가 4인 수열이 세번째 줄에 주어진다.
세번째 줄은, 첫 인덱스는 +, 두번째 인덱스는 -, 세번쨰는 *, 네번쨰는 /의 개수를 뜻한다.
이 때, 연산자를 주어진 개수만큼 수열 사이에 끼워넣어, 그 최댓값과 최솟값을 구해보자.
단, 나눗셈의 경우 몫만 취하고, 나누어지는수가 -인 경우에만 -값이 결과값으로 나오게된다.
그리고 마지막에 나온 식은 통상 계산순서는 무시하고 맨 앞부터 차례로 계산한다.

원래 루비를 한번 해보고, JS로도 해볼 생각이었습니다. (얼마나 많이 쓸까는 둘쨰치고)
그래서 입출력에대해 (한 시간동안) 연습해보고, 실버 랜덤문제 하나 잡아서 풀었습니다. 그게 이거고.
근데 어.. 느낀점이...
루비가 낫다......
체감상 JS < 루비 < 자바 < 코틀린 < GO < 파이썬 = C++인 느낌.
나중에 Rust도 해보고싶긴한데 Rust 걍 보기만해도 어려워보이네요.
백준의 장점은 그냥 언어 문법만 익히면 어떻게 풀지만 생각하면 되기떄문에 언어선택이 자유롭다는 점입니다.
이 특성상 어떤 언어를 한동안 안쓰다가 써도 몇 문제 풀어보면 바로 해당언어 감각 돌아오고요.

어쨌든 이 문제는 전형적이진 않은 브루트포스 문제입니다.
조금 더 손보면 백트래킹도 됩니다. 백트래킹 문제는 (2)로 있는거같습니다.
저같은경우 그냥 브루트포스로 했습니다.
무려 -0을 출력하면 틀린다는 점만 빼고 조심할건 없는 문제입니다.
*/
function div(x, y) {
    let minus = x < 0 || y < 0 ? -1 : 1
    return Math.floor(Math.abs(x) / Math.abs(y)) * minus
}

function dfs(obj, visit, acc, x) {
    if (x === n) {
        MAX = Math.max(MAX, acc)
        MIN = Math.min(MIN, acc)
    }
    for (let i = 0; i < 4; i++) {
        if (obj[i] === visit[i]) continue
        visit[i]++
        if (i === 0) dfs(obj, visit, acc + arr[x], x + 1)
        if (i === 1) dfs(obj, visit, acc - arr[x], x + 1)
        if (i === 2) dfs(obj, visit, acc * arr[x], x + 1)
        if (i === 3) dfs(obj, visit, div(acc, arr[x]), x + 1)
        visit[i]--
    }
}

let n = Number(input[0])
let arr = input[1].trim().split(' ').map(Number)
let obj = input[2].trim().split(' ').map(Number)
let MAX = -1_000_000_000, MIN = 1_000_000_000
dfs(obj, [0, 0, 0, 0], arr[0], 1)
if (MAX === -0) MAX = 0
if (MIN === -0) MIN = 0
console.log(MAX)
console.log(MIN)