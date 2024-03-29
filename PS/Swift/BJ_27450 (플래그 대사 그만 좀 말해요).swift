/*
27450번 플래그 대사 그만 좀 말해요

수열의 길이 N과 정수 K가 주어진다. 이후 수열 A와 B가 주어진다.
수열의 한 인덱스를 선택하면 우측 값이 모두 max(K-(떨어져있는 거리), 0)만큼 증가한다.
A에서 인덱스를 몇 번 선택하여 0 <= i < N번째 인덱스에 대하여 A[i] < B[i]로 만들려 한다.
최소한으로 선택할 때 그 횟수를 구하시오.

배열 이름을 dp라고 했지만 dp는 아닌.
우린 이걸 그리디와 누적합이라고 부르기로 했어요.

그리디는 정말 쉽게 알 수 있습니다. 이건 뭐 실버 5 푸는사람 갖다 앉혀놔도 인덱스 0부터 하려 하겠죠.
오른쪽밖에 수가 증가하지 않으니, 왼쪽에 수가 안채워진놈들은 바로바로 채워줘야하거든요.
문제는 소리치고나서 오른쪽에 값을 일일히 더하면 O(N^2)으로 당연히 시간초과 엔딩이 날거란 점.
이걸 처리하는 난이도가 골드2라서 골드2가 책정되었습니다.. 라고 난이도 기여에 기여한 사람이 말했습니다.

이 부분만 처리하면 난이도가 파악 줄어듭니다. 어떻게처리할까요.
K = 3일 때 인덱스 0번, 1번, 3번을 고른다면,
[3, 3+2, 2+1, 3+1+0, 2+0+0, 1+0+0]
이렇게되겠죠.
0번을 두 번 고르면 0에서 인덱스 3으로 가는동안 2라는 값이 지속적으로 줄어들겁니다.

이를 일반화합시다 :
i번째 인덱스를 T번 고르면 i+K번째 인덱스로 가는동안 T라는 값이 지속적으로 줄어듭니다.
즉, prefix에 [지금까지 몇 번 선택을 했는가] 를 넣어줍시다. 이 값을 누적합으로 쓸겁니다.
그리고 배열 하나를 만들어서 [i번째 인덱스에서 몇 번 골랐는가]를 넣어줍시다.
이후 i가 K 이상으로 커진다면 이제 Ti라는 값을 줄이지 말아야합니다.
이는 배열에 저장되어있습니다. i가 K보다 크다면 prefix에서 arr[i-K]를 빼줍니다.
즉, [지금까지 몇 번 선택을 했는가] 에서 [max(i-K, 0)인덱스까지 몇 번 선택을 했는가] 를 빼는 아이디어입니다.

그러면 몇 번 골라야하는가는 어떻게 계산할까요?
일단 넘어야하므로, B[i] - (A[i] + prefix) 만큼을 채워야 함은 자명합니다.
이를 그냥 K로 나누면 내림이 되어 (나온값)*K+A[i] >= B[i]가 되지 못할 수 있으므로,
K-1을 추가로 더해주고 K로 나눕시다. 이러면 나머지가 있을땐 올림을 하고, 없으면 그냥 그 값만 나오게 됩니다.
(B[i] - (A[i] + prefix) + K - 1) / K

전 깔끔하게 구현하지 못했네요. 일단 맞추고 보자라는 생각이 너무 강했던거같습니다.
음.. 아닌가, 스위프트라서그런가..?
*/
var I = readLine()!.split(separator: " ").map {Int($0)!}
let N = I[0], K = Int64(I[1])
let cur = readLine()!.split(separator: " ").map {Int64($0)!}
let dst = readLine()!.split(separator: " ").map {Int64($0)!}
var dp = [Int64](repeating: 0, count: Int(N))
var plus = Int64(0), cnt = Int64(0), fix = Int64(0)
for i in 0..<N {
    if K <= i {
        fix -= dp[i-Int(K)]
    }
    let tmp = max(Int64((dst[i]-(cur[i]+plus)+K-1)/K), Int64(0))
    cnt += tmp
    plus += tmp*(K-1) - fix
    fix += tmp
    dp[i] = tmp
}
print(cnt)