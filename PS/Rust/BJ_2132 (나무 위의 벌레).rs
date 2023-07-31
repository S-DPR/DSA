use std::fmt::Write;
use std::io;
/*
2132번 나무 위의 벌레

트리가 주어지고, 각 트리의 노드에 가중치가 주어진다.
정점 하나에서 출발하여 가중치를 가장 높게 모아보자. 단, 이미 왔던 길은 되돌아갈 수 없다.

또또 나만 어렵게풀었지
아...

아니님들아진짜막이거그냥아니보자마자그냥트리dp가생각낫는데요이게막트리dp같은거필요없고그냥트리의지름이래요나억울해
심지어트리dp를빠르게잘짰으면모르겠어그런데3시간이나박아서겨우겨우푼거다시생각해봐도내가너무멍청해서할말이아나와진자
하...
내시간..

트리dp로는 어떻게했느냐..
내 자식 노드를 다 살펴봅니다.
그중 누적가중치가 가장 큰 두 개를 뽑아옵니다. 인덱스랑 같이.

1. 그 두 노드의 가중치가 0이라면 자기 자신이 출발점이 될 수 있습니다.
2. 나 자신이 꺾이는 점이 될 수 있습니다. 이런 경우 두 노드가 모두 가중치를 가지고있어야하고, 두 노드중 인덱스가 더 작은걸 보내주면 됩니다.
3. 그냥 내 부모에게 가는 길이 될 수도 있습니다. 이 경우 두 노드 중 더 큰 가중치를 가진 인덱스를 가져옵니다.
어.. 어쨌든 이런느낌으로.. 쭉 풀면 됩니다.. 너무쉽다 와..
트리지름이 더쉽다 와.....
심지어 트리dp로 굴리면 dfs 진행도중 정답을 갱신해주면서 가야해서.. 중간에 retW, retN이 수정되는게 그 부분입니다..
*/
fn dfs(dp: &mut [i64;10001], G: &Vec<Vec<usize>>,
		V: &mut [bool;10001], items: &[i64;10001],
		x: usize, retW: &mut i64, retN: &mut usize) -> (i64, usize) {
	if V[x] { return (-1, 10001); }
	V[x] = true;
	dp[x] = items[x];
	let mut fM = 0;
	let mut sM = 0;
	let mut fI = 10001;
	let mut sI = 10001;
	for i in &G[x] {
		let R = dfs(dp, G, V, items, *i, retW, retN);
		let ret = R.0;
		let idx = R.1;
		if std::cmp::min(fM, sM) <= ret {
			if fM <= sM {
				fI = if fM < ret {idx} else {std::cmp::min(fI, idx)};
				fM = ret;
			} else {
				sI = if sM < ret {idx} else {std::cmp::min(sI, idx)};
				sM = ret;
			}
		}
	}
	
	let curM = items[x]+fM+sM;
	if *retW < curM {
		*retW = curM;
		*retN = std::cmp::min(fI, sI);
		if fM == 0 || sM == 0 {
			*retN = std::cmp::min(*retN, x);
		}
	} else if *retW == curM {
		*retN = std::cmp::min(*retN, fI);
		*retN = std::cmp::min(*retN, sI);
		if fM == 0 || sM == 0 {
			*retN = std::cmp::min(*retN, x);
		}
	}
	
	let mut mI = 10001;
	if fM != sM {
		mI = if fM < sM {sI} else {fI};
	} else {
		mI = std::cmp::min(fI, sI);
	}
	if fM+sM == 0 {
		mI = std::cmp::min(mI, x);
	}

	dp[x] += std::cmp::max(fM, sM);
	return (dp[x], mI);
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let N = input();
	let mut items = [0;10001];
	for i in 1..=N {
		items[i as usize] = input();
	}
	let mut G = Vec::new();
	for _ in 0..=N {
		G.push(Vec::new());
	}
	for i in 1..N {
		let u = input() as usize;
		let v = input() as usize;
		G[u].push(v);
		G[v].push(u);
	}
	let mut dp = [0;10001];
	let mut retW = 0;
	let mut retN = N as usize;
	let mut root = 1;
	dfs(&mut dp, &G, &mut [false;10001], &items, 1, &mut retW, &mut retN);
	println!("{} {}", retW, retN);
}

/*
트리의 지름 기반

use std::fmt::Write;
use std::io;

fn dfs(dist: &mut [i64;10001], G: &Vec<Vec<usize>>, V: &mut [bool;10001], 
		items: &[i64;10001], x: usize, pf_dist: i64) {
	if V[x] { return; }
	dist[x] = pf_dist+items[x];
	V[x] = true;
	for i in &G[x] {
		dfs(dist, G, V, items, *i, dist[x]);
	}
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let N = input() as usize;
	let mut items = [0;10001];
	for i in 1..=N {
		items[i] = input();
	}
	let mut G = Vec::new();
	for _ in 0..=N {
		G.push(Vec::new());
	}
	for i in 1..N {
		let u = input() as usize;
		let v = input() as usize;
		G[u].push(v);
		G[v].push(u);
	}
	let mut dist = [0;10001];
	dfs(&mut dist, &G, &mut [false;10001], &items, 1, 0);
	let mut root = 0;
	let mut root_w = -1;
	for i in 1..=N {
		if root_w < dist[i] {
			root = i;
			root_w = dist[i];
		}
	}
	let mut ret_dist = [0;10001];
	dfs(&mut ret_dist, &G, &mut [false;10001], &items, root, 0);
	let mut ret = 0;
	let mut ret_w = -1;
	for i in 1..=N {
		if ret_w < ret_dist[i] {
			ret = i;
			ret_w = ret_dist[i];
		}
	}
	println!("{} {}", ret_w, std::cmp::min(ret, root));
}
*/