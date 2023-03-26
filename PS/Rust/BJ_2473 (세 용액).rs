use std::fmt::Write;
use std::io;
/*
2473번 세 용액

수열이 주어진다.
여기서 서로 다른 세 요소룰 선택하자. 이 값의 합이 0에 가장 가까운 경우를 출력하자.

Ruby로 풀려니까 시간초과나서 Rust로 눈물을 흘리며 짰는데 쭈욱 오르는 퍼센트
알고리즘을 내가 잘못짰나 했습니다..

그냥 두개 고정하고 하나를 이분탐색으로 찾는 아이디어입니다.
투포인터도 있지만 개인적으로 전 이분탐색이 더 눈에가서..
그냥 두 용액 문제랑 크게 다를거 없다고 보이네요.
*/
fn lowerBound(arr: &Vec<i64>, x: i64, l: i64, r: i64) -> i64 {
	let mut left = l;
	let mut right = r;
	while left < right {
		let mid = (left + right) >> 1;
		if arr[mid as usize] >= x {
			right = mid;
		} else {
			left = mid + 1;
		}
	}
	return right;
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	
	let N = input();
	let mut arr = Vec::new();
	for i in 0..N {
		arr.push(input());
	}
	let mut res = [0; 3];
	let mut diff = 1_000_000_000_000;
	arr.sort();
	for i in 0..N-2 {
		for j in i+1..N-1 {
			let sum = arr[i as usize] + arr[j as usize];
			let mut idx = lowerBound(&arr, -sum, j+1, N-1);
			if idx-1 > j && (arr[(idx-1) as usize]+sum).abs() < (arr[idx as usize]+sum).abs() {
				idx -= 1;
			}
			if (sum+arr[idx as usize]).abs() < diff {
				res = [arr[i as usize], arr[j as usize], arr[idx as usize]];
				diff = (sum+arr[idx as usize]).abs();
			}
		}
	}
	for i in 0..3 {
		print!("{} ", res[i]);
	}
}
