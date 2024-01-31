let nxti = { () -> Int in return Int(readLine()!)! }
let nxtl = { () -> Int64 in return Int64(readLine()!)! }
let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let twoi = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let twol = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let thri = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let thrl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
26079번 곰곰이의 벼락치기

연결그래프가 아닐수도 있는 그래프가 주어진다.
그래프를 위상정렬하는 경우의 수를 구해보자.

ㅋㅋ 와 이게 되네

어떻게할까.. 하다가
위상정렬 조금 뒤적뒤적해보는데,
자식을 위상정렬하는 경우의 수랑, 자식의 개수를 사용해서 조합론 굴릴줄은..
자식의 개수 중 각 자식컴포넌트가 사용할 수 있는 경우의 수 체크.
그거로 조합론 굴려버립니다. 와..
*/
let MOD: Int64 = 1_000_000_007
let (N, K) = twoi()
var G = [[Int]](repeating: [Int](), count: N+1)
var indep = [Int](repeating: 0, count: N+1)
for _ in 0..<K {
    let (u, v) = twoi()
    G[u].append(v)
    indep[v] += 1
}

func pow(_ x: Int64, _ p: Int64) -> Int64 {
    var ret: Int64 = 1
    var prod = x
    var pp = p
    while pp > 0 {
        if pp&1 == 1 {
            ret = (ret * prod) % MOD
        }
        prod = (prod * prod) % MOD
        pp >>= 1
    }
    return ret
}

var facto = [Int64](repeating: 1, count: 200_001)
for i in 1...200000 {
    facto[i] = (facto[i-1] * Int64(i)) % MOD
}
func comb(_ n: Int, _ c: Int) -> Int64 {
    let (up, down) = (facto[n], (facto[c]*facto[n-c])%MOD)
    return (up*pow(down, MOD-2))%MOD
}

func loop(_ x: Int) -> (Int64, Int) {
    var ret: Int64 = 1
    var node: Int = 0
    var child = [Int]()
    var childRet = [Int64]()
    for i in G[x] {
        let (nxtRet, nxtChild) = loop(i)
        node += nxtChild
        child.append(nxtChild)
        childRet.append(nxtRet)
    }
    let retn = node+1
    for i in 0..<child.count {
        ret *= (comb(node, child[i])*childRet[i])%MOD
        ret %= MOD
        node -= child[i]
    }
    return (ret, retn)
}

var (ret, retC) = ([Int64](), [Int]())
for i in 1...N {
    if indep[i] != 0 { continue }
    G[0].append(i)
}
print(loop(0).0)
