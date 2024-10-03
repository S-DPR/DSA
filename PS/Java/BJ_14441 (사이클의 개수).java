import java.io.*;
import java.util.*;
/*
14441번 사이클의 개수

행렬그래프가 주어진다.
노드의 개수가 M 이하인 사이클의 개수의 합을 구해보자.
사이클이란, 사이클의 마지막 노드에서 사이클의 첫 노드로 갈 수 있는 경우를 의미한다.

N이 35라서 하 이거 또 완탐인가 했었는데,
ㅋㅋ 어림도없지 수끼야야아악!!

일단 뭐 행렬곱까지는 생각했고.
이걸 이제 A^1 + A^2 + ... + A^M을 어캐구하냐.. 생각했는데.
하나하나 하는 방식을 좀 틀어서 하면서 계산했다가 그냥 MN^3logM터지고 멸망 ㅋㅋ

그래서 GPT야 도와줘~ 하니까 완전새롭던데요?
우선 저는 역그래프 구해서 올 수 있는 경우를 처리했었는데(문제의 말대로 M-1개의 간선, M개의 노드)
생각해보니 그냥 M개간선 타게하고 자기 자신으로 오는경우만 세면 되더라고요??
그래서 revG는 의미가 없고..

A^1+A^2+...+A^K를 구하려면 다음과 같이 분할한다고합니다.
S(k) = A^1+...+A^k일 때,
S(K/2) * (I + A^(K/2)).
I는 항등행렬이었나?? 어쨌든 주대각선이 1인 행렬.

실제로 저거 좀 생각해보면 S(K)와 같은값이됩니다.
I의 의미를 도통 모르겠어서 계속 캐물었었는데 그냥 분배해보니 그렇더라고요..
굳이 저렇게 나눈건 A^(K/2)를 쓰려고. S(K^2) 구할때 A^(K/2)도 같이 구해지거든요.
와.. 진짜 수학 개쩐다..
왜 간단한 행렬곱이 플레4인가했다 진자..
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int ini() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long lni() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}

    public static String sni() throws IOException {
        while (!st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    public static int[] ins(int sz) throws IOException {
        int[] ret = new int[sz];
        for (int i = 0; i < sz; i++) {
            ret[i] = ini();
        }
        return ret;
    }

    public static long[] lns(int sz) throws IOException {
        long[] ret = new long[sz];
        for (int i = 0; i < sz; i++) {
            ret[i] = lni();
        }
        return ret;
    }

    static int N, M;
    static long K, ret;
    static long[][] origin;
    static Vector<Integer> revG[];

    public static long[][] prodMat(long[][] x, long[][] y) {
        long[][] ret = new long[N][N];
        for (int i = 0; i < N; i++) {
            for (int j  = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    ret[i][j] = (ret[i][j]+x[i][k]*y[k][j])%K;
                }
            }
        }
        return ret;
    }

    public static long[][] addMat(long[][] x, long[][] y) {
        long[][] ret = new long[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ret[i][j] = (x[i][j]+y[i][j])%K;
            }
        }
        return ret;
    }

    public static long[][] getIdentityMatrix() {
        long[][] I = new long[N][N];
        for (int i = 0; i < N; i++) {
            I[i][i] = 1;
        }
        return I;
    }

    public static class Pair {
        long[][] first;
        long[][] second;
        Pair(long[][] A, long[][] B) {
            first = A;
            second = B;
        }
    }

    public static Pair sqMat(long[][] A, int k) {
        if (k == 1) return new Pair(A, A);
        Pair half = sqMat(A, k/2);
        long[][] SHalf = half.first;
        long[][] AHalf = half.second;
        long[][] iPlusAHalf = addMat(getIdentityMatrix(), AHalf);
        long[][] S = prodMat(SHalf, iPlusAHalf);
        long[][] FullA = prodMat(AHalf, AHalf);
        if ((k&1) == 1) {
            FullA = prodMat(FullA, origin);
            S = addMat(S, FullA);
        }
        return new Pair(S, FullA);
    }

	public static void main(String[] args) throws IOException {
        N = ini(); M = ini(); K = lni();
        origin = new long[N][N];
        revG = new Vector[N];
        for (int i = 0; i < N; i++) revG[i] = new Vector<>();
        for (int i = 0; i < N; i++) {
            String str = sni();
            for (int j = 0; j < N; j++) {
                if (str.charAt(j) != 'Y') continue;
                origin[i][j] = 1;
                revG[j].add(i);
            }
        }
        long[][] S = sqMat(origin, M-1).first;
        long ret = 0;
        for (int i = 0; i < N; i++) ret = (ret + S[i][i])%K;
        System.out.println(ret);

	    bw.flush();
        bw.close();
        br.close();
    }
}
