/*
27085번 최대 점수

N, s가 주어지고, 수열이 주어진다.
수열의 s번째 원소는 반드시 0임이 보장된다.
초기 점수가 0이고, 좌측, 우측으로 한칸 씩 이동 가능하다.
단, 이동할 때 반드시 해당 수 만큼 점수를 얻게되며, 이미 점수를 얻은 곳에서는 다시 점수를 얻을 수 없다.
이동 중 점수가 0 미만이 되면 즉시 점수가 청산된다.
언제든 위 행위를 멈출 수 있다. 점수의 최댓값을 구해보자.

그리디인걸 보고도 왜 그리디인지 이해가 안됐던 문제
풀고보니 확실하게 그리디가 맞는 문제..

왼쪽과 오른쪽을 잡고, 한쪽으로 쭉 가는데 누적값이 +가 될 때까지 밀어붙이는 형식의 풀이를 사용했습니다.
+가 되면 다시 0으로 초기화하고 밀어붙이고..
단, 중간에 음수가 되면 그건 잠시 멈춰두고 반대쪽부분을 먼저 처리합니다.
그러다 양수 되면 음수 풀리니까 또 지속가능해지겠죠.

참.. 신기한문제..
*/
let I = readLine()!.split(separator: " ").map {Int($0)!}
let (N, s) = (I[0], I[1]-1)
let arr = readLine()!.split(separator: " ").map {Int64($0)!}
var (left, right) = (s-1, s+1)
var (accL, accR) = (Int64(0), Int64(0))
var score = Int64(0)
while 0 <= left || right < N {
    while left >= 0 && accL <= 0 {
        if score+accL+arr[left] < 0 { break }
        accL += arr[left]
        left -= 1
    }
    while right < N && accR <= 0 {
        if score+accR+arr[right] < 0 { break }
        accR += arr[right]
        right += 1
    }
    if accL <= 0 && accR <= 0 { break }
    if 0 < accL {
        score += accL
        accL = 0
    }
    if 0 < accR {
        score += accR
        accR = 0
    }
}
print(score)