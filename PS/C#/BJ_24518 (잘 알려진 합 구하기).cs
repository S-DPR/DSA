var br = new StreamReader(new BufferedStream(Console.OpenStandardInput()));
var bw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
var bri = () => Convert.ToInt64(br.ReadLine());
var brs = () => br.ReadLine().Split(" ").Select(x=>Convert.ToInt64(x)).ToArray();
/*
24158번 잘 알려진 합 구하기

N과 M이 주어질 때, 함수 f(x)와 g(x)는 다음 의미를 가진다.
f(x) = [N/x] ([x]는 x보다 작은 가장 큰 정수)
g(x) = x%M
이 때, sigma(i = 1->N) {f(i)*g(i)}의 합을 구해보자.

C# 데뷔문제.
역시 처음 쓸 때는 수학문제죠.

어디서 많이 본 친구, f(x) = [N/x]
조화수열 아시는구나! 이름을 기억해둔 덕에 편하게 할 수 있었습니다.
아, 편하게 검색했다고요. 이제 대충 계산하는 방법은 알게 되었습니다. O(sqrtN).

적절한 수 i, j에 대해 f(i) = f(i+1) = ... = f(j-1) = f(j)일겁니다.
위 식을 성립시키는 가장 작은 i와 가장 같은 j에 대해, 아래와 같이 식을 다시 정리할 수 있습니다.
[N/i] * sigma(t = i->j) {g(t)}

g(t)는 g(j)-g(i-1)로 대신 구합니다.
위 식의 값은 모듈러연산의 특징으로 O(1)만에 구할 수 있습니다.
즉, 최종 시간복잡도는 O(sqrtN)이 됩니다.
*/
var MOD = 1_000_000_007;
var I = brs();
var N = I[0];
var M = I[1];
long ret = 0;
long j;
for (long i = 1; i <= N; i = j + 1)
{
    j = N / (N / i);
    var left = (i - 1) / M * (M * (M - 1) / 2) + ((i-1) % M * ((i-1) % M + 1) / 2);
    var right = j / M * (M * (M - 1) / 2) + (j % M * (j % M + 1) / 2);
    var sum = right - left;
    ret += (N / i) * sum;
    ret %= MOD;
}
bw.Write(ret);

br.Close();
bw.Flush();
bw.Close();