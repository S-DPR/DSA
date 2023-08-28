using System.Collections.Immutable;
/*
24396번 푸앙이와 별

완전그래프가 있다. 여기서 K개의 간선을 제거 할 것이다.
정점 1에서 다른 모든 노드로의 최단경로를 구해보자.
경로가 없으면 -1을 출력하자.

와 진짜 이건 좀 놀라운문제네
등교하면서 등교한다는 내 생각을 떨쳐버리기 위해 천천히 방법을 생각해보다가 답이 나왔네..

1번에서 전체를 방문하긴 하는데, 방문하면서 다시 방문하는 경우를 제거해버리는 놀라운 발상.
와 진짜 이게 되긴 하는구나..
시간복잡도는 계산 못하겠고 이론상 몇번의 연산정도가 필요할까 생각해봤는데 진짜 되네;
골드까지는 깔끔한 구현을 목표로 한다면, 확실히 플레부터는 생각해야 할 점이 많아지네요..
*/
internal class Program
{
    static void Main(string[] args) {
        var br = new StreamReader(new BufferedStream(Console.OpenStandardInput()));
        var bw = new StreamWriter(new BufferedStream(Console.OpenStandardOutput()));
        var bri = () => Convert.ToInt64(br.ReadLine());
        var brs = () => br.ReadLine().Split(" ").Select(x => Convert.ToInt32(x)).ToArray();

        var I = brs();
        var (N, K) = (I[0], I[1]);
        var G = new SortedSet<int>[N+1];
        for (int i = 0; i <= N; i++)
            G[i] = new SortedSet<int>();
        for (int i = 0; i < K; i++)
        {
            var II = brs();
            var (u, v) = (II[0], II[1]);
            G[u].Add(v);
            G[v].Add(u);
        }

        var Q = new Queue<int>();
        var V = new SortedSet<int>();
        var D = new int[N + 1];
        for (int i = 2; i <= N; i++)
        {
            V.Add(i);
            D[i] = -1;
        }
        Q.Enqueue(1);
        D[1] = 0;
        while (Q.Count > 0)
        {
            var curN = Q.Dequeue();
            var visItems = new List<int>();
            foreach (var i in V)
            {
                if (G[curN].Contains(i)) continue;
                D[i] = D[curN] + 1; 
                visItems.Add(i);
                Q.Enqueue(i);
            }
            foreach (var visItem in visItems)
            {
                V.Remove(visItem);
            }
        }
        for (int i = 1; i <= N; i++)
            bw.Write(D[i] + "\n");

        br.Close();
        bw.Flush();
        bw.Close();
    }
}