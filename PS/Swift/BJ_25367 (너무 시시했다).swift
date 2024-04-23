let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
/*
25367번 너무 시시했다

A+B = X, A^B = Y일 때 가능한 음이 아닌 정수쌍 (X, Y)의 개수를 구해보자.

와..
개신기하다..
답지봐서 정말 다행이야...

우선.. 이거 풀려면 사전지식
a+b = a^b+2(a&b)
이걸 열심히 정리하면..
X = Y+2(a&b)
(X-Y)/2 = a&b가 되니까,
X-Y가 짝수여야 함은 자명합니다. 소수 나오면 a&b가 커버를 못쳐요.
물론 음의 정수가 아니어야하고요.

그럼 이제 (X-Y)/2를 D라고 둡시다.
D = a&b로 둡시다. 이제 각 비트를 본다는 생각을 가져야한대요.
Y가 갑자기 아래서 나오는데, 그 이유는 Y = a^b라고 문제에서 줬기때문.

i번째 비트를 보았을 때,
D&1 == 0 && Y&1 == 0 : 둘 다 해당 비트가 꺼져있어야하니 *1
D&1 == 0 && Y&1 != 0 : a나 b에서 해당 비트가 하나 켜져있어도 되니 *2
D&1 != 0 && Y&1 == 0 : 둘 다 해당 비트가 켜져있어야하니 *1
D&1 != 0 && Y&1 != 0 : A&B가 0이 아니고 A^B도 0이 아닌 경우는 없음. *0
..라고 하네요. 하아..

골드2 애드혹은 이런맛이구나.. 어우 써.. 퉤..
*/
let Q = ini()
for _ in 0..<Q {
    let (x, y) = tnl()
    var ret: Int64 = 1
    if (x-y < 0 || (x-y)&1 == 1) {
        ret = 0
    }
    let d = (x-y) >> 1
    for i in 0..<63 {
        let bit = Int64(1) << i
        if (d&bit == 0) {
            if (y&bit != 0) {
                ret *= 2
            }
        }
        else {
            if (y&bit != 0) {
                ret = 0
            }
        }
    }
    print(ret)
}
