spl = ->() { gets.split.map &:to_i }
=begin
20191번 줄임말

문자열 S와 T가 있다. T를 연속해서 N번 이어붙이면 S의 부분문자열이 될 수도 있다.
만약 S의 부분문자열이 된다면 최소의 N을, 아니라면 -1을 출력하자.

아이디어는 꽤 금방 생각해냈는데 구현에 살짝 애를 먹은 케이스.
너무 당연한걸 생각못해 세번이나 틀렸습니다.

T에서 나오는 문자의 인덱스를 각각의 배열에 넣습니다.
a는 0번, b는 1번, ..., z는 25번째 배열에.
그리고 idx변수를 만듭니다. 이 변수는 T에서 현재 인덱스 번호입니다.
이후 S를 순회하며 각 문자에 맞는 번호에 대해 이분탐색을 시행합니다.
이분탐색에 사용하는 x값은 idx입니다.

이 때 이분탐색을 실패했다면, -1을 넣고 다시 이분탐색합니다. 그리고 결과값을 1 증가합니다.
최종적으로 idx에는 다시 C[ord][nxt]를 넣습니다. ord는 문자의 번호, nxt는 이분탐색 결과인덱스입니다.
=end
lowerBound = ->(arr, x) {
    left, right = 0, arr.length
    while left < right
        mid = (left + right) >> 1
        arr[mid] > x ? right = mid : left = mid+1
    end
    right
}

S = gets.strip
T = gets.strip
C = 26.times.map do [] end
T.length.times do |i|
    C[T[i].ord-'a'.ord] << i
end
ret = 1
idx = -1
die = false
S.each_char do |i|
    ord = i.ord - 'a'.ord
    die = die || C[ord].length.zero?
    break if die
    nxt = lowerBound.(C[ord], idx)
    if nxt == C[ord].length
        nxt = lowerBound.(C[ord], -1)
        ret += 1
    end
    idx = C[ord][nxt]
end
puts die ? -1 : ret
