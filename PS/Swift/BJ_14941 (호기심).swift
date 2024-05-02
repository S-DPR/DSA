let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
24941번 호기심

수 A, B가 주어질 떄, A와 B 사이의 소수를 크기순서대로 a1, a2, ..., an이라고 하자.
이 때, a1*3-a2+a3*3-a4+... 의 값을 구해보자.
한 번만 하면 재미 없으니 10^5번 구해보자.

그냥 어..
아.. 이거 분배법칙 써서 누적합.
누적합은 그냥 홀짝나눠서 하면되고..
그 인덱스 찾는건 prime배열 하나 만들고 거따가 A B 넣어서 이분탐색굴리자.
이제 구간내에 소수가 홀수개냐 짝수개냐에 따라 누적합 쓰는게 다르니까 알아서 처리하자.
이 단계거 머리로 굴러가면 AC.
간단하네요. 20~30분걸렸나. 언어 좀 더 익숙했으면 금방했을거같은데..
*/
let MAX = 100000
var prime = [Int]()
var div = Array(0...MAX)
for i in 2...MAX {
    if div[i] == i {
        prime.append(i)
    }
    for j in prime {
        if i*j > MAX { break }
        div[i*j] = j
        if i%j == 0 { break }
    }
}
prime.insert(0, at: 0)
prime.insert(0, at: 0)
var sm = [0, Int64(0)]
for i in 2..<prime.count {
    sm.append(sm[i-2]+Int64(prime[i]))
}

func bisect(_ x: Int) -> Int {
    var (lo, hi) = (0, prime.count)
    while lo < hi {
        let mid = (lo + hi) >> 1
        if prime[mid] >= x {
            hi = mid
        } else {
            lo = mid + 1
        }
    }
    return hi
}

var ret = ""
let N = ini()
for _ in 0..<N {
    let (l, r) = tni()
    let li = bisect(l)
    let ri = bisect(r+1)-1
    if (ri-li+1)&1 == 1 {
        ret += "\((sm[ri]-sm[li-2])*3 - (sm[ri-1]-sm[li-1]))\n"
    } else {
        ret += "\((sm[ri-1]-sm[li-2])*3 - (sm[ri]-sm[li-1]))\n"
    }
}
print(ret)
