/*
13848번 나누는 자가 지배한다

N개의 카드가 있다. 각 카드는 1부터 13까지의 수가 있으며, 같은 카드가 최대 4개 존재할 수 있다.
첫 카드는 마음대로 놓고, 이후로는  내려놓은 카드의 숫자 합을 나눌 수 있는 값을 가진 카드만 내려놓을 수 있다 할 때,
전부 다 놓을 수 있는 경우가 있다면 그것을 순서대로 출력하고, 없다면 No를 출력해보자.

기묘한문제
진짜 몬가몬가임

그니까..
일단 백트래킹은 맞아요. 태그대로 백트래킹은 맞는데..
전 백트래킹이 이런 문제에 있길래 먼가 정수론적인 무언가를 써야한다 생각했는데,
의외로 그냥.. 단순하게 0에서 하나하나 둔다 대신 전부 다 놓고 하나하나 뺀다.. 가 정답이었습니다.

그런데 왜 이게 되는지는 잘 모르겠어요.
진짜 왜 시간초과가 안나지..?
0에서 두면 나던데..
*/
func loop(_ sum: Int, _ card: inout [Int], _ trace: inout [Int]) -> Bool {
    if sum == 0 { return true }
    for i in 1...13 {
        if card[i] == 0 { continue }
        if (sum-i) % i != 0 { continue }
        card[i] -= 1
        if loop(sum-i, &card, &trace) {
            trace.append(i)
            return true
        }
        card[i] += 1
    }
    return false
}

var out = ""
while true {
    let N = Int(readLine()!)!
    if N == 0 { break }
    let arr = readLine()!.split(separator: " ").map {Int($0)!}
    let sum = arr.reduce(0, +)
    var card = [Int](repeating: 0, count: 14)
    arr.forEach { i in card[i] += 1 }
    var ret = [Int]()
    if loop(sum, &card, &ret) {
        for i in ret {
            out += "\(i) "
        }
    } else {
        out += "No"
    }
    out += "\n"
}
print(out)