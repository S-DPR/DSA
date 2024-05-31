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
31233번 관광 상품

수열 A의 길이 2 이상의 모든 부분배열의 중앙값의 최댓값을 구해보자.

엄..
코포에서 직관으로 풀었는데..
이유는 몰랐었어요.
근데 여기서 알게되네..
서순..
*/
let N = ini()
let A = spi()
if N == 2 {
    print(A.min()!)
} else {
    var ret = 0
    for i in 2..<N {
        ret = max(ret, A[i-2]+A[i-1]+A[i]-max(A[i-2],A[i-1],A[i])-min(A[i-2],A[i-1],A[i]))
    }
    print(ret)
}
