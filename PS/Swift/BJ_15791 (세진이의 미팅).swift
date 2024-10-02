let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
let inf = 1<<28
let lnf = Int64(1)<<60
/*
15791번 세진이의 미팅

nCr을 10^9+7로 나눈 나머지를 구해보자.

랜덤마라톤에서 나온 날먹문제
평소에 랜덤문제에서 나왔으면 빠르게 걸렀겠지만..
랜덤마라톤은 그냥 무조건 풉니다. 맛있다~
*/
let MOD = Int64(1_000_000_007)

func pow(_ x: Int64, _ p: Int64) -> Int64 {
    var k = x
    var ret = Int64(1)
    var a = p
    while a > 0 {
        if a&1 == 1 {
            ret = (ret * k) % MOD
        }
        k = (k * k) % MOD
        a >>= 1
    }
    return ret
}

let (N, M) = tni()
var facto = [Int64(1)]
for i in 1...N {
    facto.append((facto[facto.count-1]*Int64(i))%MOD)
}
var up = facto[N]
var down = (facto[N-M]*facto[M])%MOD
print((up*pow(down, MOD-2))%MOD)
