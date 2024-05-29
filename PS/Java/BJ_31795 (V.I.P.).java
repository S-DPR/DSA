import java.io.*;
import java.util.*;
/*
31795 V.I.P.

가중치가 있는 N개의 노드가 있는 완전그래프가 있다.
이 그래프에서 u, v를 잇는 M개의 간선만 현재 활성화되어있다.
V.I.P. 경로란 활성화된 간선만을 이용해 갈 수 있으며,
가중치가 작은 정점에서 큰 정점으로만 이동하는 경로이다.
이제 Q개의 간선 u, v가 차례로 활성화/비활성화 될 예정이다.
활성화된 간선은 비활성화되고, 비활성화되어있는 간선은 활성화된다고 하자.
그리고, 모든 쿼리는 항상 시도 후 원래대로 복원된다 할 때, 아래 쿼리에 대한 값을 구하시오.
u v : u와 v를 잇는 간선이 활성화/비활성화 되었을 때 V.I.P.경로의 개수를 구하자.

오..
그냥 빠르게 에디토리얼 봤는데 오...
신기해.

우선 지나가던 멍멍이가 봐도 '이건 위성정렬이랑 dp쓰는거네' 라고 할 수준이고,
여기에 조금 더 추가해서 정방향도 해야하고 역방향도 해야하는정도?
연산에 x에서 끝나는 V.I.P.경로의 개수와 x에서 시작하는 V.I.P.경로의 개수가 둘 다 필요합니다.

그럼 쿼리를 처리해야하는데..
u와 v의 가중치가 같다면 있으나 없으나 상관없으므로 그냥 dp합이나 출력해줍니다.
이제 다르면, 일단 u가 무조건 v보다 가중치가 작다고 합시다. 아니면 스왑하고.

활성화 연산이라면, 가중치가 적은곳(u)에서 가중치가 큰곳(v)로 갈 수 있게 된다는 소리입니다.
total에다가 u로 끝나는 개수와 v로 시작하는 개수를 곱한 값을 더해줍시다.

비활성화 연산이라면, 가중치가 적은곳에서 가중치가 큰곳으로 갈 수 없다는 소리입니다.
위랑 똑같이 하는데, 곱한 값을 빼줍시다.

음..
어쩌면 정말 간단하지만..
이게 역방향을 생각 못했던게 한번에 못푼 패인.
에디토리얼 안보고 풀만했을지도 모르겠네요.
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

	public static void main(String[] args) throws IOException {
        long MOD = 1_000_000_007;
        int N = ini(), M = ini(), Q = ini();
        int[] I = new int[N+1], RI = new int[N+1];
        long[] W = new long[N+1];
        TreeSet<Integer>[] G = new TreeSet[N+1];
        TreeSet<Integer>[] RG = new TreeSet[N+1];
        for (int i = 1; i <= N; i++) W[i] = lni();
        for (int i = 0; i <= N; i++) {
            G[i] = new TreeSet<Integer>();
            RG[i] = new TreeSet<Integer>();
        }
        for (int i = 0; i < M; i++) {
            int u = ini(), v = ini();
            if (W[u] < W[v]) {
                I[v]++;
                G[u].add(v);
                RI[u]++;
                RG[v].add(u);
            }
            if (W[u] > W[v]) {
                I[u]++;
                G[v].add(u);
                RI[v]++;
                RG[u].add(v);
            }
        }
        ArrayDeque<Integer> q = new ArrayDeque<>();
        long[] E = new long[N+1], S = new long[N+1];
        Arrays.fill(E, 1);
        Arrays.fill(S, 1);
        for (int i = 1; i <= N; i++) if (I[i] == 0) q.add(i);
        while (!q.isEmpty()) {
            int x = q.pollFirst();
            for (int i: G[x]) {
                E[i] = (E[i]+E[x])%MOD;
                if (--I[i] == 0) q.add(i);
            }
        }
        for (int i = 1; i <= N; i++) if (RI[i] == 0) q.add(i);
        while (!q.isEmpty()) {
            int x = q.pollFirst();
            for (int i: RG[x]) {
                S[i] = (S[i]+S[x])%MOD;
                if (--RI[i] == 0) q.add(i);
            }
        }
        long total = (Arrays.stream(E).sum()-1)%MOD;
        for (int qq = 0; qq < Q; qq++) {
            int u = ini(), v = ini();
            if (W[u] == W[v]) {
                bw.write(total + "\n");
                continue;
            }
            if (W[u] > W[v]) {
                int tmp = u;
                u = v;
                v = tmp;
            }
            if (G[u].contains(v)) {
                // off
                bw.write((total-(E[u]*S[v])%MOD+MOD)%MOD + "\n");
            } else {
                // on
                bw.write((total+(E[u]*S[v])%MOD)%MOD + "\n");
            }
        }

	    bw.flush();
        bw.close();
        br.close();
    }
}
