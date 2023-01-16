/*
17349번 1루수가 누구야

범인을 찾기 위한 9명의 추론이 있다.
이중 한 명은 거짓말을 하고 있으며, 범인은 정확히 한명이다.
추론은 두 종류로 나뉜다.
1 A : A가 범인이다.
0 A : A는 범인이 아니다.
한 명만 거짓말을 하고 있다고 할 때, 9명중 범인을 찾아보자.
만약 찾을 수 없다면 -1을 출력하자.

구현문제 오랜만이네요.
많은 조건 분기가 특히 오랜만이고요.
솔직히 많은 조건인진 잘 모르겠지만..
저만 쉽게 느낀듯 합니다.

원리는 간단합니다.
그냥 한명씩 돌아가면서 범인으로 지명해두고, 추론에 대입하면 돼요.
그냥 뭐.. 코드 한번 보면 이게 왜 골드3인가 하는 생각이 들게됩니다.
*/
var arr = Array<Array<Int>>()
for _ in 0..<9 {
    let I = readLine()!.split(separator:" ").map {Int($0)!}
    let x = I[0], y = I[1]
    arr.append([x, y])
}

var answer = 0
for maybe in 1...9 {
    var lie = false
    for idx in 0..<9 {
        let kind = arr[idx][0]
        let who = arr[idx][1]
        if kind == 1 && who != maybe {
            if lie { break }
            else { lie = true }
        }
        if kind == 0 && who == maybe {
            if lie { break }
            else { lie = true }
        }
        if idx == 8 && lie {
            if answer == 0 { answer = maybe }
            else { answer = -1 }
        }
    }
}
print(answer <= 0 ? -1 : answer)

/*
이하는 루비 숏코딩입니다. (194B)
arr=9.times.map{gets.split.map &:to_i}
t=0
(1..9).each{|k|
l=0
f=0
arr.each{|i,j|if(j!=k&&i==1)||(j==k&&i==0)then l==1 ?(f=1;break): l=1end}
if f==0&&l==1then t==0? t=k : t=-1end}
puts t<=0?-1:t
*/