import java.io.*;
import java.util.*;
/*
14285번 간선 끊어가기

그래프에서 u, v번이 이어져있는동안 간선을 제거하면 점수를 얻을 수 있다.
얻을 수 있는 최대점수를 구해보자.

진짜 솔직히 이게 왜 되는건진 잘 모르겠습니다.
하도 안돼서 답지보고 푼건데.. 음..

제가 한 방식은 일단 뭐 최단경로에서 제일 긴거 하나 빼고 남은 값 빼면 되겠거니 했습니다.
그런데 안돼서 이거저거 뭐가문젠가 다 찔러보다가 몇시간 후에 GG치고 그냥 답지본거고..
이건 나중에 다시 봐야겠는데..
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

	static int N, K;
	static ArrayList<Edge>[] G;
	public static int[] dij(int x) {
		int[] dist = new int[N+1];
		Arrays.fill(dist, 1<<30);
		PriorityQueue<Edge> pq = new PriorityQueue<>((i, j) -> {
			return i.w-j.w;
		});
		pq.add(new Edge(x, 0));
		dist[x] = 0;
		while (!pq.isEmpty()) {
			Edge c = pq.poll();
			int curN = c.v, curW = c.w;
			if (dist[curN] < curW) continue;
			for (Edge i: G[curN]) {
				int nxtN = i.v, nxtW = i.w;
				if (nxtW+curW < dist[nxtN]) {
					dist[nxtN] = nxtW+curW;
					pq.add(new Edge(nxtN, dist[nxtN]));
				}
			}
		}
		return dist;
	}

	public static void main(String[] args) throws IOException {
		N = sti();
		K = sti();
		G = new ArrayList[N+1];
		Triple[] E = new Triple[K];
		int ret = 0;
		for (int i = 0; i <= N; i++)
			G[i] = new ArrayList<Edge>();
		for (int i = 0; i < K; i++) {
			int u = sti(), v = sti(), w = sti();
			G[u].add(new Edge(v, w));
			G[v].add(new Edge(u, w));
			E[i] = new Triple(u, v, w);
			ret += w;
		}
		int x = sti(), y = sti();
		int[] distX = dij(x);
		int[] distY = dij(y);
		int minus = 1<<30;
		for (Triple i: E) {
			minus = Math.min(minus, distX[i.u]+distY[i.v]);
			minus = Math.min(minus, distX[i.v]+distY[i.u]);
		}
		bw.write(ret-minus+"");

        bw.flush();
        bw.close();
        br.close();
    }
}

class Triple {
	int u, v, w;
	Triple(int u, int v, int w) {
		this.u = u;
		this.v = v;
		this.w = w;
	}
}

class Edge {
	int v, w;
	Edge(int v, int w) {
		this.v = v;
		this.w = w;
	}
}
