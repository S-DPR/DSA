use std::fmt::Write;
use std::io;
/*
1736번 쓰레기 치우기

맵에 1로 표시된 쓰레기가 있다.
좌측 상단에서 출발해, 아래와 우측으로만 이동 가능한 로봇을 이용해 쓰레기를 수거하려 한다.
적어도 몇 개의 로봇을 보내야할까?

히히 확실히 세팅해두니까 훨씬 편하다
이제 폴더 일일히 들어가는 노가다 안해도 대..

그리디인걸 봐버린 문제입니다. 몇달 전에 봤는데 안잊혀져서 그냥 풉니다..
그냥 뭐.. 솔직히 진짜 이건 개인적인 생각인데..
.. 그냥 대충 하다보면 '이거 그냥 어.. 그리디하게 해야겠는데?'라는 생각을 하게될거같긴한데..
솔직히 맵형 그리디는 처음봤지만.. 이건 너무 그..

그냥 (0, 0)에서 출발해 오른쪽에 쓰레기가 있나 확인합니다. 있으면 보냅니다.
그리고 거기서도 오른쪽을 우선순위로, 없으면 아래로 한칸씩 내립니다.
그런데 잘 생각해보면 굳이 우선순위는 없어도 되고, 우측이든 아래쪽이든 아무거나 먼저 하면 되는건 쉽게 알 수 있겠죠.

처음 호출을 isRoot 매개변수로 판단하는데, 얘는 한칸씩 내려보면서,
우측에 쓰레기가 있으면 결과에 1을 더하고 보내고, 없으면 그냥 내려옵니다.
물론 보내진 로봇은 isRoot이 false여서 답에는 영향 못주고 쓰레기수거만 열심히 하겠죠.
그냥 쉬운 그리디였습니다.. 내가 그리디인거를 봐서 그런가..
*/
fn go(x: usize, y: usize, isRoot: bool, R: usize, C: usize, M: &mut [[bool;100];100]) -> i64 {
    let mut ret = 0;
    let mut right = false;
    M[y][x] = false;
    for i in x..C {
        if !M[y][i] { continue }
        if isRoot { ret += 1; }
        go(i, y, false, R, C, M);
        right = true;
    }
    if isRoot {
        for i in y..R {
            let mut needToGo = false;
            for j in x..C {
                if !M[i][j] { continue; }
                needToGo = true;
            }
            if needToGo {
                ret += 1;
                go(x, i, false, R, C, M);
            }
        }
    } else if !right && y != R-1 {
        go(x, y+1, isRoot, R, C, M);
    }
    return ret;
}

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let R = input() as usize;
    let C = input() as usize;
    let mut M = [[false; 100]; 100];
    for i in 0..R {
        for j in 0..C {
            if input() == 0 { continue; }
            M[i][j] = true;
        }
    }
    println!("{}", go(0, 0, true, R, C, &mut M));
}
