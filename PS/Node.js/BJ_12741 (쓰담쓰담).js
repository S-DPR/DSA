const filePath = process.platform === "linux" ? "/dev/stdin" : "./io/input.txt"
let input = require("fs").readFileSync(filePath).toString().trim().split("\n")
let __idx = 0
let ini = () => Number(input[__idx++])
let ins = () => input[__idx++].split(" ").map(Number)
/*
12741번 쓰담쓰담

두 종류의 쿼리가 주어진다. 적절히 처리해보자.
1 u v : u부터 v까지 오름차순 정렬이 되어있나 확인한다.
2 u v : u와 v번째 인덱스 값을 바꾼다.

보고 곧 방법은 떠올랐는데, 세그트리 하도 안쓰니까 까먹은부분에서 발목이 잡힌 부분..
결론 : 1-indexed 비재귀 세그트리 쓸때는 l++, r--가 맞다!
괜히 l++, --r이랑 헷갈렸네요.

대충 i번째 인덱스에 대해, i-1번째보다 i가 더 크면 0을, 아니면 1을 넣어줍니다.
그리고 1 u v가 오면 u+1~v의 합을 구해서 0이면 오름차순이고, 아니면 아닌거에요.
생각보다 여기까지 도달하는데 시간이걸렸네요. 아쉬워라..
*/
let update = (idx, val) => {
    seg[idx += N] = val
    while (idx > 1) {
        seg[idx>>1] = seg[idx]+seg[idx^1]
        idx >>= 1
    }
}

let query = (l, r) => {
    [l, r] = [l+N, r+N]
    let ret = 0
    while (l <= r) {
        if ((l&1) == 1) {
            ret += seg[l++]
        }
        if ((~r&1) == 1) {
            ret += seg[r--]
        }
        [l, r] = [l>>1, r>>1]
    }
    return ret
}

let [N, Q] = ins()
let A = [1<<30, ...ins()]
let seg = Array(N*4).fill(0)
for (let i = 1; i <= N; i++) {
    let isLarge = A[i-1] <= A[i] ? 0 : 1
    update(i, isLarge)
}
let ret = ""
for (let i = 0; i < Q; i++) {
    let [q, u, v] = ins()
    if (q == 1) {
        ret += query(u+1, v) == 0 ? "CS204\n" : "HSS090\n"
    } else {
        [A[u], A[v]] = [A[v], A[u]]
        update(u+1, A[u] <= A[u+1] ? 0 : 1)
        update(v+1, A[v] <= A[v+1] ? 0 : 1)
        update(u, A[u-1] <= A[u] ? 0 : 1)
        update(v, A[v-1] <= A[v] ? 0 : 1)
    }
}
console.log(ret)
