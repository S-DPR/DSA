/*
14622번 소수 게임

A와 B가 다음 규칙으로 게임을 한다.
1. A는 항상 첫 턴으로 시작한다. 이후, 번갈아가며 음이 아닌 정수를 말한다.
2. 말한 수가 소수가 아니면 상대는 자신이 말한 소수 중 3번째로 큰 소수만큼의 점수를 얻는다.
2-1. 만약에 지금까지 말한 소수의 개수가 3개 미만이라면, 1000점을 얻는다.
3. 지금까지 한번이라도 나온 소수를 말하면 그 사람은 1000점을 잃는다.
4. 두 사람은 5_000_000미만의 정수만 말할 수 있다.
첫 줄에 두 사람이 각각 가질 턴 수 T가 주어진다. (5 <= N <= 100000)
2번째줄부터 T+1번째 줄까지, 두 음이 아닌 정수가 공백으로 구분되어 주어진다.
좌측에 주어지는 정수가 A가 말한 것이고, 우측은 B가 말한 것이다.
A가 이기면 '소수의 신 갓대웅', B가 이기면 '소수 마스터 갓규성',
비기면 '우열을 가릴 수 없음'을 출력하라.

구현중에 실수를 빡세게 해버린 케이스.
Linear-Sieve를 사용할 때는 Set형을 함부로 쓰면 안된다는 교훈을 얻었습니다.
골드 3정도면 정말 적당한 난이도같아요.
하란대로 하기만 하면 되긴한데 실수하기 진짜 너무 쉬워요..
*/
let MAX = 5_000_000
var Prime = Array<Int>()
var arr = Array<Int>(0...MAX)
for i in 2...MAX {
    if arr[i] == i { Prime.append(i) }
    for j in Prime {
        if (MAX < i*j) { break }
        arr[i*j] = j
        if (i%j == 0) { break }
    }
}
let P = Set(Prime)

let T = Int(readLine()!)!
var D = Set<Int>(), G = Set<Int>()
var DMax = Array<Int>(), GMax = Array<Int>()
var DScore = Int64(0), GScore = Int64(0)
for _ in 0..<T {
    let I = readLine()!.split(separator:" ").map {Int($0)!}
    let x = I[0], y = I[1]

    if P.contains(x) {
        if D.contains(x) || G.contains(x) {
            DScore -= 1000
        } else {
            D.insert(x)
            DMax.append(x)
            DMax.sort()
            if DMax.count >= 4 { DMax.remove(at: 0) }
        }
    } else {
        if GMax.count < 3 { GScore += 1000 }
        else { GScore += Int64(GMax[0]) }
    }

    if P.contains(y) {
        if D.contains(y) || G.contains(y) {
            GScore -= 1000
        } else {
            G.insert(y) 
            GMax.append(y)
            GMax.sort()
            if GMax.count >= 4 { GMax.remove(at: 0) }
        }
    } else {
        if DMax.count < 3 { DScore += 1000 }
        else { DScore += Int64(DMax[0]) }
    }
}

if DScore > GScore { print("소수의 신 갓대웅") }
else if DScore < GScore { print("소수 마스터 갓규성")}
else { print("우열을 가릴 수 없음") }