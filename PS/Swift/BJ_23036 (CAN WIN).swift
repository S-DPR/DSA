let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
23036번 CAN WIN

길이가 N인 수열이 주어진다.
여기서 left, right 인덱스를 잡고 모든 수를 더할 생각이다.
그리고 그 평균을 구할건데, 이 값이 P를 넘겨야 한다.
가능한 경우의 수의 개수 구해보자.

세상에마상에 이걸 풀이를 싹 생각하고 풀어냈네
이거시 실력상승??

처음 생각할땐 '일단 모든 A[i]에서 P를 빼고 누적합 구하자.'
그래야 누적합이 양수일 때 평균이 P를 넘고, 0이면 평균이 P인거니까.
다음으로 생각해보니, 구간합을 많이 구해야해서 세그트리가 튀어나와야할거같은데? 이러고.
일단 오랜만에 펜윅트리 짜두고 생각.

다음으로 '10의 9승은 좀..' 이러면서 좌표압축 갈겨주고.
펜윅트리에서 쿼리 처리하고 업데이트 하는 방식.
물론 펜윅에 0도 넣어서 그냥 자기 자신이 P 이상인 경우도 처리.

와! 진짜 옛날 세그트리만 굴리던 시절엔 절대 못 할 생각이다..
늘긴했나봐..
*/
struct fenwick {
    var arr: [Int64]
    var size: Int
    init(_ size: Int) {
        arr = [Int64](repeating: 0, count: size+1)
        self.size = size+1
    }
    mutating func update(_ idx: Int, _ val: Int64) {
        var idx = idx
        while idx < size {
            arr[idx] += val
            idx += idx & -idx
        }
    }
    func query(_ idx: Int) -> Int64 {
        var ret: Int64 = 0
        var idx = idx
        while idx > 0 {
            ret += arr[idx]
            idx -= idx & -idx
        }
        return ret
    }
    func query(_ s: Int, _ e: Int) -> Int64 {
        return query(e)-query(s-1)
    }
}

func bisect(_ arr: [Int64], _ x: Int64) -> Int {
    var (left, right) = (0, arr.count)
    while left < right {
        let mid = (left + right) >> 1
        if arr[mid] >= x {
            right = mid
        } else {
            left = mid + 1
        }
    }
    return right
}

let I = spi()
let (N, P) = (I[0], Int64(I[1]))
var A = [0] + spl()
var compressSet = Set<Int64>(minimumCapacity: N+1)
compressSet.insert(0)
for i in 1...N {
    A[i] -= P
    A[i] += A[i-1]
    compressSet.insert(A[i])
}
var compress = compressSet.sorted()
var F = fenwick(N+1)
var ret: Int64 = 0
F.update(bisect(compress, 0)+1, 1)
for i in 1...N {
    let comp = bisect(compress, A[i]) + 1
    ret += F.query(1, comp)
    F.update(comp, 1)
}
print(ret)
