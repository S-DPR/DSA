let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
28284번 스터디 카페

사람들이 s날에 오기 시작해 e날까지 오던 기록이 M개 있다.
카페에는 자리가 A개 있고, 각각의 자리에는 가격이 있다.
사람들이 하루에 한번씩 자리를 구매했다고 할 때,
누적 최댓값과 최솟값을 구해보자.

자바스크립트로 똑같이 했다가 오버플로난 문제.
덕분에 스위프트 써서 그냥 풀었습니다..

그냥 스위핑 한번 긁은 다음 A를 정렬하고 최대/최소를 같이 구해주면 됩니다.
솔직히 어렵진 않은듯??
*/
let I = spi()
let (N, M) = (I[0], I[1])
let A = spl().sorted()
var S = [Int: Int64]()
for _ in 0..<M {
    let II = spi()
    let (s, e) = (II[0], II[1])
    if S[s] == nil { S[s] = 0 }
    if S[e+1] == nil { S[e+1] = 0 }
    S[s] = S[s]! + 1
    S[e+1] = S[e+1]! - 1
}
let keys = S.keys.sorted()
var pf = S[keys[0]]!
var cnt = [Int64](repeating: 0, count: N+1)
for i in 1..<keys.count {
    cnt[Int(pf)] += Int64(keys[i]-keys[i-1])
    pf += S[keys[i]]!
}
if N >= 2 {
    for i in (2...N).reversed() {
        cnt[i-1] += cnt[i]
    }
}
var (retm, retM) = (Int64(0), Int64(0))
for i in 1...N {
    retM += cnt[i]*A[N-i]
    retm += cnt[i]*A[i-1]
}
print(retm, retM)
