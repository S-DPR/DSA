import java.io.*;
import java.util.*;
/*
14717번 앉았다

1~10카드 2장씩을 갖고 다음 게임을 하자.
1. 둘 모두 카드를 2장 뽑았을 때,
2-1. 둘 모두 카드의 숫자가 같다면 숫자가 더 큰 사람이 이긴다.
2-2. 한 명만 같다면, 그 사람이 이긴다.
2-3. 둘 모두 다르다면 더했을때 1의자리가 더 큰 사람이 이긴다.
내 패가 주어졌을 때 이길 확률을 구해보자.

화려한 확률문제
그냥 백준 스터디에서 풀어보라고 주길래 풀었는데 실버4에 1시간 걸렸습니다.
확률문제 안푸는 이유는 다~ 있다..
*/
public class Main {
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        
        int n = sti(st), m = sti(st);
        int[] cnt = new int[11];
        cnt[n]++; cnt[m]++;
        int[] left = new int[18];
        int idx = 0;
        for (int i = 1; i <= 10; i++)
        	while (cnt[i]++ < 2) left[idx++] = i;
        
        int result = 0;
        for (int i = 0; i < 18; i++) {
        	for (int j = i+1; j < 18; j++) {
        		if (n == m) {
        			if (left[i] != left[j]) result++;
        			else if (left[i] < n) result++;
        		} else {
        			if (left[i] != left[j] &&
        				(left[i]+left[j])%10 < (n+m)%10) result++;
        		}
        	}
        }
        System.out.printf("%.3f", (double)result/153);
        
        bw.flush();
        bw.close();
        br.close();
    }
}
