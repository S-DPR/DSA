import java.io.*;
import java.util.*;
/*
9623번 부분 수열의 길이

수열 A가 주어진다.
A의 연속한 부분 수열 중, 합이 x를 넘고 그중 가장 짧은 수열의 길이를 구해보자.

문제 보고나서 알고있는 알고리즘 하나씩 꺼내봤습니다.
"음.. 먼저 누적합.. 이거 꽤 유용하겠는데..
다음으로.. 매개변수탐색.. 원소가 음수일수도 있어서 K가 된다고 무조건 K이하가 다 되진 않네.. 탈락..
dp.. 이건 항상 매력적이긴한데 50만개에 dp면 보통 2N인데 이건 좀 힘들거같고..
그래프..는 아닌거같고..
세그트리..는 킹만한데.."

그렇게 생각하다가 A[i]에 대해서 A[j] (1 <= j < i) 중 A[i]-x보다 작은 가장 큰 j를 구하면 되겠구나..
아.. 세그트리 또 너야?

이렇게 풀었습니다.
재귀세그트리는 오랜만에 구현해서 한번 틀려버렸네요.
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

	static int N;
	static long[] segT;

	public static void update(int n, int l, int r, int idx, long val) {
		if (!(l <= idx && idx <= r)) return;
		if (l == r) {
			segT[n] = val;
			return;
		}
		int mid = (l + r) >> 1;
		update(n*2, l, mid, idx, val);
		update(n*2+1, mid+1, r, idx, val);
		segT[n] = Math.min(segT[n*2], segT[n*2+1]);
	}

	public static int query(int n, int l, int r, int s, int e, long x) {
		if (l == r) return l;
		int mid = (l + r) >> 1;
		if (segT[n*2+1] <= x)
			return query(n*2+1, mid+1, r, s, e, x);
		if (segT[n*2] <= x)
			return query(n*2, l, mid, s, e, x);
		return -1;
	}

	public static void main(String[] args) throws IOException {
		int T = sti();
		for (int tc = 0; tc < T; tc++) {
			int N = sti();
			long x = stl();
			long[] A = new long[N+1];
			segT = new long[N<<2];
			Arrays.fill(segT, 1L<<60);
			int ret = N+1;
			for (int i = 1; i <= N; i++) {
				A[i] = A[i-1] + stl();
				if (x <= A[i]) ret = Math.min(ret, i);
				if (N == 1) continue;
				int prv = query(1, 1, N, 1, N, A[i]-x);
				update(1, 1, N, i, A[i]);
				if (prv == -1) continue;
				ret = Math.min(ret, i-prv);
			}
			bw.write((ret == N+1 ? -1 : ret) + "\n");
		}

        bw.flush();
        bw.close();
        br.close();
    }
}
