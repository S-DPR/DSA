const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
1898번 이전 수열은 어떤 수열일까

수열이 주어진다.
각 인덱스에 대하여, 원래 인덱스에 있는 값과 1 이하의 차이만 나며,
사전순으로 가장 앞에 오는 수열은 어떤 수열일까?
단, 수열에는 1부터 N(수열의 길이)만큼의 수가 하나씩 들어있다.

그리-디
대충봐도 그리-디

사실 이게 먼소린가 하고 앉아있었는데 그냥 대충 보니까 답이 보이더라고요.
수열이 있고, 가장 큰 N을 기준으로 잡읍시다. 이를 i라고 하고요.
i에 대해, i-1의 위치를 찾아봅니다. 각각 idx[i]와 idx[i-1]로 합시다.
idx[i]가 idx[i-1]보다 큰 경우 (즉, 뒤에있는 경우)
 - idx[i-1]이 더 작아지는게 이득이므로 arr[i]의 값을 유지합니다.
idx[i]가 idx[i-1]보다 작은 경우 (즉, 앞에있는 경우)
 - i는 '현재 삽입 가능한 가장 큰 수'이므로 i가 될 수 있는 수는 i-1과 i뿐입니다. (i+1은 이미 소비되었거나 없습니다.)
 - i가 어쨌든 존재해야하고, i는 가능한 뒤에 있는게 유리하므로 idx[i]는 arr[i]의 값입니다.
 
이정도 골드2면 할만한 문제네요..
*/
let N = Number(input[0])
let arr = [0]
for (let i = 1; i <= N; i++)
    arr.push(Number(input[i]))
let idx = [0]
let res = [0]
for (let i = 1; i <= N; i++) {
    idx.push(0)
    res.push(0)
}
for (let i = 1; i <= N; i++)
    idx[arr[i]] = i

for (let i = N; i >= 1; i--) {
    if (res[idx[i]] !== 0) continue
    if (idx[i] < idx[i-1]) {
        res[idx[i]] = i-1
        res[idx[i-1]] = i
    } else {
        res[idx[i]] = i
    }
}

let answer = ""
res.slice(1, N+1).forEach(i=>answer+=`${i}\n`)
console.log(answer)