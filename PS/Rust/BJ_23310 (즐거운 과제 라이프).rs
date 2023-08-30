use std::fmt::Write;
use std::io;
/*
23310번 즐거운 과제 라이프

과제가 N개 있다. 각 과제를 다 하는데 Ai의 시간이 필요하다고 하자.
각 과제를 하나씩 돌아가면서 하려 한다. 단, K의 배수인 날에는 과제를 안하고 놀 것이다.
이 때, 가장 먼저 끝나는 과제의 번호를 출려갛자.

와 쉽지 않네
20번만에 맞췄다고?

후..
일단 사이클을 구해야합니다. 이건 대충 배열 만들어서 처리해주면 됩니다.
다음으로, 모든 과제에 대해 언제 끝나는지 구하면 됩니다. 이 부분은 완전탐색이네요..

그런데, 그 모든 과제에 대해 시간을 계산하다 망했습니다.
하아..
1. 일단 처음으로 사이클 시작되는 부분이 들쑥날쑥하니 이걸 평탄화시키고,
2. 쉬는시간을 계산해서 처리해주고,
3. 나머지 처리.

1, 3은 괜찮은데 2에서 망했네요.
앞으론 식을 더 잘 세워보는거로 합시다..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let N = input();
	let M = input() as usize;
	let mut items = [[-1;2];20001];
	let mut idx = [0;20001];
	let mut arr = [0;20001];
	for i in 1..=N as usize {
		arr[i] = input();
	}
	let mut cur: usize = 0;
	while true {
		cur += M as usize;
		let mut cnt: i64 = (cur - 1) as i64 / N;
		let mut cidx = cur % N as usize;
		if cidx == 0 {
			cidx = N as usize;
		}
		let curIdx = idx[cidx];
		if curIdx == 2 {
			break
		} else {
			items[cidx][curIdx] = cnt;
		}
		idx[cidx] += 1;
	}
	let mut ret = 0;
	let mut day = -1;
	for i in 1..=N as usize {
		let mut rep = arr[i];
		let first = items[i][0];
		let second = items[i][1];
		let restGap = second-first;
		let mut curD: i64 = 0;
		let mut flg = false;
		if rep > first && first != -1 {
			curD = (first + 1)*N;
			rep -= first;
		}
		if restGap > 0 {
			if restGap == 1 {
				flg = true;
			} else {
				curD += rep / (restGap-1) * N;
				if rep % (restGap - 1) == 0 {
					curD -= N;
				}
			}
		}
		if rep > 0 {
			curD += (rep-1)*N+i as i64;
		}
		if !flg && (day == -1 || day > curD) {
			day = curD;
			ret = i;
		}
	}
	println!("{}", ret);
}
