use std::fmt::Write;
use std::io;
/*
3844번 백준 공화국

N 이하의 수 중 원하는 만큼 골라 제곱수를 만들려 한다.
가능한 가장 큰 제곱수를 1_000_000_007로 나눈 값을 출력하자.

시간 제한이 5초같은건 역시 속도빠른거로 박아야해요
이게 시간제한보고 괜히 시간보너스 많이받는다 아싸~ 하고 가면 항상 멸망의길에 이르더라

1부터 N 이하의 수를 모두 곱한 값을 소인수분해하면 소수 i의 지수는 어떻게될까?
를 생각해야하는 문제입니다.
그냥.. 별건 없고 N/i, N/i/i, N/i/i/i... 를 다 더하면 됩니다.
나누는거다보니 항상 수는 수렴합니다. 그러니 그건 문제없고.
수가 충분히 크다면 저 값이 몇백만이 됩니다. 분할정복거듭제곱 써줍시다.

네, 이정도. 이정도면 인간사는정도죠.
*/
fn pow(x: i64, y: i64, MOD: i64) -> i64 {
	if y == 0 { return 1; }
	if y == 1 { return x; }
	let mid = pow(x, y/2, MOD);
	let double = (mid*mid)%MOD;
	if y&1 == 0 { return double; }
	return (double*x)%MOD;
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	let MOD = 1_000_000_007;
	let MAX = 10_000_000;
	
	let sqrtM: usize = (MAX as f64).sqrt() as usize;
	let mut prime: Vec<i64> = Vec::new();
	let mut arr: Vec<_> = (0..=MAX).collect();
	for i in 2..=MAX {
		if arr[i] != i { continue; }
		prime.push(i as i64);
		let mut j = i+i;
		while j <= MAX {
			arr[j] = i;
			j += i;
		}
	}
	loop {
		let N = input();
		if N == 0 { break; }
		let mut ret = 1;
		for i in &prime {
			if N < *i { break; }
			let mut cur = N;
			let mut cnt = 0;
			while cur != 0 {
				cur /= i;
				cnt += cur;
			}
			if cnt&1 == 1 { cnt -= 1; }
			ret = (ret*pow(*i, cnt, MOD))%MOD;
		}
		writeln!(output, "{}", ret);
	}
	print!("{}", output);
}
