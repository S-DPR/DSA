/*
13422번 도둑

첫째줄에 테스트케이스가 주어진다.
테스트케이스는 두 줄로 이루어져있다.
먼저, N, M, K가 나온다.
N은 배열의 길이, M은 배열에서 골라야하는 연속한 배열의 길이, 
K는 배열을 골랐을 때 그 합이 같거나 넘지 않아야하는 한계점이다.
두번째줄엔 N개의 양의정수가 공백으로 구분되어 주어진다.
배열의 양 끝인 이어져있다고 본다. 즉, 인덱스 N-1 다음은 0이다.
길이가 M인 연속하는 부분배열을 골랐을 때, K 미만이 되게 하는 경우의 수를 구하시오.

평범한 투포인터 문제입니다.
주의하지 않으면 걸리게 되는 함정이 있는데, N과 M의 값이 같을 때 입니다.
그때를 제외하면 그냥 투포인터로 풀어내면 됩니다.
*/
let T = Int(readLine()!)!
for _ in 0..<T {
    let I = readLine()!.split(separator:" ").map {Int($0)!}
    let N = I[0], M = I[1], K = I[2]
    let arr = readLine()!.split(separator:" ").map {Int($0)!}
    var acc = arr[0..<M].reduce(0, +)
    var cnt = acc < K ? 1 : 0
    if N == M {
        print(cnt)
        continue
    }
    for i in 0..<(N-1) {
        acc += arr[(M+i)%N] - arr[i]
        cnt += acc < K ? 1 : 0
    }
    print(cnt)
}