import java.io.*;
import java.util.*;
/*
3108번 로고

2차원 평면상에 직사각형이 N개 그려져있다.
한붓그리기를 하면 몇 번 연필을 떼어야할까?

분리집합 기하학이어서 튀려했는데 생각해보니 흥미로워서 해본 문제.
그냥 dfs 긁으면 될줄알았는데, 사각형 내부에 바로 사각형이 있는 예외를 인터넷에서 찾아서..
암튼 그래서 인터넷에서 풀이가 신박해서 해봤습니다.

바로 비트마스킹으로 해당 칸에서 갈 수 있는 방향 표현하기.
흥미롭더라고요? 그래서 한번 구현해봤습니다.
쓸데가 있을진 모르겠지만..
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
        int N = ini();
        int[][] M = new int[1001][1001];
        boolean[][] V = new boolean[1001][1001];
        for (int i = 0; i < N; i++) {
            int x1 = ini()+500, y1 = ini()+500;
            int x2 = ini()+500, y2 = ini()+500;
            for (int x = x1; x <= x2; x++) {
                if (x != x2) {
                    M[y1][x] |= 1<<2;
                    M[y2][x] |= 1<<2;
                }
                if (x != x1) {
                    M[y1][x] |= 1<<3;
                    M[y2][x] |= 1<<3;
                }
            }
            for (int y = y1; y <= y2; y++) {
                if (y != y1) {
                    M[y][x1] |= 1<<1;
                    M[y][x2] |= 1<<1;
                }
                if (y != y2) {
                    M[y][x1] |= 1<<0;
                    M[y][x2] |= 1<<0;
                }
            }
        }
        int ret = 0;
        int[] dr = new int[] {1, -1, 0, 0};
        int[] dc = new int[] {0, 0, 1, -1};
        for (int r = 0; r < 1001; r++) {
            for (int c = 0; c < 1001; c++) {
                if (M[r][c] == 0 || V[r][c]) continue;
                ArrayDeque<int[]> q = new ArrayDeque<>();
                q.add(new int[] {r, c});
                boolean no = false;
                V[r][c] = true;
                while (!q.isEmpty()) {
                    int[] cur = q.pollFirst();
                    int rr = cur[0], cc = cur[1];
                    no = no || (rr == 500 && cc == 500);
                    for (int i = 0; i < 4; i++) {
                        int nr = rr+dr[i];
                        int nc = cc+dc[i];
                        if (!(0 <= nr && nr < 1001)) continue;
                        if (!(0 <= nc && nc < 1001)) continue;
                        if ((M[rr][cc]&(1<<i)) == 0) continue;
                        if (V[nr][nc] || M[nr][nc] == 0) continue;
                        V[nr][nc] = true;
                        q.add(new int[] {nr, nc});
                    }
                }
                if (!no) ret++;
            }
        }
        System.out.println(ret);

	    bw.flush();
        bw.close();
        br.close();
    }
}
