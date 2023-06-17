use std::fmt::Write;
use std::io;
/*
1311번 할 일 정하기 1

N*N크기의 행렬이 주어진다. 이 행렬을 M이라 할 때, 아래의 의미를 갖는다.
M[i][j] = i번째 일을 j번째 사람이 할 때 걸리는 시간
한명에게 하나의 일을 할당하려 한다. 걸리는 시간의 합의 최소를 구해보자.

헝가리안알고리즘으로 푸는게 할 일 정하기 2,
비트DP나 헝가리안알고리즘으로 푸는게 할 일 정하기 1.
옛날에 이거 어떻게푸냐 하고 북마크에 넣었다가 뺀게 기억이 났네요.
랜덤에서 떴길래 채왔습니다.

비트DP인건 알고있었습니다. 이거 어캐푸냐 하면서 본적이 있어서..
그래서 그냥 비트dp 돌려서 AC맞았습니다. 비트DP 기초문제네요.
외판원보다 쉬운거같기도하고..
*/
fn loopfn(M: &[[i64; 20]; 20], dp: &mut [[i64; 1<<20]; 20], V: usize, cur: usize, N: usize) -> i64 {
	if cur == N { return 0; }
	if dp[cur][V] != -1 { return dp[cur][V]; }
	dp[cur][V] = 1_000_000_000;
	for i in 0..N {
		if (V & (1<<i)) != 0 { continue; }
		let nxtV = V | (1<<i);
		dp[cur][V] = std::cmp::min(dp[cur][V], loopfn(M, dp, nxtV, cur+1, N)+M[cur][i]);
	}
	return dp[cur][V];
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let N = input() as usize;
	let mut M = [[0;20];20];
	for i in 0..N {
		for j in 0..N {
			M[i][j] = input();
		}
	}
	let mut dp = [[i64::from(-1); 1<<20]; 20];
	println!("{}", loopfn(&M, &mut dp, 0, 0, N));
}
