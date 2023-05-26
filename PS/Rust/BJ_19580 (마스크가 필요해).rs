use std::fmt::Write;
use std::io;
use std::collections::BinaryHeap;
/*
19580번 마스크가 필요해

N과 K가 주어진다.
이후 N개의 줄에 S, E가 주어진다.
이후 K개의 줄에 X, C가 주어진다.
먼저 받은 N개의 줄에 대해 S부터 E까지 겹치지 않게 선을 그리고,
그 선이 X좌표를 포함하면 그 선을 지우는 행위를 주어진 X좌표당 최대 C번 할 수 있다 할 떄,
지울 수 있는 최대의 선의 개수를 구해보자.

꺄아악!!!!!!
이거를 그리디 PQ구나 생각해놓고 아닌가? 하면서 스위핑으로 넘어가???????
심지어 며칠전에 비슷한거 풀어놓고????
이러니까 한문제에 3~4시간씩 맨날 쳐박지 아...

쉬운 플레5입니다. 아니 이게 진짜 님들아 아니 아..
그냥.. 그냥.. PQ굴리면 끝나요.. 진짜..
그냥 C++이나 Rust같이 빠른거 아니면 시간초과나서 플레5인거야..
아.. 진짜 너무 허무하다..
*/
#[derive(Debug, Eq, PartialEq)]
struct cus(i64, i64);

impl Ord for cus {
	fn cmp(&self, other: &Self) -> std::cmp::Ordering {
		other.1.cmp(&self.1).then_with(|| self.0.partial_cmp(&other.0).unwrap())
	}
}

impl PartialOrd for cus {
	fn partial_cmp(&self, other: &Self) -> Option<std::cmp::Ordering> {
		Some(self.cmp(other))
	}
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let N = input();
	let K = input();
	let mut customer = Vec::new();
	let mut seller = Vec::new();
	for _ in 0..N {
		let u = input();
		let v = input();
		customer.push(cus(u, v));
	}
	for _ in 0..K {
		let u = input();
		let v = input();
		seller.push([u, v].to_vec());
	}
	customer.sort_by_key(|i| -i.0);
	seller.sort();
	let mut ret = 0;
	let mut customer_pq: BinaryHeap<cus> = BinaryHeap::new();
	for i in 0..K {
		let item = &seller[i as usize];
		let (val, cnt) = (item[0], item[1]);
		while !customer.is_empty() && customer.last().unwrap().0 <= val {
			customer_pq.push(customer.pop().unwrap());
		}
		while !customer_pq.is_empty() && customer_pq.peek().unwrap().1 < val {
			customer_pq.pop();
		}
		for _ in 0..cnt {
			if !customer_pq.is_empty() && val <= customer_pq.peek().unwrap().1 {
				customer_pq.pop();
				ret += 1;
			}
			else { break; }
		}
	}
	println!("{ret}");
}
