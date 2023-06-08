package Main;
import java.io.*;
import java.util.*;
/*
18235번 지금 만나러 갑니다

두 수가 있고. 이 수들을 하루마다 2^(day-1)만큼 앞 혹은 뒤로 옮기려고 한다.
적절하게 옮겼을 때, day의 최소값을 구해보자. 각 수는 0 이하 혹은 N+1 이상으로 갈 수 없다.

약간 전형적이진 않은 BFS문제.
Visit을 계속 초기화시켜야하는 류의 문제입니다.
처음 도달한 날에서 홀/짝이 있는 술래잡기 5나, 그런 문제와 다르게,
얘는 자기 앞뒤로는 충분한 거ꈰ가 있다면 언제나 도달할 수 있습니다.
2 -> 1도 되지만,
2 -> 3 -> 1도 되듯이요.
2 -> 3 -> 5 -> 1도 있고요.
이런걸 하나하나 찾지말고, 깔끔하게 BFS로 한번에 긁어버리는게 답이다, 라는 문제였습니다.
*/
class Item {
	int day, pos;
	Item(int day, int pos) {
		this.day = day;
		this.pos = pos;
	}
}

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
		
		st = new StringTokenizer(br.readLine());
		int N = sti(st), A = sti(st), B = sti(st);
		ArrayDeque<Item> Apos = new ArrayDeque<Item>();
		ArrayDeque<Item> Bpos = new ArrayDeque<Item>();
		Apos.add(new Item(0, A)); Bpos.add(new Item(0, B));
		
		int[] AV = new int[N+1];
		int[] BV = new int[N+1];
		for (int day = 1; 1<<(day-1) <= N; day++) {
			int dist = 1<<(day-1);
			while (!Apos.isEmpty() && Apos.peekFirst().day != day) {
				int cur = Apos.pollFirst().pos;
				if (cur+dist <= N && AV[cur+dist] != day) {
					AV[cur+dist] = day;
					Apos.add(new Item(day, cur+dist));
				}
				if (cur-dist > 0 && AV[cur-dist] != day) {
					AV[cur-dist] = day;
					Apos.add(new Item(day, cur-dist));
				}
			}
			while (!Bpos.isEmpty() && Bpos.peekFirst().day != day) {
				int cur = Bpos.pollFirst().pos;
				if (cur+dist <= N && BV[cur+dist] != day) {
					BV[cur+dist] = day;
					Bpos.add(new Item(day, cur+dist));
					if (AV[cur+dist] == day) {
						System.out.println(day);
						return;
					}
				}
				if (cur-dist > 0 && BV[cur-dist] != day) {
					BV[cur-dist] = day;
					Bpos.add(new Item(day, cur-dist));
					if (AV[cur-dist] == day) {
						System.out.println(day);
						return;
					}
				}
			}
		}
		System.out.println(-1);
		
		br.close();
		bw.flush();
		bw.close();
	}
}