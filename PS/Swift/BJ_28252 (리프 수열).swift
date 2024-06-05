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
28252번 리프 수열

트리에서 리프 노드를 순서대로 제거했을 때 제거한 수를 나타내는 수열이 주어진다.
이에 해당하는 트리를 출력해보자.

해구성하기는 아무래도 관찰이 대부분인것같네..

트리가 안되는 경우만 세면 나머지는 알아서 해결됩니다.
부모는 하나이므로, 붙여야 하는 노드가 부모 노드보다 적으면 문제가 됩니다.
왜냐면 부모는 다음차례에 제거되어야하는데 이번에 제거되니까요.
그래서.. 트리가 안되는 경우만 잘 거르면 AC입니다.
*/
let N = ini()
let A = spi()
var ret = [[Int]]()
var nodeN = 1
func dfs(_ idx: Int, _ parent: [Int]) -> Bool {
    if idx < 0 {
        return true
    }
    if parent.count > A[idx] || parent.count == 1 && A[idx] == 1 {
        return false
    }
    var cur = [Int]()
    for i in 0..<parent.count {
        ret.append([parent[i], nodeN])
        cur.append(nodeN)
        nodeN += 1
    }
    if parent.count < A[idx] {
        for _ in 0..<A[idx]-parent.count {
            ret.append([parent[0], nodeN])
            cur.append(nodeN)
            nodeN += 1
        }
    }
    return dfs(idx-1, cur)
    return true
}
var parent = [Int]()
if A[N-1] == 1 {
    parent = [nodeN]
    nodeN = 2
} else if A[N-1] == 2 {
    ret.append([nodeN, nodeN+1])
    parent = [nodeN, nodeN+1]
    nodeN = 3
}
let can = A[N-1] <= 2 && dfs(N-2, parent)
if can {
    print(nodeN-1)
    ret.forEach{print($0[0], $0[1])}
} else {
    print(-1)
}