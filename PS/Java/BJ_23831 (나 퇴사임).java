package Main;
import java.io.*;
import java.util.*;
/*
23831번 나 퇴사임?

당신은 자습시간에 4가지 선택이 가능하다. : 자습실 자습, 큰자습실 자습, 휴게실 자습, 요양
하지만 N일동안 최소 B번은 자습실류에서 자습을 해야하며,
최대 A번까지만 요양을 할 수 있다.
마지막으로 휴게실에서 2일 연속으로 자습할 수 없다.
첫줄에 N이, 두번째줄에 A, B가, 세번째줄부터 p, q, r, s가 N줄동안 주어진다.
3번째줄부터는 i-2일에 각각 p는 자습실, q는 큰자습실, r은 휴게실, s는 요양할 때 얻을 수 있는 만족도이다.
조건을 만족하는 최대 만족도를 구해보자.

골때리는 dp
3차원 dp는 본 적이 없는데 결국 보게되네..
날짜도 포함할거면 4차원dp가 됩니다. 그래서 조건만 구현하면 되는 간단한 dp치고 난이도가 높아진 케이스.
어렵다..보다는 이거 틀리면 진짜 꿈도 희망도 없는데, 라는 구현 풀 때 생각이 나는 문제였습니다.
*/
public class Main {
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	public static long stl(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		int A = sti(st), B = sti(st);
		// dp[i][j][2] : 자습 i번, 요양신청 j번, 0, 1: 휴게여부
		int[][][] dp = new int[B+1][A+1][2];
		for (int i = 0; i <= B; i++)
		    for (int j = 0; j <= A; j++)
		        for (int k = 0; k < 2; k++)
		            dp[i][j][k] = -1;
		dp[0][0][0] = 0;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int p = sti(st), q = sti(st), r = sti(st), s = sti(st);
			int[][][] dpNew = new int[B+1][A+1][2];
			for (int x = 0; x <= B; x++)
				for (int y = 0; y <= A; y++)
					for (int z = 0; z < 2; z++)
						dpNew[x][y][z] = -1;
			// 이번에 공부 할 때 최댓값
			for (int study = 0; study <= B; study++) {
				for (int care = 0; care <= A; care++) {
					if (dp[study][care][0] == -1 && dp[study][care][1] == -1) continue;
					int cur = dpNew[Math.min(B, study+1)][care][0];
					int next = Math.max(dp[study][care][0], dp[study][care][1]) + Math.max(p, q);
					dpNew[Math.min(B, study+1)][care][0] = Math.max(cur, next);
				}
			}
			// 이번에 요양 할 때 최댓값
			for (int care = 0; care < A; care++) {
				for (int study = 0; study <= B; study++) {
					if (dp[study][care][0] == -1 && dp[study][care][1] == -1) continue;
					int cur = dpNew[study][care+1][0];
					int next = Math.max(dp[study][care][0], dp[study][care][1])+s;
					dpNew[study][care+1][0] = Math.max(cur, next);
				}
			}
			// 이번에 자습실 공부 할 때 최댓값
			for (int care = 0; care <= A; care++) {
				for (int study = 0; study <= B; study++) {
					if (dp[study][care][0] == -1) continue;
					int cur = dpNew[study][care][1];
					int next = dp[study][care][0]+r;
					dpNew[study][care][1] = Math.max(cur, next);
				}
			}
			dp = dpNew;
		}
		int max = 0;
		for (int i = 0; i <= A; i++)
			for (int j = 0; j < 2; j++)
				max = Math.max(max, dp[B][i][j]);
		System.out.println(max);
		
		br.close();
		bw.flush();
		bw.close();
	}
}