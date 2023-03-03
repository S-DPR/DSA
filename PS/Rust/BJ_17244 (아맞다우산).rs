use std::fmt::Write;
use std::io;
use std::collections::{VecDeque};
/*
17244번 아맞다우산

S에서 출발해 모든 X지점에 도달한 뒤 E에 도착하려 한다.
최소 경로의 길이를 구해보자.

저어기 옆집 난이도가 같은 로봇청소기랑 같은문제
모든 S와 X에서 모든 경로의 최소경로의 길이를 구한 뒤,
브루트포스로 모든 X에 대해 어떤 순서로 도달하냐를 확인하며 길이를 처리하면됩니다.
나온 값중 제일 작은값이 정답이겠죠.
BFS, DFS 모두 사용하는 문제였습니다.

틀린이유는 bfs에서 이유는 몰라도 무한루프가 돌아서..
*/
fn dfs(nums: &mut Vec<i32>, idx: usize, result: &mut Vec<Vec<i32>>) {
    if idx == nums.len() {
        result.push(nums.clone());
    } else {
        for i in idx..nums.len() {
            nums.swap(idx, i);
            dfs(nums, idx+1, result);
            nums.swap(idx, i);
        }
    }
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace();//.flat_map(str::parse::<i32>);
    let mut input = || input.next().unwrap();
	
	let N = input().parse::<i32>().unwrap();
	let K = input().parse::<i32>().unwrap();
	let mut M = Vec::new();
	for _ in 0..K {
		M.push(input().as_bytes().to_vec());
	}
	let mut S = Vec::new();
	let mut get = Vec::new();
	let mut E = Vec::new();
	for i in 0..K {
		for j in 0..N {
			if M[i as usize][j as usize] == ('S' as u8) {
				S.push([i, j, 0]);
				M[i as usize][j as usize] = b'.';
			}
			if M[i as usize][j as usize] == ('X' as u8) {
				get.push([i, j, 0]);
				M[i as usize][j as usize] = '.' as u8;
			}
			if M[i as usize][j as usize] == ('E' as u8) {
				E.push([i, j]);
				M[i as usize][j as usize] = '.' as u8;
			}
		}
	}
	let l = get.len();
	for i in &get {
		S.push(*i);
	}
	let dx = [1, -1, 0, 0];
	let dy = [0, 0, 1, -1];
	let mut visits = Vec::new();
	let CS = S.clone();
	for i in CS {
		let mut Q = VecDeque::new();
		Q.push_back(i);
		let mut visit = vec![vec![2500; N as usize]; K as usize];
		visit[Q[0][0] as usize][Q[0][1] as usize] = 0;
		while !Q.is_empty() {
			let cur = Q.pop_front().unwrap();
			let curY = cur[0];
			let curX = cur[1];
			let curT = cur[2];
			for i in 0..4 {
				let nx = curX+dx[i];
				let ny = curY+dy[i];
				if !(0 <= nx && nx < N && 0 <= ny && ny < K) { continue; }
				if visit[ny as usize][nx as usize] != 2500 { continue; }
				if M[ny as usize][nx as usize] == b'#' { continue; }
				visit[ny as usize][nx as usize] = curT+1;
				Q.push_back([ny, nx, curT+1]);
			}
		}
		let mut v = Vec::new();
		for j in 0..l {
			v.push(visit[get[j][0] as usize][get[j][1] as usize]);
		}
		v.push(visit[E[0][0] as usize][E[0][1] as usize]);
		visits.push(v);
	}
	let mut dfs_result = Vec::new();
	let mut v = Vec::new();
	for i in 1..=l {
		v.push(i as i32);
	}
	let mut result = 15000;
	dfs(&mut v, 0, &mut dfs_result);
	for i in dfs_result {
		let mut i = i;
		i.insert(0, 0);
		let mut cur = 0;
		for j in 1..i.len() {
			cur += visits[i[j-1] as usize][(i[j]-1) as usize];
		}
		cur += visits[i[i.len()-1] as usize][l];
		result = std::cmp::min(result, cur);
	}
	println!("{result}");
}
