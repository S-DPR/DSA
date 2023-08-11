const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let ini = (i) => Number(input[i])
let ins = (i) => input[i].split(" ").map(Number)
let idx = 0
/*
3362번 수수께끼

수열이 주어진다. 이 수열에서 T번째 수까지 어떻게 잘 더하여, 1부터 K까지 나오게 하려 한다.
가능한 T의 최솟값을 찾아보자. 만약 수열 전체를 이용해도 안된다면, -1을 출력하자.

이야
태그봐도 아예 뭔소린지 몰랐는데, 어제 심심해서 골드2 추가로 (거의 답지보고) 하나 더 푼거 있었는데,
그게 이거 기본문제였네..
이렇게 풀 수 있는 문제 늘려나가는거지 ㅋㅋ.. 아마도..?

일단 수를 정렬합니다. 그리고 하나씩 보는데, 일단 기본값을 1로 잡고 시작합니다.
만약 이 기본값보다 배열에서 다음으로 오는 수가 작거나 같다 기본값에 그 수를 더해줍니다.
1부터 A-1까지 만들 수 있다 할 때, A-1 이하의 수가 추가로 들어오면 어떻게든 조합해서 1부터 2A-2까지는 만들 수 있다는 논리로요.
하지만 A+1 이상의 수가 들어오면 A를 만들 수 없겠죠. 그 로직을 매개변수탐색으로 굴리는겁니다.

골드1의 세계는 넓고 놀랍다..
*/
function check(arr) {
    let cur = 1
    arr.sort((i, j) => { return i-j })
    arr.forEach(i => {
        if (i > cur) return cur
        cur += i
    })
    return cur
}

let ret = ""
let T = ini(idx++)
for (let tc = 0; tc < T; tc++) {
    let [N, K] = ins(idx++)
    let arr = ins(idx++)
    let [left, right] = [0, N]
    while (left < right) {
        let mid = (left + right) >> 1
        if (K <= check(arr.slice(0, mid)))
            right = mid
        else
            left = mid + 1
    }
    ret += (K > check(arr.slice(0, right))) ? `-1\n` : `${right}\n`
}
console.log(ret)