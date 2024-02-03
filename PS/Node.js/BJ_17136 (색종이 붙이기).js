const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
17136번 색종이 붙이기

10*10판에 1이 몇 개 있는데, 색종이를 붙여 이를 0으로 만들 수 있다.
넓이가 1, 4, 9, 16, 25인 정사각형 색종이가 각각 5개 있다.
최소 몇 개의 색종이를 써야 덮을 수 있나 구해보자.
단, 0인 부분은 색종이를 대면 안되며 색종이를 덧붙여서도 안된다.

그냥 문제 나온거 그대로 구현하면 AC.
그리디로 잘못 빠져든분들이 어렵다고 느끼신 것 같습니다.
전 보자마자 단순하게 '이런건 백트래킹이더라' 느낌 받고 백트래킹이겠거나 하고 북마크 박아놨다가,
오늘은 문제 풀기 너무 싫어서 쉬운거 대충 구현하고 갑니다.
*/
let loop = (map, paper, kth) => {
    if (kth == 100) {
        let ret = 25
        paper.forEach(i => ret -= i)
        return ret
    }
    let [r, c] = [Math.floor(kth/10), kth%10]
    if (map[r][c] == 0) return loop(map, paper, kth+1)
    let ret = 1<<30
    for (let i = 0; i < 5; i++) {
        if (paper[i] == 0) continue
        let canPlace = true
        for (let j = 0; j <= i; j++) {
            for (let k = 0; k <= i; k++) {
                canPlace = canPlace && r+j < 10 && c+k < 10
                canPlace = canPlace && map[r+j][c+k] == 1
            }
        }
        if (!canPlace) break
        for (let j = 0; j <= i; j++)
            for (let k = 0; k <= i; k++)
                map[r+j][c+k] = 0
        paper[i]--
        ret = Math.min(ret, loop(map, paper, kth+1))
        paper[i]++
        for (let j = 0; j <= i; j++)
            for (let k = 0; k <= i; k++)
                map[r+j][c+k] = 1
    }
    return ret
}

let M = []
for (let i = 0; i < 10; i++) {
    M.push(ins())
}
let ret = loop(M, [5, 5, 5, 5, 5], 0)
console.log(ret == 1<<30 ? -1 : ret)
