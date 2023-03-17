import java.io.*;
import java.util.*;
/*
5624번 좋은 수

길이가 N인 수열이 있다.
i번째에 있는 수가 1, 2, .., i-1번째 수 중 세 번 이상 더해 만들어지는 수라면 좋은 수라고 한다.
좋은 수의 개수를 구해보자.

보자마자 해결법 딱 떠올랐는데 의외로 난이도가 높네요?
저는 그냥 set으로 구현했습니다.
배열로 구현하는 방법도 있고, 그 방법이 4배정도 빨라서 익혀두긴 해야겠네요.

수 하나짜리 set, 수 두개를 더한 set을 만듭니다.
하나짜리 set을 iterate하면서 (현재 수)-(현재 하나짜리 수)가 수 두개 set에 있나 확인합니다.
있으면 cnt를 올리고 없으면 넘어갑니다.

다음으로 수 하나짜리 set에 현재 수를 집어넣습니다.
이후 수 하나짜리 set을 iterate하면서 현재 수와 더해 수 두개짜리 set에 넣습니다.

O(N^3)이 드는 것을 수 하나와 두 개로 분리하여 O(N^2)으로 바꾸는게 목적인 문제였습니다.
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
		
		HashSet<Integer> two = new HashSet<Integer>();
		HashSet<Integer> one = new HashSet<Integer>();
		int N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			int cur = sti(st);
			for (int x: one) {
				if (two.contains(cur-x)) {
					cnt++;
					break;
				}
			}
			one.add(cur);
			for (int x: one)
				two.add(cur+x);
		}
		System.out.println(cnt);
	}
}