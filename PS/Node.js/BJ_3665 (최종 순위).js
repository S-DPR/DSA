const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
3665번 최종 순위

길이가 N인 수열이 있다. 이후 M개의 "반전"이 어떻게 일어났는지 주어진다.
반전이란, 두 수 i, j에 대해 두 수의 "상대적인 순서"가 바뀌는 것을 의미한다.
예를 들면, 1, 2, 3에서 반전이 1, 2 일어났다면 2, 1, 3이 된다.
하지만 1, 2, 3에서 반전이 1, 3으로 일어난다면 불가능한 경우가 된다.
어쩌면, M개의 반전 정보를 모두 모았을 때 순서를 정할 수 없는 경우가 생길 수 있다.
수열과 반전의 정보가 주어진다. 반전의 정보는 반드시 적용해야한다.
모든 반전을 적용할 수 있다면 반전을 적용한 수열을, 불가능하면 IMPOSSIBLE을, 알 수 없다면 ?를 출력해보자.

위?상??정??렬??
정렬은 커녕 그래프도 안그리고 풀었습니다.
그냥 앞에 몇 개가 있나.. 정보를 다 두고.. 나오는 반전마다 + - 적절히 해주고..
참 쉽죠? 코드 로직이 GOAT수준으로 간단해요.
*/
let result = ""
let T = Number(input[0])
let idx = 1
for (let i = 0; i < T; i++) {
    let N = Number(input[idx++])
    let items = input[idx++].split(" ").map(Number)
    let rank = []
    for (let j = 0; j < N; j++)
        rank[items[j]] = j
    let ret = Array.from(rank)
    let changeRank = Number(input[idx++])
    for (let j = 0; j < changeRank; j++) {
        let [u, v] = input[idx++].split(" ").map(Number)
        if (rank[u] < rank[v]){
            ret[u]++
            ret[v]--
        } else {
            ret[u]--
            ret[v]++
        }
    }
    let final = []
    let flg = false
    for (let i = 1; i <= N; i++) {
        if (final[ret[i]]) flg = true
        final[ret[i]] = i
    }
    result += flg ? "IMPOSSIBLE" : final.join(" ")
    result += "\n"
}
console.log(result)