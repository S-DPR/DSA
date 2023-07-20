using System;
using System.IO;
using System.Linq;
using System.Collections;
using System.ComponentModel;
/*
20420번 화살표 미로 (Normal)

화살표가 그려진 맵이 있다. 당신은 반드시 화살표 방향으로만 움직일 수 있다.
단, 당신은 화살표를 왼쪽 혹은 오른쪽으로 각각 K번 방향을 바꿀 수 있다.
좌측 상단에서 우측 하단으로 이동할 수 있을까?

Hard가 플레 3이던데, 그거까지 고려하다가 괜히 시간먹힌 문제.
사실상 이 문제 속도 상위권들 코드 보면 Hard코드일거같네요..
제가 푼 방법은 전형적인 G2수준의 코드같습니다.

그냥 BFS 그대로 굴리면됩니다. Ruby로는 비트세트까지 박아줬건만 시간초과나더라고요.
배열 크기가 살짝 크긴 합니다. 50*50*150*150이라 5600만개정도?
솔직히 어렵지는 않았죠.. 그냥 BFS인데..

그나저나 C# 다차원배열 이거 히트네.. 안좋은쪽으로..
*/
internal class Program
{
    struct Info
    {
        public int x, y, l, r;

        public Info(int x, int y, int l, int r)
        {
            this.x = x;
            this.y = y;
            this.l = l;
            this.r = r;
        }
    }
    
    static void Main(string[] args) {
        var br = new StreamReader(new BufferedStream(Console.OpenStandardInput()));
        var bw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        var bri = () => Convert.ToInt32(br.ReadLine());
        var brs = () => br.ReadLine().Split(" ").Select(x => Convert.ToInt32(x)).ToArray();
        
        var I = brs();
        int R = I[0], C = I[1], K = I[2];
        var dict = new Dictionary<char, int>();
        dict['U'] = 0;
        dict['R'] = 1;
        dict['D'] = 2;
        dict['L'] = 3;

        int[,] M = new int[R, C];
            for (int i = 0; i<R; i++)
        {
            string item = br.ReadLine();
            for (int j = 0; j < C; j++)
                M[i, j] = dict[item[j]];
        }

        int[] dx = { 0, 1, 0, -1 };
        int[] dy = { -1, 0, 1, 0 };
        bool[,,,] V = new bool[R, C, K+1, K+1];
        Queue<Info> Q = new Queue<Info>();
        Q.Enqueue(new Info(0, 0, K, K));
        while (Q.Count != 0)
        {
            Info item = Q.Dequeue();
            int x = item.x, y = item.y;
            int l = item.l, r = item.r;
            int dir = M[y, x];
            for (int i = 1; i <= 3; i++)
            {
                if (l < i) break;
                int nxt = (dir + 4 - i) % 4;
                int nx = x + dx[nxt], ny = y + dy[nxt];
                if (!(0 <= nx && nx < C)) continue;
                if (!(0 <= ny && ny < R)) continue;
                if (V[ny, nx, l - i, r]) continue;
                Q.Enqueue(new Info(nx, ny, l-i, r));
                V[ny, nx, l - i, r] = true;
            }

            for (int i = 0; i <= 3; i++)
            {
                if (r < i) break;
                int nxt = (dir + i) % 4;
                int nx = x + dx[nxt], ny = y + dy[nxt];
                if (!(0 <= nx && nx < C)) continue;
                if (!(0 <= ny && ny < R)) continue;
                if (V[ny, nx, l, r - i]) continue;
                Q.Enqueue(new Info(nx ,ny, l, r-i));
                V[ny, nx, l, r - i] = true;
            }
        }

        bool ret = false;
        for (int i = 0; i <= K; i++)
            for (int j = 0; j <= K; j++)
                ret |= V[R - 1, C - 1, i, j];
        bw.Write(ret ? "Yes" : "No");

        br.Close();
        bw.Flush();
        bw.Close();
    }
}
