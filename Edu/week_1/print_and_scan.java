package Main; // 이 파일이 있는 폴더 이름

import java.util.Scanner; // 자바는 입력을 받으려면 Scanner를 import해야한다. 쩌어기 밑에 스캐너 사용법 있음.

public class Main { // Main class : class 이름은 첫 글자를 대문자로 짓는다.
    public static void main(String[] args) { // method = C의 함수, Python의 def
        byte byteVar; // 데이터타입 변수
        short shortVar;
        int intVar;
        long longVar;

        byteVar = 13; // 1byte
        shortVar = 323; // 2byte, 근데 학교에서 short랑 byte를 쓸 일이 있을지는 잘 모르겠다.
        intVar = 1222; // 4byte, Default
        longVar = 100000000000000000L; // 8byte

        System.out.println("byteVar = " + byteVar);
        System.out.println("shortVar = " + shortVar);
        System.out.println("intVar = " + intVar);
        System.out.println("longVar = " + longVar);

        float floatVar = 89.4F; // 4byte, 뒤에 F 붙여야함
        double doubleVar = 1323.132312; // 8byte, Default
        System.out.println("floatVar = " + floatVar);
        System.out.println("doubleVar = " + doubleVar);

        boolean booleanVar = true; // 1 bit
        System.out.println("booleanVar = " + booleanVar);

        char charVar; // 2byte, unicode : C언어의 Char은 아스키코드(1byte)라서 한글이 안들어가는데 자바는 들어감
        charVar = '아';
        System.out.println("charVar = " + charVar);

        String stringVar; // String
        stringVar = "으아악";
        System.out.println("stringVar = " + stringVar);

        int[] arr = new int[3]; // int가 3개 들어가는 메모리를 arr에 부여
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;
        Scanner t = new Scanner(System.in); // Scanner 사용을 t로 정의
        int x = t.nextInt();
        System.out.println(x); // int 입력받기
        String str = t.next();
        System.out.println(str); // stirng 입력받기
        long k = t.nextLong();
        System.out.println(k); // long 입력받기
        System.out.println(x + intVar); // 참고로, 당연히 같은 자료형이면 연산 가능.
        System.out.println(x + longVar); // 이렇게 int형이랑 long형이랑 더하면 long형으로 튀어나옴.
        t.close(); // Scanner 닫기

        if (longVar >= intVar) { // if문은 C언어랑 다를거 없다. 대신 다른거 하나, 숫자를 True-False로 못쓴다.
            // 그러니까 C에선 조건문에선 0을 False로, 나머지 수를 True로 처리해주었지만, 자바는 무조건 Boolean형이어야한다.
            System.out.println(longVar + " is bigger than " + intVar); // 출력문은 이런식으로도 사용 가능.
        } else {
            System.out.println("Never Used");
        }
        // else - if문도 C언어랑 같으니 걱정말자. 그냥 else뒤에 if 붙여서 쓰면됨.
        System.out.printf("%05d", intVar); // format쓰고싶어? 그러면 printf로 쓰면됨. 출력값 : 01222
        // System.out 뒤에 print, printf, println이 쓰이는데 차이점은 이렇다.
        // print는 개행문자가 뒤에 없고, printf는 format 쓸 수 있고, println은 개행문자를 마지막에 삽입해준다.
        System.out.print(intVar);
        System.out.println("hihi"); // 두개 합쳐 출력값 1222hihi
    }
}