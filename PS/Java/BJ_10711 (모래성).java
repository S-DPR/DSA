import java.io.*;
import java.util.*;
/*
10711번 모래성

.과 숫자로 이루어진 맵이 주어진다.
숫자는 매 초마다 주변의 .의 수를 센다. 만약 .의 수가 숫자 이상이라면 숫자는 .으로 변한다.
이 행위를 몇 번 할 때부터 변화가 없게될까?

쉬운 BFS.
이정도면 골드2중 날먹수준이죠..
대충 직관적으로 구현해주면 됩니다. 맵 크기가 1000*1000이라 시간초과만 조심.
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

    public static List<Integer>[][] createEmptyMap() {
        List<Integer>[][] map = new List[5][5];
        for (int r = 1; r <= 4; r++) {
            for (int c = 1; c <= 4; c++) {
                map[r][c] = new ArrayList<>();
            }
        }
        return map;
    }

	public static void main(String[] args) throws IOException {
        int R = ini(), C = ini();
        int[][] M = new int[R][C];
        int[][] cnt = new int[R][C];
        boolean[][] V = new boolean[R][C];
        ArrayDeque<Integer[]> deq = new ArrayDeque<>();
        for (int r = 0; r < R; r++) {
            String s = br.readLine();
            for (int c = 0; c < C; c++) {
                M[r][c] = s.charAt(c) == '.' ? 0 : s.charAt(c)-'0';
            }
        }
        int[] dr = new int[] { -1, -1, 0, 1, 1, 1, 0, -1 };
        int[] dc = new int[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (M[r][c] != 0) continue;
                for (int i = 0; i < 8; i++) {
                    int nr = r+dr[i], nc = c+dc[i];
                    if (!(0 <= nr && nr < R)) continue;
                    if (!(0 <= nc && nc < C)) continue;
                    cnt[nr][nc]++;
                }
                V[r][c] = true;
            }
        }
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (!V[r][c] && M[r][c] <= cnt[r][c]) {
                    deq.add(new Integer[] { r, c });
                    V[r][c] = true;
                }
            }
        }
        int t = 0;
        while (true) {
            if (deq.isEmpty()) break;
            ArrayDeque<Integer[]> newd = new ArrayDeque<>();
            t++;
            while (!deq.isEmpty()) {
                Integer[] x = deq.pollFirst();
                int r = x[0], c = x[1];
                for (int i = 0; i < 8; i++) {
                    int nr = r+dr[i], nc = c+dc[i];
                    if (!(0 <= nr && nr < R)) continue;
                    if (!(0 <= nc && nc < C)) continue;
                    cnt[nr][nc]++;
                    if (!V[nr][nc] && M[nr][nc] <= cnt[nr][nc]) {
                        V[nr][nc] = true;
                        newd.add(new Integer[] { nr, nc });
                    }
                }
            }
            deq = newd;
        }
        System.out.println(t);

	    bw.flush();
        bw.close();
        br.close();
    }
}
