const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")

const PriorityQueue = {
    arr: [],
    arrSize: 0,
    push(x, compare) {
        let idx = ++this.arrSize
        while (idx !== 1 && compare(this.arr[idx>>1], x)) {
            this.arr[idx] = this.arr[idx>>1]
            idx >>= 1
        }
        this.arr[idx] = x
    },
    pop(compare) {
        if (this.arrSize === 0) return 0
        let result = this.arr[1]
        let [idx, val] = [1, this.arr[this.arrSize--]]
        while (idx<<1 <= this.arrSize) {
            let child = idx<<1
            if (child < this.arrSize && compare(this.arr[child], this.arr[child+1]))
                child++
            if (compare(this.arr[child], val)) break
            this.arr[idx] = this.arr[child]
            idx = child
        }
        this.arr[idx] = val
        return result
    }
}

function compare(A, B) {
    return A > B
}

let answer = ""
for (let i = 1; i <= Number(input[0]); i++) {
    let query = Number(input[i])
    switch (query) {
        case 0:
            answer += PriorityQueue.pop(compare) + "\n"
            break
        default:
            PriorityQueue.push(query, compare)
            break
    }
}
console.log(answer)