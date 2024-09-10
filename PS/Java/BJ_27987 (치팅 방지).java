import java.io.*;
import java.util.*;
/*
27987번 치팅 방지

각 수가 몇 개씩 들어있는지에 대한 정보가 주어진다.
이 때, 같은 수가 최대한 거리를 벌려 배치되게 하려고 한다.
가능한 수열을 하나 출력해보자.

이런게 은근 복잡해보이는데 항상 그리디더라고요
그래서 그리디로 바로 접근했고..
저는 그 그리디의 구현에 매개변수+힙을 썼습니다.
골드1 바로푼거 진짜 오랜만이다
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

    static int N, S;
    static int[] A;

    public static Vector<Integer> trying(int mid) {
        int[] cnts = new int[N+1];
        Vector<Integer> ret = new Vector<>();
        PriorityQueue<Integer[]> pq = new PriorityQueue<>((i, j) -> {
            return j[0] - i[0];
        });
        for (int i = 1; i <= N; i++) {
            if (A[i] > 0) pq.add(new Integer[] {A[i], i});
            cnts[i] = A[i];
        }
        for (int i = 0; i < S && !pq.isEmpty(); i++) {
            Integer[] x = pq.poll();
            int k = x[1];
            ret.add(k);
            cnts[k]--;
            if (i >= mid) {
                int u = ret.get(i-mid);
                if (cnts[u] >= 1) pq.add(new Integer[] {cnts[u], u});
            }
        }
        return ret;
    }

	public static void main(String[] args) throws IOException {
        N = ini();
        A = new int[N+1];
        S = 0;
        for (int i = 1; i <= N; i++) {
            A[i] = ini();
            S += A[i];
        }
        int lo = 0, hi = N+1;
        while (lo < hi) {
            int mid = (lo + hi) >> 1;
            if (trying(mid).size() == S) lo = mid + 1;
            else hi = mid;
        }
        Vector<Integer> ret = trying(hi-1);
        for (int i: ret) System.out.print(i + " ");

	    bw.flush();
        bw.close();
        br.close();
    }
}
