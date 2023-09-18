import java.io.*;
import java.util.*;
/*
19700번 수업

N명의 학생이 있다. 각 학생의 키는 h인데, 자신의 조에 자신보다 키큰사람이 k-1명까지 있는 것을 버틸 수 있다.
조를 최소한으로 만들어 모든 학생을 만족시키려 한다. 적어도 몇 개의 조를 만들어서 배정해야할까?

정렬하면 '키가 더 큰 학생'이란 조건을 무시할 수 있다는 것을 알지 못해서 그냥 답지 보고 푼 문제.
이런 느낌의 그리디를 너무 옛날에 했던거같네..

말했다시피, 키순으로 정렬하면 뒤쪽으로 갈수록 무조건 '먼저 배정된 사람은 키가 크다'라는 의미가 됩니다.
그래서 키 정보를 TreeMap에 넣을 필요가 없어지고, 그냥 해당 팀의 크기가 몇인가만 넣으면 되죠.
TreeMap을 MultiSet처럼 사용하여 AC를 맞을 수 있습니다.
아.. 너무 멍청했고..
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

	public static void main(String[] args) throws IOException {
		int N = sti();
		Item[] items = new Item[N];
		for (int i = 0; i < N; i++) {
			int h = sti(), k = sti();
			items[i] = new Item(h, k);
		}
		Arrays.sort(items, (i, j) -> {
			return j.h - i.h;
		});
		TreeMap<Integer, Integer> s = new TreeMap<>();
		for (int i = 0; i < N; i++) {
			Integer key = s.lowerKey(items[i].k);
			if (key == null)
				s.put(1, s.getOrDefault(1, 0)+1);
			else {
				s.put(key, s.get(key)-1);
				if (s.get(key) == 0) s.remove(key);
				s.put(key+1, s.getOrDefault(key+1, 0)+1);
			}
		}
		int ret = 0;
		for (int val: s.values()) ret += val;
		System.out.println(ret);

        bw.flush();
        bw.close();
        br.close();
    }
}

class Item {
	int h, k;
	Item(int h, int k) {
		this.h = h;
		this.k = k;
	}
}
