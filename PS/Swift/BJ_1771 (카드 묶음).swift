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
1771번 카드 묶음

수열이 주어진다.
각 수를 하나의 집합으로 보자. 그리고 집합의 수를 정렬할 경우 연속된 수만 있다면, 그 집합은 옳은 집합이다.
집합을 N-1번 합쳐 하나의 집합으로 하려는데, 그동안 나오는 모든 집합이 옳은 집합이어야한다.
집합을 어떻게 합쳐야할까? 두 집합을 합칠 때 앞 집합의 마지막 원소의 인덱스를 N-1개 출력해보자.

웰-논
대충 스택 긁으면 됩니다.
분리집합인가? 했는데, 생각해보니 이거 스택이 좀 더 낫더라고요.
대충 쓱- 긁어주면 AC. 오랜만에 순식간에 푼 G2네요.
*/
let N = ini()
let A = spi()
var stk = [[Int]]()
var ret = [Int]()
stk.append([-1<<30, 1<<30])
for i in 0..<N {
    let x = A[i]
    if stk[stk.count-1][0] != x && stk[stk.count-1][1] != x {
        stk.append([x, x, i+1])
    }
    while stk.count >= 2 && (stk[stk.count-1][1]+1 == stk[stk.count-2][0] || stk[stk.count-1][0]-1 == stk[stk.count-2][1]) {
        let f = stk.removeLast()
        let s = stk.removeLast()
        ret.append(min(f[2], s[2]))
        stk.append([min(f[0], s[0]), max(f[1], s[1]), max(f[2], s[2])])
    }
}
var str = ""
for i in ret {
    str += "\(i) "
}
print(str)
