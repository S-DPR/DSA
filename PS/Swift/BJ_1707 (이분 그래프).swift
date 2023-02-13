/*
1707번 이분 그래프

주어진 그래프가 이분 그래프인지 판별해보자.

이분그래프란?
그래프의 노드를 두 개의 색으로 칠한다 했을 때 [모든 간선이 서로 다른 두 개의 색만을 잇는 그래프]
그래프를 두 개의 색으로 번갈아서 모두 칠할 수 있다. 이는 이분그래프인가? -> 네.
[어떤 한 점에서 시작했을 때 그래프를 모두 잇지 못한다. 다만, 색을 굳이 칠하면 조건을 만족한다. 이는 이분그래프인가?] -> 네.

이분그래프가 뭔지 정확히 몰라서 많이 틀린문제.
근데 옛날에 푼 흔적을 보면 틀릴만한거같기도 하고..

틀렸던 주 이유는 그래프를 노드 1에서만 시작했기 때문입니다.
visit을 돌면서 갱신이 안된 경우에 모두 돌아주고, 그걸 result에 저장하니 되더라고요.
오랜만에 그래프문제였네요..
*/
func dfs(x: Int, cur:Int, G: [[Int]], visit: inout [Int]) -> Int {
    if visit[x] != -1 {
        return 0
    }
    var result = 1
    visit[x] = cur
    for i in G[x] {
        if visit[i] == -1 {
            result &= dfs(x: i, cur: cur^1, G: G, visit: &visit)
        } else if visit[i] != cur^1 {
            result = 0
            break
        }
    }
    return result
}

let T = Int(readLine()!)!
for _ in 0..<T {
    let I = readLine()!.split(separator: " ").map {Int($0)!}
    let V = I[0]+1, E = I[1]
    var G = [[Int]](repeating:[Int](), count:V)
    for _ in 0..<E {
        let I = readLine()!.split(separator: " ").map {Int($0)!}
        G[I[0]].append(I[1])
        G[I[1]].append(I[0])
    }
    var visit = [Int](repeating: -1, count:V)
    var result = 1
    for i in 1..<V {
        if visit[i] == -1 {
            result &= dfs(x: i, cur: 0, G: G, visit: &visit)
        }
    }
    print(result == 1 ? "YES" : "NO")
}