/*
23128번 Math

서로 다른 수가 있는 수열 A가 주어진다.
A의 서로 다른 두 수를 고르자. 각각을 x, y라고 하자.
x^2 + y가 제곱수가 되는 경우의 수를 구해보자.

이게머시여 했던 문제.
수학 발표하는 김에 이름부터 Math인 문제를 풀었습니다.

x^2 + y = K라고 둡시다. K는 k*k, 다시말해 제곱수고요.
x^2 - K = -y
K - x^2 = y
(k-x)(k+x) = y
가 되는데, 이제 x와 k를 고정시키고 y가 있나 없나만 찾아주면 됩니다.

쉽죠?
*/
internal class Program
{
    static void Main(string[] args) {
        var br = new StreamReader(new BufferedStream(Console.OpenStandardInput()));
        var bw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        var bri = () => Convert.ToInt64(br.ReadLine());
        var brs = () => br.ReadLine().Split(" ").Select(x => Convert.ToInt64(x)).ToArray();

        var N = bri();
        var arr = brs();
        var items = new int[1000001];
        foreach (var i in arr) items[i]++;

        var ret = 0;
        foreach (long x in arr)
            for (long k = x; (k-x)*(k+x) <= 1000000; k++)
                ret += items[(k - x) * (k + x)];
        bw.Write(ret);

        br.Close();
        bw.Flush();
        bw.Close();
    }
}