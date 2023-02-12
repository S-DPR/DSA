package Main;
import java.io.*;
import java.util.*;
/*
SW_16003번 화면 캡쳐

테스트케이스의 개수 T가 주어진다.
이후 각 테스트케이스마다 수 N이 주어진다.
각 테스트케이스마다 1부터 N이하의 수를 [사전순으로] 정렬하여 출력해보자.
출력해야 하는 수의 개수는 min(50, N)개이다.
사전순의 의미를 설명하기 위해 105까지 수중 5개를 출력한다면,
1, 10, 100, 101, 102 가 될 것이다.

문제를 잘못읽어서 두 번 틀린 문제..
출력도 쓸데없이 많아서 정답 제대로 확인도 안하고 걍 내버려서 틀렸습니다.
삼성 소프트웨어 난이도 4짜리라는데 그냥 평범한 구현문제여서 그런가, 대충 실버따리같네요.

출력해야하는 텍스트의 개수가 미칠듯이 적음을 이용해야합니다.
백준이었으면 무조건 min(100000, N)개 출력하라고 했을텐데, 여긴 진짜 코테준비용이라 그런지 순하네요.
배열에 [1, 10, 100, ..., 1000000000]를 저장해두고 하나씩 string으로 만들며 비교해보면 됩니다.
자바에선 딱 Integer.toString과 str.compareTo가 있어서 그거 그대로 쓰면 됩니다.
테스트케이스 문제여서 백준이었다면 시간복잡도가 터지겠지만, 우리 코테준비용 사이트에서는 그런거 없이 간단하게 통과가 됩니다.
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

        int N = Integer.parseInt(br.readLine());
        for (int i = 1; i <= N; i++) {
        	int T = Integer.parseInt(br.readLine());
        	String result = "#"+i+" ";
        	int[] arr = new int[11];
        	arr[1] = 1;
        	for (int j = 2; j <= 10; j++)
        		arr[j] = arr[j-1]*10;
        	
        	for (int t = 0; t < Math.min(T, 50); t++) {
        		int selectIdx = 1;
        		String cur = Integer.toString(arr[1]);
        		for (int j = 1, d = 1; j <= 10; j++, d *= 10) {
        			if (arr[j] <= T && cur.compareTo(Integer.toString(arr[j])) > 0) {
        				cur = Integer.toString(arr[j]);
        				selectIdx = j;
        			}
        		}
        		arr[selectIdx]++;
        		result += cur+".png ";
        	}
        	
        	bw.write(result+"\n");
        }
        
        bw.flush();
        bw.close();
        br.close();
    }
}
