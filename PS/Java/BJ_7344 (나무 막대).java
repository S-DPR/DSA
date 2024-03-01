import java.io.*;
import java.util.*;

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

	public static void main(String[] args) throws IOException {
		int T = ini();
		for (int tc = 0; tc < T; tc++) {
			int N = ini();
			int[][] A = new int[N][2];
			for (int i = 0; i < N; i++) {
				A[i] = new int[] { ini(), ini() };
			}
			Arrays.sort(A, (i, j) -> {
				return i[0] != j[0] ? i[0]-j[0] : i[1]-j[1];
			});
			boolean[] vis = new boolean[N];
			int cnt = 0;
			while (true) {
				boolean isChange = false;
				int cur = -10001;
				for (int i = 0; i < N; i++) {
					if (vis[i]) continue;
					if (cur <= A[i][1]) {
						vis[i] = true;
						cur = A[i][1];
						isChange = true;
					}
				}
				if (!isChange) break;
				cnt++;
			}
			bw.write(cnt + "\n");
		}

        bw.flush();
        bw.close();
        br.close();
    }
}
