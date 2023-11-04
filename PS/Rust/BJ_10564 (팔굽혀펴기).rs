#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
10564번 팔굽혀펴기

응원하는 팀이 득점을 할 때마다 팔굽혀펴기를 했다고 한다.
그런데, 팔굽혀펴기의 횟수와 득점의 종류만 기억이 나서 몇 점을 얻었는지를 모른다.
예를들어 29번 팔굽혀펴기를 했고 득점 종류가 7 3 2라면,
먼저 7점을 따 팔굽혀펴기를 7번 한다.
이후 3점을 따 7+3번, 즉 10번 한다.
이후 2점을 따 7+3+2번, 즉 12번 한다.
이러면 29회의 팔굽혀펴기를 하는 최고점수이다.
횟수와 종류가 주어졌을 때 최고점을 구해보자. 불가능하면 -1을 대신 출력하자.

dp할당제로 인해 dp를 찾다가 풀게 된 문제.
dp식은 금방 세웠는데 멍청한짓해서 푸는데 시간이 걸렸습니다.
dp[i][j] = i번 팔굽혀펴기 했고 j점.
이러면 금방 풀립니다. j가 많아도 460이라는 점을 이용해서.
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let T = input();
    for _ in 0..T {
        let n = input() as usize;
        let m = input() as usize;
        let mut dp = Vec::new();
        let mut item = [0;10];
        for _ in 0..=n {
            dp.push(Vec::from([false;500]));
        }
        for i in 0..m {
            item[i] = input() as usize;
        }
        dp[0][0] = true;
        for i in 0..=n {
            for j in 0..500 {
                if !dp[i][j] { continue; }
                for k in 0..m {
                    let score = item[k];
                    if i+j+score > n { continue; }
                    dp[i+j+score][j+score] = true;
                }
            }
        }
        let mut ret = -1;
        for i in 0..500 {
            if dp[n][i] { ret = i as i64 }
        }
        println!("{}", ret);
    }
}
