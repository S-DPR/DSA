=begin
27440번 1로 만들기 3

정수 X에 다음 연산을 사용할 수 있다.
1. 3으로 나누어 떨어진다면 3으로 나눈다.
2. 2로 나누어 떨어진다면 2로 나눈다.
3. 1을 뺀다.
정수 N이 주어졌을 때, 적절히 사용하여 1로 만들어보자.
(1 <= N <= 10^18)

N의 크기 보고 ?? 했던 문제
결국 태그 봤더니 BFS..
아.. 원본이 BFS로 풀리는건 알고있긴 했는데 BFS는 이렇게 큰 수도 처리가 되는구나..

진짜 신기하네요. O(N+M) 생각해서 시간초과나겠거니 했는데 10의 18승 넣어도 수 빠르게 줄더니 처리 끝나더라고요.
=end
n = gets.to_i
visit = {}
queue = [[n, 0]]
until queue.empty?
  cur, time = queue.shift
  if cur == 1
    puts time
    break
  end
  next if visit[cur] && time < visit[cur]
  if (cur%3).zero? && (!visit[cur/3] || time+1 < visit[cur/3])
    queue << [cur/3, time+1]
    visit[cur/3] = time+1
  end
  if (cur%2).zero? && (!visit[cur/2] || time+1 < visit[cur/2])
    queue << [cur/2, time+1]
    visit[cur/2] = time+1
  end
  if !visit[cur-1] || time+1 < visit[cur-1]
    queue << [cur-1, time+1]
    visit[cur-1] = time+1
  end
end