let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
25760번 귀경길 교통상황을 알려드립니다

1번이 루트인 트리에 특별한 정점이 몇 개 있다.
특별한 정점 모두를 위로 끌어올리려한다. 단, 한 노드에는 하나의 특별한 정점만 있을 수 있다.
당연히 트리의 간선을 타면서 올라와야한다. 이 때, 몇 초만에 모든 정점을 끌어올릴 수 있을까?

와 이게 골드2?
이건 진짜 탈 골드2인데

풀다가 안돼서 태그 봤더니 트리dp가 아니라 BFS인거에서 당황 1
그래도 모르겠어서 답지보니까 끌어올리는게아니라 정점을 배치하는거로 보라해서 당황 2
와..
진짜 그리디는 왜 그리디인지 아는거부터 일이다..
*/
let N = Int(readLine()!)!
var G = [[Int]](repeating: [Int](), count: N+1)
var D = [Int](repeating: 0, count: N+1)
for _ in 1..<N {
    let I = spi()
    let (u, v) = (I[0], I[1])
    G[u].append(v)
    G[v].append(u)
}

D[1] = 1
var (Q, lQ) = ([1], [Int]())
while Q.count+lQ.count > 0 {
    if Q.count == 0 {
        Q = lQ.reversed()
        lQ = []
    }
    let curN = Q.removeLast()
    G[curN].forEach{i in
        if D[i] == 0 {
            D[i] = D[curN]+1
            lQ.append(i)
        }
    }
}

let A = spi()
let ret = (0..<N).filter{A[$0] == 1}.sorted{D[$0+1] > D[$1+1]}
print((0..<ret.count).map{D[ret[$0]+1]+$0}.max()!)
