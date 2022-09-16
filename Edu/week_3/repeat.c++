#include <stdio.h>

int main() {
	int arr[5] = { 10, 20, 30, 40, 50 };
	// 컴공중엔 모르는놈이 없는 for문
	//
	// for (초기값; 조건; 연산자) { 반복문 내용 }
	//
	// 반복문 내용이 한 줄인경우 중괄호 생략 가능
	// for문 실행 순서 : 초기값 -> 조건 -> 반복문 내용 -> 
	//                  연산자 -> 조건 -> 반복문 내용 -> 
	//                  연산자 -> ...
	for (int i = 0; i < 5; i++) printf("%d ", i); // 출력값 : 0 1 2 3 4
	printf("\n");

	for (int i = 0; i < 5; i++) printf("%d ", arr[i]); // 출력값 : 10 20 30 40 50
	printf("\n");
    
	for (int i = 0; i < 5; i++) printf("%d %d | ", i, arr[i]);
	printf("\n");
	// 출력값 : 0 10 | 1 20 | 2 30 | 3 40 | 4 50 | 

	// 주의점 : 의도가 아니라면 반복문 내용에서 반복문 연산자는 건드리지 않는 것이 좋다.
	for (int i = 0; i < 3; i++) printf("%d ", i++);
	printf("\n");
	// 출력값 : 0 2

	// 아래는 모르는사람 있을 for문
	// 범위 기반 for문
	for (int x : arr) printf("%d ", x);
	printf("\n");
	// 출력값 : 10 20 30 40 50

	// 초기값을 갖는 범위 기반 for문
	for (int i = 0; auto x: arr) { // C++20 이상 버전부터 가능
		printf("%d %d | ", i, x);
		i++;
	} // 출력값 : 0 10 | 1 20 | 2 30 | 3 40 | 4 50 | 
	printf("\n");

	// 초기값 혹은 연산자를 두 개 이상 갖는 for문
	for (int i = 0, j = 10; i < 3; i++, j--) printf("%d %d | ", i, j);
	printf("\n");
	// 출력값 : 0 10 | 1 9 | 2 8 | 

	// 초기값 혹은 조건문 혹은 연산자를 갖지 않는 for문
	for (int i = 3; i--;) printf("%d ", i);
	printf("\n");
	// 조건식에 숫자가 들어가면 0을 제외한 모든 수는 True로, 0은 False로 대체된다.
	// 초기값 실행 후 조건문을 한번 실행해보고 들어가기에, 첫 출력 i는 2이다.
	// 출력값 : 2 1 0

	// while (1)과 다를게 없는 for문
	int i = 3;
	for (;;) {
		printf("%d ", i--);
		if (!i) break;
	}
	printf("\n");
	// 출력값 : 3 2 1


	// while문은 뭐 특별할게 없다.
	// 그냥 for문에서 '조건문만 필요해요~'할 때 while 쓰는거다.
	// for문 마스터하면 while문은 알아서 마스터된다.
	int i = 3;
	while (i--) printf("%d ", i);
	// 출력값 : 3 2 1

	// N번의 입력을 받거나 하는 경우, while문으로 간단하게 작성할 수 있다.
}