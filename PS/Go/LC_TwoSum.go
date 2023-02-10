/*
LeetCode - TwoSum

배열에서 두 수를 잡아 target으로 만들려고 합니다.
어떤 인덱스를 골라야하는지 두 수를 return해주세요.
정답이 반드시 있음이 보장됩니다.

화려하진 못해도, LeetCode 처음 가입해본김에 풀어봤습니다.
dart도 있는게 맘에 들어서 앞으로 종종 쓸듯합니다.

문제는 귀찮아서 그냥 이렇게 했습니다.
난 또 원소 100만개 이럴줄알고 이분탐색 쓸 준비하고 있었는데,
10의 4승.. 10000개더라고요.

그냥 가입축포였습니다. 시스템은 맘에 정말 드네요.
*/
func twoSum(nums []int, target int) []int {
	for i := 0; i < len(nums); i++ {
		for j := i+1; j < len(nums); j++ {
			if nums[i] + nums[j] == target {
				return []int{i, j}
			}
		}
	}
    return []int{}
}