import java.io.*;
import java.util.*;
/*
14677번 병약한 윤호

'B', 'L', 'D'로만 이루어진 문자열이 주어진다.
문자열의 양쪽 끝만 취할 수 있다고 할 때, 'B', 'L', 'D' 순서로만 취하려고 한다.
최대 몇 개의 문자를 취할 수 있을까?

문제 푸는 모임(나 포함 2명)에서 BFS문제라고 내놓았지만, 아무리생각해도 DP.
구간DP입니다. 난이도는 그냥 구간DP알면 쉽고 모르면 BFS로 박아야합니다.
근데 DP가 특성상 BFS보다 복잡해지기는 어렵기때문에.. DP구현이 빠르고 쉽습니다. 알기만한다면.

골드5라서 올릴만해보이기 때문에 올렸습니다. 브~실문제 푼다면서 어쨰서 난이도가 이렇게 치솟는거지..
*/
public class Main {
	static char[] kindof = new char[] {'B', 'L', 'D'};
	public static int loop(String M, int cur, int left, int right, int[][] dp) {
		if (left >= M.length() || right < 0) return 0;
		if (dp[left][right] != 0 || left > right) return dp[left][right];
		if (M.charAt(left) == kindof[cur])
			dp[left][right] = Math.max(dp[left][right], loop(M, (cur+1)%3, left+1, right, dp)+1);
		if (M.charAt(right) == kindof[cur])
			dp[left][right] = Math.max(dp[left][right], loop(M, (cur+1)%3, left, right-1, dp)+1);
		return dp[left][right];
	}
	
	public static void main(String[] args) throws IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    
	    int N = Integer.parseInt(br.readLine());
	    int[][] dp = new int[N*3][N*3];
	    String M = br.readLine();
	    System.out.println(loop(M, 0, 0, N*3-1, dp));
	    
	    br.close();
	}
}
