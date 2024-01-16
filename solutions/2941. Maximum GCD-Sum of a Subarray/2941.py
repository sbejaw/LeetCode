class Solution:
  def maxGcdSum(self, nums: List[int], k: int) -> int:
    ans = 0
    # [(startIndex, gcd of subarray starting at startIndex)]
    startIndexAndGcds = []
    prefix = [0] + list(itertools.accumulate(nums))

    for i, num in enumerate(nums):
      nextStartIndexAndGcds = []
      for startIndex, gcd in startIndexAndGcds:
        nextGcd = math.gcd(gcd, nums[i])
        if not nextStartIndexAndGcds or \
                nextStartIndexAndGcds[-1][1] != nextGcd:  # Skip duplicates.
          nextStartIndexAndGcds.append((startIndex, nextGcd))
      startIndexAndGcds = nextStartIndexAndGcds
      startIndexAndGcds.append((i, nums[i]))
      for startIndex, gcd in startIndexAndGcds:
        if i - startIndex + 1 >= k:
          ans = max(ans, (prefix[i + 1] - prefix[startIndex]) * gcd)

    return ans
