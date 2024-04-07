let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
1561번 놀이 공원

M개의 놀이기구가 있다. i번째 놀이기구는 A[i]마다 1명을 순환시킬 수 있다.
N명의 사람을 순환시키려 한다. N번째 사람은 몇 번째 놀이기구에 탈 수 있을까?

처음 봤을 때 와 이거 수학+그리디인가? 하면서 열심히 했던 문제
그런데 짜잔 매개변수탐색이네요

매개변수탐색 보고도 '아!' 하고 떠오르지 않았던거도 신기한게,
N을 매개변수로? 로 생각해봐도 아닌거같고..
그렇다고 M은 그냥 배열 길이고..
이러다가 '그럼 시간을 매개변수로구나..'
하아..
어렵네요.
후처리도 살짝 까다로운 편.
*/
let (N, M) = tnl()
if N <= M {
    print(N)
} else {
    let A = spl()
    var (lo, hi) = (Int64(0), N*30)
    while lo < hi {
        let mid = (lo + hi) >> 1
        var cnt = Int64(0)
        for i in A {
            cnt += mid/i
        }
        if cnt >= N-M  {
            hi = mid
        } else {
            lo = mid + 1
        }
    }
    var cnt = N-M
    for i in A {
        cnt -= (hi-1)/i
    }
    for i in 0..<Int(M) {
        if hi%A[i] != 0 { continue }
        cnt -= 1
        if cnt == 0 {
            print(i+1)
        }
    }
}
