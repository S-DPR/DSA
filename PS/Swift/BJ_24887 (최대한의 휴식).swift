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
24887번 최대한의 휴식

길이가 N인 수열이 주어진다.
이 수열에서 몇 개의 원소를 뽑아 합이 K가 되게 하려고 한다.
이 때, 뽑은 원소 사이에 있는 원소 개수의 최소를 최대로 만들어보자.

내 신성한 매개변수탐색에 dp를 묻혀버리네..

대충 보고
"어.. 최솟값의 최댓값 이거 매개변수 광고문구고.."
"그러면 대충 사이 원소 개수가 매개변수일테니까.."
"적어도 거리가 X만큼 떨어지면 되니까.. 대충 이거 할만한게 dp네?"
라는 사고방식으로 1시간정도만에 풀었습니다.
간단한 골드2네요.
*/
let I = spi()
let (N, K) = (I[0], I[1])
let A = spl()

func solve() -> String {
    if A.reduce(0, +) < K {
        return "-1"
    }
    if A.max()! >= K {
        return "Free!"
    }
    var (lo, hi) = (0, N)
    while lo < hi {
        let mid = (lo + hi) >> 1
        var mx = Int64(0)
        var dp = [Int64](repeating: 0, count: N)
        for i in 0..<N {
            if i > mid {
                mx = max(mx, dp[i-mid-1])
            }
            dp[i] = max(dp[i], mx+A[i])
        }
        if dp.max()! < K {
            hi = mid
        } else {
            lo = mid + 1
        }
    }
    return "\(hi-1)"
}

print(solve())
