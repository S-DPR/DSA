import java.io.*;
import java.util.*;
/*
28092번 우선순위와 쿼리

N개의 정점이 있다. Q개의 쿼리를 처리해보자.
1 u v : u와 v를 잇는 간선을 추가한다. u와 v는 항상 존재함이 보장되고, 이었던 간선은 다시 나오지 않는다.
2 : 트리의 정점 중 갖고있는 정점이 가장 작은 트리를 지운다. 이후 그 정점의 번호를 출력한다. 트리가 존재함이 보장된다.

'트리를 지운다'가 아니라 '정점을 지운다'로 봐서 시간이 걸린 문제.
그거 아니면 그냥 30분컷내고 히힣 난 천재야 하고 껐을텐데..

그래도 보자마자 분리집합인건 알았습니다. 근데 신기하네, 어떻게안거지? 이게 '직관적으로'인가?
이후 힙을 어떻게 잘 관리하면 되겠다.. 하다가 조금 이상한 모양이 됐는데.
뭐.. 풀었으면 된거죠? 아마도?
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
    
    public static boolean union(int u, int v) {
        int pu = find(u);
        int pv = find(v);
        if (pu == pv) return false;
        if (sz[pu] < sz[pv]) {
            sz[pv] += sz[pu];
            mn[pv] = Math.min(mn[pu], mn[pv]);
            U[pu] = U[pv];
        } else {
            sz[pu] += sz[pv];
            mn[pu] = Math.min(mn[pu], mn[pv]);
            U[pv] = U[pu];
        }
        return true;
    }

    public static int find(int u) {
        if (U[u] != u) U[u] = find(U[u]);
        return U[u];
    }

    static int[] U, sz, mn;
	public static void main(String[] args) throws IOException {
        int N = ini(), Q = ini();
        U = new int[N+1];
        sz = new int[N+1];
        mn = new int[N+1];
        int[] upd = new int[N+1];
        boolean[] isTree = new boolean[N+1];
        // f = sz, s = upd, t = min_node, n = root_node
        PriorityQueue<Tuple> pq = new PriorityQueue<>((i, j) -> {
            if (i.f != j.f) return j.f-i.f;
            return i.t-j.t;
        });
        for (int i = 1; i <= N; i++) {
            U[i] = i;
            mn[i] = i;
            pq.add(new Tuple(1, 0, i, i));
        }
        Arrays.fill(sz, 1);
        Arrays.fill(isTree, true);
        for (int i = 0; i < Q; i++) {
            int q = ini();
            if (q == 1) {
                int u = ini(), v = ini();
                int pu = find(u), pv = find(v);
                boolean res = union(u, v);
                int tu = find(u);
                upd[tu]++;
                if (res && isTree[pu] && isTree[pv]) {
                    pq.add(new Tuple(sz[tu], upd[tu], mn[tu], tu));
                } else {
                    isTree[tu] = false;
                }
            } else {
                while (true) {
                    Tuple top = pq.peek();
                    int pt = find(top.t);
                    boolean first = top.s != upd[pt];
                    boolean second = !isTree[pt];
                    if (first || second) pq.poll();
                    else break;
                }
                Tuple ret = pq.poll();
                bw.write(ret.t + "\n");
                isTree[ret.n] = false;
            }
        }

	    bw.flush();
        bw.close();
        br.close();
    }
}

class Tuple {
    int f, s, t, n;
    Tuple(int f, int s, int t, int n) {
        this.f = f;
        this.s = s;
        this.t = t;
        this.n = n;
    }
}
