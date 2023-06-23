package Main;
import java.io.*;
import java.util.*;
/*
17258번 인기가 넘쳐흘러

N개 선의 시작점과 끝점이 주어진다.
여기에 최대 K개의 선을 더 그으려 한다. 단, 한 점에 대해 원래 있던 선의 개수가 T개를 넘는다면 이번에 그은 선들은 사라지게 된다.
(이번에 새로 그은 선은 여기서의 T개에 포함하지 않는다.)
이 때, 선이 T개 이상이 되는 정수 좌표의 개수를 구해보자. 이 때는 새로 그은 선도 개수에 포함한다.

또 나만 어렵게 풀었지 또
스위핑+누적합+배낭 굴렸더니 또 이제 그냥 dp만 써도 풀리는 문제래요 막
그냥 뭐.. 진짜 스위핑에 배낭에 누적합 굴리면 일단 풀립니다.
답지좀 보고 dp로 다시 해봐야겠네..
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
		
		st = new StringTokenizer(br.readLine());
		int N = sti(st), M = sti(st), K = sti(st), T = sti(st);
		int[] timeLine = new int[N+3];
		int[] dp = new int[K+1];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int s = sti(st), e = sti(st);
			timeLine[s]++;
			timeLine[e]--;
		}
		int ret = 0;
		int[] value = new int[K+1];
		boolean isIncreasing = true;
		for (int i = 1; i <= N+1; i++) {
			timeLine[i+1] += timeLine[i];
			timeLine[i] = T-timeLine[i];
			if ((timeLine[i] <= 0 && isIncreasing) || i == N+1) {
				for (int j = 1; j <= K; j++)
					value[j] += value[j-1];
				int[] newdp = dp.clone();
				for (int j = K; j > 0; j--) {
					for (int k = K-j; k >= 0; k--)
						if (dp[k] != 0)
							newdp[k+j] = Math.max(newdp[k+j], dp[k]+value[j]);
					newdp[j] = Math.max(newdp[j], value[j]);
				}
				dp = newdp;
				for (int j = 1; j <= K; j++)
					value[j] = 0;
				isIncreasing = false;
			} else if (1 <= timeLine[i] && timeLine[i] <= K) {
				value[timeLine[i]]++;
				isIncreasing = true;
			}
			if (!isIncreasing && i != N+1) ret++;
		}
		System.out.println(ret + Arrays.stream(dp).max().getAsInt());
		
		br.close();
		bw.flush();
		bw.close();
	}
}