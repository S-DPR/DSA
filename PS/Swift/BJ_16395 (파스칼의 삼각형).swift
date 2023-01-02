/*
16395번 파스칼의 삼각형

n과 k가 주어진다. C(n-1, k-1)을 구해보자.

실버5문제에서 진지하게 고민했..
오버플로가 나지 않게 조심조심히 조합의 값을 구하는 문제입니다.
언어가 많아지니 이런거로 땡칠날도 생기니 좋은거같기도 하고..
*/
let I = readLine()!.split(separator: " ").map {Int($0)!}
let n = I[0]-1, k = I[1]-1
var ans = 1
for i in 0..<k {
    ans *= n-i
    ans /= i+1
}
print(ans)