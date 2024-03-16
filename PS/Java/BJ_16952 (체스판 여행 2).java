import java.io.*;
import java.util.*;
/*
16952번 체스판 여행 2

체스판에 1 2 ... N*N이 써져있고, 1에서 아무말이나 놓고 시작한다.
말은 비숍, 룩, 나이트가 있으며 옮길때 1의 비용이 든다.
원한다면 현재 말을 바꿀 수 있다. 이것도 1의 비용이 든다.
이 때, 1에서 N*N까지 가는 최단시간과 그 때 말 바꾸는 최소비용을 구해보자.

하아
마지막에 knight면 break시키기로 해놓고
중간에 continue로직 넣으면 당연히 틀리지...
이거때매 5시간 쳐박았다.. 아..
진짜..난바보야..
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
		int N = ini();
		int[][] M = new int[N][N];
		int[] start = new int[2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				M[i][j] = ini();
				if (M[i][j] == 1) {
					start = new int[] {i, j};
				}
			}
		}
		PriorityQueue<Info> pq = new PriorityQueue<>((i, j) -> {
			if (i.w != j.w)
				return i.w-j.w;
			return i.ch-j.ch;
		});
		int[][] dr = new int[][] {
			{-2, -1, 1, 2, 2, 1, -1, -2},
			{1, 1, -1, -1},
			{1, -1, 0, 0},
		};
		int[][] dc = new int[][] {
			{1, 2, 2, 1, -1, -2, -2, -1},
			{1, -1, 1, -1},
			{0, 0, 1, -1},
		};
		int sz = N*N+2;
		int[][][][] vis = new int[sz][N][N][3];
		int[][][][] change = new int[sz][N][N][3];
		for (int i = 0; i < sz; i++) {
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < N; k++) {
					vis[i][j][k] = new int[] { 1<<30, 1<<30, 1<<30 };
					change[i][j][k] = new int[] { 1<<30, 1<<30, 1<<30 };
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			pq.add(new Info(0, 0, i, 2, start[0], start[1]));
			vis[2][start[0]][start[1]][i] = 0;
			change[2][start[0]][start[1]][i] = 0;
		}
		int retW = 1<<30, retCh = 1<<30;
		while (!pq.isEmpty()) {
			Info cur = pq.poll();
			int curW = cur.w, curCh = cur.ch, curK = cur.k;
			int curO = cur.o, curR = cur.r, curC = cur.c;
			if (curO == N*N+1) {
				if (curW <= retW) {
					retCh = curW < retW ? curCh : Math.min(retCh, curCh);
					retW = curW;
				}
				continue;
			}
			if (vis[curO][curR][curC][curK] < curW) continue;
			if (change[curO][curR][curC][curK] < curCh) continue;
			for (int i = 0; i < dr[curK].length; i++) {
				int nr = curR;
				int nc = curC;
				while (true) {
					nr += dr[curK][i];
					nc += dc[curK][i];
					if (!(0 <= nr && nr < N)) break;
					if (!(0 <= nc && nc < N)) break;
					int nxtO = M[nr][nc] == curO ? curO+1 : curO;
					int thisVis = vis[nxtO][nr][nc][curK];
					if (thisVis >= curW+1) {
						int thisCh = change[nxtO][nr][nc][curK];
						if (!(thisVis == curW+1 && thisCh <= curCh)) {
							change[nxtO][nr][nc][curK] = curW+1 < thisVis ? curCh : Math.min(change[nxtO][nr][nc][curK], curCh);
							vis[nxtO][nr][nc][curK] = curW+1;
							pq.add(new Info(curW+1, curCh, curK, nxtO, nr, nc));
						}
					}
					if (curK == 0) break;
				}
			}
			for (int i = 0; i < 3; i++) {
				int thisVis = vis[curO][curR][curC][i];
				if (thisVis >= curW+1) {
					int thisCh = change[curO][curR][curC][i];
					if (thisVis == curW+1 && thisCh <= curCh+1) continue;
					change[curO][curR][curC][curK] = curW+1 < thisVis ? curCh+1 : Math.min(change[curO][curR][curC][curK], curCh+1);
					vis[curO][curR][curC][i] = curW+1;
					pq.add(new Info(curW+1, curCh+1, i, curO, curR, curC));
				}
			}
		}
		bw.write(retW + " " + retCh);

        bw.flush();
        bw.close();
        br.close();
    }
}

class Info {
	int w, ch, k, o, r, c;
	Info(int w, int ch, int k, int o, int r, int c) {
		this.w = w;
		this.ch = ch;
		this.k = k;
		this.o = o;
		this.r = r;
		this.c = c;
	}
}
