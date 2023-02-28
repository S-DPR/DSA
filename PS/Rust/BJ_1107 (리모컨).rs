use std::fmt::Write;
use std::io;
/*
1107번 리모컨

리모컨의 숫자 버튼이 K개 고장났다. 즉, 채널을 바꿀 때 K개의 숫자는 사용할 수 없다.
하지만 +, -가 있어 1씩 위, 아래로 왔다갔다 할 수 있다.
현재 보고있는 채널은 100번이다. N번 채널로 가기 위해 최소 몇 번 버튼을 눌러야하는지 구해보자.

Rust 입문문제
그리디인줄 알았는데 브루트포스더라고요. 앞으로 브루트포스 되나 확인하고 문제를 풀어야하나..

어쨌든 첫 문제로 다행히 그닥 어렵진 않은 문제가 나왔습니다.
태그 안봤으면 체감 골드 2 될뻔했지만, 태그를 봐버렸네요.
그냥 말 그대로 전부 체크해보면 되는문제이므로, 푸는데 지장은 없었습니다.
언어장벽때문에 난이도 +2 된 느낌이지만..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i32>);
    let mut input = || input.next().unwrap();
    let mut output = String::new();

	let N = input();
	let K = input();
	let mut arr = [true; 10];
	for i in 0..K {
		let broken = input();
		arr[broken as usize] = false;
	}
	let mut current = 100;
	let mut min = (N - 100).abs();
	for cur in 0..=1000000 {
		let length = cur.to_string().len() as i32;
		let diff = (N - cur).abs();
		let mut flg = false;
		for i in cur.to_string().chars() {
			if !arr[(i as i32 - '0' as i32) as usize] {
				flg = true;
			}
		}
		if flg { continue; }
		if (length + diff) < min {
			current = cur;
			min = length + diff;
		}
	}
	output = min.to_string();
	println!("{}", output);
}
