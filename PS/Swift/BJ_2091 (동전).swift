/*
2091번 동전

각각 1, 5, 10, 25의 가치를 가진 동전이 A, B, C, D개 있다.
동전을 최대한 사용해 X원을 만들려고 한다.
각각 어떻게써야하는지 구해보자.

브루트포스, 그리디, dp가 같이 있는 기적의 문제
전 dp 역추적으로 풀었습니다.

use배열은 사용한 동전을, dp는 사용한 개수를, possible은 도달가능여부를 체크합니다.
그리고 뭐.. 마지막에 그냥 use[i][X]꺼 다 출력하면 됩니다.

그리디는 조금 재밌었는데 보면 10이랑 25는 배수관계가 아니지만 1, 5, 10은 배수관계여서 이 부분에 대해 그리디를 적용하겠다는겁니다.
25는 그냥 따로 적용하겠다 이거죠.

브루트포스는 그냥 재귀 돌려버리네요.. 10000이라 되지 않을까 싶긴했는데 진짜되네..
*/
let I = readLine()!.split(separator: " ").map {Int($0)!}
let (X, A, B, C, D) = (I[0], I[1], I[2], I[3], I[4])
var use = [[Int]](repeating:[Int](repeating: 0, count:X+1), count:4)
var dp = [Int](repeating: 0, count: X+1)
var possible = [Bool](repeating: false, count: X+1)
let value = [1, 5, 10, 25]
let limit = [A, B, C, D]
possible[0] = true
dp[0] = 0
for i in 1...X {
    for j in 0..<4 {
        if i < value[j] { continue }
        if !possible[i-value[j]] { continue }
        if use[j][i-value[j]] >= limit[j] { continue }
        if dp[i-value[j]]+1 <= dp[i] { continue }
        dp[i] = dp[i-value[j]]+1
        for k in 0..<4 {
            use[k][i] = use[k][i-value[j]]
        }
        use[j][i] += 1
        possible[i] = true
    }
}
print(use[0][X], use[1][X], use[2][X], use[3][X])