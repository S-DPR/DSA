let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
let inf = 1<<30
let lnf = Int64(1)<<60
/*
1724번 그림판

R*C크기 그림판에 선 몇개를 X, Y축과 평행하게 그었다.
이제 채우기 기능을 모든 구역에 사용할 것인데,
가장 넓게 칠해진 구역의 넓이와 가장 좁게 칠해진 구역의 넓이를 구하시오.

선그려서 구역만드는 BFS문제
저번에 겪어본 김에 한번 골라서 풀었습니다.
그런데 음.. 문제 조건이 조금 덜 결정적(?)이어서..
WA 조금 많이 받았네요. 그래도 할만했습니다.
*/
let (R, C) = tni()
let N = ini()
var M = [[Int]](repeating: [Int](repeating: (1<<5)-1, count: C+2), count: R+2)
var V = [[Bool]](repeating: [Bool](repeating: false, count: C+2), count: R+2)
var cnt = [Int]()
for _ in 0..<N {
    let I = spi()
    let (y1, x1, y2, x2) = (I[0], I[1], I[2], I[3])
    if y1 == y2 {
        for x in x1+1...x2 {
            M[y1][x] &= ~(1<<1) // 아래
            M[y1+1][x] &= ~(1<<0) // 위
        }
    }
    if x1 == x2 {
        for y in y1+1...y2 {
            M[y][x1] &= ~(1<<2) // 오른쪽
            M[y][x1+1] &= ~(1<<3) // 왼쪽
        }
    }
}
let dr = [-1, 1, 0, 0]
let dc = [0, 0, 1, -1]
func dfs(_ r: Int, _ c: Int) {
    cnt[cnt.count-1] += 1
    V[r][c] = true
    for i in 0..<4 {
        let (nr, nc) = (r+dr[i], c+dc[i])
        if !(1 <= nr && nr < R+1) { continue }
        if !(1 <= nc && nc < C+1) { continue }
        if M[r][c]&(1<<i) == 0 { continue }
        if V[nr][nc] { continue }
        dfs(nr, nc)
    }
}
for r in 1...R {
    for c in 1...C {
        if V[r][c] { continue }
        cnt.append(0)
        dfs(r, c)
    }
}
print(cnt.max()!)
print(cnt.min()!)
