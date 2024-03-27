import java.io.*;
import java.util.*;
/*
26658번 소떡소떡

불판 위에 소시지와 떡이 N개 있다.
소시지와 떡은 겹쳐있지 않고, 각각 불판 Y번째 가로줄의 L부터 R까지 존재한다.
x번째 세로줄에 꼬치를 꼽으려 한다. 이 때, x번째 세로줄을 가로지르는 모든 재료는 꼬치에 꼽힌다.
꼬치에 꼽힌 재료가 소시지와 떡이 번갈아져 나올 때 그 크기의 최댓값을 구하려한다.
소떡소떡의 크기는 각 재료의 길이의 합이다.
이 때, 그 최댓값은 몇일까?

눈물겨운 22%테러당하고 마지막에 깨달음을 얻었습니다.
대충 넣을때랑 뺄 때 인접한 Y좌표를 Tree자료구조 이용해서 구하고,
인접한경우 인접함 개수 + 1 이런식으로 해서 x좌표 하나 처리 끝낸 뒤,
인접함 개수가 0일 경우 최대 길이를 업데이트 하는 방식인데..

빼는 이벤트가 항상 넣는 이벤트보다 먼저 일어나야 한다는 사실을..
한.. 6시간만에 깨달았나..?
하아..
깨닫고 상당히 현타가 왔습니다..
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int ini() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long lni() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}

	public static void main(String[] args) throws IOException {
		int N = ini();
		TreeMap<Long, List<Item>> A = new TreeMap<>();
		for (int i = 0; i < N; i++) {
			long l = lni(), r = lni(), y = lni();
			String k = st.nextToken();
			long len = r-l+1;
			if (!A.containsKey(l)) A.put(l, new ArrayList<>());
			if (!A.containsKey(r+1)) A.put(r+1, new ArrayList<>());
			A.get(l).add(new Item(y, len, k, false));
			A.get(r+1).add(new Item(y, len, k, true));
		}
		TreeMap<Long, Boolean> m = new TreeMap<>();
		long fail = 0;
		long len = 0, mx = 0;
		for (long i: A.keySet()) {
			A.get(i).sort((x, y) -> {
				return Boolean.compare(y.isEnd, x.isEnd);
			});
			for (Item x: A.get(i)) {
				Long lower = m.lowerKey(x.y);
				Long higher = m.higherKey(x.y);
				int cnt = 0;
				if (lower != null && m.get(lower).equals(x.k)) cnt++;
				if (higher != null && m.get(higher).equals(x.k)) cnt++;
				if (lower != null && higher != null && m.get(lower).equals(m.get(higher))) cnt--;
				if (x.isEnd) {
					m.remove(x.y);
					fail -= cnt;
					len -= x.len;
				} else {
					m.put(x.y, x.k);
					fail += cnt;
					len += x.len;
				}
			}
			if (fail == 0) {
				mx = Math.max(mx, len);
			}
		}
		System.out.println(mx);

        bw.flush();
        bw.close();
        br.close();
    }
}

class Item {
	long y, len;
	boolean k, isEnd;
	Item(long y, long len, String k, boolean isEnd) {
		this.y = y;
		this.len = len;
		this.k = k.equals("S");
		this.isEnd = isEnd;
	}
}
