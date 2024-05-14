let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
22996번 유니온 파인드 복원

유니온 파인드 관련 쿼리 Q개를 처리한 이후 배열과 그 결과가 주어진다.
쿼리가 어떻게 주어진걸까?

에디토리얼 보고 1차 놀라움을 얻고,
답지를 보고 2차 놀라움을 얻은...

1. union연산인 1번이랑 find 출력 연산인 2번이 있는데,
2. 2번은 배열에 영향 안주므로 처음에 싹 다 처리해버리자.
가 첫번째 아이디어. 여기서 시작해야 뭔가 된다네요.

저거만해도 놀라웠는데 이제 쭉 하다가..
음.. 쿼리 개수가 좀 잉여가 남네? 하고 마지막에 남은만큼 1 1 1 출력했는데..
가볍게 WA! 뜨고 하.. 한숨 좀 쉰 다음, 열심히 서칭.

그 결과,
아!! 1 u u같은게 초기배열이 아니면 find에서 다른 애 찾으니까 터지는구나!!
하..
쉽지않네...
*/
var str = ""
var ret = [[Int]]()
let (N, Q) = tni()
let A = [0] + spi()
let M = ini()
var G = [[Int]](repeating: [Int](), count: N+1)
var root = [Int]()
for _ in 0..<M {
    let x = ini()
    str += "2 \(x)\n"
}
for i in 1...N {
    if A[i] == i {
        root.append(i)
    } else {
        G[A[i]].append(i)
    }
}
func dfs(_ x: Int) {
    for i in G[x] {
        dfs(i)
    }
    for i in G[x] {
        ret.append([1, i, x])
    }
}
for i in root {
    dfs(i)
}
if ret.count+M < Q {
    for _ in ret.count+M..<Q {
        str += "1 1 1\n"
    }
}
for i in ret {
    for j in i {
        str += "\(j) "
    }
    str += "\n"
}
print(N, Q)
print(str)
