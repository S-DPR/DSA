import java.io.*;
import java.util.*;
/*
7344번 나무 막대

길이 L과 무게 W로 이루어진 나무막대가 N개 있다.
이 나무막대를 조각하려는데 이번에 조각할 것이 이전에 조작할 것보다 무게가 같거나 크고 길이가 같거나 길다면 비용을 지불하지 않아도 된다.
최소 몇 번 비용을 지불해야할까?

이거 어떻게하냐 생각하다가..
일단 그리디는 맞는거같고.. 길이순 정렬은 맞는거같고..
트리맵써야하나..? 하다가 태그 까보니까 정렬그리디가 끝이네?

아니 대체 머지? 근데 보니까 N이 최대 5000이네?
어? N^2이 되네? 그러면 넉넉하게 완탐 해도 되겠는데?
하고 AC. 최적화해야했으면 더 어려웠을 문제같네요. 한 10만이었다면..
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
