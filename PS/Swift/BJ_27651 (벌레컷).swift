/*
27651번 벌레컷

길이가 N인 수열 arr을 세 부분으로 나누자. 나누어진 두 점을 l, r이라고 할 때,
arr[:l], arr[l+1:r], arr[r+1:N]의 합을 각각 leftS, midS, rightS라고 하자.
leftS < rightS < midS를 만족하는 두 점 l, r의 경우의 수를 구해보자.

북마크에 있는 골드3문제 처리중입니다.
진짜 이딴게 골드3..? 이라는 생각만 드네..

태그는 꿰뚫었지만 구현에 상당히 애를 먹은 문제.
일단 누적합은 다 구하고, 1..<N-1에서 하나씩 순회하고, 그 값을 i라고 하면.
arr[i:N]까지 더한 값 / 2를 넘는 인덱스를 구하고. 이게 mid값이 될 수 있는 최소 인덱스고요.
다음으로 pf[N]-pf[i]인 인덱스를 구해야합니다. 이건 right값이 될 수 있는 최대 인덱스입니다.
그리고 right-mid를 구하고, 음수면 0으로 고정시켜서 더하면 됩니다.
*/
func lowerBound(_ arr: [Int64], _ x: Int64) -> Int {
    var lo = 0, hi = arr.count
    while lo < hi {
        let mid = (lo + hi) >> 1
        if arr[mid] >= x {
            hi = mid
        } else {
            lo = mid + 1
        }
    }
    return hi
}

let N = Int(readLine()!)!
let arr = readLine()!.split(separator: " ").map {Int64($0)!}
var pf = [Int64(0)]
arr.forEach { i in
    pf.append(pf[pf.count-1]+i)
}
var ret = Int64(0)
for i in 1..<N-1 {
    let mid = lowerBound(pf, 1+pf[i]+(pf[N]-pf[i])/2)
    let right = lowerBound(pf, pf[N]-pf[i])
    ret += max(Int64(right-mid), 0)
}
print(ret)
