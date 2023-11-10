const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => BigInt(input[__idx++])
let ins = () => input[__idx++].split(" ").map(BigInt)
/*
23022번 숙제

N개의 숙제가 나오는 시간 T[i]와 단위시간 1이 지날 때 마다 받는 벌점 P[i]가 있다.
숙제를 모두 끝낼 때 까지 받아야하는 최소 벌점을 구해보자.

그냥 우선순위큐 그리디구나 바로 알았는데 문젠 우선순위큐를 너무 오래 구현해봤다는거..
.. 조금 현타와서 짧게써야지..
*/
class Heap {
    arr = [null]
    size = 0
    cmp = (i, j) => i < j

    constructor(cmp = null) {
        if (cmp) this.cmp = cmp
    }

    push = (x) => {
        let idx = ++this.size
        while (idx != 1 && this.cmp(x, this.arr[idx >> 1])) {
            this.arr[idx] = this.arr[idx >> 1]
            idx >>= 1
        }
        this.arr[idx] = x;
    }

    pop = () => {
        if (this.empty()) return null
        let ret = this.arr[1]
        let idx = 1
        let item = this.arr[this.size--]
        while (idx * 2 <= this.size) {
            let child = idx * 2
            if (child < this.size && this.cmp(this.arr[child+1], this.arr[child])) child++
            if (this.cmp(item, this.arr[child])) break
            this.arr[idx] = this.arr[child]
            idx = child
        }
        this.arr[idx] = item
        return ret
    }

    peek = () => {
        return this.empty() ? null : this.arr[1]
    }

    empty = () => {
        return this.size === 0
    }
}

let T = ini()
for (let _ = 0; _ < T; _++) {
    let h = new Heap((i, j) => i[0] > j[0])
    let [N, curT] = ins()
    let [time, punish] = [ins(), ins()]
    let A = []
    for (let i = 0; i < N; i++)
        A[i] = [punish[i], time[i]]
    A.sort((i, j) => i[1] < j[1] ? -1 : 1)
    let [pf, ret] = [0n, 0n]
    let idx = 0
    for (; idx < N && A[idx][1] <= curT; idx++) {
        h.push(A[idx])
        pf += A[idx][0]
        ret += (curT-A[idx][1])*A[idx][0]
    }
    while (idx < N || !h.empty()) {
        while (idx < N && A[idx][1] <= curT) {
            let [p, t] = A[idx++]
            h.push([p, t])
            pf += p
        }
        if (h.empty()) {
            curT = A[idx][1]
            continue
        }
        let [p, t] = h.pop()
        pf -= p
        ret += pf
        curT++
    }
    let retS = String(ret)
    console.log(retS.slice(0, retS.length))
}
