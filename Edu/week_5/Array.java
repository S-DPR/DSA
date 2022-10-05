package Main;

import java.util.Scanner;
import java.util.StringTokenizer;

class Main {
    public static void main(String args[]) {

        Scanner t = new Scanner(System.in);

        // 배열의 선언
        int i[] = new int[3]; // 크기가 3인 배열 선언
        int[] j = new int[3]; // int[] j나 int []j나 상관 없음
        // []int k = new int[3]; 이건 안됨.
        System.out.println("배열 w의 크기 입력");
        int[] w = new int[t.nextInt()];

        // 배열의 초기화는 따로 할 필요 없다.
        // 숫자류는 0으로, 문자열은 ''로 자동 초기화가 된다.
        for (int x : w)
            System.out.printf("%d ", x);
        System.out.println();
        // 다른 수로 초기화하고싶다고?
        int a[] = new int[] { 1, 2, 3 };
        // 1. 배열 a처럼 int[] {원소} 를 입력한다.

        int b[] = new int[3];
        for (int x = 0; x < b.length; x++)
            b[x] = x + 1;
        // 2. 모든걸 포기하고 for문으로 직접 초기화한다.
        // 여러 수를 공백으로 구별해 입력받은 한 줄을 배열에 저장하고싶다.
        // sol 1.
        System.out.println("배열 c에 들어갈 정수를 공백으로 구분해 입력.");
        t.nextLine();
        // nextLine은 엔터가 나올떄까지의 한 줄을 입력받는다.
        // 위 nextLine()은 개행문자까지 입력받는데 위에서 입력한 개행문자를 정리해준다.
        StringTokenizer s = new StringTokenizer(t.nextLine());
        int c[] = new int[s.countTokens()];
        // .countTokens() = s에 몇개의 구분된 텍스트가 있는지 출력
        // 예를들어, 1 3 5 를 입력받았으면 s에는 1, 3, 5 이렇게 3개가 있으니 3 출력
        for (int x = 0; x < c.length; x++)
            c[x] = Integer.parseInt(s.nextToken());
        // arr.length : arr의 길이 출력 :: 파이썬의 len(arr), C의 sizeof
        System.out.print("C is ");
        for (int x : c)
            System.out.printf("%d ", x);
        System.out.println();

        /*
         * 위 내용을 파이썬으로 그대로 풀어주자면,
         * Java : StringTokenizer s = new StringTokenizer(t.nextLine());
         * Python : s = input().split()
         * 
         * Java :
         * StringTokenizer s = new StringTokenizer(t.nextLine());
         * int c[] = new int[s.countTokens()]
         * for (int x = 0; x < c.length; x++)
         * c[x] = Integer.parseInt(s.nextToken());
         * for (int x: c) System.out.printf("%d ", x);
         * 
         * Python : c = list(map(int, input().split())); print(*c)
         */

        // sol 2.
        System.out.println("배열 d 크기 입력.");
        int dsize = t.nextInt();
        int[] d = new int[dsize];
        System.out.println("배열 d 내용 입력.");
        for (int x = 0; x < dsize; x++)
            d[x] = t.nextInt();
        for (int x : d)
            System.out.printf("%d ", x);

        // n차원 배열을 만들려면, []를 그만큼 붙이면 된다.
        int[][] e = new int[5][5];
        // 크기가 5*5인 2차원 배열

        int[][][] f = new int[5][5][5];
        // 크기가 5*5*5인 3차원 배열

        t.close();
    }
}