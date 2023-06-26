use std::fmt::Write;
use std::io;
/*
1222번 홍준 프로그래밍 대회

F(arr, k)는 arr에 있는 수중 k 배수의 개수를 센 뒤 k와 곱하여 반환하는 함수이다.
수열이 주어질 때, F(arr, k)의 최댓값을 구해보자.

진짜세상이막너무억울해나는분명시간복잡도계산해서ONsqrtN은안될거라생각했는데막이거그냥NsqrtN이정해고그러면좀그래진짜루

어디보자.. 배열 길이가.. 20만..
배열 최댓값은.. 200만..
어디보자.. sqrt(200만)이 약 1414..
그러면.. 어.. 20만에 1000 대충 곱하면.. 2억..
여기에 1.4 더 곱할생각 하니까.. 시간제한이 2초고.. 그러면 시간초과네?

는무슨 NsqrtN으로 뚫리는 문제였고~
진짜 저번에도 한번 당했는데 또 당하니까 어지럽다
난 막 숫자들 소인수분해 한다음 그거로 약수구하고 어쩌구저쩌구 하고있었는데,
시간초과나서 이거 뭐야 하면서 답지보니까 아..

하.. 진짜..
앞으로 한참 넘어가는거 아니고 아슬아슬하게 안되면 Rust로 그냥 박고 시작해본다..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let N = input();
	let mut arr = [0;200000];
	let mut max = 0;
	for i in 0..N {
		arr[i as usize] = input();
		max = std::cmp::max(max, arr[i as usize]);
	}
	let mut cnt = [i64::from(0); 2000001];
	let mut ret = 0;
	for i in arr {
		cnt[i as usize] += 1;
	}
	for i in 1..=max {
		let mut j = i;
		let mut sum = 0;
		while j <= max {
			sum += cnt[j as usize];
			j += i;
		}
		if sum > 1 {
			ret = std::cmp::max(ret, sum*i);
		}
	}
	println!("{}", ret);
}
