/*
17495번 피아노 연주

왼손과 오른손이 각각 정수 위에 있을 때,
정수로 되어있는 악보(0 <= Note <= 120)를 순서대로 치려고 한다.
최소 이동거리와, 각 이동마다 왼손이 이동한경우 1, 오른손이 이동한경우 2를 출려갷보자.

"와 이게 맞네"
시간초과날줄알았는데 의외다.
시간복잡도가 O(N^2)일줄알았는데..

다른사람 코드 조금 봤는데 원리는 같더라고요.
차이점이라면 저는 dp가 120*120*1000이면 메모리초과날거같아서,
120*120을 매번 새로 만들어 쓴정도..

어쨌든 그냥 매번 새로 갈아끼우는 문제입니다.
그거말고 특별할거는 없는데, 아이디어 생각하기가 힘들었던거같아요.
처음엔 그리디로 풀어서 틀렸습니다를 받았었습니다.
*/
func noteToInt(x: String) -> Int {
    let noteDict = ["C" : 0, "D" : 2, "E" : 4, "F" : 5, "G" : 7, "A" : 9, "B" : 11]
    let note = String(x[x.index(x.startIndex, offsetBy: 0)])
    let octave = Int(String(x[x.index(x.startIndex, offsetBy: 1)]))!
    let shap = (x.count == 3) ? 1 : 0
    return octave*12+noteDict[note]!+shap
}

let INF = 1_000_000_000
var I = readLine()!.split(separator: " ").map {noteToInt(x:String($0))}
let left = I[0], right = I[1]
let N = Int(readLine()!)!
let arr = readLine()!.split(separator: " ").map {noteToInt(x:String($0))}

var dp = [[Int]](repeating: [Int](repeating:0, count:121), count: 121)
var trace = [[[Int]]](repeating: [[Int]](repeating: [Int](), count:121), count:121)
var stack = [[left, right]]
for next in arr {
    var tmpdp = [[Int]](repeating: [Int](repeating:INF, count:121), count: 121)
    var tmptrace = [[[Int]]](repeating: [[Int]](repeating: [Int](), count:121), count:121)
    var nextStack = [[Int]]()
    while stack.count > 0 {
        let cur = stack.removeLast()
        let curL = cur[0], curR = cur[1]
        if curL == curR { continue }
        if tmpdp[curL][next] > dp[curL][curR] + abs(curR-next) {
            if tmpdp[curL][next] == INF {
                nextStack.append([curL, next])
            }
            tmpdp[curL][next] = dp[curL][curR] + abs(curR-next)
            tmptrace[curL][next] = trace[curL][curR] + [2]
        }
        if tmpdp[next][curR] > dp[curL][curR] + abs(curL-next) {
            if tmpdp[next][curR] == INF {
                nextStack.append([next, curR])
            }
            tmpdp[next][curR] = dp[curL][curR] + abs(curL-next)
            tmptrace[next][curR] = trace[curL][curR] + [1]
        }
    }
    dp = tmpdp
    trace = tmptrace
    stack = nextStack
}

var result = INF
var resultT = [Int]()
for i in 0..<121 {
    for j in 0..<121 {
        if dp[i][j] < result {
            result = dp[i][j]
            resultT = trace[i][j]
        }
    }
}
print(result)
var string = ""
for i in resultT {
    string += "\(i) "
}
print(string)