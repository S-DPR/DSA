package Main;
import java.io.*;
import java.util.*;
/*
12013번 248게임

수열이 있다. 또, 인접한 같은 수를 합치는 연산을 수행할 수 있다. 이 연산을 수행 시 합쳐진 자리에 합친 수에 1을 더한 값이 나오게 된다.
이 연산을 적절히 수행하여 만들 수 있는 가장 큰 수를 출력해보자.

이게 대체 뭔 문젠가 해서 북마크에 넣어놨는데, 가만 보니 파일합치기 류 dp였네요.
파일 합치기 풀 때는 몰랐는데, 생각해보니 파일 합치기도 구간dp였습니다.
구간dp는 재귀이용한 1가지 방법만 말하는건지 알았는데, 파일 합치기 류 dp도 구간 dp라고 할 수 있겠더라고요.
어쨌든 그 아이디어 활용하면 AC입니다. 파일 합치기 보면 O(N^2) 최적화도 있다곤 하는데 공부하기 귀찮아서..
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
		int[][] dp = new int[N][N];
		for (int i = 0; i < N; i++)
			dp[i][i] = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < N; i++) {
			for (int j = i-1; j >= 0; j--) {
				for (int k = 1; j+k <= i; k++) {
					if (dp[j][i-k] > 0 && dp[i-k+1][i] > 0 && dp[j][i-k] == dp[i-k+1][i])
						dp[j][i] = Math.max(dp[j][i], dp[i-k+1][i]+1);
				}
			}
		}
		System.out.println(Arrays.stream(dp).mapToInt(i -> Arrays.stream(i).max().orElse(0)).max().orElse(-1));
		
		br.close();
		bw.flush();
		bw.close();
	}
}