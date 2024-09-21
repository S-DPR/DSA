import java.io.*;
import java.util.*;
/*
25567번 줄 세우기

1부터 K번까지 사람이 나눠서 서있는 N개의 줄이 있다. 다음 쿼리를 처리해보자.

1 u v
u번과 v번 사람의 줄이 같을경우 NO를 출력하고, 
다를경우 YES를 출력하고 v사람이 있는 줄을 u사람이 있는 줄 뒤에 서게한다.

2 u v
u번과 v번 사람 사이에 있는 사람들의 번호의 합을 출력한다.

하
분리집합, Small-To-Large 테크닉 다 알았는데 아쉽다
연결리스트도 태그에 있던데 이건 Small-To-Large 안써도 되더라고요
근데 연결리스트 자체가 좀 헷갈리기때문에 안했고..

오프라인쿼리로 하면 누적합 한번으로 싹 다 처리할 수 있다는 점을 파악 못해서 망했습니다.
오프라인쿼리 거의 안나왔는데 플3 하니까 슬슬 튀어나오네요..
솔직히 진짜 아까운데 오프라인쿼리는 절대 생각 못했을거같아요.
계속 누적합 어떻게해야 매번 온라인으로 처리할지 생각했었기때문에..
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

    static int N;
    static int[] U;
    static ArrayDeque<Long>[] deq;

    public static boolean union(int x, int y) {
        int xp = find(x);
        int yp = find(y);
        if (xp == yp) return false;
        if (deq[xp].size() < deq[yp].size()) {
            while (!deq[xp].isEmpty()) deq[yp].addFirst(deq[xp].pollLast());
            U[xp] = U[yp];
        } else {
            while (!deq[yp].isEmpty()) deq[xp].add(deq[yp].pollFirst());
            U[yp] = U[xp];
        }
        return true;
    }

    public static int find(int x) {
        if (U[x] != x) U[x] = find(U[x]);
        return U[x];
    }

	public static void main(String[] args) throws IOException {
        int MX = 200000;
        N = ini();
        U = new int[MX+1];
        deq = new ArrayDeque[MX+1];
        for (int i = 0; i <= MX; i++) {
            deq[i] = new ArrayDeque<>();
            U[i] = i;
            deq[i].add(((long) i));
        }
        for (int i = 0; i < N; i++) {
            int L = ini();
            ArrayDeque<Long> cur = new ArrayDeque<>();
            int x = ini();
            cur.add((long) x);
            for (int j = 1; j < L; j++) {
                int y = ini();
                union(x, y);
                cur.add((long) y);
            }
            deq[find(x)] = cur;
        }
        int Q = ini();
        String[] ret = new String[Q];
        List<Integer[]> sec = new ArrayList<>();
        for (int q = 0; q < Q; q++) {
            int cmd = ini(), u = ini(), v = ini();
            if (cmd == 1) {
                ret[q] = union(u, v) ? "YES" : "NO";
            } else {
                if (find(u) == find(v)) sec.add(new Integer[] {q, u, v});
                else ret[q] = "-1";
            }
        }
        boolean[] vis = new boolean[MX+1];
        List<Long>[] pf = new List[MX+1];
        int[] idx = new int[MX+1];
        for (int i = 1; i <= MX; i++) {
            int f = find(i);
            if (vis[f]) continue;
            vis[f] = true;
            List<Long> curpf = new ArrayList<>();
            curpf.add((long) 0);
            int idxx = 1;
            long first = deq[f].pollFirst();
            curpf.add(first);
            idx[(int) first] = idxx++;
            while (!deq[f].isEmpty()) {
                first = deq[f].pollFirst();
                curpf.add(curpf.get(curpf.size()-1)+first);
                idx[(int) first] = idxx++;
            }
            pf[f] = curpf;
        }
        for (Integer[] s: sec) {
            int q = s[0];
            int u = find(s[1]);
            int uidx = idx[s[1]];
            int vidx = idx[s[2]];
            ret[q] = pf[u].get(Math.max(uidx, vidx))-pf[u].get(Math.min(uidx, vidx)-1) + "";
        }
        for (String r: ret) bw.write(r + "\n");

	    bw.flush();
        bw.close();
        br.close();
    }
}
