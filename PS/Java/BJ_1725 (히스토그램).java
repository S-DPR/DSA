import java.io.*;
/*
1725번 히스토그램

배열 arr이 주어진다.
min(arr[l:r])*(r-l+1)의 최댓값을 구하시오.

GO에서 분할정복으로 풀어본 그 히스토그램 문제 맞습니다.
이번엔 세그먼트트리로 풀었습니다.
백번쓰면 백번 불편한 자바로 하니까 꺄아아아아앙 이라는 생각밖에 안드네요.
프로그램 개발은 몰라도 PS에서 자바를 쓰는건 최악의 선택중 하나가 아닐까 생각해봅니다.
옛날엔 GO가 훨씬 불편했는데 쓰다보니 자바가 더 힘든거같기도하고..

일단 문제를 풀기 위해 세그먼트트리를 구축합니다.
각 노드에 값과 인덱스를 각각 저장하고, 값을 기준으로 최솟값 세그트리를 만듭니다.
이제 분할정복과 세그트리를 같이 씁시다.

분할정복에서 세그트리 쿼리를 이용해 l, r 범위 내의 최솟값을 찾습니다.
그러면 인덱스와 값이 딸려오는데, 값을 이용해서 min(arr[l:r])*(r-l+1)의 값을 구하고,
재귀를 이용해 loop(l, idx-1), loop(idx+1, r)의 최댓값을 구해줍시다.
생각해보면 idx가 포함되어있으면 최솟값이 arr[idx]로 고정이 될테니, 그걸 제외한 곳에서 찾는건 당연하겠죠.
여하튼 위의 방법을 반복하여, l > r이되면 재귀중단을 하도록 하고 최종적으로 나온 것이 답이 됩니다.
*/
class node {
	int idx, val;
	node(int idx, int val) {
		this.idx = idx;
		this.val = val;
	}
	static node getMin(node x, node y) {
		if (x.val < y.val) return x;
		return y;
	}
}

class segT {
	node[] T;
	int size;
	segT(int[] arr) {
		size = arr.length;
		T = new node[size*2];
		for (int i = 0; i < size; i++)
			T[i+size] = new node(i+1, arr[i]);
		for (int i = size-1; i > 0; i--)
			T[i] = node.getMin(T[i<<1], T[i<<1|1]);
	}
	node query(int left, int right) {
		node res = T[left+size-1];
		for (left += size-1, right += size-1; left <= right; left >>= 1, right >>= 1) {
			if ((left&1) == 1)
				res = node.getMin(res, T[left++]);
			if ((right&1) == 0)
				res = node.getMin(res, T[right--]);
		}
		return res;
	}
}

public class Main {
	static long loop(segT T, int left, int right) {
		if (left > right) return 0;
		node point = T.query(left, right);
		long res = (long)point.val * (right - left + 1);
		long divide = Math.max(loop(T, left, point.idx-1),
							  loop(T, point.idx+1, right));
		return Math.max(res, divide);
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = Integer.parseInt(br.readLine());
		segT T = new segT(arr);
		System.out.println(loop(T, 1, n));
	}
}