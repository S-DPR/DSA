/*
12852번 1로 만들기 2

정수 X가 주어진다. 이 X에 당신은 다음과 같은 연산을 할 수 있다.
1. X가 3으로 나누어 떨어지면, 3으로 나눌 수 있다.
2. X가 2로 나누오 떨어지면, 2로 나눌 수 있다.
3. X에서 1을 뺼 수 있다.
X를 1로 만들기 위한 횟수의 최솟값과 그 과정을 출력하자.

문제풀이 처음 했을때 DP에 벽을 느끼게한게 1로 만들기였습니다.
이제 걍 과정을 기억해버려서 풀어버렸지만..
이번에도 DP로 풀었는데 의외로 BFS로도 되나보네요?
근데 swift에는 덱이 없는걸.. 안될거야 아마..
*/
func tracking(trace:Array<Int>, cur:Int) {
    if (trace[cur] != cur) {
        tracking(trace:trace, cur:trace[cur])
    }
    print(cur, terminator: " ")
}

let N = Int(readLine()!)!
var trace = Array(repeating:N, count:N+1)
var cnt = Array(repeating:N, count:N+1)
cnt[N] = 0
var cur = N
for i in (1...N).reversed() {
    if (cnt[i-1] > cnt[i]+1) {
        cnt[i-1] = cnt[i]+1
        trace[i-1] = i
    }
    if (i%2==0 && cnt[i/2] > cnt[i]+1) {
        cnt[i/2] = cnt[i]+1
        trace[i/2] = i
    }
    if (i%3==0 && cnt[i/3] > cnt[i]+1) {
        cnt[i/3] = cnt[i]+1
        trace[i/3] = i
    }
}
print(cnt[1])
tracking(trace:trace, cur:1)