use std::fmt::Write;
use std::io;
/*
13146번 같은 수로 만들기 2

배열이 주어진다. 이 배열에 add연산을 수행할 수 있다.
이 연산을 수행하면 그 인덱스와 인접한 그 인덱스와 같은 수들이 1씩 증가한다.
add에는 매개변수로 인덱스를 주는데, add(1)을 하면 1번 인덱스에 add를 적용한다는 뜻이다.
배열을 모두 어떤 수로 맞추려고 한다. 이를 위한 연산의 최솟값을 구해보자.

이거 될거같은데.. 될거같은데.. 하다가 진짜 된 문제
막 HashSet도 쓰고 HashMap도 쓰고 난리치다가 문득 떠오른 생각대로 해봤더니 AC를 맞았습니다.

스택은 사용하지 않았으나 실질적으로 스택과 같은 방식으로 작동합니다.
자기보다 작은 수는 쭉 지나가다가 큰 수가 나오면 그 수와 현재 인덱스의 차를 횟수에 더합니다.
이 행위를 쭉 합니다. 그러면 AC가 나옵니다.
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	
	let N = input();
	let mut arr = Vec::new();
	let mut max = i64::from(0);
	let mut cnt = i64::from(0);
	let mut cur = -1;
	for i in 0..N {
		let tmp = input();
		arr.push(tmp);
		max = std::cmp::max(max, tmp);
	}
	arr.push(max);
	for i in 0..N {
		let cur = arr[i as usize];
		let next = arr[(i+1) as usize];
		if cur >= next { continue; }
		cnt += next - cur;
	}
	println!("{cnt}");
}
