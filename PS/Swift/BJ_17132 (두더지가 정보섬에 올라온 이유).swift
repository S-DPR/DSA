/*
17132번 두더지가 정보섬에 올라온 이유

트리가 주어진다. 그리고 각 간선에 가중치가 주어진다.
두 정점 (i, j)를 잡자. 이 두 정점을 이동할 때 지나치는 가중치가 가장 작은 간선의 가중치만큼 만족도를 얻는다고 한다.
i < j를 만족하는 모든 (i, j) 쌍에 대해 얻는 만족도의 합을 구해보자.

보자마자 아하, 분리집합.
트리 히스토그램을 풀 떄 쓰였던 놀라운 방법을 그대로 응용하면 됩니다.
그래서 본지 20분정도만에 풀었어요.
오늘도 빅데이터님 1승 적립합니다.
*/
let spl = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}

let N = Int(readLine()!)!
var G = [[Int]]()
var U = Array(0...N)
var S = [Int](repeating: 1, count: N+1)
for i in 1..<N {
    let I = spl()
    G.append(I)
}
G.sort{$0[2] > $1[2]}

func union(_ x: Int, _ y: Int) {
    let xp = find(x)
    let yp = find(y)
    S[xp] += S[yp]
    U[yp] = U[xp]
}

func find(_ x: Int) -> Int {
    if U[x] != x {
        U[x] = find(U[x])
    }
    return U[x]
}

var ret : Int64 = 0
for i in G {
    let (u, v, w) = (i[0], i[1], i[2])
    let size = S[find(u)]*S[find(v)]
    union(u, v)
    ret += Int64(size*w)
}
print(ret)