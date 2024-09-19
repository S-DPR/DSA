let spi = { () -> [Int] in return readLine()!.split(separator: " ").map {Int($0)!} }
let spl = { () -> [Int64] in return readLine()!.split(separator: " ").map {Int64($0)!} }
let ini = { () -> Int in return Int(readLine()!)! }
let lni = { () -> Int64 in return Int64(readLine()!)! }
let tni = { () -> (Int, Int) in let I = spi(); return (I[0], I[1]) }
let tnl = { () -> (Int64, Int64) in let I = spl(); return (I[0], I[1]) }
let hni = { () -> (Int, Int, Int) in let I = spi(); return (I[0], I[1], I[2]) }
let hnl = { () -> (Int64, Int64, Int64) in let I = spl(); return (I[0], I[1], I[2]) }
let inf = 1<<28
let lnf = Int64(1)<<60
/*
31913번 돈 복사

한 번의 한 개의 물건만 가질 수 있는 게임이 있다.
또, 아래와 같은 물물교환이 가능하다.
무한번 반복해 무한한 돈을 얻을 수 있는 최소 C를 구해보자.
그런 값이 없다면 INF를 출력하자.
1. i번 물건을 c원에 산다.
2. i원 물건을 c원에 판다.
3. i번 물건과 j번 물건을 바꾼다.
4. i번 물건과 c원을 주어 j번 물건을 얻는다.
5. i번 물건을 주고 j번 물건과 c원을 얻는다.

와 이거 어캐풀지 하다가,
현재 갖고있는 물건 번호 = 정점으로 보고,
산다판다..같은 개념이 있으니 + - 간선 개념으로 보아 벨만포드.
그리고 최솟값이니 매개변수.
이런 사고방식으로 접근했습니다.

문제는 1<<31같은게 js에서는 오버플로나는지 몰랐다는점,
그리고 벨만포드를 몰랐다는 점 ㅋㅋ
벨만포드야 뭐 옛날에 푼 문제 하나 있으니까 거기서 코드 가져왔는데,
js는 뭐..
tle까지 터져서 스위프트로 바꿔줘! 해서 가져왔습니다.
근데 의외로 스위프트도 괜찮은 문법 지원하네요? 흠..
*/
let (N, M) = tni()
var G = [(Int, Int, Int)]()
var add: [Int: (Int, Int, Int) -> Void] = [
    1: { (c, i, _) in G.append((0, i, -c)) },
    2: { (i, c, _) in G.append((i, 0, c)) },
    3: { (u, v, _) in G.append((u, v, 0)) },
    4: { (u, c, v) in G.append((u, v, -c)) },
    5: { (u, v, c) in G.append((u, v, c)) }
]
for _ in 0..<M {
    let Q = spi()
    if let addFunc = add[Q[0]] {
        addFunc(Q[1], Q[2], Q.count > 3 ? Q[3] : 0)
    }
}

func bel(_ initValue: Int) -> Bool {
    var dist = [Int](repeating: -inf, count: N + 1)
    dist[0] = initValue
    
    for _ in 0..<N {
        for (u, v, w) in G {
            if dist[u] >= -w && dist[u] + w > dist[v] {
                dist[v] = dist[u] + w
            }
        }
    }
    
    for (u, v, w) in G {
        if dist[u] >= -w && dist[u] + w > dist[v] {
            return true
        }
    }
    
    return false
}

var (lo, hi) = (0, inf)
while (lo < hi) {
    let mid = (lo + hi) >> 1
    if (bel(mid)) {
        hi = mid
    } else {
        lo = mid + 1
    }
}
print(hi == inf ? "INF" : hi)

