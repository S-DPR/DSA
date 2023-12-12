let nxtI = { () -> Int in
    return Int(readLine()!)!
}
let nxtL = { () -> Int64 in
    return Int64(readLine()!)!
}
let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
11401번 이항 계수 3

nCk를 구해보자. 수가 커질 수 있으니, 1_000_000_007로 나눈 나머지를 구해보자.

 - 분할정복 거듭제곱을 아는가? YES
 - 모듈러 역원을 아는가? YES
 - N이 충분히 작은가? YES

비상식량 마싯서~
*/
func pow(_ x: Int64, _ p: Int64, _ m: Int64) -> Int64 {
    var ret: Int64 = 1
    var k = x
    var pp = p
    while pp > 0 {
        if pp&1 == 1 {
            ret = (ret * k) % m
        }
        k = (k * k) % m
        pp >>= 1
    }
    return ret
}

let I = spl()
let (P, Q) = (I[0], I[1])
let MOD: Int64 = 1_000_000_007
var up: Int64 = 1
var down: Int64 = 1
for i in 1...P {
    up = (up * i) % MOD
}
if Q > 0 {
    for i in 1...Q {
        down = (down * i) % MOD
    }
}
if P-Q > 0 {
    for i in 1...P-Q {
        down = (down * i) % MOD
    }
}
print((up*pow(down, MOD-2, MOD))%MOD)
