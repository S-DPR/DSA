/*
PG_148653 마법의 엘레베이터

수 N을 적절하게 10의 k승 (k는 0 이상의 정수)를 더하고 빼서 0으로 만들려고 한다.
그 최소 횟수를 구해보자.

그리디 아시는구나~
정말정말 어렵습니다~
그냥 코너케이스 파악하고 찾아내기가 까다로운 문제였습니다.
*/
class Solution {
    public int solution(int storey) {
        int answer = 0;
        while (storey > 0) {
            int mod = storey%10;
            if (mod <= 5) {
                answer += storey%10;
                storey /= 10;
                if (mod == 5 && storey%10 >= 5) storey++;
            } else {
                answer += 10 - storey%10;
                storey /= 10;
                storey++;
            }
        }
        return answer;
    }
}