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
27531번 치즈

치즈가 두 묶음씩 묶여있다. 각 묶음의 가격은 w이다.
각 치즈는 1부터 N까지의 종류가 있으며, 총 2N개의 치즈가 있다.
각각 어떻게 묶였는지와 그 가격이 주어진다.
모든 치즈를 하나 이상 갖기 위해 필요한 최솟값을 구하시오.

와
드디어풀었어
순열사이클분할은 이런느낌이구나..

사실 순열사이클분할은 둘째치고,
아니 여기서 dp를 어캐써? 하면서 당황한 문제.
근데 하다보니 됐네..

first[i] = 0이면 안이은거, 1이면 이은거. second도 같은데,
first와 second의 차이는 second는 이전에 이미 마지막줄을 이어둔거.
그래서 최종 first[0]는 마지막 요소가 이어지지 않았지만,
최종 second[0]는 마지막 요소가 이어진 요소입니다.
이런느낌.. 이런식 구조 꽤 자주나오는데 이렇게하는구나..
MST가아니었어.. 저번에 이런거 코포에서 못풀었는데..
*/
let N = ini()
var G = [[(Int, Int)]](repeating: [], count: N + 1)

for _ in 0..<N {
    let (u, v, w) = hni()
    G[u].append((v, w))
    G[v].append((u, w))
}

var vis = [Bool](repeating: false, count: N + 1)
var ret = 0

for i in 1...N {
    if vis[i] { continue }
    var cur = i
    var prv = 0
    var weight = [Int]()
    
    while cur != -1 {
        var nxtN = -1
        for (n, w) in G[cur] {
            if !vis[n] && nxtN == -1 {
                nxtN = n
                weight.append(w)
            } else if cur == i {
                prv = w
            }
        }
        vis[cur] = true
        cur = nxtN
    }
    
    var first = [0, Int.max]
    var second = [Int.max, prv]
    
    for w in weight {
        first = [first[1], min(first[0], first[1]) + w]
        second = [second[1], min(second[0], second[1]) + w]
    }
    
    ret += min(first[1], second[0], second[1])
}

print(ret)
