const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
1516번 게임 개발

건물짓는 게임을 하나 시뮬레이션 해보자.
N개의 건물을 지으려하는데, 건물을 짓는데는 순서가 있다.
첫줄에 N이 주어지고, 이후 N줄에 [T, NEEDS, -1]의 형식으로 입력으 들어오는데,
i번 건물을 짓는데 i+1번째의 T의 시간이 소요되고, NEEDS에 있는 건물들을 필요로 한다는 뜻이다.
이 때, 모든 건물을 짓는데 걸리는 시간을 출력해보자.
참고로, 한번에 원하는만큼의 건물을 지을 수 있다.

이상하네..
분명 위상정렬만 써서 풀려했는데 틀려서 반례 넣고 고치다보니 DP가 뜬금없이 튀어나오네...

위상정렬과 별 다를게 없는데, 문제는 "필요한 건물을 짓는" 시간중 최대치를 계속 갱신해야 한다는 점입니다.
이 점을 놓치고 일반 위상정렬처럼 헤헤 하면서 하면 못풀어요.
뭐.. 그거 빼면 그냥 위상정렬 문제입니다. (맞출 수 있어서) 재밌었네요.
*/
let N = Number(input[0])
let [G, dependent, T] = [[], [], [0]]
for (let i = 0; i <= N; i++) {
    G[i] = []
    dependent.push(0)
}
for (let i = 1; i <= N; i++) {
    let tmp = input[i].split(" ").map(Number)
    T.push(tmp[0])
    for (let j = 1; j < tmp.length-1; j++) {
        G[tmp[j]].push(i)
        dependent[i]++
    }
}

let result = [0]
let queue = []
for (let i = 1; i <= N; i++) {
    if (dependent[i] === 0) queue.push([i, 0])
    result.push(0)
}
while (queue.length > 0) {
    let [cur, curT] = queue.shift()
    curT += T[cur]
    result[cur] = Math.max(curT, result[cur]+T[cur])
    G[cur].forEach(i => {
        if (--dependent[i] === 0)
            queue.push([i, curT])
        result[i] = Math.max(result[cur], result[i])
    })
}

result.shift()
result.forEach(i=>console.log(i))
