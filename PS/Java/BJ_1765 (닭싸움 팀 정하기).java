import java.io.*;
import java.util.*;
/*
1765번 닭싸움 팀 정하기

친구의 친구는 친구다. 원수의 원수는 친구다.
친구와 원수의 관계가 주어진다. 총 몇 개의 친구그룹이 있을까?

하아
전에 푼 원수의 원수의 하위호환인줄알았는데 원수의 친구의 원수는 친구가 아니네..
그거때문에 dfs를 못쓰게되어버렸네요.
아쉽다.

그냥.. union-find에 완탐맛 섞은느낌.
이게 그 어렵게생각하면 한없이 어려워지는 문제더라고요.
저는 어렵게 생각해서 결국 답지보고 풀었습니다.
그런데 얻어가는건 있긴하네요.. 이런 이분 문제 좀 있던데 잘 알아놔야지.
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

    static int N, K;
    static Vector<Integer>[] E;
    static int[] U;

    public static boolean union(int u, int v) {
        int up = find(u), vp = find(v);
        if (up == vp) return false;
        U[up] = U[vp];
        return true;
    }

    public static int find(int x) {
        if (U[x] != x) U[x] = find(U[x]);
        return U[x];
    }

	public static void main(String[] args) throws IOException {
        N = ini(); K = ini();
        E = new Vector[N+1];
        U = new int[N+1];
        for (int i = 0; i <= N; i++) {
            E[i] = new Vector<>();
            U[i] = i;
        }
        for (int i = 0; i < K; i++) {
            int x = cni() == 'E' ? 1 : 0;
            int u = ini(), v = ini();
            if (x == 0) {
                union(u, v);
            } else {
                E[u].add(v);
                E[v].add(u);
            }
        }
        for (int i = 1; i <= N; i++) {
            for (int u: E[i]) for (int v: E[u]) union(i, v);
        }
        Set<Integer> s = new HashSet<>();
        for (int i = 1; i <= N; i++) s.add(find(i));
        System.out.println(s.size());

	    bw.flush();
        bw.close();
        br.close();
    }
}
