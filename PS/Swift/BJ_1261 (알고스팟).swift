/*
1261 알고스팟

0과 1로 이루어진 맵이 주어진다.
1은 벽인데, 이를 최소한으로 부셔서 좌측 최상단에서 우측 최하단으로 이동하려 한다.
1을 최소한으로 부셔서 갈 때, 그 최솟값을 구해보자.

아니 나만 느리네
이게 swift는 뭔가 다른애들이랑 다른느낌이 있어요..
뭐랄까.. ruby랑 node.js 처음 접할때보다 더 별로라 해야하나..
java는 제가 그렇게 싫어하는데 이거랑 java랑 고르라고 하면 java고를듯..
뭔가 얘는 우선순위큐 구현방법을 다시 배워야할거같네요.
배열 범위 벗어나서 정의하면 확장이 아니라 에러를 내버리니.
*/

class PriorityQueue {
    var arr: Array<Array<Int>>
    var arr_size: Int
    init() {
        self.arr = Array<Array<Int>>(repeating:[0, 0, 1000], count:1000)
        self.arr_size = 0
    }
    func push(item: Array<Int>) {
        self.arr_size += 1
        var idx = self.arr_size
        while idx != 1 && self.arr[idx/2][2] > item[2] {
            self.arr[idx] = self.arr[idx/2]
            idx /= 2
        }
        self.arr[idx] = item
    }
    func pop() -> Array<Int> {
        let result = self.arr[1]
        let item = self.arr[self.arr_size]
        var idx = 1
        self.arr_size -= 1
        while idx<<1 <= self.arr_size {
            var child = idx<<1
            if child+1 <= self.arr_size && self.arr[child][2] > self.arr[child+1][2] {
                child += 1
            }
            if self.arr[child][2] >= item[2] {
                break
            }
            self.arr[idx] = self.arr[child]
            idx = child
        }
        self.arr[idx] = item
        return result
    }
}

let I = readLine()!.split(separator: " ").map {Int($0)!}
let N = I[0], M = I[1]
var Map = Array(repeating:[Int](), count:M)
for i in 0..<M {
    Map[i] = readLine()!.map{Int(String($0))!}
}

let vector = [[-1, 0], [1, 0], [0, 1], [0, -1]]
var PQ = PriorityQueue()
PQ.push(item: [0, 0, 0])
var visit = Array<Array<Int>>(repeating:Array<Int>(repeating:1000, count:N), count:M)
visit[0][0] = 0
while PQ.arr_size > 0 {
    let current = PQ.pop()
    let x = current[0], y = current[1], brk = current[2]
    if x == N-1 && y == M-1 { continue }
    if visit[M-1][N-1] <= brk { continue }
    for i in 0..<4 {
        let dx = vector[i][0], dy = vector[i][1]
        let nx = x+dx, ny = y+dy
        if !(0 <= nx && nx < N && 0 <= ny && ny < M) { continue }
        if Map[ny][nx] == 1 {
            if visit[ny][nx] <= brk+1 { continue }
            visit[ny][nx] = brk+1
            PQ.push(item: [nx, ny, brk+1])
        } else {
            if visit[ny][nx] <= brk { continue }
            visit[ny][nx] = brk
            PQ.push(item: [nx, ny, brk])
        }
    }
}
print(visit[M-1][N-1])
