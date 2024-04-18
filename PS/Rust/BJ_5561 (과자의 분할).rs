#![allow(warnings)]
use std::fmt::Write;
use std::io;
/*
5561번 과자의 분할

1*N칸으로 이루어진 과자가 있다.
이 과자를 두 조각으로 쪼갤 수 있다. 길이가 1이 아니라면 그 조각 또한 다시 쪼갤 수 있다.
각 부분을 쪼갤 때 필요한 힘이 주어진다.
어떻게든 조각을 모아 N/2개가 되게 하려 할 때, 그 최솟값을 구해보자.

답지 보고 풀었는데 아직도 모르겠네
이게 공간복잡도 N^2인걸 N으로 만든건 알겠는데.
이거 그니까 그냥 배낭아닌가?? 싶은데 보다보면..
근데 솔직히 아직도 잘 모르겠어요. 한번 계속 봐야할거같은데.
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let N = input() as usize;
    let mut dp = [[1<<30; 10001]; 2];
    for i in 2..=N {
        let r = i&1;
        let ir = r^1;
        let x = input() as i32;
        for j in (2..=i).rev() {
            dp[r][j] = std::cmp::min(dp[ir][j-1], dp[ir][i-j]+x);
        }
        dp[r][1] = x;
    }
    println!("{}", dp[0][N/2]);
}
