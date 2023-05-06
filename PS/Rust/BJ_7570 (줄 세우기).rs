use std::fmt::Write;
use std::io;
use std::collections::HashMap;
/*
7570번 줄 세우기

1부터 N까지 있는 길이가 N인 수열이 있다.
수 하나를 뽑아 맨 앞, 혹은 맨 뒤로 보낼 수 있다.
이 행위를 최소화하여 수열을 정렬할 때, 필요한 최소 횟수를 구해보자.

히히 답 다 봐버리고 구현만 해버리기
LIS 문제에 이런것도 있구나, 처음알았습니다.
LIS는 LIS인데 연속한 수의 LIS..
사실상 LIS 구현은 필요없고, 위 아이디어만 잡아내면 풀 수 있는 문제로 보입니다.

문젠 답을 이미 봐버렸단거.. 보자마자 구현법 떠오른거..
앞으로는 좀 모르겠다고 질문게시판 가는걸 자중해야겠네요..
그냥 오늘은 코테 수고했다는 의미로 받아들여야지..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i32>);
    let mut input = || input.next().unwrap();
	
	const INF: i32 = 1_000_000;
	let N = input();
	let mut dp = [INF;(INF+1) as usize];
	for _ in 0..N {
		let cur = input() as usize;
		if dp[cur-1] != INF {
			dp[cur] = dp[cur-1]+1;
		} else {
			dp[cur] = 1;
		}
	}
	let mut longest = 0;
	for i in 1..=N {
		longest = std::cmp::max(longest, dp[i as usize]);
	}
	println!("{}", N-longest);
}