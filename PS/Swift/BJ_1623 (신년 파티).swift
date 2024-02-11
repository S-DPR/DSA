let nxti = { () -> Int in return Int(readLine()!)! }
let nxtl = { () -> Int64 in return Int64(readLine()!)! }
let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let twoi = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let twol = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let thri = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let thrl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
1623번 신년 파티

트리에서 이어져있는 두 개의 노드를 선택하지 않고, 노드를 최대 점수로 선택해보자.
그리고 어떻게 선택해야하는지 구해보고,
루트노드가 1번인데, 루트노드가 포함된 경우, 포함되지 않은 경우 모두 구해보자.

그냥.. 트리의 독립집합인가? 그문제랑 똑같은 문제.
대충 그거 보고 따라쳐봤습니다.
날먹좋다,,
*/
let N = nxti()
let A = [0] + spl()
var G = [[Int]](repeating: [Int](), count: N+1)
var dp = [[Int64]](repeating: [Int64](repeating: 0, count: 2), count: N+1)
var P = spi()
for i in 2...N {
    G[P[i-2]].append(i)
}

func dfs(_ x: Int) -> [Int64] {
    dp[x][1] = A[x]
    for i in G[x] {
        let ret = dfs(i)
        dp[x][0] += max(0, ret.max()!)
        dp[x][1] += max(0, ret[0])
    }
    return dp[x]
}

func backT(_ x: Int, _ prv: Bool, _ trace: inout [Int]) -> [Int] {
    for i in G[x] {
        var nxtPrv = false
        if !prv && dp[i][0] < dp[i][1] {
            trace.append(i)
            nxtPrv = true
        }
        backT(i, nxtPrv, &trace)
    }
    return trace
}

let f = dfs(1)
print(f[1], f[0])
var fbtt = [Int]()
var sbtt = [Int]()
let fbt = [1] + backT(1, true, &fbtt).sorted() + [-1]
let sbt = backT(1, false, &sbtt).sorted() + [-1]
print(fbt.map{String($0)}.joined(separator: " "))
print(sbt.map{String($0)}.joined(separator: " "))
