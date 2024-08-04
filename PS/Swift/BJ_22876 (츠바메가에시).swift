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
22876번 츠바메가에시

100만*100만 배열에 제비가 N개 있다.
각 제비는 점수를 가지고있으며, 세 번의 검격으로 제비를 제거하려한다.
각 검격은 X, Y축중 하나로 평행하게 무한한 길이로 할 수 있으며,
3번의 검격으로 제거한 제비의 점수가 총 점수가 된다.
총 점수의 최댓값을 구해보자.

예엣날에 트리셋으로 비벼보려다가 포기했는데 트리셋이 된다네
어휴..

암튼 옛날옛적 푼 수열과 쿼리 24번인가? 이거 한 100번은 꼬아 만든 문제
핵심 로직은 수열쿼리24입니다.

난이도는.. 솔직히 수쿼24보다 어려운거같은데 다른풀이가 있다고하네요.
다들 멀티셋 막히면 플레2라고들 하고..
하여튼 세 번을 한 방향으로만 휘두르는건 대충 NlogN으로 되는데,
두 번을 한 방향으로, 한 번을 다른방향으로 휘두르는걸 어떻게 처리할래? 묻는 문제였습니다.
여기서 핵심은 이것조차 Nlog^2N으로 처리해버리는것.
사실상 완탐이었네요.
*/
let MX = 1_000_001
var segTX = [(Int, Int)](repeating: (0, 0), count: MX*2+10)
var segTY = [(Int, Int)](repeating: (0, 0), count: MX*2+10)

func update(_ segT: inout [(Int, Int)], _ idx_: Int, _ val: Int) {
    var idx = idx_ + MX
    segT[idx] = (segT[idx].0, segT[idx].1+val)
    while idx > 1 {
        if segT[idx].1 < segT[idx^1].1 {
            segT[idx>>1] = (segT[idx^1].0, segT[idx^1].1)
        } else {
            segT[idx>>1] = (segT[idx].0, segT[idx].1)
        }
        idx >>= 1
    }
}

func query(_ segT: [(Int, Int)], _ l_: Int, _ r_: Int) -> (Int, Int) {
    var (l, r) = (l_+MX, r_+MX)
    var ret = (0, 0)
    while l <= r {
        if l&1 == 1 {
            if ret.1 < segT[l].1 {
                ret = segT[l]
            }
            l += 1
        }
        if ~r&1 == 1 {
            if ret.1 < segT[r].1 {
                ret = segT[r]
            }
            r -= 1
        }
        l >>= 1
        r >>= 1
    }
    return ret
}

for i in (MX+1)...(MX*2+1) {
    segTX[i] = (i-MX, 0)
    segTY[i] = (i-MX, 0)
}

let N = ini()
var X = [Int: Int]()
var Y = [Int: Int]()
var CX = [Int: [(Int, Int)]]()
var CY = [Int: [(Int, Int)]]()
var A = [(Int, Int, Int)]()
for _ in 0..<N {
    var (x, y, w) = hni()
    x += 1
    y += 1
    update(&segTX, x, w)
    update(&segTY, y, w)
    if X[x] == nil { X[x] = 0 }
    if Y[y] == nil { Y[y] = 0 }
    if CX[x] == nil { CX[x] = [(Int, Int)]() }
    if CY[y] == nil { CY[y] = [(Int, Int)]() }
    X[x]! += w
    Y[y]! += w
    CX[x]!.append((y, w))
    CY[y]!.append((x, w))
    A.append((x, y, w))
}
var SX = [0, 0, 0] + X.values.sorted()
var SY = [0, 0, 0] + Y.values.sorted()
var (sxln, syln) = (SX.count, SY.count)
var ret = max(SX[sxln-1]+SX[sxln-2]+SX[sxln-3], SY[syln-1]+SY[syln-2]+SY[syln-3])
for (x, w) in X {
    for (y, ww) in CX[x]! {
        update(&segTY, y, -ww)
    }
    var (fi, f) = query(segTY, 1, MX)
    var (_, s1) = query(segTY, 1, fi-1)
    var (_, s2) = query(segTY, fi+1, MX)
    ret = max(ret, w+f+max(s1, s2))
    for (y, ww) in CX[x]! {
        update(&segTY, y, ww)
    }
}
for (y, w) in Y {
    for (x, ww) in CY[y]! {
        update(&segTX, x, -ww)
    }
    var (fi, f) = query(segTX, 1, MX)
    var (_, s1) = query(segTX, 1, fi-1)
    var (_, s2) = query(segTX, fi+1, MX)
    ret = max(ret, w+f+max(s1, s2))
    for (x, ww) in CY[y]! {
        update(&segTX, x, ww)
    }
}
print(ret)
