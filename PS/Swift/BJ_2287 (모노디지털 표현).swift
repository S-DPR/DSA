let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
2287번 모노디지털 표현

K를 8개 이하로 사용하고, 연산자는 +*-/만 쓸 수 있을 때 X를 만들 수 있는지 여부를 구해보자.
만들 수 있다면 K를 적어도 몇 개 써야하는지 구해보자.

유사다익인줄알았는데..
dp..인데 그거도 엄청 나이브하네?
솔직히 시간초과 안나는게 더 신기..
*/
let K = ini()
var dp = [Set<Int64>](repeating: Set<Int64>(), count: 9)
for i in 1...8 {
    dp[i].insert(Int64(String(repeating: "\(K)", count: i))!)
}
for i in 1...8 {
    for j in 1..<i {
        for x in dp[j] {
            for y in dp[i-j] {
                dp[i].insert(x+y)
                if x-y > 0 {
                    dp[i].insert(x-y)
                }
                dp[i].insert(x*y)
                if x/y > 0 {
                    dp[i].insert(x/y)
                }
            }
        }
    }
}
let N = ini()
var ret = ""
for _ in 0..<N {
    let x = lni()
    var find = false
    for i in 1...8 {
        if !dp[i].contains(x) { continue }
        ret += "\(i)\n"
        find = true
        break
    }
    if !find {
        ret += "NO\n"
    }
}
print(ret)
