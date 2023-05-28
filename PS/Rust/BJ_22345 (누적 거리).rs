use std::fmt::Write;
use std::io;
/*
22345번 누적 거리

수직선상의 어떤 좌표 a와 그 가중치 x가 N개 주어진다.
이후 K개의 쿼리가 절댓값이 10의 9승 이하인 숫자로 주어진다.
모든 좌표에 대해, |Q-a|*x의 합을 구하면 된다.
모든 쿼리에 답을 해보자.

아 ㅋㅋㅋㅋㅋㅋ
이게 수학이 껴있네 ㅋㅋㅋㅋㅋㅋㅋㅋㅋ

식을 쓸 생각조차 안해서 못풀고 결국 정해를 봐버린 문제
스위핑으로도 된다는데 어떻게..? 한번 애들 코드 뒤져봐야겠네..
처음에 했던건 O(1000*QlogN)이었습니다. 가중치 x가 1이상 1000이하여서 거기 꽂혔거든요.
그런데 시간초과나길래 뭐지.. 했는데..

이런문제 나오면 꼭 식으로 써봐야겠네요.. 아....
*/
fn lower_bound(arr: &Vec::<i64>, x: i64) -> usize {
	let mut lo = 0 as usize;
	let mut hi = (arr.len()-1) as usize;
	while lo < hi {
		let mid = (lo + hi) >> 1;
		if arr[mid] >= x {
			hi = mid;
		} else {
			lo = mid + 1;
		}
	}
	return hi;
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	const INF: i64 = 1_000_000_000;
	
	let N = input();
	let K = input();
	let mut I = Vec::new();
	let mut Xf = vec![i64::from(0); 1];
	let mut Pf = vec![i64::from(0); 1];
	let mut D = vec![INF+1];
	for _ in 0..N {
		let u = input();
		let v = input();
		I.push([v, u]);
		D.push(v);
	}
	I.sort();
	D.sort();
	for i in I {
		let (v, u) = (i[0], i[1]);
		Pf.push(Pf[Pf.len()-1] + u);
		Xf.push(Xf[Xf.len()-1] + u*v);
	}
	for _ in 0..K {
		let Q = input();
		let idx = lower_bound(&D, Q);
		let left_d = Q*Pf[idx];
		let left_p = Xf[idx];
		let right_d = Q*(Pf[Pf.len()-1] - Pf[idx]);
		let right_p = Xf[Xf.len()-1] - Xf[idx];
		let S = left_p-left_d+right_d-right_p;
		writeln!(output, "{}", -S);
	}
	print!("{}", output);
}
