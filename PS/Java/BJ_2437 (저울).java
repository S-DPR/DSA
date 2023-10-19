import java.io.*;
import java.util.*;
/*
2437번 저울

수가 N개 있다. 수를 어떤식으로든 각각 한번씩 골라 더했을 때,
나오지 않는 가장 작은 수는 몇일까?

이전에 풀었던 수수께끼 문제.
아는 문제는 최대한 피하려하지만..
오늘은 병원이랑 PT 알아보고 교수님이 부탁하신거 처리하느라 피곤해서 가져왔습니다.
네, 그때 그 문제처럼 풀면 됩니다. 쉽죠.
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
		int[] A = new int[N];
		for (int i = 0; i < N; i++)
			A[i] = sti();
		Arrays.sort(A);
		int max = 0;
		for (int i: A) {
			if (max+1 < i) break;
			max += i;
		}
		System.out.println(max+1);

        bw.flush();
        bw.close();
        br.close();
    }
}
