const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
5550번 헌책방

수 C와 그 수의 종류 X가 N개의 줄에 주어진다.
여기서 K개를 고를건데, 그 때 가치는 다음과 같이 계산된다.
 - C의 합
 - 같은 종류끼리 묶고 개수가 T일 때, 각 종류의 모든 책에 (T-1)만큼의 부가세가 붙음
얻을 수 있는 최대 가격은 얼마일까?

하다보니 배낭이 됐다
종류가 10개 이하라고? 라고할때 조금 쌔했는데,
이게 배낭일줄은 몰랐지..
태그 깠을때 배낭 없어서 얼마나 당황했는데 딴사람 코드 보니 배낭..

대신 저 종류가 문제인데, 이거는 그냥 그리디하게 정렬하고 부가세를 뒤에 붙여주는 방식.
내림차순 정렬 -> i번째 요소에 (i-1)*2만큼의 가격을 추가로 붙여줌
이후 저걸 누적합처리해주고, i번째 요소에는 i만큼의 가격이 필요하도록 항목 구성.
이제 배낭을 쓸 수 있게 됩니다. 휴~
*/
let [N, K] = ins()
let A = []
for (let i = 0; i <= 10; i++) {
    A.push([])
}
for (let i = 0; i < N; i++) {
    let [c, k] = ins()
    A[k].push(c)
}
for (let i = 0; i <= 10; i++) {
    A[i].sort((i, j) => j-i)
    A[i].unshift(0)
    for (let j = 1; j < A[i].length; j++) {
        A[i][j] += (j-1)*2+A[i][j-1]
    }
}
let prv = Array(K+1).fill(-1)
prv[0] = 0
for (let i = 0; i <= 10; i++) {
    let dp = prv.map(i => i)
    for (let k = 1; k < A[i].length; k++) {
        for (let j = K; j-k >= 0; j--) {
            if (prv[j-k] == -1) continue
            dp[j] = Math.max(dp[j], prv[j-k]+A[i][k])
        }
    }
    prv = dp
}
console.log(prv[K])
