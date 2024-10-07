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
1818번 책정리

정렬되지 않은 수열이 있다.
몇 개의 원소를 골라 원하는 자리에 넣을 수 있을 때,
최소 몇 개의 원소를 골라야할까?

너ㅓㅓㅓㅓ무 너ㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓ무 유명한 웰노운 LIS
물론 어느때나의 웰노운답게 모르면 맞아야지 방식이지만..
알면 그냥 풀면 됩니다.
보자마자 언어 제일 쓰기 싫은거 고르고 3분컷 골드2 ㅋㅋ
*/
func bisect(_ A: [Int], _ x: Int) -> Int {
    var (lo, hi) = (0, A.count)
    while lo < hi {
        let mid = (lo + hi) >> 1
        if A[mid] >= x {
            hi = mid
        } else {
            lo = mid + 1
        }
    }
    return hi
}

let N = ini()
let A = ins()
var lis = [-inf]
for i in A {
    let idx = bisect(lis, i)
    if lis.count == idx {
        lis.append(i)
    } else {
        lis[idx] = i
    }
}
print(N-lis.count+1)
