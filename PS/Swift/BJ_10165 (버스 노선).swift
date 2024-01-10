let nxtI = { () -> Int in
    return Int(readLine()!)!
}
let nxtL = { () -> Int64 in
    return Int64(readLine()!)!
}
let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
10165번 버스 노선

원형 선로에서 [l, r]을 왕복하는 버스가 L개 있다.
어떤 한 버스가 다른 버스 경로에 완벽하게 포함될경우, 그 버스를 운행하지 않으려 한다.
이 때, 사라지지 않는 버스의 번호를 구해보자.

아이디어는.. 진짜 빨리 구했는데..
원으로 보는 대신 선분 두개로 보자는 이야기 진짜 바로 나왔는데..
구현을..못했다.. 두시간..갈았다..

두 가지 작업을 합시다.
만약에 l < r이면, 즉 평상이라면 l+N, r+N과 l, r을 모두 검사할겁니다.
l > r이면 r에 N을 더하고 l, r만 넣을겁니다.
이제 end 하나 잡아주고 end보다 작은 경우 그 버스를 빼면 끝납니다.

진짜.. 아이디어만큼은 진짜 빨리 꺼냈는데..
*/
let N = nxtI()
let L = nxtI()
var ret = [Bool](repeating: true, count: L+1)
var A = [(l: Int, r: Int, i: Int)]()
for i in 1...L {
    let I = spi()
    var (l, r) = (I[0], I[1])
    if l < r { A.append((l+N, r+N, i)) }
    else { r += N }
    A.append((l, r, i))
}
A.sort{$0.l != $1.l ? $0.l < $1.l : $0.r > $1.r}

var end = -1
for i in A {
    if i.r <= end {
        ret[i.i] = false
        continue
    }
    end = i.r
}

var retS = ""
for i in 1...L {
    if !ret[i] { continue }
    retS += "\(i) "
}
print(retS)
