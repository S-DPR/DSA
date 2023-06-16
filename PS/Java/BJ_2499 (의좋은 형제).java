package Main;
import java.io.*;
import java.util.*;
/*
2499번 의좋은 형제

N*N 행렬이 주어진다. 이 행렬을 T라 하자. 길이 N의 새 수열을 만들려 한다. 새 수열을 A라고 하자.
sigma(i->N){sum(T[:A[i]])}의 값을 구해보자.
단, 새 수열 A는 단조 증가하는 수열이어야 한다.

내가 짠 코드지만 코드 진짜 개드럽네

2차원에 배낭 잘 때리고, for문 적절하게 잘 쓰면 됩니다.
이게.. 그냥 음..
N이 20 이하입니다. 엄청 작죠. 그래서 최적화고 나발이고 배낭만 쓰면 됩니다.
아, 그래. 배낭 역추적입니다. 그거 하나정돈 신경써주자고요.
*/
class Info {
	int prvVal, prv, cur;
	boolean update;
	Info(int prv, int cur, int prvVal, boolean update) {
		this.prv = prv;
		this.cur = cur;
		this.prvVal = prvVal;
		this.update = update;
	}
}

public class Main {
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	public static long stl(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
	public static void backT(Info[][] trace, int[][] M, int cur, int N, int totalN) {
		if (N == 0) return;
		backT(trace, M, trace[N][cur].prvVal, N-1, totalN);
		if (N-1 == 0) System.out.printf("%d ", totalN-trace[N][cur].prv);
		System.out.printf("%d ", totalN-trace[N][cur].cur);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		
		int sum = 0;
		int N = Integer.parseInt(br.readLine());
		Vector<Vector<HashSet<Integer>>> dp = new Vector<Vector<HashSet<Integer>>>();
		Info[][] trace = new Info[N][N*N*100+1];
		int[][] M = new int[N][N];
		for (int i = 0; i < N; i++) {
			dp.add(new Vector<HashSet<Integer>>());
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				dp.get(i).add(new HashSet<Integer>());
				M[i][j] = sti(st);
				sum += M[i][j];
			}
		}
		for (int i = N-1; i > 0; i--)
			for (int j = 0; j < N; j++)
				M[i-1][j] += M[i][j];
		for (int i = 0; i < N; i++)
			for (int j = 0; j <= N*N*100; j++)
				trace[i][j] = new Info(N, N, N, false);
		for (int i = 0; i < N; i++)
			dp.get(N-1).get(i).add(0);
		for (int i = 0; i < N; i++) {
			for (int j = N-1; j >= 0; j--) {
				for (int k = N-1; k >= j && i != 0; k--) {
					for (int t: dp.get(k).get(i-1)) {
						int cur = t+M[j][i];
						if (dp.get(j).get(i).contains(cur)) continue;
						if (trace[i][cur].update) continue;
						dp.get(j).get(i).add(cur);
						trace[i][cur] = t == 0 ? new Info(N, j, t, true) : new Info(k, j, t, true);
					}
				}
				dp.get(j).get(i).add(M[j][i]);
			}
		}
		int ret = 1_000_000_000;
		int diff = 1_000_000_000;
		for (int i = 0; i < N; i++) {
			for (int x: dp.get(i).get(N-1)) {
				if (Math.abs(Math.abs(sum-x)-x) < diff) {
					diff = Math.abs(Math.abs(sum-x)-x);
					ret = x;
				}
			}
		}
		System.out.println(diff);
		backT(trace, M, ret, N-1, N);
		
		br.close();
		bw.flush();
		bw.close();
	}
}