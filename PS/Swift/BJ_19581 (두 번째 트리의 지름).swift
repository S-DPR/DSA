/*
19581번 두 번째 트리의 지름

가장 긴 지름을 제외하고, 두 번째 트리의 지름을 구해보자.

골드2 백트래킹에 멘탈 깨지고 가져온 문제.
답은 저번에 본적이 있어서 풀긴했는데..
나중에 증명은 다시풀어보려 합니다.
*/

// 입력 함수들
func readInt() -> Int {
    return Int(readLine()!)!
}

func readInts() -> [Int] {
    return readLine()!.split(separator: " ").map { Int(String($0))! }
}

// 메인 실행 함수
func main() {
    let N = readInt()
    var G: [[(Int, Int)]] = Array(repeating: [], count: N + 1)
    
    for _ in 1..<N {
        let inputs = readInts()
        let (u, v, w) = (inputs[0], inputs[1], inputs[2])
        G[u].append((v, w))
        G[v].append((u, w))
    }


    func dfs(_ x: Int, _ vis: inout [Bool], _ depth: Int) -> (Int, Int) {
        vis[x] = true
        var retN = x, retDepth = depth
        for i in G[x] {
            let (nxtN, nxtW) = i
            if !vis[nxtN] {
                let (nxtRetN, nxtDepth) = dfs(nxtN, &vis, depth + nxtW)
                if retDepth < nxtDepth {
                    retDepth = nxtDepth
                    retN = nxtRetN
                }
            }
        }
        return (retN, retDepth)
    }
    
    var vis = [Bool](repeating: false, count: N + 1)
    let (farthest1, _) = dfs(1, &vis, 0)
    vis = [Bool](repeating: false, count: N + 1)
    let (farthest2, _) = dfs(farthest1, &vis, 0)
    
    var mx = 0
    for k in [farthest1, farthest2] {
        vis = [Bool](repeating: false, count: N + 1)
        vis[k] = true
        var VV = vis
        for i in 1...N where i != k {
            let (f, _) = dfs(i, &vis, 0)
            let (_, dist) = dfs(f, &VV, 0)
            mx = max(mx, dist)
            break
        }
    }
    
    print(mx)
}

main()
