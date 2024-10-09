import java.io.*;
import java.util.*;
/*
2325번 개코전쟁

1번 노드에서 N번 노드로가려고 한다.
그리고 그래프에서 하나의 간선을 제거할 수 있다.
하나를 제거한 뒤 최단거리가 최대가 되게 하려한다. 최단거리는 몇이될까?

뭐 일단 다익인건 알았는데,
최단경로에서 거치는 노드 수는 많아야 (노드개수)-1개라는걸 간과해서 못풀었네요.
그래서 VElogV가 되고..
사실 저거도 10^9는 가뿐히 넘어서 (V <= 1000, E <= V*(V-1)/2) 고민좀 했을것같긴한데,
시간제한 2초인거 보고 간파해야했습니다.
그냥 무조건 최단경로의 모든 간선 빼보는건 안된다고만 생각한게 패인이네요.
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int ini() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long lni() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}

    public static String sni() throws IOException {
        while (!st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public static int[] ins(int sz) throws IOException {
        int[] ret = new int[sz];
        for (int i = 0; i < sz; i++) {
            ret[i] = ini();
        }
        return ret;
    }

    public static long[] lns(int sz) throws IOException {
        long[] ret = new long[sz];
        for (int i = 0; i < sz; i++) {
            ret[i] = lni();
        }
        return ret;
    }

    static int N, M;
    static Vector<int[]> G[];

    public static int[] dij(int ban1, int ban2) {
        PriorityQueue<Integer[]> pq = new PriorityQueue<>((i, j) -> {
            return i[0]-j[0];
        });
        int[] dist = new int[N+1];
        for (int i = 0; i <= N; i++) dist[i] = 1<<29;
        pq.add(new Integer[] { 0, 1 });
        dist[1] = 0;
        while (!pq.isEmpty()) {
            var cur = pq.poll();
            int curN = cur[1], curW = cur[0];
            for (int[] nxt: G[curN]) {
                int nxtN = nxt[0], nxtW = nxt[1];
                if (curN == ban1 && nxtN == ban2) continue;
                if (curN == ban2 && nxtN == ban1) continue;
                if (curW+nxtW < dist[nxtN]) {
                    dist[nxtN] = curW+nxtW;
                    pq.add(new Integer[] { dist[nxtN], nxtN });
                }
            }
        }
        return dist;
    }

	public static void main(String[] args) throws IOException {
        N = ini(); M = ini();
        G = new Vector[N+1];
        for (int i = 0; i <= N; i++) G[i] = new Vector<>();
        for (int i = 0; i < M; i++) {
            int u = ini(), v = ini(), w = ini();
            G[u].add(new int[] { v, w });
            G[v].add(new int[] { u, w });
        }
        int[] dist = dij(-1, -1);
        ArrayDeque<Integer> q = new ArrayDeque<>();
        int[] prv = new int[N+1];
        int ret = -1<<30;
        q.add(N);
        while (!q.isEmpty()) {
            int curN = q.pollFirst();
            for (int[] nxt: G[curN]) {
                int nxtN = nxt[0], nxtW = nxt[1];
                if (dist[curN]-dist[nxtN] != nxtW) continue;
                prv[nxtN] = curN;
                q.add(nxtN);
            }
        }
        int cur = 1;
        while (cur != N) {
            ret = Math.max(ret, dij(cur, prv[cur])[N]);
            cur = prv[cur];
        }
        System.out.println(ret);

	    bw.flush();
        bw.close();
        br.close();
    }
}
