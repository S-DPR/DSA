import java.io.*;
import java.util.*;
/*
2579번 계단 오르기

계단의 각 칸에 점수를 부여하고 연속해 3칸을 밟지 않는 선에서, 마지막 계단에 도달했을 때 최댓값을 구해보자.
계단은 한칸 또는 두 칸만 올라갈 수 있다.

아아..
죽이는 DP다..
멋지단게 아니라 진짜 죽일거라고..

난이도? 실버 3 (공식)
체감난이도? 골드 5
분명히 이제 dp는 골드까지도 어느정도 푸는 실력이 되었으나,
제가 4달전인가 5달전에 그냥 ㅈㅈ치고 포기한 이 문제는,
아주 끄떡없이 45분을 견뎌냈습니다.

dp를 N+1, 3의 크기로 선언해줍시다.
dp[i][1]은 i칸까지 연속하여 1칸만 밟은 경우 점수의 최댓값을,
dp[i][2]는 i칸까지 연속하여 2칸만 밟은 경우 점수의 최댓값을 저장합니다.
dp[i][1]에 가기 위해서는 dp[i-2][1] 혹은 dp[i-2][2]에서 2칸을 올라오는방법밖에 없으며,
dp[i][2]에 가기 위해서는 dp[i-1][1]에서 1칸을 올라가는 방법밖에 없습니다.
이를 그대로 코드에 적용해줍니다.
*/
public class Main {
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	
	public static long stl(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
	
	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N+1];
        for (int i = 1; i <= N; i++)
        	arr[i] = Integer.parseInt(br.readLine());
        
        int[][] dp = new int[N+1][3];
        dp[1][1] = arr[1];
        for (int i = 0; i <= N; i++) {
        	if (i+1 <= N)
        		dp[i+1][2] = dp[i][1]+arr[i+1];
        	if (i+2 <= N)
        		dp[i+2][1] = Math.max(dp[i][1], dp[i][2])+arr[i+2];
        }
        
        System.out.println(Math.max(dp[N][1], dp[N][2]));
        
        bw.flush();
        bw.close();
        br.close();
    }
}
