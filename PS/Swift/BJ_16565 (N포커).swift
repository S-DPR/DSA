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
16565번 N포커

트럼프카드 52장이 있다.
여기서 N개의 카드를 뽑았을 때, 숫자가 같은 4쌍이 한 개 이상일 경우의 수는 몇개일까?

솔직히 어렵진 않았다
근데 난 바보멍청이야

dp로 되지않을까 하고 두들기다가 멸망 ㅋㅋ
포함배제는 처음에 바로 떠올랐는데 근데도 dp같은데 으음 하면서 두들기다가..
ㅠㅠ
*/
let mod = 10007
let N = ini()
var dp = [[Int]](repeating: [Int](repeating: 0, count: 53), count: 53)
for i in 0...52 { dp[i][0] = 1 }
for i in 1...52 {
    for j in 1...52 {
        dp[i][j] = (dp[i-1][j] + dp[i-1][j-1])%mod
    }
}
var ret = 0
for i in 1...13 {
    if N-4*i < 0 { break }
    if i&1 == 1 {
        ret = (ret+dp[52-4*i][N-4*i]*dp[13][i])%mod
    } else {
        ret = (ret-(dp[52-4*i][N-4*i]*dp[13][i])%mod+mod)%mod
    }
}
print(ret)
