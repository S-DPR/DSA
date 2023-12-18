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
2568번 전깃줄-2

LIS를 구성하지 않는 원소의 첫번째 원소를 오름차순으로 출력해보자.

아..
확실히 멘탈이 중요하긴하다.
비상식량.. 힘들게먹네..
*/
func bisect(_ arr: [[Int]], _ x: Int) -> Int {
    var lo = 0
    var hi = arr.count
    while lo < hi {
        let mid = (lo + hi) >> 1
        if arr[mid][0] >= x {
            hi = mid
        } else {
            lo = mid + 1
        }
    }
    return hi
}

let N = nxtI()
var items = [[Int]]()
for i in 0..<N {
    let I = spi()
    items.append([I[0], I[1], i])
}
items.sort{$0[0] < $1[0]}
var lis = [[0, -1]]
var trace = [Int](repeating: 0, count: N)
for i in 0..<N {
    let x = items[i]
    let idx = bisect(lis, x[1])
    if lis.count == idx {
        lis.append([x[1], i])
    }
    if x[1] < lis[idx][0] {
        lis[idx] = [x[1], i]
    }
    trace[i] = lis[idx-1][1]
}
var V = [Bool](repeating: false, count: N)
func loop(_ x: Int) {
    if trace[x] != -1 {
        V[trace[x]] = true
        loop(trace[x])
    }
}
V[lis.last![1]] = true
loop(lis.last![1])

print(N-(lis.count-1))
var ret = [Int]()
for i in 0..<N {
    if !V[i] {
        ret.append(items[i][0])
    }
}
ret.sort()
var retS = ""
for i in ret {
    retS += "\(i)\n"
}
print(retS)
