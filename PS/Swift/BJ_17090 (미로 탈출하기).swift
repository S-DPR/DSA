/*
17090번 미로 탈출하기

N*K크기의 미로가 주어진다.
각 칸에는 RDUL중 하나가 적혀있으며, 각각 우측, 아래, 위, 왼쪽으로 이동하라는 뜻이다.
미로에 적힌대로 이동했을 때 탈출할 수 있는 칸의 수를 구하시오.

그냥 문제 하나에 치이기 북마크 돌아다니다가 나온, 골드5같은 문제.
10분동안 끄적끄적하다가 AC맞고 난이도 보니까 골드3..
이후 생각해보니 그래프에 dp박은 개 악질문제를 골드5에 둘리가 없구나..

dp랑 그래프만 한거같아서 다른거 풀려했는데, 뇌수 조금 흘리면서 결국 푼건 dp와 그래프네요.
내일은 꼭..
*/
func dfs(_ M: [[Character]], _ V: inout [[Int]], _ x: Int, _ y: Int) -> Int {
    let (N, K) = (M.count, M[0].count)
    if !(0 <= x && x < K) { return 1 }
    if !(0 <= y && y < N) { return 1 }
    if V[y][x] != -1 { return V[y][x] }
    V[y][x] = 0
    switch M[y][x] {
        case "R":
            V[y][x] = dfs(M, &V, x+1, y)
        case "L":
            V[y][x] = dfs(M, &V, x-1, y)
        case "U":
            V[y][x] = dfs(M, &V, x, y-1)
        default:
            V[y][x] = dfs(M, &V, x, y+1)
    }
    return V[y][x]
}
let I = readLine()!.split(separator: " ").map{Int($0)!}
let (N, K) = (I[0], I[1])
var M = [[Character]]()
for _ in 0..<N {
    M.append(Array(readLine()!))
}
var V = [[Int]](repeating: [Int](repeating: -1, count: K), count: N)
for i in 0..<N {
    for j in 0..<K {
        if V[i][j] == -1 {
            dfs(M, &V, j, i)
        }
    }
}
print(V.reduce(0) { $0 + $1.reduce(0, +) })