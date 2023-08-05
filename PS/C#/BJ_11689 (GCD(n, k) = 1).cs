using System;
using System.IO;
using System.Linq;
using System.Collections;
using System.ComponentModel;
using System.Numerics;
/*
11689번 GCD(n, k) = 1

수 N이 주어진다. N과 서로소관계인 수의 개수를 구해보자.

저어기 옆집 '서로소' 문제처럼 포함배제도 되기는 할거라고 봤는데.
오일러피함수 한번 슥 보자 하고 골랐습니다. 골드1이라 레이팅도 관계없기도하고.
그런데 진짜 너무 간단해서 놀랐습니다. 허..

수 N이 있을 때, 소수 P1, P2, P3, ...를 소인수로 가질텐데.
이 중 겹치는건 하나빼고 다 버리고, 남은 소수들만 모아서.
N*(1-(1/P1))*(1-(1/P2))*...를 하면.. 소수의 개수가 나온다?
음... 이게되네..

그래도 옆집 서로소문제에선 못쓰겠고.. 이거 쓰는 문제 자체도 적은가보네요.
*/
internal class Program
{
    static void Main(string[] args) {
        var br = new StreamReader(new BufferedStream(Console.OpenStandardInput()));
        var bw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        var bri = () => Convert.ToInt64(br.ReadLine());
        var brs = () => br.ReadLine().Split(" ").Select(x => Convert.ToInt64(x)).ToArray();

        var N = bri();
        var cond = N;
        var ret = N;
        for (long i = 2; i * i <= cond; i++)
        {
            if (N % i != 0) continue;
            while (N % i == 0)
            {
                N /= i;
            }

            ret /= i;
            ret *= i - 1;
        }

        if (N > 1)
        {
            ret /= N;
            ret *= N - 1;
        }
        bw.Write(ret);

        br.Close();
        bw.Flush();
        bw.Close();
    }
}