import java.io.*;
import java.util.*;
/*
5551번 쇼핑몰

N개의 도시가 있고, M개의 간선이 있다.
도시와 간선의 모든 점에는 집이 있고, 도시에는 쇼핑몰이 있을 수 있다.
쇼핑몰이 있는 도시가 주어진다. 쇼핑몰과 도시간의 최장거리를 구해보자.

예제 설명에 소수점나오길래 구아아악하고 도망쳤는데,
두 정점을 잇는 간선의 가중치가 w, 두 정점의 가중치가 u, v라면.
u+x = v+(w-x)를 만족하는 x를 찾아야하고..
대충 전개 슥슥하면 2x = v+w-u.
x = (v+w-u)/2.
u, v, w는 모두 상수이므로 x를 구할 수 있죠..

다익스트라 한 번 쓰고 모든 간선에 저 아이디어 쓰면 끝납니다.
간단하죠.
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

    public static char cni() throws IOException {
        while (!st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken().charAt(0);
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

	public static void main(String[] args) throws IOException {
        int N = ini(), M = ini(), K = ini();
        int[][] G = new int[N+1][N+1];
        for (int i = 0; i <= N; i++) Arrays.fill(G[i], 1<<30);
        for (int i = 0; i < M; i++) {
            int u = ini(), v = ini(), w = ini();
            G[u][v] = w;
            G[v][u] = w;
        }
        PriorityQueue<Integer[]> pq = new PriorityQueue<>((i, j) -> {
            return i[0]-j[0];
        });
        int[] dist = new int[N+1];
        Arrays.fill(dist, 1<<30);
        for (int i = 0; i < K; i++) {
            int k = ini();
            dist[k] = 0;
            pq.add(new Integer[] {0, k});
        }
        while (!pq.isEmpty()) {
            Integer[] x = pq.poll();
            int curW = x[0], curN = x[1];
            for (int nxtN = 1; nxtN <= N; nxtN++) {
                if (G[curN][nxtN] == 1<<30) continue;
                int nxtW = G[curN][nxtN];
                if (curW+nxtW < dist[nxtN]) {
                    dist[nxtN] = curW+nxtW;
                    pq.add(new Integer[] {dist[nxtN], nxtN});
                }
            }
        }
        int mx = Arrays.stream(dist).skip(1).max().getAsInt();
        int ret = mx;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (G[i][j] == 1<<30) continue;
                int x = (dist[j]+G[i][j]-dist[i]+1)/2;
                ret = Math.max(ret, dist[i]+x);
            }
        }
        System.out.println(ret);

	    bw.flush();
        bw.close();
        br.close();
    }
}
