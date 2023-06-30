import java.io.*;
import java.util.*;
/*
3144번 자석

자석이 N개가 있다. 자석은 서로 다른극끼리는 붙게되며, 이를 떼놓을 수는 없다.
초기 모든 자석이 직선상에 놓여있는 방향을 알 때, 자석을 적절하게 뒤집어 길이가 L인 자석을 만들어보자.
동시에 자석을 뒤집을 수는 없으며, 뒤집자마자 서로 다른 극이 주변에 있다면 그 자석들과 붙게된다.
마지막으로, 각 자석의 길이는 1이고, K개가 붙은 자석은 길이가 K가 된다.

이거 투포인터같은데? 하고 풀다가, 어 아닌가? 싶어서 알고리즘 분류 옵션 키고왔는데,
알고리즘 분류가 없어서 당황한 문제.
투표한 사람이 너무 적은게 그 이유였습니다..

일단 시작하자마자 붙을 자석은 다 붙습니다.
그래서 그거 전처리 해주고요.
이제 투포인터+예외처리를 해주면 풀립니다.

자석을 뒤집을 때는 아래와 같은 예외를 처리해주면 됩니다.
 - 뒤집을 자석 덩어리들의 왼쪽, 오른쪽에 무언가 있는데,
 - 덩어리의 가장 왼쪽, 오른쪽 자석의 방향이 다른경우.
왜냐고요?
[  +-+-  ]
이렇게 자석이 있고, 주변에 무언가 있다고 했으니.
[  - +-+- +  ]
모양은 이렇게되는데.
이러면 +-+-를 어떻게 뒤집어도 자기 옆에있는 자석과 붙어 길이가 L이 되지 않습니다.
그러니 한쪽에 아무것도 없거나 둘이 NS극 방향이 똑같거나 그래야합니다.
할만하네요.. 이정도는..
*/
public class Main {
	static Vector<Vector<Integer>> G;
	static int[][] ret;
	static int[] dp;
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
		
		st = new StringTokenizer(br.readLine());
		int N = sti(st), L = sti(st);
		Vector<Integer> items = new Vector<Integer>();
		Vector<String> status = new Vector<String>();
		items.add(0);
		status.add(null);
		st = new StringTokenizer(br.readLine());
		String cur = null;
		for (int i = 0; i < N; i++) {
			String nxt = st.nextToken();
			if (status.get(status.size()-1) == null || !status.get(status.size()-1).equals(nxt))
				status.add(nxt);
			int lastIdx = items.size()-1;
			if (cur == null) {
				items.add(1);
			}
			else if (cur.equals("SN")) {
				if (nxt.equals("SN")) {
					items.set(lastIdx, items.get(lastIdx)+1);
				} else {
					items.add(1);
				}
			} else {
				if (nxt.equals("NS")) {
					items.set(lastIdx, items.get(lastIdx)+1);
				} else {
					items.add(1);
				}
			}
			cur = nxt;
		}
		status.add(null);
		int size = items.size();
		int ret = 1<<20;
		int left = 1, right = 0;
		int sum = 0;
		while (left < size) {
			if (right+1 < items.size() && sum < L) {
				right++;
				sum += items.get(right);
			} else {
				sum -= items.get(left);
				left++;
			}
			if (sum == L) {
				boolean flg = false;
				flg |= status.get(left-1) == null || status.get(right+1) == null;
				flg |= status.get(left).equals(status.get(right));
				if (flg) ret = Math.min(ret, (right-left+1)/2);
			}
		}
		System.out.println(ret);
		
		br.close();
		bw.flush();
		bw.close();
	}
}