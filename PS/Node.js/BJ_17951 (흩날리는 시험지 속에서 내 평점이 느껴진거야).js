const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
17951번 흩날리는 시험지 속에서 내 평점이 느껴진거야

수열이 주어진다.
이 수열을 연속한 M개의 수열로 나눈다고 하자.
M개의 수열의 합의 최솟값을 구해보자.

코테문풀방에서 매개변수탐색 하길래 생각나서 북마크에 박혀있던거 푼 문제.
매개변수탐색이란걸 모르면 그대로 끝장난다..
..지만 알아도 지금까지 못풀었다가, 문풀방에서 실버문제(6236) 하나 풀어보고 지혜를 얻어 풀었습니다.

로직은 뭐..
매개변수탐색 돌리면서..
M개의 그룹 만들고.. 그리디하게 꽉꽉 채워넣으면서..
원하는 값보다 작은게 있으면 false, 아니면 true 리턴하고.. 그런거죠 머..
매개변수탐색이 다 그럼 그렇지.. 알아채기 힘들어서 그렇지..
*/
function test(arr, M, x) {
    let idx = 0
    let group = []
    for (let i = 0; i < M; i++)
        group.push(0)
    for (let i = 0; i < arr.length && idx < M; i++) {
        group[idx] += arr[i]
        if (group[idx] >= x) idx++
    }
    let result = true
    for (let i = 0; i < M; i++) {
        if (group[i] < x) result = false
    }
    return result
}

let [N, M] = input[0].split(" ").map(Number)
let arr = input[1].split(" ").map(Number)
let [l, r] = [0, 20*N]
while (l <= r) {
    let m = (l + r) >> 1
    if (!test(arr, M, m)) r = m-1
    else l = m + 1
}
console.log(r)