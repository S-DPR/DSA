const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
7983번 내일 할거야

[u, t]가 원소인 리스트 A가 주어진다. t일까지 u일의 시간을 소모하여 과제를 해야 한다는 뜻이다.
과제를 최대한 미루면서 데드라인 직전에 완료하려 한다. 
이 때, 0일부터 최대 몇 일 연속 쉴 수 있는지 구해보자.

이딴게.. 골드5..?
그냥 정렬하고 대충 min 씌우고 하다보면 바로 풀립니다.
골드5도 고저차가 참 크단말이지..
*/
let N = Number(input[0])
let items = []
for (let i = 1; i <= N; i++) {
    let [d, t] = input[i].split(" ").map(Number)
    items.push([d, t])
}
items.sort((i, j) => {
    return j[1]-i[1]
})
let cur = items[0][1]
for (let i = 0; i < N; i++) {
    cur = Math.min(cur, items[i][1])
    cur -= items[i][0]
}
console.log(cur)