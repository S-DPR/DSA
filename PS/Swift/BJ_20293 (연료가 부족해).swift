/*
20293번 연료가 부족해

맵의 가로, 세로 크기 C, R와 주유소의 개수 N이 주어진다.
이후 N개의 줄에 r, c, w (w는 (r, c) 주유소에서 충전되는 최대 연료량)의 모양으로 주유소의 위치와 정보가 주어진다.
(1, 1)에서 출발하여 (R, C)로 가려 할 때, 출발시 필요한 연료량을 최소로 하려한다.
또한, x와 y좌표가 증가하는 방향으로만 이동할 수 있다.
출발시 필요한 연료의 최솟값을 구해보자.

보고 1시간도 안돼서 푼 문제.
뭐? x와 y좌표가 증가하는 방향으로만?
이거 그래프인척하는 순도 100% dp겠구나..

처음엔 (1, 1)에서 시작해봤는데 주유소 들릴때마다 지금까지 사용한 양이 초기화가 되어버려서,
대신 (R, C)에서 (1, 1)로 이동하는 방식을 선택했습니다.
역시 좀 다른문제에 두드려맞아야 빅데이터로 문제를 풀 수 있는것같아요.
평소였으면 BFS로 왜 시간초과나지.. 이러고있었겠지만..
*/
let INF = 1_000_000_000
let I = readLine()!.split(separator: " ").map {Int($0)!}
let (r, c) = (I[0], I[1])
let N = Int(readLine()!)!
var M = [[Int]](repeating:[Int](repeating: INF, count: c+2), count: r+2)
var charge = [[Int]](repeating:[Int](repeating: 1, count: c+2), count: r+2)
M[r][c] = 0
for _ in 0..<N {
    let I = readLine()!.split(separator: " ").map {Int($0)!}
    let (r, c, w) = (I[0], I[1], I[2])
    charge[r][c] -= w
}
for i in (1...r).reversed() {
    for j in (1...c).reversed() {
        M[i][j] = max(0, min(M[i][j], min(M[i][j+1], M[i+1][j])+charge[i][j]))
    }
}
print(M[1][1])
