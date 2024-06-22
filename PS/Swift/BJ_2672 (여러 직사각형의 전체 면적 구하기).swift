let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
let inf = 1<<30
let lnf = Int64(1)<<60
/*
2672번 여러 직사각형의 전체 면적 구하기

직사각형의 왼쪽아래 꼭짓점 좌표와 폭, 높이가 주어진다.
2차원 좌표상에 이를 나타냈을 때, 그 면적을 구해보자.

어떻게할까.. 스위핑으로 한번에 안될까.. 하다가..
그냥 뭐.. 40000N으로 처리했습니다.
솔까 최적화 되긴할거같은데 귀찮아서 맞춘대로 둡니다.
*/
struct Info: Hashable {
    let y: Int64
    let my: Int64
    init(_ y: Int64, _ my: Int64) {
        self.y = y
        self.my = my
    }
    func get() -> (Int64, Int64) {
        return (y, my)
    }
}

let N = ini()
var S = [Info]()
var X = [(Int64, Int64, Int64, Bool, Int)]()
var V = [Bool](repeating: false, count: N)
for i in 0..<N {
    let I = readLine()!.split(separator: " ").map { Int64(Double($0)!*10) }
    let (x, y, w, h) = (I[0], I[1], I[2], I[3])
    X.append((x, y, y+h, true, i))
    X.append((x+w, y, y+h, false, i))
    S.append(Info(y, y+h))
}
X.sort { $0.0 < $1.0 }
var ret = Int64(0)
for i in 0..<X.count-1 {
    let (x, y, my, put, idx) = X[i]
    let nxtX = X[i+1].0
    var pos = [Int64](repeating: 0, count: 20001)
    V[idx] = put
    for i in 0..<N {
        if !V[i] { continue }
        let (hy, hmy) = S[i].get()
        for j in hy..<hmy {
            pos[Int(j)] = 1
        }
    }
    ret += pos.reduce(0, +) * (nxtX-x)
}
if ret%100 == 0 {
    print(ret/100)
} else {
    print(Double(ret)/100.0)
}
