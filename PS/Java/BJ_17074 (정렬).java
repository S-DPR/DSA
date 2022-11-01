import java.io.*;
import java.util.StringTokenizer;
/*
17074번 정렬

길이가 N인 수열 A가 주어집니다.
수열 A에서 수 하나를 빼내어 비오름차순 정렬이 된 상태로 만들고싶습니다.
이때, 뺄 수 있는 경우의 수가 몇 개인지 세주세요.

DP태그가 있을줄은 몰랐지만 있었네요..
일단 DP로 안풀거면 문제를 그냥 보면 절대 못풀고,
문제의 답은 최소 0 최대 2라는점을 생각해야합니다.

생각해봅시다.. 수열 A가 a, b, c, d, .. 이렇게 이루어져있다 할 때,
두 개 이상의 수가 정렬이 되어있지 않다고 한다면,
하나의 수를 뺀다 한들 정렬이 될리가 없습니다. 그런 경우엔 정답이 0입니다.

하나의 수가 정렬이 되어있지 않다고 합시다.
그러면 그 수를 빼거나, 그 앞 수를 대소비교하여 빼내면 정렬이 될 수 있겠죠.
단, 이 수가 맨 앞이거나 맨 뒤인경우는 예외처리를 해야겠죠.
이런 경우엔 정답이 1, 2중 하나로 나오게 됩니다.

모든 수가 정렬이 되어있다고 합시다.
이 때는 어떤 수를 빼더라도 정렬이 되게됩니다. 이 때는 N이 정답이 되게됩니다.
*/
public class Main {
	public static int sti(StringTokenizer st) {
		return Integer.parseInt(st.nextToken());
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = sti(st);
		int ans = -1;
		
		if (n == 2) ans = 2;
		for (int idx = 1; idx < n && n != 2; idx++) {
			if (arr[idx-1] <= arr[idx]) continue;
			if (ans == -1) {
				ans = 0;
				if (idx > 1 && arr[idx-2] <= arr[idx]) ans++;
				if (idx < n-1 && arr[idx-1] <= arr[idx+1]) ans++;
				if (idx == 1 || ans == n-1) ans++;
				// idx가 1인데 비정렬인 경우는 맨 앞 수를 빼낼 수 있는 유일한 경우입니다.
				// 뒤쪽이 모두 정렬이 되어있다는 가정을 한다면, 무조건 1을 더해야하죠.
				// idx가 맨 마지막인 경우도 맨 마지막 수를 빼낼 수 있는 유일한 경우입니다.
			} else {
				ans = 0;
				break;
			}
		}
		System.out.println(ans < 0 ? n : ans);
		br.close();
	}
}