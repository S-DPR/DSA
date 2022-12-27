let input = require("fs").readFileSync("/dev/stdin").toString().trim().split("\n")
//let input = require("fs").readFileSync("./input.txt").toString().trim().split("\n")

/*
25909번 수확의 계절이다!

첫 줄에 수열의 길이 N과 정수 K가 주어진다.
두번째 줄부터 'E', 'W', 'S', 'N'과 정수 L이 공백으로 구분되어 주어진다.
각각 동서남북을 뜻하며, 정수 L만큼 이동한다는 뜻이다.
만약에 이동 한 곳을 일정 시간 T가 지난 후 다시 방문한다면 점수를 1점 얻을 수 있다.
주어진대로 이동하였을 때, 점수가 K 이상이 되는 가장 큰 정수 T를 구하시오.
(1 <= N <= 1000, 1 <= K <= 1_000_000, 1 <= L <= 1000)

까다로운 문제입니다. 파이썬으로 했어도 어려울거같아요.
일단 Map을 사용하여 이동한 좌표에, 몇 초에 이동하였는지 기록해줍니다.
기록을 다 한 뒤에는 T에 대하여 매개변수탐색을 합니다.

harvest함수가 한번당 O(N)이 걸리는데, 이게 많아봐야 1_000_000을 넘기가 힘들다는 점을 이용해야합니다.
어쨌든 기록할 수 있는 최대 양이 100만밖에 안된다는거죠.
그래서 섣불리 시간복잡도만 계산하면 절대 풀지 못하는 문제였습니다.
*/

function harvest(arr, x) {
    let result = 0, cur = arr[0]
    for (let i = 1; i < arr.length; i++) {
        if (arr[i]-cur < x) continue
        cur = arr[i]
        result++
    }
    return result
}

function count(planted, x) {
    let result = 0
    planted.forEach((resultX, _) => {
        resultX.forEach((resultY, _) => {
            result += harvest(resultY, x)
        })
    })
    return result
}

let curX = 0, curY = 0
let time = 0
let planted = new Map()
let [n, x] = input[0].split(" ").map(Number)
for (let i = 1; i <= n; i++) {
    let [pos, length] = input[i].split(" ")
    length = Number(length)
    for (let i = 0; i < length; i++) {
        switch(pos) {
            case 'S': curY++; break
            case 'N': curY--; break
            case 'E': curX++; break
            case 'W': curX--; break
        }
        if (!planted.has(curX)) planted.set(curX, new Map())
        if (!planted.get(curX).has(curY)) planted.get(curX).set(curY, [])
        planted.get(curX).get(curY).push(++time)
    }
}

planted.forEach((resultX, _) => {
    resultX.forEach((resultY, _) => {
        resultY.sort((a, b) => a-b)
    })
})

let left = 0, right = 1_000_000
while (left < right) {
    let mid = (left + right) >> 1
    if (count(planted, mid) < x) right = mid
    else left = mid + 1
}
console.log(right-1)