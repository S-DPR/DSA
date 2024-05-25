let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
1352번 문자열

아래 조건을 만족하는 대문자로 이루어진 문자열을 출력해보자.
 - 사전 순으로 가장 앞 문자열을 출력한다.
 - 각 문자가 처음으로 나온 인덱스만큼 해당 문자가 나온다. (가장 앞 문자는 1번 문자이다.)
 - 길이가 N이다.
없다면 -1을 출력하자.

음..
하 그리디가 아니라니
dp 열심히 짜다가 힘들어서 태그 보고 멸망..

그냥.. 그냥 백트래킹 열심히 짜기..
근데 수가 작아서 그냥 내부에서 비효율 최대로 때려도 앵간해서 통과됩니다.
아예 런타임 전의 전처리 해버려도 되고..
아.. 근데 너무 많이틀려버렸다..
*/
let N = ini()
var ret = [Character]()
var rem = [(Int, Int)]()
var sum = 0
let A = Int(("A" as UnicodeScalar).value)
func loop(_ len: Int, _ cur: Int) -> Bool {
    if len == N { return sum == 0 }
    if len+sum > N { return false }
    if !rem.isEmpty {
        rem.sort{$0.0 > $1.0}
        let store = rem.map{$0}
        var (k, v) = rem.popLast()!
        while !rem.isEmpty && v == 0 {
            rem.sort{$0.0 > $1.0}
            let (kk, vv) = rem.popLast()!
            k = kk
            v = vv
        }
        if v != 0 {
            ret.append(Character(UnicodeScalar(A+k)!))
            sum -= 1
            if v > 0 { rem.append((k, v-1)) }
            if loop(len+1, cur) { return true }
            sum += 1
            ret.popLast()
        }
        rem = store
    }
    rem.append((cur, len))
    ret.append(Character(UnicodeScalar(A+cur)!))
    sum += len
    if loop(len+1, cur+1) { return true }
    sum -= len
    ret.popLast()
    rem.popLast()
    return false
}
if loop(0, 0) {
    print(ret.map{String($0)}.joined(separator: ""))
} else {
    print(-1)
}