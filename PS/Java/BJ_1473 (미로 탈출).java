import java.io.*;
import java.util.*;
/*
1473번 미로 탈출

A, B, C, D로 이루어진 맵이 주어진다.
A는 모든 곳에 벽이 없고, B는 모든 곳에 벽이 있고,
C는 위아래에 벽이 없고, D는 양옆에 벽이 없음을 나타낸다.
한 칸을 이동하는데는 1의 시간이 걸리고,
1의 시간을 소모해 버튼을 눌러 현재 있는 행과 열을 90도 돌릴 수 있다.
(0, 0)에서 (R-1, C-1)로 가려고 한다. 최소 시간은 몇일까?

쉽지 않은 난이도?
쭉 풀었는데.. 비트마스킹 실수를 한 번 해버려서 한 시간을 소모했습니다.
그리고.. 전체적으로 하드코딩을 살짝 한 면이 있어서 아쉽긴하네요.

그냥 돌아간거는 비트마스킹으로 처리해주면 됩니다.
그거 하나 처리하고 돌아가는거 for문 이후에 하나 추가해주면 끝나요.
참.. 이렇게보면 간단한데..
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int sti() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long stl() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}

	public static void main(String[] args) throws IOException {
		int R = sti(), C = sti();
		char[][] M = new char[R][C];
		for (int i = 0; i < R; i++)
			M[i] = br.readLine().toCharArray();
		int[] dr = new int[]{1, -1, 0, 0};
		int[] dc = new int[]{0, 0, 1, -1};
		int[][][][] V = new int[R][C][1<<R][1<<C];
		ArrayDeque<Info> Q = new ArrayDeque<>();
		Q.add(new Info(0, 0, 0, 0, 0));
		while (!Q.isEmpty()) {
			Info N = Q.pollFirst();
			char curTile = M[N.r][N.c];
			if (curTile == 'B') continue;
			boolean rotateR = (N.rv&(1<<N.r)) != 0;
			boolean rotateC = (N.cv&(1<<N.c)) != 0;
			if ((rotateR && !rotateC) || (!rotateR && rotateC)) {
				if (curTile == 'C') curTile = 'D';
				else if (curTile == 'D') curTile = 'C';
			}
			for (int i = 0; i < 4; i++) {
				int nxtR = N.r+dr[i];
				int nxtC = N.c+dc[i];
				if (!(0 <= nxtR && nxtR < R)) continue;
				if (!(0 <= nxtC && nxtC < C)) continue;
				char nxtTile = M[nxtR][nxtC];
				boolean rotateNxtR = (N.rv&(1<<nxtR)) != 0;
				boolean rotateNxtC = (N.cv&(1<<nxtC)) != 0;
				if ((rotateNxtR && !rotateNxtC) || (!rotateNxtR && rotateNxtC)) {
					if (nxtTile == 'C') nxtTile = 'D';
					else if (nxtTile == 'D') nxtTile = 'C';
				}
				if (nxtTile == 'B') continue;
				if (V[nxtR][nxtC][N.rv][N.cv] != 0) continue;
				if (curTile == 'C' && nxtTile == 'D') continue;
				if (curTile == 'D' && nxtTile == 'C') continue;
				if ((nxtTile == 'C' || curTile == 'C') && i >= 2) continue;
				if ((nxtTile == 'D' || curTile == 'D') && i < 2) continue;
				V[nxtR][nxtC][N.rv][N.cv] = N.t+1;
				Q.add(new Info(nxtR, nxtC, N.rv, N.cv, N.t+1));
			}
			if (V[N.r][N.c][N.rv^(1<<N.r)][N.cv^(1<<N.c)] != 0) continue;
			V[N.r][N.c][N.rv^(1<<N.r)][N.cv^(1<<N.c)] = N.t+1;
			Q.add(new Info(N.r, N.c, N.rv^(1<<N.r), N.cv^(1<<N.c), N.t+1));
		}
		int ret = 1<<30;
		for (int i = 0; i < 1<<R; i++)
			for (int j = 0; j < 1<<C; j++)
				if (V[R-1][C-1][i][j] != 0)
					ret = Math.min(ret, V[R-1][C-1][i][j]);
		System.out.println(ret == 1<<30 ? -1 : ret);

        bw.flush();
        bw.close();
        br.close();
    }
}

class Info {
	int r, c, rv, cv, t;
	Info(int r, int c, int rv, int cv, int t) {
		this.r = r;
		this.c = c;
		this.rv = rv;
		this.cv = cv;
		this.t = t;
	}
}
