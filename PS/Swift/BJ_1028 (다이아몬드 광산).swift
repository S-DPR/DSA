let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
1028번 다이아몬드 광산

모서리의 길이가 K인 다이아몬드를 캐려고 한다.
다이아몬드는 각 변이 1로 이루어진 정사각형을 45도 돌린도형이다.
캘 수 있는 가장 큰 다이아몬드의 크기를 구해보자.

캬
실력이 늘긴 했구나
옛날엔 dp인걸 알고도 문제 보면 어잌후 내가 잘못들어왔네 하고 도망쳤는데,
다시보니까 어.. 그냥 1의 대각선위치를 dp..
그리고 윗점과 아랫점 잡고 전처리 해둔 결과로 처리.
누적합+dp+완전탐색이었네요.

분류 알고있던게 크긴 했던거같아요. dp만 알고있었지만.
근데 어떻게해, 1년 지난거같은데 안잊혀지는걸..
swift로는 푼 사람이 없더라고요. 1600명중에.
그래서! 제가 풀었습니다. 시간제한 750ms인데 700ms 썼네요.
상수커팅해냈다..
*/
let (R, C) = tni()
var M = [[Int]](repeating: [Int](repeating: 0, count: C+2), count: R+2)
var dp = [[[Int]]](repeating: [[Int]](repeating: [Int](repeating: 0, count: 4), count: C+2), count: R+2)
for i in 1...R {
    M[i] = [0] + Array(readLine()!).map{Int($0.asciiValue!) - 48} + [0]
}
let dr = [1, 1, -1, -1]
let dc = [1, -1, 1, -1]
let rr = [Array((1...R).reversed()), Array((1...R).reversed()), Array((1...R)), Array((1...R))]
let cr = [Array((1...C).reversed()), Array((1...C)), Array((1...C).reversed()), Array((1...C))]
for i in 0..<4 {
    for r in rr[i] {
        for c in cr[i] {
            if M[r][c] == 0 { continue }
            let (nr, nc) = (r+dr[i], c+dc[i])
            if M[nr][nc] == 0 { continue }
            dp[r][c][i] = dp[nr][nc][i] + 1
        }
    }
}

var isEnd = false
for sz in (0...R/2).reversed() {
    if isEnd { break }
    for r in 1...R {
        if isEnd || r+sz*2 >= R+2 { break }
        if sz/2 > C { break }
        for c in sz/2...C {
            if isEnd { break }
            if M[r][c] == 0 { continue }
            if dp[r][c][0] < sz || dp[r][c][1] < sz { continue }
            if dp[r+sz*2][c][2] < sz || dp[r+sz*2][c][3] < sz { continue }
            print(sz+1)
            isEnd = true
            break
        }
    }
}
if !isEnd { print(0) }
