let nxti = { () -> Int in
    return Int(readLine()!)!
}
let nxtl = { () -> Int64 in
    return Int64(readLine()!)!
}
let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
3117번 Youtube

노트 i번에서 N번 이동할 경우 어느 노드에 있게될까?
중간에 이동을 못하게 되는 경우는 없다.

히히 희소배열
LCA, 희소배열과 너무 멀어져 버린 탓에 의심했었지만..
얼심히 scc dfs로 짜던 도중 '이거 아무리봐도 희소배열인데'하면서 분류 까니까 진짜네

그냥 희소배열 기본문제입니다.
희소배열 개념 까먹어서 어던식으로 하면 될지 파악하느라 늦어버렸네요..
*/
let I = spi()
let (N, K, M) = (I[0], I[1], I[2])
let A = spi()
let conn = [0] + spi()
var lgM = 0
while 1<<lgM < M-1 {
    lgM += 1
}
var sparse = [[Int]](repeating: Array(0...K), count: lgM+1)
sparse[0] = conn
for i in 0...lgM {
    if i == 0 { continue }
    for j in 1...K {
        sparse[i][j] = sparse[i-1][sparse[i-1][j]]
    }
}
var ret = ""
for i in A {
    let m = M-1
    var bit = 0
    var cur = i
    while (1<<bit) <= m {
        if (m&(1<<bit)) != 0 {
            cur = sparse[bit][cur]
        }
        bit += 1
    }
    ret += "\(cur) "
}
print(ret)
