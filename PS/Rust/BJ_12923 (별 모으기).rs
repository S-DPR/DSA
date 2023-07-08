use std::fmt::Write;
use std::io;
use std::cmp::Ordering;
use std::collections::BinaryHeap;
/*
12923번 별 모으기

각 단계에서 별을 1개 혹은 2개를 얻을 수 있다.
1개를 얻으려면 이전에 별이 Ai개가 있어야하고, 2개를 얻으려면 Bi개가 있어야 한다.
각 단계에서 별을 얻기 위해 필요한 Ai, Bi가 주어질 때 스테이지를 적어도 몇 번 클리어해야할까?
Ai <= Bi임이 보장되고, 초기에는 0개의 별이 있다.

음..
더 나은 방법이 있는거같은데..

저는 우선순위큐+그리디. 그냥 정렬그리디로도 되는 모양입니다.
덕분에 Rust에 대한 이해도가 살짝 더 늘었어요..
뭘 이해했냐고요? 그러게요? 뭐였더라?

어쨌든, 일단 받은거 정렬합니다. Ai < Aj, if Ai == Aj -> Bi < Bj로.
그리고 우선순위큐를 두개 만드는데..
하나는 B가 작은순, 하나는 큰 순입니다.

일단 B를 바로 먹는게 무조건 이득인데, 그게 되면 일단 그걸 처리해줍니다.
위 작업을 위해 B가 작은순으로 정렬된 우선순위 큐가 필요하고요.
어쩔 수 없이 A를 먹어야 하는 상황도 있겠죠. 그러면 B가 큰 순으로 정렬된거에서 꺼내먹습니다.

아래 작업을 위해 현재 갖고있는 별로 A를 먹을 수 있는 너석은 따로 넣어줘야합니다.
처음에 정렬한거에서 포인터 이동시키면서 얻어오면됩니다.
그러면 최종적으로 O(NlogN).
솔직히 N이 1000이라 DP일줄알았는데..
*/
#[derive(Eq, PartialEq, Clone)]
struct Info {
	first: i64,
	second: i64,
	idx: usize
}

#[derive(Eq, PartialEq)]
struct FInfo(Info);

impl Ord for FInfo {
	fn cmp(&self, other: &Self) -> Ordering {
		return other.0.first.cmp(&self.0.first).then_with(|| other.0.second.cmp(&self.0.second));
	}
}

impl PartialOrd for FInfo {
	fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
		return Some(other.cmp(self));
	}
}

#[derive(Eq, PartialEq)]
struct SInfo(Info);

impl Ord for SInfo {
	fn cmp(&self, other: &Self) -> Ordering {
		return other.0.second.cmp(&self.0.second);
	}
}

impl PartialOrd for SInfo {
	fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
		return Some(self.cmp(other));
	}
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let N = input();
	let mut farr = Vec::new();
	let mut spq: BinaryHeap::<SInfo> = BinaryHeap::new();
	let mut mspq: BinaryHeap::<SInfo> = BinaryHeap::new();
	for i in 0..N {
		let u = input();
		let v = input();
		let item = Info{first: u, second: v, idx: i as usize};
		farr.push(FInfo(item));
	}
	farr.sort();
	let mut visit = [0; 1000];
	let mut star = 0 as i64;
	let mut cnt = 0;
	let mut idx = 0 as usize;
	while star < N*2 {
		let mut isStuck = true;
		while let Some(item) = spq.pop() {
			if star < item.0.second {
				spq.push(item);
				break;
			}
			if visit[item.0.idx] == 2 { continue; }
			star += 2-visit[item.0.idx];
			visit[item.0.idx] = 2;
			cnt += 1;
			isStuck = false;
		}
		while idx < N as usize && farr[idx].0.first <= star {
			let item = &farr[idx];
			spq.push(SInfo(Info{first: item.0.first, second: item.0.second, idx: item.0.idx}));
			mspq.push(SInfo(Info{first: item.0.first, second: -item.0.second, idx: item.0.idx}));
			isStuck = false;
			idx += 1;
		}
		while let Some(item) = mspq.pop() {
			if !isStuck {
				mspq.push(item);
				break;
			}
			if visit[item.0.idx] != 0 { continue; }
			visit[item.0.idx] += 1;
			star += 1;
			cnt += 1;
			isStuck = false;
			break;
		}
		if isStuck { break; }
	}
	if star == N*2 {
		println!("{}", cnt);
	} else {
		println!("Too Bad");
	}
}
