let nxtI = { () -> Int in
    return Int(readLine()!)!
}
let nxtL = { () -> Int64 in
    return Int64(readLine()!)!
}
let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
12738번 가장 긴 증가하는 부분 수열 3

가장 긴 증가하는 부분 수열의 길이를 구해보자.

비상식량 Yammy~
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

let N = nxtI()
let A = spi()
var lis = [-1<<63]
for i in A {
    let idx = bisect(lis, i)
    if idx == lis.count {
        lis.append(i)
    } else {
        lis[idx] = i
    }
}
print(lis.count-1)
