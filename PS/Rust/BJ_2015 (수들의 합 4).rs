use std::fmt::Write;
use std::io;
use std::collections::HashMap;
/*
2015번 수들의 합 4

수열의 길이 N, 정수 K가 주어지고, 수열 arr이 주어진다.
1 <= i <= j <= N에 대하여 arr[i]부터 arr[j]의 합이라고 하자.
그 합이 K가 되는 두 쌍 (i, j)의 개수를 구하시오.

투포인터인줄알았는데 아니네?
어.. 자세히보니까 누적합 써야겠는데?
근데 어떻게쓰지?
(2시간 후)
걍 답지 봐야지..
아..?

.. 라는 과정을 거쳐 푼 문제.
정확히는 처음엔 정답대로 갔으나, 구현미스를 알고리즘이 틀린거로 착각해서 못풀었습니다.
아..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	
	let N = input();
	let K = input();
	let mut arr = Vec::new();
	for i in 0..N {
		arr.push(input());
	}
	let mut prefix = i64::from(0);
	let mut past = HashMap::new();
	let mut cnt = i64::from(0);
	past.insert(0, 1);
	for i in 0..N {
		prefix += arr[i as usize];
		{ cnt += past.get(&(prefix-K)).unwrap_or(&0); }
		{ *past.entry(prefix).or_insert(0) += 1; }
	}
	println!("{}", cnt);
}
// Ruby 숏코딩입니다. (104B)
// _,m=gets.split;m=m.to_i;r,p=0,0;d=Hash.new(0);d[0]=1;gets.split.each{|i|p+=i.to_i;r+=d[p-m];d[p]+=1};p r