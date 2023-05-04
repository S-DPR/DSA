/*
5015번 ls

0개 이상의 어떤 문자든 커버하는 *가 포함된 문자열 S가 있다.
이후 T개의 문자열이 주어진다. 각 문자열이 S로 표현 될 수 있으면 그 문자열을 출력하자.

이게 왜 dp 이렇게 한동안 북마크에 박은 문제였는데, 갑자기 생각나서 풀었습니다.
수업시간 그대로 쳐박은거같네요. 풀었으니까 된게아닐까?

dp[i][j]를 정의합니다. i번째 패턴이 Q의 j번째 인덱스와 맞다는 의미입니다.
i번째 패턴이 *면 당연히 예외처리 해줘야합니다. 이전에 true였던 부분 뒤로 전부 true로 해주어야합니다.
이후로 뭐.. 그냥 돌리면 됩니다. 그렇게해서, dp[-1][-1]이 true면 표현 가능한 문자열이 됩니다.
*/
let S = Array(readLine()!)
let T = Int(readLine()!)!
var ans = [String]()
for _ in 0..<T {
    let Q = Array(readLine()!)
    var dp = [[Bool]](repeating: [Bool](repeating: false, count: Q.count+1), count: S.count+1)
    dp[0][0] = true
    for i in 1...S.count {
        if S[i-1] == "*" {
            dp[i] = dp[i-1]
        }
        var exist = false
        for j in 1...Q.count {
            exist = exist || (dp[i-1][j-1] && Q[j-1] == S[i-1])
            dp[i][j] = (Q[j-1] == S[i-1] && exist) || (S[i-1] == "*" && (dp[i][j-1] || dp[i-1][j]))
        }
    }
    if dp[S.count][Q.count] {
        print(String(Q))
    }
}
/*
세상에.
import fnmatch
t = input()
for _ in ' '*int(input()):
    s = input()
    if fnmatch.fnmatch(s, t): print(s)

Ruby 숏코딩입니다. (58B)
t=gets;gets.to_i.times{s=gets;puts s if File.fnmatch(t,s)}

fnmatch는 처음봤네.. 앵간한 언어에 있는듯 합니다.
*/