#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
3086번 초콜릿 자르기

R*C크기의 직사각형을 정사각형이 가장 적게 나오도록 잘라보자.

사실상 그냥 뭐 답지보고 푼 문젠데..
이게 꼭 바텀업이어야하나..?
1초제한에서 900ms가걸리네..

내생각에 내가 본 dp문제중 최적화 제일 빡세게 통과한 문제.
솔직히 dp[r][c]는 r*c에서 만들 수 있는 가장 작은 정사각형 개수..라고 금방 결론냈는데.
최적화 난이도가.. 너무.. 높아요..

이건 2초줄만하지않나.. 바텀업 C++로 300ms걸리는데..
어쨌든 오랜만에 바텀업으로 푼 dp문제.
그동안은 생각해보면 트리dp니 비트dp니해서 전부 재귀로했었죠.

코드는 진짜 간단해요. 이게 플레3에 같은문제에선 최적화 더 해야하는거 같긴 한데, 어쨌든요.
쉽지않네요..
구간dp인거같긴한데 안풀리는 구간dp..
보니까 상수커팅이라는데 아..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let R = input() as usize;
    let C = input() as usize;
    let mut dp = [[1<<30;1001];1001];
    for i in 1..=R {
        for j in 1..=C {
            if i == j {
                dp[i][j] = 1;
                continue;
            }
            for k in 1..=i/2 {
                dp[i][j] = std::cmp::min(dp[i][j], dp[i-k][j]+dp[k][j]);
            }
            for k in 1..=j/2 {
                dp[i][j] = std::cmp::min(dp[i][j], dp[i][j-k]+dp[i][k]);
            }
        }
    }
    println!("{}", dp[R][C]);
}
