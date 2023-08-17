use std::fmt::Write;
use std::io;
/*
28433번 게리맨더링

수열이 주어진다.
수열을 원하는대로 잘라내자. 그리고 각 수열의 합을 구해보자.
이 때, 합이 양수인 수열이 합이 음수인 수열보다 많아질 수 있을까?

이거 내가 깃허브에 안썼었구나
빨리써야지..

대회떄는 이거 골드3수준인거같은데.. 했는데,
아하, 이건 플레4라는거에요!
대회때 못풀고 아 진짜 이거 조금만 더 건들면 될거같은데 해서 건드렸더니 된 문제입니다.

그리디인거는 솔직히 직감적으로 알았는데..
구현을.. 구현을 못해서..
그나마 더 어이없는건 진짜 단순한 실수 두개정도를 대회떄 못찾아서 못풀었어..
너무슬프다..

현재 수가 양수고 다음 수도 양수면, 현재 수는 잘라서 합이 양수인 부분수열로 보관합니다.
현재 수가 양수고 다음 수가 음수면, 다음 수가 현재 수보다 크면 잘라서 양수로 보관해두고, 아니면 합칩니다.
현재 수가 음수고 다음 수가 음수면, 합칩니다.
현재 수가 음수고 다음 수가 양수면, 다음 수가 더 크면 합치고 현재 수가 더 크면 보관합니다.

이 간단한거를! 생각을! 못해서!
아..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let T = input();
	for _ in 0..T {
		let N = input();
		let mut cur = 0;
		let mut cnt = 0;
		for i in 0..N {
			let x = input();
			if x == 0 { continue; }
			if cur <= 0 && (cur+x > 0 || x < 0) {
				cur += x;
			} else if cur > 0 && cur+x > 0 && x < 0 {
				cur += x;
			} else {
				cnt += if cur > 0 { 1 } else { -1 };
				cur = x;
			}
		}
		cnt += if cur > 0 { 1 } else { -1 };
		writeln!(output, "{}", if cnt > 0 {"YES"} else {"NO"});
		
	}
	println!("{}", output);
}
