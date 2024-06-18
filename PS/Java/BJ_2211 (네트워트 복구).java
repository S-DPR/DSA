import java.io.*;
import java.util.*;
/*
2211번 네트워크 복구

그래프가 주어진다.
1번 시작해 모든 정점에 대해 원래 최단거리를 유지하는 상태로 간선을 지우려한다.
간선은 최소 몇 개가 필요할까? 그리고 남은 간선은 무엇일까?

그냥 다익스트라 역추적. 다익스트라 결과로 그래프를 만들면 트리가 되니까.
다익스트라 내부에서 처리하는거도 방법인데..
아무래도 편한건 그냥 다익스트라 가져오고 bfs로 처리해주는겁니다.
간단한 문제였어요.
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

    public static void printArray(int[] arr) {
        for (int i: arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void printArray(long[] arr) {
        for (long i: arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static int N, K;
    static Vector<int[]>[] G;
    public static int[] dij() {
        int[] dist = new int[N+1];
        PriorityQueue<int[]> pq = new PriorityQueue<>((i, j) -> {
            return i[0] - j[0];
        });
        Arrays.fill(dist, 1<<30);
        dist[1] = 0;
        pq.add(new int[] {0, 1});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curW = cur[0], curN = cur[1];
            for (int[] x: G[curN]) {
                int nxtN = x[0], nxtW = x[1];
                if (nxtW+curW < dist[nxtN]) {
                    dist[nxtN] = nxtW+curW;
                    pq.add(new int[] {dist[nxtN], nxtN});
                }
            }
        }
        return dist;
    }

	public static void main(String[] args) throws IOException {
        N = ini(); K = ini();
        G = new Vector[N+1];
        for (int i = 0; i <= N; i++) {
            G[i] = new Vector<>();
        }
        for (int i = 0; i < K; i++) {
            int u = ini(), v = ini(), w = ini();
            G[u].add(new int[] {v, w});
            G[v].add(new int[] {u, w});
        }
        bw.write(N-1 + "\n");
        int[] dist = dij();
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] V = new boolean[N+1];
        q.add(1);
        while (!q.isEmpty()) {
            int curN = q.pollFirst();
            for (int[] x: G[curN]) {
                int nxtN = x[0], nxtW = x[1];
                if (V[nxtN]) continue;
                if (dist[nxtN]-dist[curN] == nxtW) {
                    bw.write(curN + " " + nxtN + "\n");
                    V[nxtN] = true;
                    q.add(nxtN);
                }
            }
        }

	    bw.flush();
        bw.close();
        br.close();
    }
}
