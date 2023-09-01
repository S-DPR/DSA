func spi() -> Array<Int> {
    return readLine()!.split(separator: " ").map{Int($0)!}
}
/*
1486번 등산

2차원 맵이 있다. 각 칸에는 높이가 있는데, 높이차가 D보다 크면 이동하지 못한다.
이동하는데에는 이동할곳이 현재 있는곳보다 높이가 낮거나 같으면 1의 시간이,
아니면 ((다음 높이) - (현재 높이))^2만큼의 시간이 필요하다.
이 때, (0, 0)에서 이동을 시작해 최대 높이를 찍고 다시 (0, 0)으로 돌아오려 한다.
왔다갔다 했을 때 시간은 T보다 커선 안된다.
가능한 최대 높이를 구해보자.

2차원 다익스트라.
억지로 1차원으로 펴고 했더니 1800ms더니 2차원으로하니 800ms네..
다르게 최적화 더 하면 200ms 이내로 되는거같긴한데, 그냥 넘어갑시다.

그나저나, 다익스트라 2차원은 처음해봤네요..
문자열 처리 능력만큼은 다른 어떤 언어와 비교해도 조악함의 최악을 달리는 Swift라 맵을 Int로 바꾸는데 시간이 오래 걸렸습니다..
*/
struct heap<T> {
    var arr: [T?]
    let cmp: (T, T) -> Bool
    init(_ cmp: @escaping (T, T) -> Bool) {
        self.arr = [nil]
        self.cmp = cmp
    }
    mutating func push(_ item: T) {
        var idx = arr.count
        arr.append(item)
        while idx > 1 && cmp(arr[idx]!, item) {
            self.arr.swapAt(idx, idx>>1)
            idx >>= 1
        }
    }
    mutating func pop() -> T? {
        if arr.count == 1 { return nil }
        let ret = arr[1]
        var idx = 1
        let item = arr.removeLast()!
        while idx<<1 < arr.count {
            var child = idx << 1
            if child+1 < arr.count && cmp(arr[child+1]!, arr[child]!) {
                child += 1
            }
            if cmp(item, arr[child]!) { break }
            arr[idx] = arr[child]
            idx = child
        }
        if idx < arr.count {
            arr[idx] = item
        }
        return ret
    }
    func isEmpty() -> Bool {
        return arr.count == 1
    }
}

let dx = [1, -1, 0, 0]
let dy = [0, 0, 1, -1]
let I = spi()
let (N, M, D, T) = (I[0], I[1], I[2] ,I[3])
var G = [[[Int]]](repeating: [[Int]](), count: N*M)
var map = [[Int]]()
for _ in 0..<N {
    let item = Array(readLine()!)
    map.append(item.map{
        if $0.asciiValue! <= Character("Z").asciiValue! {
            return Int($0.asciiValue! - Character("A").asciiValue!)
        }
        return Int($0.asciiValue! - Character("a").asciiValue! + 26)
    })
}

func dij(_ sx: Int, _ sy: Int) -> [[Int]] {
    var dist = [[Int]](repeating: [Int](repeating: 1<<29, count: M), count: N)
    var pq = heap<[Int]>({$0[0] < $1[0]})
    dist[sy][sx] = 0
    pq.push([0, sx, sy])
    while !pq.isEmpty() {
        let cur = pq.pop()!
        let curX = cur[1], curY = cur[2], curW = cur[0]
        if curW > dist[curY][curX] { continue }
        for i in 0..<4 {
            let nxtX = curX+dx[i], nxtY = curY+dy[i]
            if !(0 <= nxtX && nxtX < M) { continue }
            if !(0 <= nxtY && nxtY < N) { continue }
            let heightDiff = map[nxtY][nxtX] - map[curY][curX]
            var nxtW = 1
            if heightDiff > 0 {
                nxtW = heightDiff * heightDiff
            }
            if abs(heightDiff) > D { continue }
            if curW+nxtW < dist[nxtY][nxtX] {
                dist[nxtY][nxtX] = curW+nxtW
                pq.push([dist[nxtY][nxtX], nxtX, nxtY])
            }
        }
    }
    return dist
}

var ret = map[0][0]
let dist_f = dij(0, 0)
for i in 0..<N {
    for j in 0..<M {
        let dist_s = dij(j, i)
        if dist_f[i][j] + dist_s[0][0] <= T {
            ret = max(ret, map[i][j])
        }
    }
}
print(ret)
