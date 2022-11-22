import java.util.*;
import java.io.*;
/*
1018번 체스판 다시 칠하기

B와 W로 이루어진 맵이 하나 주어진다.
맵을 8*8로 잘랐을 때, 적어도 하나 이상의 부분격자판에서 모든 B와 W가 교차하게 하기 위해 새로 칠해야하는 조각의 최소 개수를 구하시오.

한 언제더라.. 제가 문제풀이 시작한지 얼마 안돼서 도전했다가 실패한 문제입니다.
그냥 평범한 완전탐색인데.. 그렇죠.
실력이 확실히 향상된듯 합니다.
*/
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[][] vaild1 = new char[8][8];
		char[][] vaild2 = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j+=2) {
				vaild1[i][j] = (i&1)==1 ? 'W' : 'B';
				vaild2[i][j] = (i&1)==0 ? 'W' : 'B';
			}
			for (int j = 1; j < 8; j+=2) {
				vaild1[i][j] = (i&1)==1 ? 'B' : 'W';
				vaild2[i][j] = (i&1)==0 ? 'B' : 'W';
			}
		}
		StringTokenizer st = new StringTokenizer(br.readLine());
		int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
		char[][] M = new char[a][b];
		for (int i = 0; i < a; i++) {
			st = new StringTokenizer(br.readLine());
			String k = st.nextToken();
			for (int j = 0; j < b; j++) {
				M[i][j] = k.charAt(j);
			}
		}
		int ans = 1000000;
		for (int i = 0; i <= a-8; i++) {
			for (int j = 0; j <= b-8; j++) {
				int res1 = 0, res2 = 0;
				for (int k = 0; k < 8; k++) {
					for (int x = 0; x < 8; x++) {
						if (vaild1[k][x] != M[i+k][j+x]) res1++;
						if (vaild2[k][x] != M[i+k][j+x]) res2++;
					}
				}
				ans = Math.min(ans, Math.min(res1, res2));
			}
		}
		System.out.println(ans);
		br.close();
	}
}