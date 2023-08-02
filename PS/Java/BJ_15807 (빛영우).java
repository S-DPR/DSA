package Main;
import java.io.*;
import java.util.*;
/*
15807번 *빛*영*우*

조명이 N개 설치된다. 이 조명은 각각 밝기 1을 가지며, y좌표가 커지는 방향으로 기울기 1, -1을 가지며 빛을 발산한다.
빛의 세기는 줄어들지 않고 조명이 겹치는 부분은 겹쳐진만큼 밝기가 강해진다 할 때,
좌표 (x, y)에 대해 조명의 밝기를 구해보자.

이게 dp였다고?
이게??

저는 스위핑으로 풀었습니다. 허 참 이게 dp래 거짓말마 진짜..
사실 스위핑은 그렇게 복잡한 로직도 아닌데..
그냥 설치된 지점을 기준으로, 가능한 좌측 상단과 우측 상단부분 바로 아래에 -1로 점을 찍어줍니다.
그리고 최상단에, 닿는 가장 작은 x좌표쪽에 1을 찍고 우측에는 -1을 찍습니다.
다음으로, 각 배열에 대해 스위핑을 실행합니다.
이러면 O(3N^2)으로 처리가 됩니다.

dp는 아예 안떠올랐고.. 스위핑은 방법은 금방 떠올렸는데 좌표계산에서 절망적인 난이도를 당해서..
좀 어려웠네요.
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
		int E = 3000;
		int[][] P = new int[E+3][E+3];
		int[][] LM = new int[E+3][E+3];
		int[][] RM = new int[E+3][E+3];
		int[][] R = new int[E+3][E+3];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int u = sti(st), v = sti(st);
			u += E>>1;
			v = (-1*v) + (E>>1);
			P[0][Math.max(u-v, 0)]++;
			P[0][Math.min(u+v+2, E+1)]--;
			RM[v+1][u]++;
			RM[Math.max(v-(E-u), 0)][Math.min(v+u+1, E+1)]--;
			LM[v+2][u+1]++;
			LM[Math.max(v-u, 0)+1][Math.max(u-v, 0)]--;
		}
		for (int i = 1; i <= E+2; i++)
			P[0][i] += P[0][i-1];
		for (int i = 1; i <= E+2; i++) {
			for (int j = 0; j <= E+2; j++) {
				if (j <= E) RM[i][j] += RM[i-1][j+1];
				if (j >= 1) LM[i][j] += LM[i-1][j-1];
			}
		}
		for (int i = 0; i <= E+2; i++) {
			for (int j = 0; j <= E+2; j++) {
				R[i][j] = LM[i][j] + RM[i][j] + P[i][j];
				if (i >= 1) R[i][j] += R[i-1][j];
			}
		}
		int Q = Integer.parseInt(br.readLine());
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			int u = sti(st), v = sti(st);
			u += E>>1;
			v = (-1*v) + (E>>1);
			bw.write(R[v][u] + "\n");
		}
		
		br.close();
		bw.flush();
		bw.close();
	}
}
