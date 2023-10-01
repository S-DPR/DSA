let spi = { () -> [Int] in
    return readLine()!.split(separator: " ").map {Int($0)!}
}
let spl = { () -> [Int64] in
    return readLine()!.split(separator: " ").map {Int64($0)!}
}
/*
15560번 구간 합 최대? 1

수열 A와 쿼리가 주어진다. 쿼리에 답해보자.
0 u v = max(U*sum(A[i..j])+V(j-i)) (u <= i <= j <= v)를 출력하라.
1 u v = A[u] = v 로 바꾸어라.

이야 이게 골드2야?
진짜 골드2 어렵다..

F(x) = U*P[x]+V*x
F(x)-F(x-1)
 = U*P[x]+V*x - (U*P[x-1]+V(x-1))
 = U(P[x]-P[x-1])+V

..라고해서 A[i]를 모두 U*A[i]+V로 바뀌고,
저거로 새로 만든 녀석의 구간연속최대합을 구하면 된다네요.
까먹고 전체의 연속최대합만 출력해서 수없이 틀렸습니다..

아.. 진짜 수학이 몰래 들어가면 절망적인 난이도를 뿜게되는구나..
*/
let I = spi()
let (N, Q, U, V) = (I[0], I[1], Int64(I[2]), Int64(I[3]))
var A = spl()
var lret: Int64
var ret: Int64
var f: Int64
for _ in 0..<Q {
    let II = spi()
    let (C, u, v) = (II[0], II[1], II[2])
    if C == 0 {
        f = U*A[u-1]+V
        ret = f
        lret = f
        for i in u..<v {
            f = U*A[i]+V
            lret = max(f, lret+f)
            ret = max(ret, lret)
        }
        print(ret-V)
    } else {
        A[u-1] = Int64(v)
    }
}
