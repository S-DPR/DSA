use std::fmt::Write;
use std::io;
/*
11693번 n^m 약수의 합

n^m의 약수의 합을 1_000_000_007로 나눈 값을 구해주세요.

새로운 것을 많이 깨달은 문제.
수학의 세계는 아직도 넓구나..

골드 중하위까지는 그냥 에라토스테네스의 체 원툴로 헤헤헿 거릴 수 있었지만,
이제 막 이상한게 엮여나오네요.

첫째, 약수의 합은 어떻게 구하는가?
먼저 약수의 개수를 구할 때
N = P1^a * P2^b * ... * Pn^z
라고 하면,
N의 약수의 개수는 (a+1)*(b+1)*...*(z+1).
이거랑 연관이 깊습니다.
간단한 예시를 위해 N을 12으로 두면,
   1   2   2^2
1 1*1 1*2 1*2^2
3 3*1 3*2 3*2^2
즉 저 최상단과 최좌단을 제외한 것들의 합을 구해야한다는건데.
다합하면 (2^0 + 2^1 + 2^2) * (3^0 + 3^1).
이거를 분배법칙에 따라 보면 됩니다.
정확한 증명은 아니지만, 약수의 합은 아래와 같이 계산됩니다.
(1+P1+P1^2+...+P1^a)*(1+P2+P2^2+...+P2^b)...
이는 공비가 각각 P이고 첫 항이 1인 공비수열이므로,
(P^(a+1) - 1) / (P-1)
이 됩니다. P는 소수이므로 P는 2 이상의 수이니 분모가 0이 될 일은 없습니다.

공비수열 좋아, 아주 좋아.
이제 우리는 저 합만 구해서 곱하면 된다. 그죠?
그런데 문젠, (A%M * B%M)%M = (AB)%M 은 만족을 하는데,
(A/B)%M이 (A%M) / (B%M)은 아니거든요.

여기서 모듈러 곱셈 역원이 사용됩니다.
어쨌든 우리가 구하려는건 (A/B)%M.
B에 역원을 취합시다. 어쨌든 마지막엔 M으로 나누기때문에 곱셈으로만 바꿔주면 됩니다.

M은 10의 9승 + 7인 소수이므로, 페르마의 소정리에 의해
B^(M-1) = 1 mod M 이 성립합니다.
우리는 이를 B * B^(M-2) = 1 mod M 으로 바꿀 수 있으며,
B에 B^(M-2)를 곱하면 M으로 나눌 때 1이 남는, 역원이 됨을 알 수 있습니다.

즉, (A*B^(M-2)) mod M은 (A/B) mod M과 같으며,
이는 골드 1이 기본난이도로 책정된, 모듈러역원이었습니다.

아, 거듭제곱 O(N)으로하면 터집니다. O(logN)으로 줄여야합니다.
*/
fn pow(x: i64, n: i64, m: i64) -> i64 {
	if n == 1 { return x%m; }
	let ret = pow(x, n/2, m)%m;
	if n%2 == 0 { return (ret*ret)%m; }
	return ((ret*ret)%m*x)%m;
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	let MOD = 1_000_000_007;
	
	let (N, K) = (input(), input());
	let mut cur = N;
	let mut ret = 1;
	for i in 2..N {
		if i*i > N { break; }
		if N%i != 0 { continue; }
		let mut cnt = 0;
		while cur%i == 0 {
			cur /= i;
			cnt += 1;
		}
		if cnt > 0 {
			ret *= ((pow(i, cnt*K+1, MOD)-1)*pow(i-1, MOD-2, MOD))%MOD;
			ret %= MOD;
		}
	}
	if cur != 1 {
		ret *= ((pow(cur, K+1, MOD)-1)*pow(cur-1, MOD-2, MOD))%MOD;
		ret %= MOD;
	}
	println!("{ret}");
}
