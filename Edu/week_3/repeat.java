package week_3;

public class Main {
    public static void main(String[] args) {
        int[] arr = { 10, 20, 30, 40, 50 };
        for (int i = 0; i < 5; i++)
            System.out.printf("%d ", i); // 출력값 : 0 1 2 3 4
        System.out.printf("\n");

        for (int i = 0; i < 5; i++)
            System.out.printf("%d ", arr[i]); // 출력값 : 10 20 30 40 50
        System.out.printf("\n");

        for (int i = 0; i < 5; i++)
            System.out.printf("%d %d | ", i, arr[i]);
        System.out.printf("\n");
        // 출력값 : 0 10 | 1 20 | 2 30 | 3 40 | 4 50 |

        // 주의점 : 의도가 아니라면 반복문 내용에서 반복문 연산자는 건드리지 않는 것이 좋다.
        for (int i = 0; i < 3; i++)
            System.out.printf("%d ", i++);
        System.out.printf("\n");

        for (int x : arr)
            System.out.printf("%d ", x);
        System.out.printf("\n");
        // 출력값 : 10 20 30 40 50

        // 초기값 혹은 연산자를 두 개 이상 갖는 for문
        for (int i = 0, j = 10; i < 3; i++, j--)
            System.out.printf("%d %d | ", i, j);
        System.out.printf("\n");
        // 출력값 : 0 10 | 1 9 | 2 8 |

        // 초기값 혹은 조건문 혹은 연산자를 갖지 않는 for문
        for (int i = 3; i-- != 0;)
            System.out.printf("%d ", i);
        System.out.printf("\n");
        ///////////////////////////////////////////
        // 자바는 조건식에 숫자가 들어가면 안된다! //
        ///////////////////////////////////////////
        // 초기값 실행 후 조건문을 한번 실행해보고 들어가기에, 첫 출력 i는 2이다.
        // 출력값 : 2 1 0

        // while (true)와 다를게 없는 for문
        int i = 3;
        for (;;) {
            System.out.printf("%d ", i--);
            if (!(i != 0))
                break;
        }
        System.out.printf("\n");
        // 출력값 : 3 2 1
    }
}