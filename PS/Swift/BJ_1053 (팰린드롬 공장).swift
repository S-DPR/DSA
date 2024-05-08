let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
1053번 팰린드롬 공장

문자열을 팰린드롬으로 만들기 위해 아래 연산을 할 수 있다.
문자열이 주어질 때, 최소 몇 번 해야하나 구해보자.
 - 문자를 삽입한다.
 - 문자를 제거한다.
 - 문자 하나를 바꾼다.
 - 두 문자의 위치를 바꾼다. 최대 1번 사용 가능.

일단 문자열 길이가 최대 50.
아.. 또p 너구나..

우선 대충 느낌적인 느낌으로 구간dp라는건 알았습니다.
dp[l][r] = l부터 r까지 팰린드롬 만들 때 최소비용
4번 어떻게하지? 하다가 할것도 많고해서 인터넷 슥 봤는데,
아하, 브루트포스로 미리 처리하는구나.
N = 50이니 N^5까지 터지질 않아서 가볍게 처리.

그럼 구간dp가 문젠데..
1 2 3번 조건 잘 보면.. 알수있는데.
그냥 현재 l~r을 보고있다면 l을 한칸 앞으로 당기던지 r을 뒤로 밀던지,
아니면 l을 당기고 r도 밀던지 하면 됩니다.
둘다 당기고 미는건 3번, 나머지는 1 2번 연산 공통입니다. 셀 필요가 없죠.
다만, S[l] == S[r]이면 둘 다 밀 때 비용을 추가하지 말고 해야합니다.

음.. 구간dp 확장식으로 생각해서 시간날린거 아쉽네..
*/
var S = Array(readLine()!)
let N = S.count
var dp = [[Int]](repeating: [Int](repeating: -1, count: N), count: N)
func loop(_ l: Int, _ r: Int) -> Int {
    if l >= r { return 0 }
    if dp[l][r] != -1 { return dp[l][r] }
    var ret = 1<<30
    if S[l] == S[r] {
        ret = min(ret, loop(l+1, r-1))
    }
    ret = min(ret, loop(l+1, r-1)+1)
    ret = min(ret, loop(l+1, r)+1)
    ret = min(ret, loop(l, r-1)+1)
    dp[l][r] = ret
    return dp[l][r]
}
var ret = loop(0, N-1)
for i in 0..<N {
    for j in i+1..<N {
        dp = [[Int]](repeating: [Int](repeating: -1, count: N), count: N)
        S.swapAt(i, j)
        ret = min(ret, loop(0, N-1)+1)
        S.swapAt(i, j)
    }
}
print(ret)
