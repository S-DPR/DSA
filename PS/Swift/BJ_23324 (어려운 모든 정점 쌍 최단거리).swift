/*
23324번 어려운 모든 정점 쌍 최단거리

노드가 N개고 간선이 M개인 그래프가 주어진다.
여기서 K번째로 입력되는 간선의 가중치는 1, 나머지는 0이다.
모든 정점 i에서 다른 모든 정점 j로 가는 길이 존재하고,
자기 자신으로 가는 간선 및 두 개 이상의 간선이 존재하는 노드 쌍도 없을 때,
모든 쌍 (i, j)에 대하여 최단거리를 구해보자. 그리고 그 최단거리를 모두 합하자.
나오는 값을 출력하시오. (1 <= i, j <= N)

이야 내가 이 문제 의도를 바로 간파하고 풀어냈다고??
이건 기적이야 기적

유니온파인드 혹은 DFS/BFS를 이용해 각 집합의 크기를 센 뒤 곱하면 되는 문제입니다.
저는 유니온파인드를 썼습니다.
이야, 객관적으로 봐도 좋은 문제네요 이거.
*/
func union(U: inout Array<Int>, x: Int, y: Int) {
    let xP = find(U: &U, x: x)
    let yP = find(U: &U, x: y)
    U[yP] = xP
}

func find(U: inout Array<Int>, x: Int)->Int {
    if U[x] != x {
        U[x] = find(U: &U, x: U[x])
    }
    return U[x]
}

let I = readLine()!.split(separator: " ").map {Int($0)!}
let N = I[0], M = I[1], K = I[2]
var U = Array<Int>(0...N)
for i in 1...M {
    let I = readLine()!.split(separator: " ").map {Int($0)!}
    if i == K {
        continue
    }
    let x = I[0], y = I[1]
    union(U: &U, x: x, y: y)
}
var firstS = 0, secondS = 0
var firstScnt = 0, secondScnt = 0
for i in 1...N {
    let V = find(U: &U, x: i)
    if firstS == 0 {
        firstS = V
    }
    else if secondS == 0 && firstS != U[i] {
        secondS = V
    }
    if firstS == V {
        firstScnt += 1
    }
    if secondS == V {
        secondScnt += 1
    }
}
let result = Int64(firstScnt)*Int64(secondScnt)
print(result)