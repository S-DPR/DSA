import java.io.*;
import java.util.*;
/*
1799번 비숍

1로 된 칸에 비숍을 둘 수 있다.
비숍끼리 공격하도록 두지 않으려면 최대 몇 개 둘 수 있을까?

와
흑백 나눠서 둔다는 생각을 못했네 ㅋㅋ
유사 MITM. 그 외에는 전부 백트래킹입니다.
오.. 생각도 못했어.
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
    
    static int N;
    static int[][][] V;
    static int[][] M;
    static int cnt, ret;

    public static void loop(int r, int c, int k) {
        for (int nr = r; nr <= N; nr++) {
            for (int nc = (nr^k)&1; nc <= N; nc += 2) {
                if (M[nr][nc] != 1) continue;
                int upLeftR = Math.max(0, nr-nc);
                int upLeftC = Math.max(0, nc-nr);
                int upRightR = Math.max(0, nr-(N-nc+1));
                int upRightC = Math.min(N+1, nr+nc);

                int downLeftR = Math.min(N+1, nr+nc);
                int downLeftC = Math.max(0, nc-(N-nr+1));
                int downRightR = Math.min(N+1, nr+(N-nc+1));
                int downRightC = Math.min(N+1, nc+(N-nr+1));
                // System.out.println(nr + " " + nc + " " + upLeftR + " " + upLeftC + " " + upRightR + " " +upRightC + " " + downLeftR + " " + downLeftC + " " + downRightR + " " + downRightC);
                if (V[0][upLeftR][upLeftC] == 1) continue;
                if (V[1][upRightR][upRightC] == 1) continue;
                if (V[2][downLeftR][downLeftC] == 1) continue;
                if (V[3][downRightR][downRightC] == 1) continue;
                M[r][c] = 2;
                ret = Math.max(ret, ++cnt);
                V[0][upLeftR][upLeftC] = 1;
                V[1][upRightR][upRightC] = 1;
                V[2][downLeftR][downLeftC] = 1;
                V[3][downRightR][downRightC] = 1;
                // for (int rr = 0; rr <= N+1; rr++) {
                //     for (int cc = 0; cc <= N+1; cc++) {
                //         System.out.print(M[rr][cc] + " ");
                //     }
                //     System.out.println();
                // }
                // System.out.println();
                loop(nr, nc, k);
                V[0][upLeftR][upLeftC] = 0;
                V[1][upRightR][upRightC] = 0;
                V[2][downLeftR][downLeftC] = 0;
                V[3][downRightR][downRightC] = 0;
                M[r][c] = 1;
                cnt--;
            }
        }
    }

	public static void main(String[] args) throws IOException {
        N = ini();
        M = new int[N+2][N+2];
        V = new int[4][N+2][N+2];
        for (int r = 1; r <= N; r++)
            for(int c = 1; c <= N; c++)
                M[r][c] = ini();
        boolean flg = false, flg2 = false;
        int ret1 = 0, ret2 = 0;
        for (int r = 1; !(flg && flg2) && r <= N; r++) {
            for(int c = 1; !(flg && flg2) && c <= N; c++) {
                if (M[r][c] == 0) continue;
                if (!flg && (r+c)%2 == 0) {
                    loop(r, c, 0);
                    flg = true;
                    ret1 = ret;
                    ret = 0;
                }
                if (!flg2 && (r+c)%2 == 1) {
                    loop(r, c, 1);
                    flg2 = true;
                    ret2 = ret;
                    ret = 0;
                }
            }
        }
        System.out.println(ret1 + ret2);

	    bw.flush();
        bw.close();
        br.close();
    }
}
