/*
17422번 지폐가 넘쳐흘러

포화이진트리가 있다. 트리의 각 노드에는 가중치가 있다.
노드 중 하나를 루트노드로 만들어, 가중치가 중력의 영향을 받도록 하려 한다.
즉, 해당 노드와 아래로 연결된 간선중 하나로 가중치를 몰아줄 수 있다는 뜻이다.
이후, Q개의 쿼리가 주어진다. x k의 형태이며, x번째 노드의 가중치를 k로 바꾼다는 뜻이다.
전부 다 몰아넣었을 때, 노드가 가질 수 있는 최대 가중치를 첫 트리와 각각의 쿼리마다 구해보자. Q쿼리의 결과는 누적된다.

모르면 시간 박아야지..
어쩔수없지..
하고 푼 문제. 진짜 속쓰려 죽는줄알았다..

일단 보다보면 결국 리프노드에서 시작해서 리프노드로 끝난다는 점을 알 수 있습니다.
이거는 파악을 빠르게 했는데, 저는 루트노드 기준 왼쪽에 있는 노드에서 시작해 우측에 있는 노드에서 끝나야 최대라고 생각했는데,
또 이게 아니더라고요. 우측에 가중치가 높은게 몰려있으면 그냥 그 둘 고르는게 최고입니다.

그래서 dp를 하나 만들고 힙을 하나 만듭니다. dp[i]는 i번째에 들어올 수 있는 최대가중치입니다. 단, 자식노드중 큰 것과 자신의 값만 합친 값입니다.
자식노드 둘을 다 합치고 자신도 합친 값은 더이상 위로 올라갈 수 없습니다. 이 값은 힙에 넣어서 보관합니다.

ver이라는 배열을 만듭니다. i번째 노드가 몇 번 업데이트가 되었나를 나타냅니다. 힙에서 꺼낼 때 몇 번째 노드인지 나오는데, 해당 아이템에 적혀있는 버전이 ver[i]보다 낮다면 폐기처분합니다.

이런짓거리 해서 만들었는데, 또 보니까 힙 안쓰고 dp만으로 max값을 O(1)로 찾는 방법이 있는거같더라고요. 딴사람들 코드는 간단하던데, 공부좀 해야겠습니다..
*/
struct item {
    let val: Int64
    let version: Int
    let idx: Int
}

struct PQ<T> {
    var arr: [T]
    var cmp: (T, T) -> Bool
    init(_ trashValue: T, _ cmpFunc: @escaping (T, T) -> Bool) {
        self.arr = [trashValue]
        self.cmp = cmpFunc
    }
    mutating func push(_ item: T) {
        self.arr.append(item)
        var idx = self.getSize()
        while idx != 1 && cmp(item, self.arr[idx>>1]) {
            self.arr[idx] = self.arr[idx>>1]
            idx >>= 1
        }
        self.arr[idx] = item
    }
    mutating func pop() -> T {
        var arrSize = self.getSize()
        let result = self.arr[1]
        let item = self.arr.removeLast()
        var idx = 1
        arrSize -= 1
        while idx<<1 <= arrSize {
            var child = idx<<1
            child += child < arrSize && cmp(self.arr[child+1], self.arr[child]) ? 1 : 0
            if cmp(item, self.arr[child]) { break }
            self.arr[idx] = self.arr[child]
            idx = child
        }
        if arrSize != 0 { self.arr[idx] = item }
        return result
    }
    func getSize() -> Int {
        return arr.count-1
    }
    func peek() -> T {
        return arr[1]
    }
}

func cmp(i: item, j: item) -> Bool {
    return i.val > j.val
}

var pq = PQ<item>(item(val: -1, version: -1, idx:-1), cmp)
let N = Int(readLine()!)!
var arr = readLine()!.split(separator: " ").map {Int64($0)!}
arr.insert(0, at: 0)
let Q = Int(readLine()!)!

var ver = [Int](repeating:0, count: N+1)
var dp = arr.map { $0 }
for i in stride(from: 2, to: (N/2)+1, by: 1).reversed() {
    dp[i] += max(dp[i<<1], dp[i<<1|1])
    pq.push(item(val: dp[i<<1]+dp[i<<1|1]+arr[i], version: 0, idx: i))
}
pq.push(item(val: (N == 1 ? arr[1] : arr[1]+dp[2]+dp[3]), version: 0, idx: 1))

for i in 0...Q {
    if i > 0 {
        let I = readLine()!.split(separator: " ").map {Int($0)!}
        var (idx, w) = (I[0], Int64(I[1]))
        dp[idx] += w-arr[idx]
        arr[idx] = w
        ver[idx] += 1
        if idx<<1 <= N {
            pq.push(item(val: dp[idx<<1]+dp[idx<<1|1]+arr[idx], version: ver[idx], idx: idx))
        }
        idx >>= 1
        while idx > 1 {
            dp[idx] = max(dp[idx<<1], dp[idx<<1|1]) + arr[idx]
            ver[idx] += 1
            pq.push(item(val: dp[idx<<1]+dp[idx<<1|1]+arr[idx], version: ver[idx], idx: idx))
            idx >>= 1
        }
        ver[1] += 1
        pq.push(item(val: (N == 1 ? arr[1] : arr[1]+dp[2]+dp[3]), version: ver[1], idx: 1))
    }
    while pq.getSize() > 0 && pq.peek().version < ver[pq.peek().idx] {
        pq.pop()
    }
    print(pq.peek().val)
}