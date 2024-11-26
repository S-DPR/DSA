let ins = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let lns = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = ins(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = lns(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = ins(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = lns(); return (I[0], I[1], I[2]) }
let inf = 1<<28
let lnf = Int64(1)<<60
/*
13392번 방법을 출력하지 않는 숫자 맞추기

문자열 S와 T가 있다.
S와 T는 모두 숫자로 이루어져있는데, 0부터 9까지, 혹은 9부터 0까지 돌릴 수 있다.
이 때, S를 T로 만들기 위한 최소 움직임 횟수를 구해보자.

옛날에 푼문제 그냥 재탕.
의외로 난이도가 높네요?
여기서 역추적 추가한게 플레5였던가..
날먹좀 했습니다.
*/
let N = ini()
let S = readLine()!.map { Int(String($0))! }
let T = readLine()!.map { Int(String($0))! }
var dp = [[Int]](repeating:[Int](repeating: inf, count: 10), count: N)
func loop(_ idx: Int, _ left: Int) -> Int {
    if idx == N {
        return 0
    }
    if dp[idx][left] != inf {
        return dp[idx][left]
    }
    let cur = (S[idx]+left)%10
    let dst = T[idx]
    let leftDist = (dst-cur+10)%10
    let rightDist = (cur-dst+10)%10
    dp[idx][left] = min(
        leftDist+loop(idx+1, (left+leftDist)%10),
        rightDist+loop(idx+1, left)
    )
    return dp[idx][left]
}
print(loop(0, 0))
