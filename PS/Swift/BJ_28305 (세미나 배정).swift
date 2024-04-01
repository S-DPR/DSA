let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
28305번 세미나 배정

세미나가 A[i]일을 포함해 열린다.
모든 세미나는 T일동안 열리는데, 각 세미나는 반드시 A[i]일을 포함해야한다.
하루에 일어나는 최대 세미나를 최소로 하려한다.
그 최소가 몇이 될까?

이분탐색? 최대의 최소라는 기적의 단어면 dp눈치 한번 보고 다시 보면 무조건이지
그리디? 이거 보고 그리디 아니라고 느낄사람이 없지. 무조건 최대한 빨리 시작해야겠지
정렬? 딱봐도 이건 정렬해줍쇼 정렬삽니다 하는 문제였지.

하지만 구현.. 구현이 안됐다..
답지를 봤는데도 이해가 안돼서 한참동안 봤다..
curT랑 curC로 열심히 두들겼는데 안돼서 봤더니,
배열로 뭔가를 처리한다.

dp[i] = i번째 세미나가 일어나는 가장 빠른 일시
현재 이분탐색의 mid가 K라면, K개 이하의 모든 요소는 그냥 넣어준다.
그래서 i < K라는 조건이 있다.

K개 이상이면? dp[i-K]+T와 A[i]-T+1을 비교한다.
뒤에꺼야 그냥 이번 세미나를 최대한 빨리 여는 식이고..
이전꺼는 i-K번째 세미나가 끝나고 T일 후를 체크하는 식.
이거를 이해 못해서 정말 시간이 오래걸렸다..
정렬되어있으니 성립하는.. 저 식.
*/
let (N, T) = tni()
let A = spi().sorted()
var (lo, hi) = (0, N)
while lo < hi {
    let mid = (lo + hi) >> 1

    var dp = A
    for i in 0..<N {
        let x = A[i]
        if i < mid {
            dp[i] = max(x-T+1, 1)
        } else {
            dp[i] = max(dp[i-mid]+T, x-T+1)
        }
    }
    
    var ret = true
    for i in 0..<N {
        ret = ret && dp[i] <= A[i]
    }

    if ret {
        hi = mid
    } else {
        lo = mid + 1
    }
}
print(hi)
