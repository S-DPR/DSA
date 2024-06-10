const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
const input = require("fs").readFileSync(filePath).toString().trim().split("\n")
const ini = () => Number(input[__idx++])
const ins = () => input[__idx++].split(" ").map(Number)
let __idx = 0
/*
22995번 증가하는 부분 수열의 개수 K

증가하는 부분 수열이 K개인 수열을 만들어보자.
단, 길이가 34를 넘어가면 안된다.
한자리 수열도 증가하는 부분 수열이다.

cnt[i] = i로 끝나는 증가하는 부분 수열의 개수.
'믿음'

처음엔 막막했는데, 생각해보니 K가 생각보다 빠르게 찹니다.
예를 들어 1부터 X까지 순서대로 나열하면 벌써 (2^X)-1개의 증가하는 부분 수열이 나와요.
그러므로, 1부터 i-1까지 증가하는 부분 수열의 개수가 K 이하인 최대 i를 찾아줍니다.
이제 그걸 넣고, cnt[i]에 1~i-1값을 넣어요.
이를 K가 0이 될때까지 반복합니다.

확실한 직관보다는 믿음으로 푼 문제입니다.
이게 되네..
*/
let T = ini()
let str = ""
while (T--) {
    let K = ini()
    let cnt = Array(35).fill(0)
    let ret = []
    cnt[0] = 1
    while (K) {
        let [cur, curCnt] = [1, 1]
        let sum = 1
        for (let i = 1; i <= 34; i++) {
            if (curCnt < sum && sum <= K) {
                cur = i
                curCnt = sum
            }
            sum += cnt[i]
        }
        ret.push(cur)
        K -= curCnt
        cnt[cur] += curCnt
    }
    str += `${ret.length}\n`
    str += `${ret.join(' ')}\n`
}
console.log(str)
