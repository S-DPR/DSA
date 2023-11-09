let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
20181번 꿈틀꿈틀 호석 애벌레 - 효율성

길이가 N인 수열이 주어진다.
수열의 시작부터 끝까지 단 한번만 훑으면서, 아래 행위를 하려한다.
 - 원소 하나를 선택한다. 그 값에 이후 바로 오른쪽 값을 계속 더한다.
 - 그러면서 K 이상의 값이 될 경우 현재까지 더해진 값에서 K를 뺀만큼 점수로 갖는다.
이 때, 얻을 수 있는 최댓값을 구해보자.

dp에 이것저것 첨가해준 문제.
한시간정도 걸린 것 같습니다. 바보같은짓해서..

대충 누적합 긁어주고 dp[i] = i번째 원소일 때 최댓값으로 한 뒤,
이분탐색 쭉쭉 써주면서 K점 이상 얻는 가장 작은 경우를 찾아내고,
dp[idx+1]에 그 값을 업데이트 해줍니다.
이걸 쭉 하면 대충 O(NlogN)으로 되겠네요.
*/
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
let (N, K) = (I[0], Int64(I[1]))
let A = spl()
var pf = [Int64](repeating: 0, count: N+1)
var ret = [Int64](repeating: 0, count: N+3)
for i in 1...N {
    pf[i] = pf[i-1]+A[i-1]
}
for i in 1...N {
    ret[i] = max(ret[i-1], ret[i])
    let idx = bisect(pf, pf[i-1]+K)
    let pfIdx = min(idx, N)
    ret[idx+1] = max(ret[idx+1], ret[i]+(pf[pfIdx]-pf[i-1]-K))
}
print(ret.max()!)
