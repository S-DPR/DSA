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
23285번 단말 수열

노드가 N개인 트리에서 리프노드를 모두 가져온 뒤,
노드 번호가 가장 작은 것을 제거하자.
이를 N-2번 반복하고 뗀 노드 번호를 순서대로 나열한 수열이 주어진다.
이 때, 원래 트리를 구하시오.

아이디어는 플5인가봅니다. 여따가 힙 하나 붙이면 플레돼요.
트리를 좀 이해하고 있으면 못풀건 아닌 문제같네요..
다만, 매번 새로 구해야한다는점은 제가 좀 잘못생각해서 시간이 걸렸습니다.
*/
let N = ini()
let A = spi()
var edges = [[Int]]()
var cur = [Int](repeating: 0, count: N+1)
for i in A {
    cur[i] += 1
}
var idx = -1
var aidx = 0
while aidx < N-2 {
    idx = (idx + 1) % N
    if cur[idx+1] != 0 { continue }
    if idx+1 == A[aidx] { continue }
    cur[A[aidx]] -= 1
    edges.append([idx+1, A[aidx]].sorted())
    aidx += 1
    cur[idx+1] = -1
    idx = -1
}
for i in 1..<N {
    if cur[i] != 0 { continue }
    for j in i+1...N {
        if cur[j] != 0 { continue }
        edges.append([i, j])
        break
    }
}
edges.sort { $0[0] != $1[0] ? $0[0] < $1[0] : $0[1] < $1[1] }
for i in edges {
    print(i[0], i[1])
}
