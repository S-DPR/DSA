/*
19645번 햄최몇?

수열이 주어질 때, 수열을 하나의 원소는 하나의 집합에 들어가도록, 아무렇게나 세 묶음으로 나눈다고 하자.
이떄 묶음중 최솟값을 구하시오.

어?
진짜 이거 왜된거지?
이해가 안되는데? 데이터가 약한가?

일단 배낭이란건 태그봐서 익히 알고있었고..
진짜 배낭만 쓰면 틀리는데 내림차순 정렬하고 배낭때리면 AC네?
왜..? 어째서..?
일반적인 속도 나오는 다른사람들은 O(N(N*50)^2)걸린거같던데..?
*/
let N = Int(readLine()!)!
var arr = readLine()!.split(separator: " ").map {Int($0)!}
var dp = [[Int]](repeating: [Int](repeating: 0, count: 2), count: N*50+1)
arr.sort()
for item in arr.reversed() {
    for i in (0...(N*50-item)).reversed() {
        if dp[i].reduce(0, +) != 0 {
            if dp[i][0] < dp[i][1] {
                if dp[i+item].min() != 0 && min(dp[i][0]+item, dp[i][1]) <= dp[i+item].min()! { continue }
                dp[i+item][0] = dp[i][0]+item
                dp[i+item][1] = dp[i][1]
            } else {
                if dp[i+item].min() != 0 && min(dp[i][0], dp[i][1]+item) <= dp[i+item].min()! { continue }
                dp[i+item][0] = dp[i][0]
                dp[i+item][1] = dp[i][1]+item
            }
        }
    }
    if dp[item].reduce(0, +) == 0 {
        dp[item][0] = item
    }
}
let S = arr.reduce(0, +)
var ret = 0
for i in 0...N*50 {
    let s = dp[i].reduce(0, +)
    if S-s <= dp[i].min()! {
        ret = max(ret, S-s)
    }
}
print(ret)