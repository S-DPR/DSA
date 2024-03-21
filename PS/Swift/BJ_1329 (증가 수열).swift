let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
1329번 증가 수열

80자리 이하의 수가 주어진다. 여기에 콤마를 적절히 써서 증가수열로 만들려고 한다.
1. 0으로 시작하는 숫자가 가능하다.
2. 마지막 숫자가 최대한 작아야한다.
3. 2번을 여러개가 충족한다면 그 중 첫번째가 가장 큰 수열, 두번째가 가장 큰 수열.. 순으로 순위가 높다.
이를 만족하는 가장 순위가 높은 수열을 구해보자.

하
진짜
진짜..
절대..다신풀기싫다
알고리즘을 푼다가 아니라 구현을 푼다라는 느낌이 너무 강하다..

첨엔 dp인줄알았는데 안돼서 뜯어보니 백트래킹
시간복잡도? 솔직히 모르겠어, 그냥 닥치고 구현
파이썬이면 그냥 int로 바꾸고 비교하면 되는데 얘는 또 오버플로나서 그거도 안돼
그래서 비교함수도 따로 구현..

.. 진짜 너무 싫다
다신 안풀고싶어..
대회테케 없었다면 난 진짜 이 문제 잊고살았을거야..
*/
func cmp(_ i: [[Character]], _ j: [[Character]]) -> Int {
    let ln = min(i.count, j.count)
    for idx in 0..<ln {
        let ret = scmp(i[idx], j[idx])
        if ret != 0 {
            return ret
        }
    }
    return 0
}

func scmp(_ i: [Character], _ j: [Character]) -> Int {
    var (cpi, cpj) = (i, j)
    while cpi.count > 0 && cpi[0] == "0" {
        cpi.removeFirst()
    }
    while cpj.count > 0 && cpj[0] == "0" {
        cpj.removeFirst()
    }
    if cpi.count != cpj.count {
        return cpi.count < cpj.count ? -1 : 1
    }
    for x in 0..<cpi.count {
        if cpi[x] == cpj[x] { continue }
        return Int(String(cpi[x]))! < Int(String(cpj[x]))! ? -1 : 1
    }
    return 0
}

let A = Array(readLine()!)
let N = A.count

func loop(_ idx: Int, _ prv: [Character]) -> (Bool, [[Character]]) {
    if idx == 0 { return (true, [[Character]]()) }
    var isUpdated = false
    var (last, arr) = (0, [[Character]]())
    for i in 0..<idx {
        let cur = Array(A[i..<idx])
        if scmp(prv, cur) <= 0 { continue }
        if isUpdated && scmp(Array(A[last..<idx]), cur) < 0 { continue }
        let (chk, chk_arr) = loop(i, cur)
        if !chk { continue }
        if !isUpdated || cmp(arr, chk_arr) < 0 {
            isUpdated = true
            last = i
            arr = chk_arr
        }
    }
    if isUpdated {
        arr.append(Array(A[last..<idx]))
        return (true, arr)
    }
    return (false, [[Character]]())
}

var isUpdated = false
var (last, arr) = (0, [[Character]]())
for i in (0..<N).reversed() {
    let cur = Array(A[i..<N])
    if isUpdated && scmp(Array(A[last..<N]), cur) < 0 { break }
    let (chk, chk_arr) = loop(i, cur)
    if !chk { continue }
    if !isUpdated || cmp(arr, chk_arr) <= 0 {
        isUpdated = true
        last = i
        arr = chk_arr
    }
}
arr.append(Array(A[last..<N]))
print(arr.map{String($0)}.joined(separator: ","))
