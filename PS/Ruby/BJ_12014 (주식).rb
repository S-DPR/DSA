spl = ->() { gets.split.map &:to_i }
=begin
12014번 주식

수열이 주어진다. 길이가 K 이상인 LIS가 있을까?

그냥 문제 쭉 요약하면 저거인 문제입니다.
연휴시작인데 갑자기 막 풀기 싫어서 그냥 골드2 하나 납치했습니다..
간단하게 LIS 구현하고 그 길이랑 K랑 비교하고 답을 출력하면 됩니다.
=end
lowerBound = ->(arr, x) {
    left, right = 0, arr.length
    while left < right
        mid = (left + right) >> 1
        arr[mid] >= x ? right = mid : left = mid + 1
    end
    right
}

r = ""
T = gets.to_i
(1..T).each do |tc|
    n, k = spl.()
    arr = spl.()
    lis = [0]
    arr.each do |i|
        idx = lowerBound.(lis, i)
        lis[idx] = lis[idx] ? [i, lis[idx]].min : i
    end
    r += "Case ##{tc}\n"
    r += lis.length > k ? "1\n" : "0\n"
end
print r
