package Main;
import java.io.*;
import java.util.*;

/*
26094번 더 어려운 스케줄링

번호가 1부터 N까지 매겨진 작업과, 그걸 처리하는 스케줄러가 있다.이 스케줄러는 LIFO의 성질을 가지고 있다.
쿼리가 Q개 주어진다. 쿼리가 다음 의미와 같을 때, 3번 쿼리가 나올 때마다 스케줄러가 처리하는 작업의 번호를 출력해보자.
0 x : 스케줄러에 x번 작업을 추가한다.
1   : 스케줄러를 정렬한다. 가장 작은 번호가 가장 먼저 출력되는 위치에 오게 된다.
2   : 스케줄러 업무 처리 순서를 뒤집는다.
3   : 스케줄러가 가장 앞에 있는 작업을 처리한다.

옛날에 세그트리인가 헤헤 하고 풀었다가 대가리 세 동강 나고 PTSD와서 안 건들였던 문제.
거 뭐냐.. 코드트리 채점기랑 장난감 묶음 할인인가? 그거 풀고 푸니까 너무 애들 장난이네요.
다만, 얘는 무려 '덱'을 사용하는 엄청난 문제라는 점. 덱 사용하는거 4달동안 2문제봤습니다.

덱을 두개 만들고, 최대/최소 우선순위큐를 만듭니다.
덱 하나는 제일 앞, 덱 하나는 제일 뒤를 담당할겁니다. 우선순위큐는 반드시 덱과 덱 사이 순서에 위치합니다.
앞을 담당하는 덱을 f, 뒤를 담당하는 덱을 b라고 합시다.
뒤집힘 여부도 어디선가 저장을 해두어야합니다. 아래 코드에서는 reversed의 약자로 rev를 사용하여 뒤집힘 여부를 체크합니다.

rev = false인 상태를 먼저 고려하여 0 x를 받았다고 합시다. 그러면 f에 x를 넣습니다. 현재 가장 앞은 f의 제일 뒤이기 때문입니다.
rev = true라면 b의 첫번째에 x를 넣습니다. 현재 가장 앞은 b의 제일 앞이기 때문입니다.

조금 더 쉽게 생각해봅시다. 현재 자료구조는 이런 모양입니다.
rev = false : [[b] -> [PQ] -> [f]]
f가 제일 앞이고, b가 제일 뒤입니다. PQ는 그 사이에 위치합니다.
rev = true면 이제 순서만 바꾸어주면 됩니다. [[b] <- [PQ] <- [f]]
이러면 b의 제일 앞이 제일 앞부분이 되고, f의 제일 뒤가 가장 뒷부분이 됩니다.

1번 쿼리가 왔다면 이제 rev는 의미가 없습니다. false로 초기화해주고, f와 b에 있는 모든 수를 우선순위큐에 넣습니다.

2번 쿼리가 왔다면 이제 rev를 토글링해줍니다.

3번 쿼리가 왔다면 rev와 우선순위큐 상태에 따라 달라지는데,
rev가 true인경우를 먼저 봅시다. 이 경우는 b의 제일 앞에 있는게 먼저 처리됩니다.
만약에 b가 비어있다면 우선순위큐에 있는게 f보다 먼저 처리됩니다.
만약 우선순위큐도 비어있다면 f의 제일 앞부분부터 처리됩니다.

rev가 true라면 f의 제일 뒷부분 / PQ / b의 제일 앞부분을 순서로 하여 처리하면 됩니다.
우선순위큐중 max와 min중 뭘 써야할지는 rev에 따라 달려있습니다. rev가 true면 max를, false면 min을 쓰면 됩니다.
단, 둘을 동기화를 시키기 위하여 f, b, PQ의 내부에 있는 작업을 어딘가에 저장해두어야합니다. 저는 그 배열을 exist로 하였습니다.
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

		st = new StringTokenizer(br.readLine());
		int N = sti(st), Q = sti(st);
		PriorityQueue<Integer> min = new PriorityQueue<Integer>();
		PriorityQueue<Integer> max = new PriorityQueue<Integer>();
		ArrayDeque<Integer> deqBack = new ArrayDeque<Integer>();
		ArrayDeque<Integer> deqFront = new ArrayDeque<Integer>();
		Boolean rev = false;
		Boolean[] exist = new Boolean[N+1];
		int item;
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			switch (sti(st)) {
			case 0:
				item = sti(st);
				exist[item] = true;
				if (rev) deqBack.add(item);
				else deqFront.add(item);
				break;
			case 1:
				while (!deqFront.isEmpty()) {
					item = deqFront.poll();
					min.add(item);
					max.add(-item);
				}
				while (!deqBack.isEmpty()) {
					item = deqBack.poll();
					min.add(item);
					max.add(-item);
				}
				rev = false;
				break;
			case 2:
				rev = rev ? false: true;
				break;
			case 3:
				if (rev) {
					if (!deqBack.isEmpty()) item = deqBack.pollLast();
					else if (!max.isEmpty()) item = -max.poll();
					else item = deqFront.pollFirst();
				} else {
					if (!deqFront.isEmpty()) item = deqFront.pollLast();
					else if (!min.isEmpty()) item = min.poll();
					else item = deqBack.pollFirst();
				}
				bw.write(item + "\n");
				exist[item] = false;
				while (!min.isEmpty() && !exist[min.peek()]) min.poll();
				while (!max.isEmpty() && !exist[-max.peek()]) max.poll();
				break;
			}
		}
		
		br.close();
		bw.flush();
		bw.close();
	}
}