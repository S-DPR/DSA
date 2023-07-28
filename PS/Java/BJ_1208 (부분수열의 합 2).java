package Main;
import java.io.*;
import java.util.*;
/*
1208번 부분수열의 합 2

길이가 N인 수열의 부분수열 중 값을 모두 더한 값이 K인 부분수열의 개수를 구해보자.

이야, MITM.
오랜만이죠 그조.
저는 문제 하나에 시간박다가 결국 자정을 넘겨버렸습니다..
내 잔디..

그냥 MITM 굴리면 됩니다. 쉽죠.
비트마스킹DP에서 배운 기술을 이용해서 모든 경우의수 구해주고.
맵이 아니라 이분탐색으로 모두 구해줍니다.
그러면 AC.
*/
public class Main {
	static int INF = 1<<30;
	static int N;
	static long K;
	static long[] left, right;
	static boolean[] V;
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	public static long stl(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
	public static int bisectLeft(long[] arr, long x) {
		int l = 0, r = arr.length;
		while (l < r) {
			int m = (l + r) >> 1;
			if (arr[m] >= x) r = m;
			else l = m + 1;
		}
		return r;
	}
	public static int bisectRight(long[] arr, long x) {
		int l = 0, r = arr.length;
		while (l < r) {
			int m = (l + r) >> 1;
			if (arr[m] > x) r = m;
			else l = m + 1;
		}
		return r;
	}
	public static void dfs(long[] def, long[] arr, int idx, long val, int vis, int plus) {
		if (V[vis]) return;
		V[vis] = true;
		arr[vis] = val;
		for (int i = 0; i < ((N/2)+plus); i++) {
			if ((vis&(1<<i)) != 0) continue;
			dfs(def, arr, idx, val+def[i+idx], vis|(1<<i), plus);
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = sti(st); K = stl(st);
		long[] ori = new long[40];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++)
			ori[i] = stl(st);
		left = new long[1<<(N/2)];
		right = new long[1<<(N/2+N%2)];
		V = new boolean[1<<(N/2)];
		dfs(ori, left, 0, 0, 0, 0);
		V = new boolean[1<<(N/2+N%2)];
		dfs(ori, right, N/2, 0, 0, N%2);
		Arrays.sort(right);
		long ret = 0;
		for (int i = 0; i < (1<<(N/2)); i++) {
			int l = bisectLeft(right, K-left[i]);
			int r = bisectRight(right, K-left[i]);
			ret += r-l;
		}
		if (K == 0) ret--;
		System.out.println(ret);
		
		br.close();
		bw.flush();
		bw.close();
	}
}