use std::fmt::Write;
use std::io;
/*
26056번 수열의 합 2

양의 정수 N에 대하여, F(N)을 sigma(n%i = 0) { (-1)^i } 로 정의하자.
두 수 S, E가 주어진다. F(S)+F(S+1)+...+F(E-1)+F(E)를 구해보자.

즈어기 골드5에서 대충 날먹하던 그 문제 확장판.
E가 이제 10의 14승까지 들어갈 수 있습니다. 무려 수가 제곱이됐어!
어쨌든 보고 이거 쉽겠다 ㅋㅋ 하고 때려박았다가 시간 좀 걸렸네요.

그 문제는 NlogN으로 됐지만.. 이건 NlogN은 커녕 N도 힘들어보이죠.
대충 약수에 대해 뭐가 있더라.. 생각해보니 나온게 sqrtN짜리 조화수열.
솔직히 조화수열인건 되게 금방 알았는데 조화수열 이제 두번째로 써보는거라 고생 좀 했습니다.

j+1-i가 짝수면 해당 약수는 다 사라지고,
홀수면 이제 i의 홀짝여부에 따라 (E/j)에 - 혹은 +가 곱해져 변수값에 더하게 됩니다.
그거 두번하면 끝나요. 솔직히 왜 left-right해야 답이 나오는진 모르겠는데.
참.. 수학의 세계는 쉽지않구나..

아 그래. 이거 이 풀이보다 두배 넘게 빠른게 있던데 그거 좀 충격과공포던데..
최적화 안한 조화수열 풀이는 TLE나게 하려했다는데,
주변에서 '상수시간갖고 TLE는 좀..'해서 그냥 넉넉하게 시간 줬다고 하네요.
두렵다..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let S = input()-1;
	let E = input();
	let mut left: i64 = 0;
	let mut right: i64 = 0;
	let mut i: i64;
	let mut j: i64;
	
	i = 1;
	j = 0;
	while i <= S {
		j = S / (S / i);
		let prv = i;
		i = j + 1;
		if (i-prv)&1 == 1 {
			left += if i&1 == 1 { -(S/j) } else { S/j };
		};
	}
	
	i = 1;
	j = 0;
	while i <= E {
		j = E / (E / i);
		let prv = i;
		i = j + 1;
		if (i-prv)&1 == 1 {
			right += if i&1 == 1 { -(E/j) } else { E/j };
		};
	}
	println!("{}", left-right);
}
