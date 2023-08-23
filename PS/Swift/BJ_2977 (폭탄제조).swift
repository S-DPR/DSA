/*
2977번 폭탄제조

폭탄을 만드는 N개 부품의 정보가 주어진다.
x y s sp b bp
x : 필요한 부품의 개수
y : 현재 갖고있는 부품의 개수
s : 작은 패키지를 사면 얻는 부품의 개수
sp : 작은 패키지의 가격
b : 큰 패키지를 사면 얻는 부품의 개수
bp : 큰 패키지의 가격
현재 K원이 있다. 몇 개의 폭탄을 만들 수 있을까?

매개변수탐색인걸 옛날에 봐버려서 너무 쉽게 푼 문제
솔직히 문제자체는 정말 재밌다고 생각하는데..

대충 매개변수탐색 굴리면 됩니다.
check함수에서는 작은패키지를 0개부터 최대치까지 구매해보는걸 다 돌려보고,
그중 최솟값을 더해주는걸 N번하고요.
그걸 다 더한 값이 K보다 크면 true, 아니면 false.

잘푼거같기도하고..
*/
func getInput() -> [Int] {
    return readLine()!.split(separator: " ").map {Int($0)!}
}

let I = getInput()
let N = I[0], K = I[1]
var items = [[Int]]()
for _ in 0..<N {
    items.append(getInput())
}

func check(_ x: Int) -> Bool {
    var needMoney = 0
    for i in items {
        let needCount = i[0]*x-i[1]
        var ret = 1_000_000_000
        var smallCount = 0
        while needCount > i[2]*smallCount {
            let bigCount = (needCount-i[2]*smallCount+i[4]-1)/i[4]
            ret = min(ret, smallCount*i[3] + bigCount*i[5])
            smallCount += 1
        }
        ret = min(ret, smallCount*i[3])
        needMoney += ret
    }
    return needMoney > K
}

var lo = 0, hi = 200000
while lo < hi {
    let mid = (lo + hi) >> 1
    if check(mid) {
        hi = mid
    } else {
        lo = mid + 1
    }
}
print(hi-1)
