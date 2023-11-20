import java.io.*;
import java.util.*;
/*
2917번 늑대 사냥꾼

S에서 J로 이동할 때, +와의 거리가 최대가 되도록 이동하려 한다.
가능한 최대 거리를 구하시오.

문제 잘못읽어서 40분
힝힝 다익스트라 쓰기 싫어 자바 싫어 스위프트쓸거야 하다가 40분 추가로 날려서 푼 문제..
매개변수탐색도 된다는데 왜 난 안되냐..

여하튼, BFS+다익스트라 굴려도 풀 수 있습니다.
BFS로 각 정점에서 나무와의 최단거리를 구해두고,
그거로 다익스트라를 굴리는데 최댓값을 구하라했으니 최대힙으로.
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
	static Info S = null;
	static int R, C;
	static char[][] M;
	static int[][] D;
	static int[] dr = new int[]{1, -1, 0, 0};
	static int[] dc = new int[]{0, 0, 1, -1};

	public static int dij() {
		int[][] dist = new int[R][C];
		for (int i = 0; i < R; i++)
			Arrays.fill(dist[i], -1);
		PriorityQueue<Info> pq = new PriorityQueue<>((i, j) -> {return j.d-i.d;});
		pq.add(S);
		dist[S.r][S.c] = D[S.r][S.c];
		while (!pq.isEmpty()) {
			Info cur = pq.poll();
			int r = cur.r, c = cur.c, d = cur.d;
			if (M[r][c] == 'J') return d;
			for (int i = 0; i < 4; i++) {
				int nr = r+dr[i], nc = c+dc[i];
				if (!(0 <= nr && nr < R)) continue;
				if (!(0 <= nc && nc < C)) continue;
				int nd = Math.min(d, D[nr][nc]);
				if (dist[nr][nc] >= nd) continue;
				dist[nr][nc] = nd;
				pq.add(new Info(nr, nc, nd));
			}
		}
		return -1;
	}

	public static void main(String[] args) throws IOException {
		R = sti(); C = sti();
		M = new char[R][C];
		D = new int[R][C];
		ArrayDeque<Info> q = new ArrayDeque<>();
		for (int i = 0; i < R; i++) {
			Arrays.fill(D[i], 1<<30);
			M[i] = br.readLine().toCharArray();
			for (int j = 0; j < C; j++) {
				if (M[i][j] == 'V') S = new Info(i, j, 0);
				if (M[i][j] == '+') {
					q.add(new Info(i, j, 0));
					D[i][j] = 0;
				}
			}
		}
		while (!q.isEmpty()) {
			Info cur = q.poll();
			int r = cur.r, c = cur.c, d = cur.d;
			for (int i = 0; i < 4; i++) {
				int nr = r+dr[i], nc = c+dc[i];
				if (!(0 <= nr && nr < R)) continue;
				if (!(0 <= nc && nc < C)) continue;
				if (D[nr][nc] <= d+1) continue;
				q.add(new Info(nr, nc, d+1));
				D[nr][nc] = d+1;
			}
		}
		S.d = D[S.r][S.c];
		System.out.println(dij());

        bw.flush();
        bw.close();
        br.close();
    }
}

class Info {
	int r, c, d;
	Info(int r, int c, int d) {
		this.r = r;
		this.c = c;
		this.d = d;
	}
}
