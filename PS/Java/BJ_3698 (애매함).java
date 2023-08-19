import java.io.*;
import java.util.*;
/*
3698번 애매함

원래 문장에서 어떤 단어의 맨 앞과 맨 뒤를 빼고 섞으면 알아보기 쉽다.
원래 문장에서 어떤 문장의 띄어쓰기를 모두 제거해도 그래도 알아보기는 쉽다.
하지만, 위 두 행위를 한번에 섞으면 읽기 쉽지 않다.
위 두 행위를 동시에 한 문장이 주어진다. 다음으로, 옳은 단어의 리스트가 주어진다.
옳은 단어들로 이루어진 원래 문장을 추출해보자.
만약 불가능하면 impossible을, 두개 이상이 가능하면 ambiguous를 출력하자.

생각보다 흥미로웠던 문제
진짜 어렵다고 생각하는데, 이게 골드1..?
해서 난이도 기여 보니까 '마스터'티어가 '골드1'로 기여
아.. 내가 또 고인물들의 농락에 당했구나..
플레5 기여하니까 그래도 플레5가 되긴하네.. 플레5 풀었다 생각해야지..

dp[i] = i번째까지 문장을 만들 수 있는지 여부
dup[i] = i번째까짖 중복된 문장을 만들 수 있는지 여부
items[i] = i번째로 입력받은 옳은 단어
itemChars[i] = i번째로 입력받은 옳은 단어의 각 chars 개수 (중간만)
find[i][j][k] = 길이가 i이고 맨 앞글자가 j이고 맨 뒷글자가 k인 단어의 인덱스들.
그리고 열심히.. dp 굴려주면 됩니다.
match에 인덱스 적고.. matchCount에 개수 적고..

matchCount가 2 이상이면 dup[i+j]를 true로.
dp[i+j]가 이미 초기화가 안된 값이면 dup[i+j]를 true로..
dup[i-1], 그러니까 이전까지 봤던게 이미 중복이면 dup[i+j]를 true로..
이런느낌으로 중복 처리하고.

생각보다 나이브해도 괜찮던 문제였습니다.
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
        
        int T = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
        	char[] str = br.readLine().toCharArray();
        	int[] dp = new int[str.length+1];
        	boolean[] dup = new boolean[str.length+1];
        	Vector<Integer>[][][] find = new Vector[101][26][26];
        	int t = Integer.parseInt(br.readLine());
        	int[][] itemChars = new int[t+1][26];
        	String[] items = new String[t+1];
        	for (int i = 0; i <= 100; i++)
        		for (int j = 0; j < 26; j++)
        			for (int k = 0; k < 26; k++)
        				find[i][j][k] = new Vector<Integer>();
        	for (int i = 1; i <= t; i++) {
        		String tmp = br.readLine().strip();
        		int len = tmp.length();
        		int front = tmp.charAt(0)-'a';
        		int back = tmp.charAt(tmp.length()-1)-'a';
        		int[] chars = new int[26];
        		for (int j = 1; j < tmp.length()-1; j++)
        			chars[tmp.charAt(j)-'a']++;
        		items[i] = tmp;
        		itemChars[i] = chars;
        		find[len][front][back].add(i);
        	}
        	dp[0] = -1;
        	for (int i = 1; i <= str.length; i++) {
        		if (dp[i-1] == 0) continue;
        		int[] chars = new int[26];
        		int front = str[i-1] - 'a';
        		for (int j = 0; j < 100 && i+j-1 < str.length; j++) {
        			int back = str[i+j-1] - 'a';
        			int match = 0;
        			int matchCount = 0;
        			for (int k: find[j+1][front][back]) {
        				if (!Arrays.equals(chars, itemChars[k])) continue;
        				matchCount++;
        				match = k;
        			}
        			if (j+1 >= 2) chars[back]++;
        			if (match == 0) continue;
        			dup[i+j] = dup[i+j] || dup[i-1] || matchCount >= 2 || dp[i+j] != 0;
        			dp[i+j] = match;
        		}
        	}
        	if (dp[str.length] == 0)
        		bw.write("impossible\n");
        	else if (dup[str.length])
        		bw.write("ambiguous\n");
        	else {
        		int idx = str.length;
        		Vector<String> ret = new Vector<>();
        		StringBuilder R = new StringBuilder();
        		while (idx != 0) {
        			ret.add(items[dp[idx]]);
        			idx -= items[dp[idx]].length();
        		}
        		for (int i = ret.size()-1; i >= 0; i--)
        			R.append(ret.get(i) + " ");
        		bw.write(R + "\n");
        	}
        }
        
        bw.flush();
        bw.close();
        br.close();
    }
}
