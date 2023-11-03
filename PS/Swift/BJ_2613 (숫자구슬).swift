let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
2613번 숫자구슬

길이가 N인 수열이 있다. 수열을 M개의 구간으로 나누려한다.
이 때, 각 구간 합 중 최댓값이 최솟값이 되게 하려한다.
이 때 가능한 최솟값을 구하고 어떻게 분할해야하나 구해보자.

저기 옆집 통나무 자르기 G1짜리 하위호환.
그때 생각하며 풀었습니다. 매개변수탐색의 역추적.
그냥 적당히 잘 처리해주면 됩니다.
그나저나 아무것도 없이 흩날리는 시험지 문제에 역추적 넣었더니 난이도가 2칸뛰네..
*/
let I = spi()
let (N, M) = (I[0], I[1])
let A = spi()

func test(_ x: Int) -> Bool {
    var count = 0
    var sum = 0
    for i in A {
        sum += i
        if sum > x {
            count += 1
            sum = i
        }
    }
    return count >= M
}

var left = A.max()!
var right = N*100
while left < right {
    let mid = (left + right) >> 1
    if test(mid) {
        left = mid + 1
    } else {
        right = mid
    }
}
print(right)
var ret = [Int]()
var cnt = 0
var sum = 0
for i in A {
    sum += i
    if sum > right {
        ret.append(cnt)
        sum = i
        cnt = 0
    }
    cnt += 1
}
if cnt != 0 {
    ret.append(cnt)
}
var idx = ret.count-1
while ret.count < M {
    while idx >= 0 && ret[idx] == 1 {
        idx -= 1
    }
    while idx >= 0 && ret[idx] > 1 && ret.count < M {
        ret[idx] -= 1
        ret.append(1)
    }
}
print(ret.map{String($0)}.joined(separator: " "))
