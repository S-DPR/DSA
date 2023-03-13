use std::fmt::Write;
use std::io;
/*
2854번 문제 출제

문제의 난이도는 1과 N 사이의 모든 자연수로 표현할 때,
어떤 문제의 난이도는 정확히 i의 값을 가지지만 어떤 문제의 난이도는 i, i+1 두개의 값을 가진다.
첫째줄에는 N이 주어지고,
두번째줄에는 i번째 수가 i의 난이도를 갖도록 문제 수가 음이 아닌 정수로 N개 주어진다.
세번쨰줄에는 i번째 수가 i 혹은 i+1의 난이도를 갖도록 문제 수가 음이 아닌 정수로 N-1개 주어진다.
1부터 N까지 모든 난이도를 하나씩 고르는 경우의 수를 1000000007로 나눈 값으로 구해보자.

풀다가 어질어질해지는 문제였습니다.
저같은경우 1차원 dp로 풀었는데, 2차원도 가능은 하다고 합니다.

1차원으로 한다면 dp[i]는 i번째 난이도까지 가능한 경우의 수가 되며,
아래 순서를 따라 dp를 갱신하면 됩니다.
1. dp의 첫번째 값을 갱신합니다. arr[0][1] + arr[1][1]로 갱신됩니다.
2. dp의 두번째 값을 갱신합니다. arr[0][1] * arr[1][1] + arr[1][1] * (arr[1][1] - 1)이 됩니다.
3. dp의 i번째 값(2 <= i <= N)을 갱신합니다. 단, 아래 순서를 따릅니다.
3-1. 현재 값에 난이도가 정해진 문제를 고른 경우를 더합니다.
3-2. 그 상태에서 난이도가 i+1이 될 수 있는 경우를 곱하여 dp[i+1]에 더합니다.
3-3. dp[i-1]에 난이도가 i+1이 될 수 있는 문제 중 두개 고른 경우를 dp[i+1]에 더합니다.
3-4. dp[i]에 i, i+1중 하나가 되는 경우 중 i를 고른 경우를 더합니다.
4. dp[N]을 출력합니다.
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	
	let N = input();
	let MOD = 1_000_000_007;
	let mut arr = [[i64::from(0);100001];2];
	let mut dp = [i64::from(0);100002];
	for i in 1..=N {
		arr[0][i as usize] = input();
	}
	for i in 1..N {
		arr[1][i as usize] = input();
	}
	dp[1] = arr[0][1] + arr[1][1];
	dp[2] = arr[0][1] * arr[1][1];
	dp[2] += arr[1][1] * (arr[1][1]-1);
	dp[2] %= MOD;
	dp[1] %= MOD;
	for i in 2..=N {
		dp[i as usize] += dp[(i-1) as usize]*arr[0][i as usize];
		dp[i as usize] %= MOD;
		dp[(i+1) as usize] += dp[i as usize]*arr[1][i as usize];
		dp[(i+1) as usize] %= MOD;
		dp[(i+1) as usize] += dp[(i-1) as usize]*((arr[1][i as usize]*(arr[1][i as usize]-1))%MOD);
		dp[(i+1) as usize] %= MOD;
		dp[i as usize] += dp[(i-1) as usize]*arr[1][i as usize];
		dp[i as usize] %= MOD;
	}
	println!("{:?}", dp[N as usize]%MOD);
}
