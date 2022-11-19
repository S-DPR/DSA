import java.util.*;
import java.io.*;
/*
25710번 점수 계산

수열의 길이 N과 길이 N인 수열 A가 주어진다.
1 <= i < j <= N을 만족하는 i, j에 대해 A_i*A_j의 각 자릿수의 최댓값을 구하여라.
(1 <= N <= 100000, 1 <= A_i <= 999)

시간복잡도 O(N^2)으로 풀면 10만의 제곱이 되어서 못풀것처럼 하는 문제입니다.
이걸 수열의 길이 10만만 보고 풀면 절대 못풀고, 대신 A의 원소가 1~999, 즉 1000밖에 안되는 점을 보아야합니다.
네, 카운팅 소트를 응용하면 됩니다.
*/
public class Main {
	public static int plusCiphers(int n) {
		int res = 0;
		while (n > 0) {
			res += n%10;
			n /= 10;
		}
		return res;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] cnt = new int[1000];
		for (int i = 0; i < n; i++)
			cnt[Integer.parseInt(st.nextToken())]++;
		int res = 0;
		for (int i = 1; i < 1000; i++) {
			if (cnt[i] == 0) continue;
			cnt[i]--;
			for (int j = i; j < 1000; j++)
				if (cnt[j] > 0) res = Math.max(res, plusCiphers(i*j));
			cnt[i]++;
		}
		System.out.println(res);
	}
}