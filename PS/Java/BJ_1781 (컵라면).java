package Main;
import java.io.*;
import java.util.*;
/*
1781번 컵라면

N개의 문제가 있고, 각각의 문제는 데드라인과 점수가 있다.
한 문제를 푸는데 단위시간 1이 걸린다. 단위시간 N동안 얻을 수 있는 최대 점수를 구해보자.

간단한 우선순위 큐 문제.
그냥 보자마자 대충 최대힙이겠거니..
했는데 좀 보니까 N일부터 1일까지, 즉, 거꾸로 가는게 좋을거같아서 스택도 썼습니다.
사실 쓸필요는 없지만 쓰는게 있어보이잖아요.

골드 2치고 너무 쉬운문제였습니다. 내가 골드 2에 고통받은 문제가 몇갠데..
*/
class Item {
	int deadline, score;
	Item(int deadline, int score) {
		this.deadline = deadline;
		this.score = score;
	}
}

public class Main {
	static final long INF = 1<<62;
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
		
		int N= Integer.parseInt(br.readLine());
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>((i, j) -> { return j-i; });
		Stack<Item> stk = new Stack<Item>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			stk.add(new Item(sti(st), sti(st)));
		}
		stk.sort((i, j) -> {
			return i.deadline - j.deadline;
		});
		
		long score = 0;
		for (int curT = N; curT >= 1; curT--) {
			while (!stk.isEmpty() && stk.lastElement().deadline >= curT)
				pq.add(stk.pop().score);
			if (!pq.isEmpty())
				score += pq.poll();
		}
		System.out.println(score);
		
		br.close();
		bw.flush();
		bw.close();
	}
}