const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => BigInt(input[__idx++])
const ins = () => input[__idx++].split(" ").map(BigInt)
const inf = 1<<29
let __idx = 0
/*
30094번 그래서 나는 사진을 그만두었다

원소의 점수는 (왼쪽에 있는 원소의 수)*A + (오른쪽에 있는 원소의 수)*B이다.
배열의 점수는 원소의 합이다.
최소/최대 배열의 점수를 구해보고, 그렇게 만들 수 있는 경우의 수를 구해보자.
그리고, 그 중 하나를 출력해보자.

경우의수 어떻게구하냐 하면서 결국 에디토리얼 봐버린 문제..
근데 생각보다 너무 간단하더라고요.
몇십분 더 생각했으면 아마 맞추지 않았을까 싶네요.

뭐 딴건 모르겠고 저런 식은 무조건 풀어 써본 다음,
결론적으로 상관이 있는 요소만 가져와서 처리해야한다는 것을 알게 되었습니다.
그냥 풀고나니 난이도가 어렵다..는 생각은 안드는데 경우의수 찾으라는건 좀 생소해서 놀랐네요.
*/
// a(i-1)+b(N-i)
// ai-a+bN-bi
// bN-a+i(a-b)

const calc = (arr) => {
    let ret = 0n
    let trace = []
    for (let i = 1n; i <= N; i++) {
        let [u, v, idx] = arr[i-1n]
        ret += u*(i-1n)+v*(N-i)
        trace.push(idx+1n)
    }
    return [ret, trace]
}

const MOD = 998_244_353n
let N = ini()
let A = []
let C = {}
let cnt = 1n
for (let i = 0n; i < N; i++) {
    let [u, v] = ins()
    A.push([u, v, i])
    C[u-v] = (C[u-v]||0n)+1n
    cnt = (cnt*C[u-v])%MOD
}
let ret = ""
let [mn, mnT] = calc(A.sort((i, j) => Number((j[0]-j[1]) - (i[0]-i[1]))))
let [mx, mxT] = calc(A.sort((i, j) => Number((i[0]-i[1]) - (j[0]-j[1]))))
ret += `${mn} ${cnt}\n${mnT.join(" ")}\n`
ret += `${mx} ${cnt}\n${mxT.join(" ")}`
console.log(ret)
