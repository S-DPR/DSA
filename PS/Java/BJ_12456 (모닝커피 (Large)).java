import java.io.*;
import java.util.*;
/*
12456번 모닝커피 (Large)

배열 A가 주어진다. A의 원소는 세개의 양의 정수로 이루어져있다.
첫번째로 c는 이 원소가 몇 개 있는지를 나타낸다.
두번째로 t는 이 원소의 유효기간을 나타낸다. 이 기간 이후로는 이 원소를 쓸 수 없다.
마지막으로 s는 이 원소의 점수를 나타낸다. 이 원소를 담으면 s만큼의 점수를 얻는다.
담을 수 있는 시간이 K만큼 주어진다. 최대 몇 점을 얻을 수 있나 구해보자.
담는데 걸리는 시간은 1이며, 한 번에 하나만 담을 수 있다.

그리-디
우리집 멍멍이가 와서 봐도 '이건 그리디네' 하고 갈 수준의 그리-디
트리맵느낌으로 풀었습니다. N이 작아서 트리맵은 안쓰긴 했는데.

s를 기준으로 정렬하고, 그걸 이제 왼쪽으로 쭉 바릅니다. 그렇게 c개의 원소를 색칠한다는 느낌으로.
바르다가 이미 색이 있는 곳을 만나면, 그곳을 건너뛰고 쭉 바릅니다.
그러면 AC. 간단하죠.
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
		int T = ini();
		for (int tc = 1; tc <= T; tc++) {
			int N = ini();
			long K = lni();
			long[][] A = new long[N][3];
			Vector<Long> time = new Vector<>();
			time.add(K);
			time.add(0L);
			for (int i = 0; i < N; i++) {
				long c = lni(), t = lni(), s = lni();
				A[i] = new long[] {c, t, s};
				if (!time.contains(t)) time.add(t);
			}
			Arrays.sort(A, (i, j) -> {
				return i[2] < j[2] ? 1 : -1;
			});
			time.sort((i, j) -> {
				return i.compareTo(j);
			});
			long[] trueT = new long[time.size()];
			for (int i = 0; i < time.size(); i++) {
				trueT[i] = time.get(i);
			}
			long ret = 0;
			for (long[] x: A) {
				int idx = 0;
				while (time.get(idx) < x[1]) idx++;
				long cnt = x[0];
				while (idx != 0 && cnt != 0) {
					long remainT = trueT[idx]-time.get(idx-1);
					if (remainT < cnt) {
						cnt -= remainT;
						ret += remainT*x[2];
						trueT[idx] = time.get(idx-1);
						idx--;
					} else {
						ret += cnt*x[2];
						trueT[idx] -= cnt;
						cnt = 0;
					}
				}
			}
			bw.write("Case #" + tc + ": " + ret + "\n");
		}

        bw.flush();
        bw.close();
        br.close();
    }
}
