/**
 * // This is the GridMaster's API interface.
 * // You should not implement it, or speculate about its implementation
 * class GridMaster {
 *   boolean canMove(char direction);
 *   void move(char direction);
 *   boolean isTarget();
 * };
 */

enum Grid { kUnvisited, kStart, kTarget, kBlocked, kEmpty }

class Solution {
  public int findShortestPath(GridMaster master) {
    final int m = 500;
    final int startX = m;
    final int startY = m;
    Grid[][] grid = new Grid[m * 2][m * 2];
    Arrays.stream(grid).forEach(A -> Arrays.fill(A, Grid.kUnvisited));

    // Build the grid information by DFS.
    dfs(master, grid, startX, startY);

    int ans = 0;
    Queue<int[]> q = new ArrayDeque<>();
    q.offer(new int[] {startX, startY});
    grid[startX][startY] = Grid.kBlocked;

    // Find the steps by BFS.
    while (!q.isEmpty()) {
      ++ans;
      for (int sz = q.size(); sz > 0; --sz) {
        final int i = q.peek()[0];
        final int j = q.poll()[1];
        for (int[] dir : dirs) {
          final int x = i + dir[0];
          final int y = j + dir[1];
          if (grid[x][y] == Grid.kTarget)
            return ans;
          if (grid[x][y] == Grid.kBlocked)
            continue;
          grid[x][y] = Grid.kBlocked;
          q.offer(new int[] {x, y});
        }
      }
    }

    return -1;
  }

  private static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
  private static final char[] charTable = {'R', 'D', 'L', 'U'};

  private void dfs(GridMaster master, Grid[][] grid, int i, int j) {
    if (grid[i][j] != Grid.kUnvisited)
      return;
    if (master.isTarget())
      grid[i][j] = Grid.kTarget;
    else
      grid[i][j] = Grid.kEmpty;

    for (int k = 0; k < 4; ++k) {
      final int x = i + dirs[k];
      final int y = j + dirs[k + 1];
      final char d = charTable[k];
      final char undoD = charTable[(k + 2) % 4];
      if (master.canMove(d)) {
        master.move(d);
        dfs(master, grid, x, y);
        master.move(undoD);
      } else {
        grid[x][y] = Grid.kBlocked;
      }
    }
  }
}
