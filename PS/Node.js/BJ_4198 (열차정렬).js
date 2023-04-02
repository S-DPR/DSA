const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
4198번 열차정렬

길이가 N인 수열이 있다. 당신은 이 수열의 제일 앞 원소부터 제일 뒤 원소까지 차례로 한번씩 접근 가능하다.
수열의 원소는 중복되지 않는다.
당신은 아래 작업중 하나를 하여 새 수열을 만들 수 있다.
1. 해당 수를 수열의 맨 앞 혹은 맨 뒤에 집어넣는다.
 - 맨 앞에 집어넣는경우 맨 앞 숫자보다 반드시 커야한다.
 - 맨 뒤에 집어넣는경우 맨 뒤 숫자보다 반드시 작아야한다.
 - 아직 수열에 수가 하나도 없는경우 그냥 넣을 수 있다.
2. 해당 수를 넣지 않고 패스한다.
새 수열의 최대길이를 구해보자.

최장 증가 부분 수열과 최장 감소 부분 수열을 i번째 인덱스에 대하여 구한 뒤 둘을 더하고 1을 빼서 답을 구하는 문제.
i번째 수부터 LIS와 LDS를 어떻게 구해야하지... 하다가,
문득 생각난 '수열을 거꾸로 하면 되는거아니야?'
그리고 실제로 그 일이 일어났습니다.

진짜 수열 거꾸로 받으면 된다는 생각 하자마자, 길게 생각한게 멍청하다는 생각이 들정도였습니다..
*/
let N = Number(input[0])
let info = []
for (let i = N; i >= 1; i--)
    info.push(Number(input[i]))
let res = 0
let [lis, lds] = [Array(N).fill(1), Array(N).fill(1)]
for (let i = 0; i < N; i++) {
    for (let j = 0; j < i; j++) {
        if (info[i] > info[j]) lis[i] = Math.max(lis[i], lis[j] + 1)
        if (info[i] < info[j]) lds[i] = Math.max(lds[i], lds[j] + 1)
    }
}
for (let i = 0; i < N; i++)
    res = Math.max(res, lis[i]+lds[i]-1)
console.log(res)
