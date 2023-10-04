import java.io.*;
import java.util.*;
/*
14238번 출근 기록

ABC로만 이루어진 문자열이 주어진다. 적절히 배치해서 다음과 같은 문자열로 바꿔보자.
 - C와 C 사이 간격은 최소 2칸이다.
 - B와 B 사이 간격은 최소 1칸이다.

출근기록2 보다가 도망친곳
딴사람 어캐풀었나 해서 그리디 풀이 살짝 봤는데 할만한거같아서 묵혀놨다 풀려고합니다.

dfs에 dp 듬뿍 넣은 문제입니다. 5차원 방문배열은 또 처음써보네.
dp[i][j][k][x][y] = A가 i개, B가 j개, C가 k개 남았고 x는 여기에 B를 둘수있는지, y는 여기에 C를 둘 수 있는지.
50^3 * 2^2가 생각보다 크기가 작아서 가능했습니다. 해봐야 50만개.
종종 100만개 저장하라는 문제도 있는데 그거에비해서야 뭐..
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
	
	static boolean[][][][][] V;
	public static boolean dfs(int idx, int[] cnt, char[] ret) {
		if (idx == ret.length) return true;
		int[] origin = new int[] {cnt[0], cnt[1], cnt[2]};
		int[] impossible = new int[3];
		for (int i = 1; i <= 2; i++) {
			if (idx-i < 0) break;
			for (int j = i; j <= 2; j++)
				impossible[j] |= ret[idx-i] == (char)(j+'A') ? 1 : 0;
		}
		if (V[origin[0]][origin[1]][origin[2]][impossible[1]][impossible[2]])
			return false;
		for (int i = 2; i >= 0; i--) {
			if (cnt[i] == 0) continue;
			if (impossible[i] == 1) continue;
			ret[idx] = (char)(i+'A');
			cnt[i]--;
			if (dfs(idx+1, cnt, ret)) return true;
			cnt[i]++;
		}
		V[origin[0]][origin[1]][origin[2]][impossible[1]][impossible[2]] = true;
		return false;
	}

	public static void main(String[] args) throws IOException {
		char[] str = br.readLine().toCharArray();
		char[] ret = new char[str.length];
		int[] cnt = new int[3];
		V = new boolean[51][51][51][2][2];
		for (char i: str) cnt[i-'A']++;
		System.out.println(dfs(0, cnt, ret) ? new String(ret) : -1);

        bw.flush();
        bw.close();
        br.close();
    }
}
