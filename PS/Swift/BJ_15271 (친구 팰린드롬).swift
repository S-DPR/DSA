let ins = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let lns = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = ins(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = lns(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = ins(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = lns(); return (I[0], I[1], I[2]) }
let inf = 1<<28
let lnf = Int64(1)<<60
/*
15271번 친구 팰린드롬

N명의 사람에게 번호가 붙어있다.
홀수는 여자, 짝수는 남자이다. 또, 서로 친한 사람들의 번호가 주어진다.
서로 친한 여자와 남자를 인접하게 두면 둘이 춤을 추게되고,
그런 짝이 옆에 없는 사람은 혼자 춤을 추게 된다.
짝이 없는 사람이 두 번 이상 연속으로 나타날 수 없을 때 배치 가능한 최대 사람은 몇명일까?

이게 뭘까 하다가 보다보니 이분매칭이라서 휴~ 하고 구현했습니다.
이분매칭 기본난이도가 P4니까.. 이거도 P4고..
그래서 긴장좀했는데 다행히 스윽 풀었습니다.
*/
let (N, K) = tni()
var G = [[Int]](repeating: [Int](), count: N+1)
for _ in 0..<K {
    var (u, v) = tni()
    if (u+v)&1 == 1 {
        if v&1 == 1 {
            swap(&u, &v)
        }
        G[u].append(v)
    }
}

var L = [Int](repeating: -1, count: N+1)
var R = [Int](repeating: -1, count: N+1)
func dfs(_ x: Int, _ vis: inout [Bool]) -> Bool {
    vis[x] = true
    for i in G[x] {
        if R[i] == -1 || (!vis[R[i]] && dfs(R[i], &vis)) {
            L[x] = i
            R[i] = x
            return true
        }
    }
    return false
}
var match = 0
for i in 1...N {
    if L[i] == -1 {
        var vis = [Bool](repeating: false, count: N+1)
        if dfs(i, &vis) {
            match += 1
        }
    }
}
print(min(match*2+1, N))
