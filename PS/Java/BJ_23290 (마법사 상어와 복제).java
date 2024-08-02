import java.io.*;
import java.util.*;
/*
23290번 마법사 상어와 복제

구현량이 정말 많다. 문제는 사이트 참조.

깡구현문제.
그냥.. 그냥..
구현만 하면 되는데 갖가지 실수에 걸쳐 1시간이 걸러버렸네요.
끔찍해라..
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
        int M = ini(), S = ini();
        List<Integer>[][] map = createEmptyMap();
        int[] dr = new int[] { 0, -1, -1, -1, 0, 1, 1, 1 };
        int[] dc = new int[] { -1, -1, 0, 1, 1, 1, 0, -1 };
        for (int i = 0; i < M; i++) {
            int r = ini(), c = ini(), p = ini()-1;
            map[r][c].add(p);
        }
        int curR = ini(), curC = ini();
        int[] sdr = new int[] { -1, 0, 1, 0 };
        int[] sdc = new int[] { 0, -1, 0, 1 };
        int[][] smell = new int[5][5];
        while (S-- > 0) {
            List<Integer>[][] newMap = createEmptyMap();
            for (int r = 1; r <= 4; r++) {
                for (int c = 1; c <= 4; c++) {
                    for (int d: map[r][c]) {
                        boolean find = false;
                        for (int i = 0; i < 8; i++) {
                            int nr = r+dr[(d+8-i)%8], nc = c+dc[(d+8-i)%8];
                            boolean can = true;
                            can = can && 1 <= nr && nr <= 4;
                            can = can && 1 <= nc && nc <= 4;
                            can = can && smell[nr][nc] == 0;
                            can = can && !(nr == curR && nc == curC);
                            if (!can) continue;
                            newMap[nr][nc].add((d+8-i)%8);
                            find = true;
                            break;
                        }
                        if (!find) {
                            newMap[r][c].add(d);
                        }
                    }
                }
            }
            int goCnt = -1;
            int[] go = new int[] { 0, 0, 0 };
            for (int f = 0; f < 4; f++) {
                for (int s = 0; s < 4; s++) {
                    for (int t = 0; t < 4; t++) {
                        int nxtR = curR, nxtC = curC, cnt = 0;
                        boolean can = true;
                        boolean[][] vis = new boolean[5][5];
                        for (int x: new int[] { f, s, t }) {
                            nxtR = nxtR+sdr[x];
                            nxtC = nxtC+sdc[x];
                            can = can && 1 <= nxtR && nxtR <= 4;
                            can = can && 1 <= nxtC && nxtC <= 4;
                            if (!can) break;
                            if (!vis[nxtR][nxtC]) cnt += newMap[nxtR][nxtC].size();
                            vis[nxtR][nxtC] = true;
                        }
                        if (can && goCnt < cnt) {
                            go = new int[] { f, s, t };
                            goCnt = cnt;
                        }
                    }
                }
            }
            for (int x: go) {
                curR += sdr[x];
                curC += sdc[x];
                if (!newMap[curR][curC].isEmpty()) smell[curR][curC] = 3;
                newMap[curR][curC] = new ArrayList<>();
            }
            for (int r = 1; r <= 4; r++) {
                for (int c = 1; c <= 4; c++) {
                    newMap[r][c].addAll(map[r][c]);
                }
            }
            map = newMap;
            for (int r = 1; r <= 4; r++) {
                for (int c = 1; c <= 4; c++) {
                    smell[r][c] = Math.max(0, smell[r][c]-1);
                }
            }
        }
        int ret = 0;
        for (int r = 1; r <= 4; r++) {
            for (int c = 1; c <= 4; c++) {
                ret += map[r][c].size();
            }
        }
        System.out.println(ret);

	    bw.flush();
        bw.close();
        br.close();
    }
}
