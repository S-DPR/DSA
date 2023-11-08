import java.io.*;
import java.util.*;
/*
28131번 K-지폐

S에서 T로 가려 한다. 이 때, 총 비용이 K의 배수가 되는 최단경로로 가려한다.
최소 비용은 얼말까?

그냥 다익스트라에 dp섞은 문제
처음에는 되게 참신해보였는데 이제 슬슬 식상해지기 시작합니다.
다익스트라로 위장한 순수dp문제가 있지않나,
dist내부에 PriorityQueue로 꽉채우고 그거로 K번째 최단경로를 찾질않나..
그런거보단 훨씬 쉽게 생각없이 코드 쭉 쓰니까 AC. 쉽네요.
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int sti() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long stl() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}

	static List<Pair>[] G;
	public static int[][] dij(int S, int K) {
		int N = G.length-1;
		int[][] dist = new int[N+1][K+1];
		PriorityQueue<Pair> pq = new PriorityQueue<>((i, j) -> {
			return i.second < j.second ? -1 : 1;
		});
		pq.add(new Pair(S, 0));
		for (int i = 0; i <= N; i++)
			Arrays.fill(dist[i], 1<<30);
		dist[S][0] = 0;
		while (!pq.isEmpty()) {
			Pair cur = pq.poll();
			int curN = cur.first;
			int curW = cur.second;
			int curP = curW%K;
			if (dist[curN][curP] < curW) continue;
			G[curN].forEach(i -> {
				int nxtN = i.first;
				int nxtW = i.second;
				int nxtP = (curW+nxtW)%K;
				if (curW+nxtW < dist[nxtN][nxtP]) {
					dist[nxtN][nxtP] = curW+nxtW;
					pq.add(new Pair(nxtN, dist[nxtN][nxtP]));
				}
			});
		}
		return dist;
	}

	public static void main(String[] args) throws IOException {
		int N = sti(), M = sti(), K = sti();
		int S = sti(), T = sti();
		G = new ArrayList[N+1];
		for (int i = 0; i <= N; i++) {
			G[i] = new ArrayList<>();
		}
		for (int i = 0; i < M; i++) {
			int u = sti(), v = sti(), w = sti();
			G[u].add(new Pair(v, w));
		}
		int[][] dist = dij(S, K);
		System.out.println(dist[T][0] == 1<<30 ? "IMPOSSIBLE" : dist[T][0]);

        bw.flush();
        bw.close();
        br.close();
    }
}

class Pair {
	int first, second;
	Pair(int first, int second) {
		this.first = first;
		this.second = second;
	}
}
