#include <iostream>
#include <algorithm>
#include <vector>
#include <set>
#include <map>
#define ll long long
using namespace std;
/*
16570번 앞뒤가 맞는 수열

수열이 주어진다. 앞에서 원하는만큼 원소를 제거할 수 있다.
이 때, 배열에서 접미사와 접두사의 길이가 같은 최대 길이를 K라고 하자.
K의 최댓값과 나오게 할 수 있는 원소의 제거 방법의 개수를 구하시오.

히힣 이건 분명 투포인터야 난 이제 투포인터에 속지않는 엄청난 고수야

라고생각했는데 어~ 형이야~ 형은 멍청한놈들 컷하는 KMP야~ 라고 하네요.
하..

KMP는 실패함수가 메인이죠. 이거 뜻 모르고 그냥 KMP만 알고있었는데 실패함수에 대해 알아봤습니다.
그리고 이 문제가 그냥 실패함수나 구해보셈 ㅋㅋ 하는 문제라는 것도 알게되었죠..
KMP 실패함수 알아가는 좋은 문제였습니다.
그리고 count라는게 있다는거도 알게됐네요.
*/
const int MAX = 1'000'000;
int N, A[MAX];
int fail[MAX];

void construct_fail(int S[], int len) {
    for (int i = 1, j = 0; i < len; i++) {
        while (j && S[i] != S[j]) j = fail[j-1];
        if (S[i] == S[j]) fail[i] = ++j;
    }
}

int main() {
    scanf("%d", &N);
    for (int i = N-1; i >= 0; i--) {
        scanf("%d", &A[i]);
    }
    construct_fail(A, N);
    int mx = *max_element(fail, fail+N);
    int cnt = count(fail, fail+N, mx);
    if (mx) printf("%d %d", mx, cnt);
    else printf("-1");
}
