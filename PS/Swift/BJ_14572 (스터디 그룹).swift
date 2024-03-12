let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
14572번 스터디 그룹

스터디를 만들려고 N명을 모집했다. 이들중 몇 명을 제외하고 다 떨어뜨릴 생각이다.
스터디의 조건은 아래를 지킬 생각이다.
1. 임의의 두 학생의 실력차이가 D 이하이다.
2. 스터디 효율은 이 식을 따른다 : (한명이라도 알고있는 알고리즘 수 - 모두가 알고있는 알고리즘 수) * 스터디원 수
스터디 효율을 최대화하면 몇이될까?

진지하게 이거 알고리즘 수가 30이라길래 한번 꼰 dp인줄알았는데 어림도없지 즉시 그리디
시렭 기준 정렬 후, 투포인터로 최대한 많은 사람을 담아내면 됩니다.
저 식 자세히 보면 그냥 사람 수가 많은게 장땡이에요.
알고리즘은 적절히 관리해주고, 그 중 최댓값을 출력하면 됩니다.
*/
let (N, K, D) = hni()
var A = [(Int, [Int])]()
for _ in 0..<N {
    let (_, Kk) = tni()
    A.append((Kk, spi()))
}
A.sort{$0.0 < $1.0}
var (left, right) = (0, 0)
var algo = [Int](repeating: 0, count: 31)
var (ret, ln) = (0, 0)
while right < N {
    if abs(A[right].0-A[left].0) <= D {
        let (k, x) = A[right]
        for i in x {
            algo[i] += 1
        }
        right += 1
        ln += 1
    }
    else {
        let (k, x) = A[left]
        for i in x {
            algo[i] -= 1
        }
        left += 1
        ln -= 1
    }
    let know = algo.filter{$0 > 0}.count
    let all_know = algo.filter{$0 == ln}.count
    ret = max(ret, (know-all_know)*ln)
}
print(ret)
