import java.util.*;
/*
9996번 한국이 그리울 땐 서버에 접속하지

와일드카드 *를 딱 하나 이용한 문자열이 하나 주어집니다.
*는 문자열의 맨 앞이나 뒤에 있지 않습니다.
이 때, 테스트케이스가 T개 주어집니다. 각 테스크케이스는 일반적인 문자열로 되어있습니다.
각 테스크케이스를 처음 주어진 와일드카드가 포함된 문자열로 표현할 수 있으면 DA, 아니면 NE를 출력하세요.

정규표현식을 사용하거나 문자열로 비슷하게 구현하는 문제입니다.
와일드카드를 딱 하나 ! 주기때문에 실버로 책정받은것같습니다.
사실 그게 아니더라도 정규표현식 쓰면되는거라 두개여도 실버일거같기도하고요..
*/
public class Main {
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int tc = input.nextInt(); input.nextLine();
		String str = input.nextLine();
		int starPos = 0;
		for (int i = 0; i < str.length(); i++) { // 저는 직접 구현했습니다. *의 위치를 구해줍니다.
			if (str.charAt(i) == '*') {
				starPos = i; break;
			}
		}
		outer:
			while (tc-- > 0) {
				String k = input.nextLine();
				// 예외 1. 테스크케이스의 길이가 스타를 제외한 길이보다 짧은경우
				if (str.length()-2 > k.length()-1) {
					System.out.println("NE");
					continue outer; // 이거는 한번 써보고싶어서 ㅎㅎ
				}
				// 예외 2. * 앞부분이 다른경우
				for (int i = 0; i < starPos; i++) {
					if (k.charAt(i) != str.charAt(i)) {
						System.out.println("NE");
						continue outer;
					}
				}
				// 예외 3. * 뒷부분이 다른경우
				for (int i = k.length()-1, j = 1; str.length()-j > starPos; i--, j++) {
					if (k.charAt(i) != str.charAt(str.length()-j)) {
						System.out.println("NE");
						continue outer;
					}
				}
				// 위 경우를 모두 통과한경우 옳은 테스크케이스가 됩니다.
				System.out.println("DA");
			}
		input.close();
	}
}