import java.io.*;
import java.util.*;
/*
13911번 집 구하기

N개의 노드가 있는 그래프가 있다.
그리고 X개의 맥도날드 노드 수가, Y개의 스타벅스 노드 수가 주어진다.
각 맥도날드 및 스타벅스의 위치가 주어졌을 때,
맥도날드나 스타벅스가 아닌 곳에서 맥도날드까지의 거리가 XD이하, 스타벅스까지의 거리가 YD 이하인 노드 중,
맥도날드와 스타벅스의 거리의 합이 최소가 될 때 그 거리를 찾아보자.

대충 다익스트라 두번 긁으면 끝나는 문제
자바도 폰으로 풀 수 있네 ㅋㅋ
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int ini() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long lni() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}
    
    static long[] dij(Vector<Integer> starts) {
        int N = G.length-1;
        long[] dist = new long[N+1];
        Arrays.fill(dist, 1L<<60);
        PriorityQueue<Pair> pq = new PriorityQueue<>((i, j) -> {
            return i.w < j.w ? -1 : 1;
        });
        for (int i: starts) {
            pq.add(new Pair(i, 0));
            dist[i] = 0;
        }
        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            int curN = p.v;
            long curW = p.w;
            G[curN].forEach(i -> {
                int nxtN = i.v;
                long nxtW = i.w;
                if (curW+nxtW < dist[nxtN]) {
                    dist[nxtN] = curW+nxtW;
                    pq.add(new Pair(nxtN, dist[nxtN]));
                }
            });
        }
        return dist;
    }

    static Vector<Pair>[] G;
	public static void main(String[] args) throws IOException {
		int V = ini(), E = ini();
       G = new Vector[V+1];
       for (int i = 0; i <= V; i++) G[i] = new Vector<>();
       for (int i = 0; i < E; i++) {
           int u = ini(), v = ini();
           long w = lni();
           G[u].add(new Pair(v, w));
           G[v].add(new Pair(u, w));
       }
       Vector<Integer> mac = new Vector<>();
       Vector<Integer> star = new Vector<>();
       int macV = ini();
       long macD = lni();
       for (int i = 0; i < macV; i++) mac.add(ini());
       int starV = ini();
       long starD = lni();
       for (int i = 0; i < starV; i++) star.add(ini());
        long[] macDist = dij(mac);
        long[] starDist = dij(star);
        long ret = 1L<<60;
        for (int i = 1; i <= V; i++) {
            if (macDist[i]*starDist[i] == 0) continue;
            if (macD < macDist[i]) continue;
            if (starD < starDist[i]) continue;
            ret = Math.min(ret, macDist[i]+starDist[i]);
        }
        System.out.println(ret == 1L<<60 ? -1 : ret);

	    bw.flush();
        bw.close();
        br.close();
    }
}

class Pair {
    int v;
    long w;
    Pair(int v, long w) {
        this.v = v;
        this.w = w;
    }
}
