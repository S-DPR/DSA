/*
17611번 직각다각형

x축이나 y축에 평행한 선으로만 이루어진 다각형의 꼭짓점이 주어진다.
여기에 x축과 y축에 평행하게 선을 그으려 한다.
평행하게 선을 그었을 때 이 다각형과 만나는 점의 개수를 각각을 i, j라고 해보자.
그리고, max(i), max(j)중 더 큰 값을 출력해보자.

간단?한가?싶은 스위핑문제.
골드3에 박혀있는게 좀 더 낫지 않았을까?
그나저나 오늘도 플레5에 시간 박았다가 실패했네..

당연히.. 그냥 뇌빼고 2D로 보면 문제풀이에 멸망을 이르르게 할 수 있고.
'난 좀 다르게 본다. 1D로 본다'를 시전하면 됩니다.
그니까, X축 그을때랑 Y축 그을때 따로 보자고요.

X축과 Y축에 대해 그냥 스위핑 그어주면 됩니다.
그리고 그 최댓값 얻어주면 돼요.
너무쉽다 와! 그런데 구현문제 100%로 100분걸렸다.. 와...
*/
let MAX = 1000002
let N = Int(readLine()!)!
var X = [Int](repeating: 0, count: MAX+1)
var Y = [Int](repeating: 0, count: MAX+1)
var items = [[Int]]()
for _ in 0..<N {
    let I = readLine()!.split(separator: " ").map {Int($0)!+500000}
    items.append(I)
}
for i in 1...N {
    if items[i-1][0] == items[i%N][0] {
        let m = min(items[i-1][1], items[i%N][1])
        let M = max(items[i-1][1], items[i%N][1])
        Y[m] += 1
        Y[M] -= 1
    } else {
        let m = min(items[i-1][0], items[i%N][0])
        let M = max(items[i-1][0], items[i%N][0])
        X[m] += 1
        X[M] -= 1
    }
}
for i in 1..<MAX {
    X[i] += X[i-1]
    Y[i] += Y[i-1]
}
print(max(X.max()!, Y.max()!))
