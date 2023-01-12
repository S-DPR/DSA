/*
26607번 시로코와 은행털기

사람의 수 N과, 고를 사람의 수 K와, 두 스텟의 합 X가 공백으로 구분되어 주어진다.
이후 N개의 줄에 힘과 스피드를 뜻하는 a, b가 공백으로 구분되어 주어지는데,
a+b의 값은 항상 X임이 보장된다.
이중 K명을 뽑는다 할 때, (K명의 a의 합) * (K명의 b의 합)의 최댓값을 구해보자.

와 이게 배낭이야???
진짜 DP의 세계는 알 수가 없다..
3차원 DP가 왜 골드4인가 하다가..
X의 존재 이유를 깨닫고 b를 매몰차게 버렸습니다.

그나저나 Ruby 이거 2배에 1초 추가가 맞나 싶네요..
Ruby랑 완전 똑같이짰는데 Ruby는 시간초과나고 얘는 안나고..
*/
let I = readLine()!.split(separator: " ").map {Int($0)!}
let N = I[0], K = I[1], X = I[2]
var DP = Array(repeating:[Int](repeating:0, count:X*N+1), count:K+1)

for _ in 0..<N {
    let I = readLine()!.split(separator: " ").map {Int($0)!}
    let x = I[0]
    for i in (1...K).reversed() {
        for j in (0...X*N).reversed() {
            if j-x < 0 {
                break
            }
            if DP[i-1][j-x] == 1 {
                DP[i][j] = 1
            }
        }
    }
    DP[1][x] = 1
}

var result = 0
for i in 0...X*N {
    if DP[K][i] == 1 {
        result = max(result, i*(X*K-i))
    }
}
print(result)