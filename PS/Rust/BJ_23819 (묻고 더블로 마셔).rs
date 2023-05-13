use std::fmt::Write;
use std::io;
/*
23819번 묻고 더블로 마셔

수 N과 K가 주어진다.
이후 K개의 수가 주어진다. 이 수들은 어떤 수열 F에 대해 F(1), F(2), ..., F(K)의 값이다.
그리고 마지막 줄에 P가 주어진다.
어떤 수열 F의 점화식이 F(T) = F(T-K)+F(T-K+1)+...+F(T-1) (T > K) 일 때,
수열 N번째 수의 P로 나눈 나머지를 구해보자.
(K < N <= 10^9, 1 <= K <= 100)

뒤적뒤적 하다가 골랐는데, "뭐야 그냥 하면 되는거아니야?"했다가 N 범위 보고 이게뭐시여 한 문제.
그냥 심심해서 대충 a b c로 두고 하면 규칙 나오겠지 했는데,
10분동안 a b c 해보고 a b c d 해보니까 나오지 않는 규칙..

그러다가 갑자기 생각난 피보나치 수
"않이 근데 이거 피보나치랑 비슷하지 않나??" 하고 태그를 보니,
분할정복을 이용한 거듭제곱?

피보나치 수 3번 문제 가서 태그 보니 똑같은게 있네?
아.. 그렇구나.. 그런거였구나..
하고 열심히 "피보나치 logN"으로 검색해서 공부했습니다.
*/
fn pow(mat: &Vec::<Vec::<i64>>, n: i64, MOD: i64) -> Vec::<Vec::<i64>> {
	if n == 1 {
		return mat.to_vec();
	}
	if n&1 == 1 {
		let mat_c = pow(&mat, n-1, MOD);
		return mul(&mat_c, &mat, MOD);
	}
	let mat = mul(&mat, &mat, MOD);
	return pow(&mat, n/2, MOD);
}

fn mul(mat1: &Vec::<Vec::<i64>>, mat2: &Vec::<Vec::<i64>>, MOD: i64) -> Vec::<Vec::<i64>> {
	let n = mat1.len();
	let m = mat2.len();
	let k = mat2[0].len();
	let mut ret = Vec::<Vec::<i64>>::new();
	for i in 0..n {
		ret.push(vec![0; k]);
	}
	for u in 0..n {
		for v in 0..k {
			for w in 0..m {
				ret[u][v] += ((mat1[u][w]%MOD)*(mat2[w][v]%MOD))%MOD;
				ret[u][v] %= MOD;
			}
		}
	}
	return ret;
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let N = input();
	let K = input();
	let mut st = Vec::<Vec<i64>>::new();
	for _ in 0..K {
		st.push(vec![input();1]);
	}
	st.reverse();
	let M = input();
	let mut mat = Vec::<Vec<i64>>::new();
	mat.push(vec![1;K as usize]);
	for i in 0..K-1 {
		let mut item = vec![0;K as usize];
		item[i as usize] = 1;
		mat.push(item);
	}
	println!("{}", mul(&pow(&mat, N-K, M), &st, M)[0][0]%M);
}