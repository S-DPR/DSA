const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
3015번 오아시스 재결합

수열이 arr이 주어진다.
서로 다른 두 수 i, j (i < j)에 대해 arr[i]와 arr[j]는 arr[i+1], ..., arr[j-1]가 모두 min(arr[i], arr[j]) 미만이라면 '마주볼 수 있다'라고 한다.
마주볼 수 있는 모든 i, j 쌍의 개수를 구해보자.

'이하'가 아니라 '미만'이라서 플레까지 뛴 문제.
일단 스택을 사용하는건 대충 단계별에서 본거라 알았는데,
그래도 못풀었었습니다..

문제가 간단한데 더 큰 문제는 그게 안풀란다는겁니다.
쿼리문제도아니고, 간단한 수열문제에 태그까지 아는데..
심지어 dp도 아닌데..
*/
let N = Number(input[0])
let stk = []
let cnt = 0
for (let i = 1; i <= N; i++) {
    let len = stk.length
    let cur = Number(input[i])
    while (len >= 1 && stk[len-1][1] < cur) {
        cnt += 1
        len -= 1
        stk.pop()
    }
    if (len !== 0) {
        if (cur === stk[len-1][1])
            cnt += len !== stk[len-1][0] ? stk[len-1][0]+1 : len
        else cnt += 1
    }
    if (len === 0 || stk[len-1][1] !== cur) stk.push([1, cur])
    else stk.push([stk[len-1][0]+1, cur])
}
console.log(cnt)