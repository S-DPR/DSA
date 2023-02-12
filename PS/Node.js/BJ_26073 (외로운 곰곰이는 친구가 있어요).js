const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
/*
26073번 외로운 곰곰이는 친구가 있어요

두 좌표가 주어지고, 한번에 이동 가능한 좌표가 주어진다.
예를들어 한번에 이동 가능한 좌표가 3, 5면 좌표 0에 있을 때 3, -3, 5, -5로만 이동 가능하다.
좌표 0으로 갈 수 있는지 계산해보고, 가능하면 Ta-da를, 불가능하면 Gave up을 출력하자.

아!
배주항등식 아시는구나!!!!!!!1
이런문제 저격하는데 완전 유용합니다!!!!!!

전에 골드 1 문제 하나 보고 와 이거 어캐풀어? 하면서 답지를 그냥 봤었는데,
와 이게 이렇게되는거야? ㅋㅋㅋㅋㅋ 하고 넘겼던 기억이 있습니다.
그리고 그 기억은 빅데이터로 남아, 이렇게 도움을 주었습니다.

Ax + By + Cz + ... = (좌표)가 되는 x, y, z, ...의 정수해가 존재할까요? 를 묻는 문제인데,
A, B, C, ..의 gcd를 구하고, 이 값으로 좌표를 완벽하게 나눌 수 있다면 정수해가 존재합니다.
이유는 몰라요. 그렇다는데요?

이런 결과만 알아도 되는걸 몰랐다고? 몰랐으면 맞아야지!
*/
let gcd = (i, j) => {
    if (i < j) [i, j] = [j, i]
    while (j !== 0)  [i, j] = [j, i%j]
    return i
}

let T = Number(input[0])
let ans = ""
for (let i = 1; i <= T*2; i += 2) {
    let [N1, N2] = input[i].split(" ").map(Number)
    let arr = input[i+1].split(" ").map(Number)
    let res = arr[1]
    for (let j = 1; j <= arr[0]; j++)
        res = gcd(arr[j], res)
    if (N1%res === 0 && N2%res === 0)
        ans += "Ta-da\n"
    else
        ans += "Gave up\n"
}
console.log(ans)