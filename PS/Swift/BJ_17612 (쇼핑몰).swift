/*
17612번 쇼핑몰

N명의 고객과 1~K로 번호가 붙여진 계산대가 있다.
N명의 고객은 각각 자신의 회원번호 i와 구매한 물품의 개수 j가 있다.
각 고객은 계산대에서 정확히j분의 시간을 소비한다.
고객은 번호가 더 낮은 계산대부터 천천히 채워나가며,
한 명 이상 고객의 계산이 끝났다면 번호가 더 높은 계산대에서 계산한 사람이 먼저 나간다.
n번째로 나가는 고객의 회원번호를 i라고 할 때, n*i를 모두 합한 값을 구하여라.

옛날옛날 고등학교 시험기간에 학원에 앉아서 공부 안하고 이거를 풀려고 했으나,
sort와 min과 index를 미친듯이 써대고 엄청난 수의 시간초과를 맞은거로 기억하는 문제.
갑자기 생각나서 가져와봤습니다.

다시보니까 그냥 우선순위 큐 문제인데,
추가시간이 없길래 추가시간 안받는 녀석들을 찾아보니,
Swift, C++, Rust..
근데 나머지 둘을 다 어제 썼네?
스위프트는 근데 우선순위큐 직접 구현해야하는데?
아.... 딴거쓸까..
하다가 그냥 결국 구현하면서 풀었습니다.

id, 산 물건 수, 몇 번 계산대에 갔는지를 적어두고,
물건수는 오름차순, 그게 같으면 계산대는 내림차순으로 우선순위큐를 만들어주고,
빈 계산대를 관리하는 우선순위큐도 하나 만들어서 풀면 됩니다.
간단하네요.
*/
struct user {
    let id: Int64
    let buy: Int64
    let Kth: Int64
}

struct PQ<T> {
    var arr: [T]
    var cmp: (T, T) -> Bool
    init(trashValue: T, cmpFunc: @escaping (T, T) -> Bool) {
        self.arr = [trashValue]
        self.cmp = cmpFunc
    }
    mutating func push(item: T) {
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

func compare(i: user, j: user) -> Bool {
    return i.buy != j.buy ? i.buy < j.buy : i.Kth > j.Kth
}


var pq = PQ<user>(trashValue: user(id: -1, buy: -1, Kth: -1),
                  cmpFunc: { $0.buy != $1.buy ? $0.buy < $1.buy : $0.Kth > $1.Kth})
var emptyPQ = PQ<Int64>(trashValue: 0, cmpFunc: { $0 < $1 })
let I = readLine()!.split(separator: " ").map {Int64($0)!}
let N = I[0], K = I[1]
for i in 1...K {
    emptyPQ.push(item: i)
}
var cnt = 0, accTime = Int64(0)
var res = Int64(0), seq = Int64(1)
while cnt < N || pq.getSize() != 0 {
    while cnt < N && pq.getSize() < K {
        let I = readLine()!.split(separator: " ").map {Int64($0)!}
        let id = I[0], buy = I[1]
        pq.push(item: user(id: id, buy: buy+accTime, Kth: emptyPQ.pop()))
        cnt += 1
    }
    accTime += pq.peek().buy - accTime
    while pq.getSize() > 0 && pq.peek().buy <= accTime {
        let cur = pq.pop()
        res += cur.id * seq
        accTime += cur.buy - accTime
        emptyPQ.push(item: cur.Kth)
        seq += 1
    }
}
print(res)
