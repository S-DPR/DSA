use std::fmt::Write;
use std::io;
/*
24337번 가희와 탑

수열의 인덱스 0부터 시작하는 가장 긴 증가하는 부분수열의 길이가 A,
수열의 제일 마지막에서 왼쪽으로 가는 가장 긴 감소하는 부분수열의 길이가 B라고 하자.
수열의 길이를 N이라고 할 때, 사전적으로 가장 앞에 오는 수열을 출력해보자.
이런 수열이 없다면 -1을 출력하자.

정말 놀라운문제
와아~ 진짜 이게 구현이지 ㅋㅋ 풀면서 진짜 걍 북마크 누르고 나가고싶었다..

먼저 -1이 나오는 조건을 생각해봅시다.
어떤 두 자연수 i와 j에 대하여, i < j이면서 i > j일 수는 없습니다.
그러므로 최대 하나의 수를 A와 B가 공유할 수 있으며, A+B-1이 수열의 최소길이가 됩니다.
최종적으로, N이 A+B-1보다 작다면 조건에 부합하는 수열이 없음을 알 수 있습니다.

맨 앞부터 천천히 처리하려 했으나, 꿈도 희망도 안보여서 역방향으로 처리했습니다.
역방향에는 반드시 1부터 B까지 수가 순서대로 존재해야합니다.
그리고 내림차순이므로 무조건 맨 뒤에 1이 있고 B개의 수가 1씩 커지며 나열되는것이 사전순으로 제일 앞에 오게 됩니다.

오름차순의 경우에도 마찬가지입니다. 적어도 반드시 1부터 A까지의 수는 존재해야합니다.
다만, 일단은 위 내림차순에 딱 붙여서 만들것이므로, A보다 B가 크다면 1~A-1, B 이런식으로 붙여도 됩니다.
이렇게, B에 max(A, B)를 넣어주고, 그 왼쪽에는 A-1, ..., 1을 순차적으로 넣어줍니다.

A+B < N이고 A가 1인경우는 조금 특별합니다.
A+B == N이라면 가장 왼쪽에는 이미 제일 큰 B가 있으므로 그냥 넘기지만,
그것보다 작고 A가 1이고, A+B < N, B > 1이라면 증가하는 부분수열의 길이는 적어도 2가 됩니다.
그러므로 이 경우 반드시 B를 왼쪽 끝에 붙여주어야합니다.
수열에서 최초로 1을 넘는 수가 나오는 경우를 캐치하여 맨 앞으로 보내주면 됩니다.

아.. 이런거에 2시간이라니..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i32>);
    let mut input = || input.next().unwrap();
	
	let N = input();
	let A = input();
	let B = input();
	if N < A+B-1 {
		println!("-1");
		return;
	}
	let mut res = [1; 100000];
	let mut cur = 0;
	for i in (N-B..N).rev() {
		cur += 1;
		res[i as usize] = cur;
	}
	cur = if A <= B { B } else { A };
	for i in (N-B-A+1..=N-B).rev() {
		res[i as usize] = cur;
		cur = if A <= B { i+B-1-N+A } else { cur-1 };
	}
	if A == 1 && A+B < N {
		for i in 1..N {
			if res[i as usize] > 1 {
				res[(A-1) as usize] = res[i as usize];
				res[i as usize] = 1;
				break;
			}
		}
	}
	for i in 0..N {
		print!("{} ", res[i as usize]);
	}
}
