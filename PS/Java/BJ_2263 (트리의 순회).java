package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
/*
2263번 트리의 순회

정점이 1부터 N까지 있는 트리가 있다.
이 트리의 중위, 후위순회가 주어질 때, 전위순회를 구해보자.

이야 드디어 풀었다.
한 4시간 고민했네..
시간은 오래걸렸어도 결국 알고리즘분류도 스스로 알아내고, 풀었으니 그렇다 칩시다.

방법은 분할정복입니다. 트리를 루트노드를 기준으로 좌, 우로 나누었을 때,
그 좌 우도 각각이 트리고, 후위순회에서 제일 마지막 부분은 반드시 루트노드가 된다는 점을 이용하여 풀어내면 됩니다.
*/
class Node {
	int left;
	int right;
	Node(int left, int right) {
		this.left = left;
		this.right = right;
	}
}

public class Main {
	static Node[] G;
	static int[] inorder;
	static int[] postorder;
	static int[][] sortedInorder;
	
	static int lowerBound(int[][] arr, int x) {
		int left = 0, right = arr.length;
		while (left < right) {
			int mid = (left + right) >> 1;
			if (arr[mid][0] >= x)
				right = mid;
			else
				left = mid+1;
		}
		return arr[right][1];
	}
	
	static int loop(int inL, int inR, int poL, int poR) {
		if (inL > inR) return 0;
		if (inL == inR) return inorder[inL];
		int root = postorder[poR];
		int findRoot = lowerBound(sortedInorder, root);
		int getLength = findRoot-inL;
		G[root].left = loop(inL, findRoot-1, poL, poL+getLength-1);
		G[root].right = loop(findRoot+1, inR, poL+getLength, poR-1);
		return root;
	}
	
	static void preorder(int x) {
		System.out.print(x + " ");
		if (G[x].left != 0) preorder(G[x].left);
		if (G[x].right != 0) preorder(G[x].right);
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		G = new Node[N+1];
		for (int i = 0; i <= N; i++)
			G[i] = new Node(0, 0);
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		inorder = new int[N];
		sortedInorder = new int[N][2];
		for (int i = 0; i < N; i++) {
			inorder[i] = Integer.parseInt(st.nextToken());
			sortedInorder[i] = new int[]{inorder[i], i};
		}

		st = new StringTokenizer(br.readLine());
		postorder = new int[N];
		for (int i = 0; i < N; i++)
			postorder[i] = Integer.parseInt(st.nextToken());
		
		Arrays.sort(sortedInorder, new Comparator<int[]>() {
			@Override
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});
		int root = loop(0, N-1, 0, N-1);
		preorder(root);
	}
}