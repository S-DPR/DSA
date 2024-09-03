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
25048번 랜선 연결

N개의 스위치가 있다. 각 스위치는 포트 v개, 비용 w를 갖는다.
인터넷 랜선 하나를 포트에 꽂을 예정이다. 스위치와 스위치도 각 포트 하나를 이용해 연결할 수 있다.
컴퓨터가 M개 있다. 이 때, 컴퓨터를 모두 인터넷에 연결하며 모든 포트를 사용할 수 있는 스위치 구매 최소비용을 구해보자.

마라톤 안끝내서 빨리 끝내야한다는 압박때문에 답지 봐버린 문제..
음.. 근데 점점 생각하다보니 배낭 생각이 산으로 가버려서 오히려 나았을지도..

대충 w를 적절히 조절해가며 배낭 쓰는게 핵심인 문제였습니다.
이런식은 또 처음이라 당황스럽네요.
쉽지않다 골드2..
*/
let N = ini()
var A = [(Int, Int64)]()
for _ in 0..<N {
    let (u, v) = tni()
    A.append((u, Int64(v)))
}
let M = ini()
var dp = [Int64](repeating: lnf, count: M+1)
dp[0] = 0
for (w, v) in A {
    if w-1 <= M {
        for i in (w-1...M).reversed() {
            if dp[i-(w-2)] != lnf {
                dp[i] = min(dp[i], dp[i-(w-2)]+v)
            }
        }
    }
    if w-1 <= M {
        dp[w-1] = min(dp[w-1], v)
    }
}
if M == 1 {
    print(0)
} else {
    print(dp[M] == lnf ? -1 : dp[M])
}
