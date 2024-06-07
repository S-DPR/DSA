import java.io.*;
import java.util.*;
/*
16137번 견우와 직녀

맵이 주어진다. (0, 0)에서 (N-1, N-1)로 가는 최단시간을 구해보자.
규칙은 아래와 같다.
- 1은 항상 지나갈 수 있는 '땅'이다.
- 2 이상의 칸이 있는 수를 지날때는 이 칸을 지나가는 시간이 이 수의 배수여야 한다.
- 0은 갈 수 없는 곳이다.
- 최대 하나의 0을 수 K로 바꿀 수 있다.
 > 이 때, 바꾸는 칸은 위아래 혹은 좌우가 1이어야 한다.

그냥 구현 BFS.
오랜만에 BFS 했네요..
제가 고른건 아니고, 그냥 solved.ac에 랜덤마라톤 생겼고,
그중 제일 어려운게 이거라 해볼만한데 해서 해봤습니다.

열심히 구현하면 됩니다.
솔직히 어려운거 없어요. 이런거 골드1, 플레5 BFS여도 솔직히 다른거에 비해 어렵진 않은데 골드2는..
그냥 문제 이해가 제일 어려웠습니다.
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
        int N = ini(), K = ini();
        int[][] M = new int[N][N];
        int[][][] V = new int[N][N][2];
        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++)
                M[r][c] = ini();
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[] {0, 0, 0, 0});
        int[] dr = new int[] {1, -1, 0, 0, 0};
        int[] dc = new int[] {0, 0, 1, -1, 0};
        while (!q.isEmpty()) {
            int[] cur = q.pollFirst();
            int r = cur[0], c = cur[1], t = cur[2], p = cur[3];
            if (N-1 == r && N-1 == c) {
                System.out.println(t);
                return;
            }
            for (int i = 0; i < 5; i++) {
                int nr = r+dr[i], nc = c+dc[i], np = p;
                if (!(0 <= nr && nr < N)) continue;
                if (!(0 <= nc && nc < N)) continue;
                if ((V[nr][nc][np]&(1<<((t+1)%20))) != 0) continue;
                if (M[nr][nc] != 1) {
                    if (M[r][c] != 1) continue;
                    if (M[nr][nc] == 0) {
                        if (np != 0) continue;
                        boolean isVaild = false;
                        isVaild = isVaild || ((0 > nr-1 || M[nr-1][nc] == 1) && (nr+1 >= N || M[nr+1][nc] == 1));
                        isVaild = isVaild || ((0 > nc-1 || M[nr][nc-1] == 1) && (nc+1 >= N || M[nr][nc+1] == 1));
                        if (!isVaild) continue;
                        if ((t+1)%K != 0) continue;
                        np = 1;
                    } else {
                        if ((t+1)%M[nr][nc] != 0) continue;
                    }
                }
                q.add(new int[] {nr, nc, t+1, np});
                V[nr][nc][np] |= 1<<((t+1)%20);
            }
        }
        System.out.println(-1);

	    bw.flush();
        bw.close();
        br.close();
    }
}
