using System.Collections.Immutable;
/*
4373번 수집합

크기가 1~1000인 정수 집합 S가 주어진다.
a+b+c=d를 만족하는 가장 큰 d를 구해보자.
단, a, b, c, d는 모두 다른 수이다.

옛날에 플레5였는데 골드1이네..
근데 생각해보니 더 내려갈거같기도하고..

솔직히 좀 흥미로운 문제.
일단 아무리생각해도 저는 완전탐색을 최적화한거같네요.

먼저 S를 sort합니다.
다음으로 a+b는 K로 묶어줍시다. 이건 O(N^2)으로 되니까요.
그리고 d를 모두 테스트할겁니다.
for문 들어가서, d를 사용한 K는 모두 뺍니다. 이는 반복 끝나기 직전에 다시 추가합니다.
그리고 j 하나 더 만듭시다. N 미만의 모든 수를 반복합니다.
i == j이면 continue를 합니다.
만약 K+c=S[i]를 만족하는 b가 없다면 continue합니다.
있어도 d를 사용한거면 뺍니다.

만약 c 만드는 방법이 0개면 당연히 continue합니다.
방법이 2개 이상인지 체크해봅시다. 이는 condition1로 합시다.
1이면 K가 c를 사용하는지 체크합니다. 이를 condition2로 합시다.
condition1, condition2 모두 false면 continue합니다.
아니면, 결과를 S[i]로 갱신해줍니다.
*/
internal class Program
{
    static void Main(string[] args) {
        var br = new StreamReader(new BufferedStream(Console.OpenStandardInput()));
        var bw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        var bri = () => Convert.ToInt64(br.ReadLine());
        var brs = () => br.ReadLine().Split(" ").Select(x => Convert.ToInt64(x)).ToArray();

        while (true)
        {
            var N = bri();
            if (N == 0) break;
            var arr = new long[N];
            for (var i = 0; i < N; i++)
            {
                arr[i] = bri();
            }
            Array.Sort(arr);
            var arrSet = arr.ToImmutableSortedSet();
            var sum = new Dictionary<long, long>();
            for (var i = 0; i < N; i++)
                for (var j = i + 1; j < N; j++)
                    if (sum.ContainsKey(arr[i] + arr[j])) sum[arr[i] + arr[j]]++;
                    else sum[arr[i] + arr[j]] = 1;
            long ret = -1;
            for (var i = 0; i < N; i++)
            {
                for (var j = 0; j < N; j++)
                    if (i != j)
                        sum[arr[i] + arr[j]]--;
                
                for (var j = 0; j < N; j++)
                {
                    if (i == j) continue;
                    var item = arr[i] - arr[j];
                    if (!sum.ContainsKey(item)) continue;
                    if (sum[item] == 0) continue;
                    var condition1 = sum[item] > 1;
                    var condition2 = !arrSet.Contains(item - arr[j]);
                    if (!(condition1 || condition2)) continue;
                    ret = arr[i];
                    break;
                }

                for (var j = 0; j < N; j++)
                    if (i != j)
                        sum[arr[i] + arr[j]]++;
            }
            bw.Write((ret == -1 ? "no solution" : ret) + "\n");
        }

        br.Close();
        bw.Flush();
        bw.Close();
    }
}