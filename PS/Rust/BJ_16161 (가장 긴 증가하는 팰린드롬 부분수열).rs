use std::fmt::Write;
use std::io;
/*
16161번 가장 긴 증가하는 팰린드론 부분수열

가장 긴 연속하는 증가하는 팰린드롬 부분수열의 길이를 구해보자.
팰린드롬 부분수열인데, 중앙으로 갈수록 크기가 커지는 부분 수열의 길이를 구하라는 뜻이다.
예시로, [1, 2, 3, 2, 1], [1, 2, 2, 1]이 있다.

앞으로 문제를 제대로 읽겠습니다
앞으로 문제를 진짜로 제대로 읽겠습니다
앞으로 문제를 정말로 제대로 읽겠습니다
앞으로 문제를 완전히 제대로 읽겠습니다
앞으로 문제를 한 글자도 빠짐없이 읽겠습니다
앞으로 문제를 완벽하게 읽겠습니다
앞으로 문제를 끝까지 읽겠습니다
앞으로 문제를 출력까지 읽겠습니다
앞으로 문제를 앞부분도 읽겠습니다
앞으로 문제를 잘 읽겠습니다

[연속하는]을 못보고 검색까지 때려박았는데요 아니 아니 진짜 아니
사람들이 막 갑자기 [left:right] = [left:right+right-left]이런걸 쓰는거에요
근데 진짜 아니 저게 왜 나오나 뒤에서 원소가 팰린드롬 만들려하면 어쩌려고 해서 문제를 다시 읽어봤는데요 진짜루
막 갑자기 문제에 없었던 [연속하는] 이라는 글자가 생겨있는거에요 진짜 나 억울해서 못살아
근데 더 억울한건 출력부분 보니까 똑같이 [연속하는]이라는 글자가 있어요 아니 님들아 진짜로 아 아니...

연! 속하는이! 있는걸! 알았다면! 나도! 금방! 풀었지!
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let N = input() as usize;
	let mut arr = [0;100000];
	for i in 0..N {
		arr[i] = input();
	}
	let mut ret = 1;
	let mut left = 0;
	let mut right = 0;
	while left < N {
		if right+1 < N && arr[right] < arr[right+1] {
			right += 1;
		} else {
			let len = right-left;
			let mut two_mid = right;
			if right+1 < N && arr[right] == arr[right+1] {
				two_mid += 1;
			}
			for i in 0..=len {
				if two_mid+i < N && arr[right-i] == arr[two_mid+i] {
					ret = std::cmp::max(ret, (two_mid+i)-(right-i)+1);
				} else { break; }
			}
			left = right+1;
			right = right+1;
		}
	}
	println!("{}", ret);
}
