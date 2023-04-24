/*
2493번 탑

모든 인덱스에 대해 자신보다 크고 앞에 있는 인덱스의 번호를 구해보자.
그런게 없다면 0을 출력하자.

골드1 플레5 찾아다니다가 마음이 꺾여서 푼 골드5문제
그냥 평범한 스택입니다. 와...
*/
let N = Int(readLine()!)!
var arr = readLine()!.split(separator: " ").map {Int($0)!}
var res = [Int](repeating: 0, count: N)
var stk = [[Int]]()
for i in (0..<N).reversed() {
    let cur = arr[i]
    while !stk.isEmpty && stk.last![0] < cur {
        let p = stk.removeLast()
        res[p[1]] = i+1
    }
    stk.append([cur, i])
}
print(res.compactMap{String($0)}.joined(separator: " "))