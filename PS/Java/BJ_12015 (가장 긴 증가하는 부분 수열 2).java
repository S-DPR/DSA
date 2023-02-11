import java.io.*;
import java.util.*;

/*
12015번 가장 긴 증가하는 부분 수열 2

수열이 주어진다. 가장 긴 증가하는 부분 수열의 길이를 구해보자.

핫하
이미 가장 긴 증가하는 부분 수열의 역추적(P5)까지 완료한 나에게 이정돈 쉽다!
..하고 그냥 푼 문제. 
3초정렬은 골드2따리면서 O(NlogN) LIS 역추적은 플레5더라고요.
딱히 풀고있진 않지만.. 솔직히 좀 신기했..

정확히는 그냥 코테문풀방에서 다이나믹 프로그래밍 한다길래,
LIS는 O(N^2)말고 O(NlogN)도 있다는거 알아만 두라고 문제 올려놨는데,
올려놓고 내가 안풀자니 좀 그래서 그냥 풀었습니다.

세그트리 방법은 솔직히 잘 모르겠고, 알아야할 필요도 모르겠어서 이분탐색으로 풀었습니다.
*/
public class Main {
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	
	public static long stl(StringTokenizer st) {
		return Long.parseLong(st.nextToken());
	}
	
	public static int bisect(Vector<Integer> arr, int x) {
		int l = 0, r = arr.size();
		while (l < r) {
			int m = (l + r) >> 1;
			if (arr.get(m) >= x) r = m;
			else l = m+1;
		}
		return r;
	}
	
	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N+1];
        Vector<Integer> dp = new Vector<Integer>();
        dp.add(0);
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++)
        	arr[i] = sti(st);
        
        for (int i = 1; i <= N; i++) {
        	int idx = bisect(dp, arr[i]);
        	if (dp.size() == idx) dp.add(arr[i]);
        	dp.set(idx, Math.min(arr[i], dp.get(idx)));
        }
        
        System.out.println(dp.size()-1);
        
        bw.flush();
        bw.close();
        br.close();
    }
}
