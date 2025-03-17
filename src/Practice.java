import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Practice {

  /**
   * Returns the count of vertices with odd values that can be reached from the given starting vertex.
   * The starting vertex is included in the count if its value is odd.
   * If the starting vertex is null, returns 0.
   *
   * Example:
   * Consider a graph where:
   *   5 --> 4
   *   |     |
   *   v     v
   *   8 --> 7 < -- 1
   *   |
   *   v
   *   9
   * 
   * Starting from 5, the odd nodes that can be reached are 5, 7, and 9.
   * Thus, given 5, the number of reachable odd nodes is 3.
   * @param starting the starting vertex (may be null)
   * @return the number of vertices with odd values reachable from the starting vertex
   */
  public static int oddVertices(Vertex<Integer> starting) {
    if (starting == null) return 0;

    Set<Vertex<Integer>> visited = new HashSet<>();
    Stack<Vertex<Integer>> stack = new Stack<>();
    stack.push(starting);
    int count = 0;

    while (!stack.isEmpty()) {
      Vertex<Integer> current = stack.pop();

      if (!visited.contains(current)) {
        visited.add(current);

        if (current.data % 2 != 0) {
          count++;
        }

        for (Vertex<Integer> neighbor : current.neighbors) {
          stack.push(neighbor);
        }
      }
    }
    
    return count;
  }

  /**
   * Returns a *sorted* list of all values reachable from the starting vertex (including the starting vertex itself).
   * If duplicate vertex data exists, duplicates should appear in the output.
   * If the starting vertex is null, returns an empty list.
   * They should be sorted in ascending numerical order.
   *
   * Example:
   * Consider a graph where:
   *   5 --> 8
   *   |     |
   *   v     v
   *   8 --> 2 <-- 4
   * When starting from the vertex with value 5, the output should be:
   *   [2, 5, 8, 8]
   *
   * @param starting the starting vertex (may be null)
   * @return a sorted list of all reachable vertex values by 
   */
  public static List<Integer> sortedReachable(Vertex<Integer> starting) {
    // Unimplemented: perform a depth-first search and sort the collected values.
    if (starting == null) return new ArrayList<>();

    Set<Vertex<Integer>> visited = new HashSet<>();
    Stack<Vertex<Integer>> stack = new Stack<>();
    List<Integer> values = new ArrayList<>();
    
    stack.push(starting);

    while (!stack.isEmpty()) {
      Vertex<Integer> current = stack.pop();

      if (!visited.contains(current)) {
        visited.add(current);
        values.add(current.data);

        for (Vertex<Integer> neighbor : current.neighbors) {
          stack.push(neighbor);
        }
      }
    }
    
    Collections.sort(values);
    return values;
  }

  /**
   * Returns a sorted list of all values reachable from the given starting vertex in the provided graph.
   * The graph is represented as a map where each key is a vertex and its corresponding value is a set of neighbors.
   * It is assumed that there are no duplicate vertices.
   * If the starting vertex is not present as a key in the map, returns an empty list.
   *
   * @param graph a map representing the graph
   * @param starting the starting vertex value
   * @return a sorted list of all reachable vertex values
   */
  public static List<Integer> sortedReachable(Map<Integer, Set<Integer>> graph, int starting) {
    if (!graph.containsKey(starting)) return new ArrayList<>();

    Set<Integer> visited = new HashSet<>();
    Stack<Integer> stack = new Stack<>();
    List<Integer> values = new ArrayList<>();
    
    stack.push(starting);

    while (!stack.isEmpty()) {
      int current = stack.pop();
  
      if (!visited.contains(current)) {
        visited.add(current);
        values.add(current);
  
        if (graph.containsKey(current)) {
          for (int neighbor : graph.get(current)) {
            stack.push(neighbor);
          }
        }
      }
    }
    
    Collections.sort(values);
    return values;
  }

  /**
   * Returns true if and only if it is possible both to reach v2 from v1 and to reach v1 from v2.
   * A vertex is always considered reachable from itself.
   * If either v1 or v2 is null or if one cannot reach the other, returns false.
   *
   * Example:
   * If v1 and v2 are connected in a cycle, the method should return true.
   * If v1 equals v2, the method should also return true.
   *
   * @param <T> the type of data stored in the vertex
   * @param v1 the starting vertex
   * @param v2 the target vertex
   * @return true if there is a two-way connection between v1 and v2, false otherwise
   */
  public static <T> boolean twoWay(Vertex<T> v1, Vertex<T> v2) {
    if (v1 == null || v2 == null) return false;
    if (v1 == v2) return true;

    return reach(v1, v2) && reach(v2, v1);
  }

  private static <T> boolean reach(Vertex<T> start, Vertex<T> target) {
    if (start == null || target == null) return false;
    
    Set<Vertex<T>> visited = new HashSet<>();
    Stack<Vertex<T>> stack = new Stack<>();
    stack.push(start);

    while (!stack.isEmpty()) {
      Vertex<T> current = stack.pop();

      if (current == target) return true;

      if (!visited.contains(current)) {
        visited.add(current);

        for (Vertex<T> neighbor : current.neighbors) {
          stack.push(neighbor);     
        }
      }
    }
    
    return false;
  }

  /**
   * Returns whether there exists a path from the starting to ending vertex that includes only positive values.
   * 
   * The graph is represented as a map where each key is a vertex and each value is a set of directly reachable neighbor vertices. A vertex is always considered reachable from itself.
   * If the starting or ending vertex is not positive or is not present in the keys of the map, or if no valid path exists,
   * returns false.
   *
   * @param graph a map representing the graph
   * @param starting the starting vertex value
   * @param ending the ending vertex value
   * @return whether there exists a valid positive path from starting to ending
   */
  public static boolean positivePathExists(Map<Integer, Set<Integer>> graph, int starting, int ending) {
    if (starting <= 0 || ending <= 0 || !graph.containsKey(starting)) return false;
    if (starting == ending) return true;

    Set<Integer> visited = new HashSet<>();
    Stack<Integer> stack = new Stack<>();
    stack.push(starting);

    while (!stack.isEmpty()) {
      int current = stack.pop();

      if (!visited.contains(current)) {
        visited.add(current);

        if (current == ending) return true;

        if (graph.containsKey(current)) {
          for (int neighbor : graph.get(current)) {
            if (neighbor > 0 && !visited.contains(neighbor)) {
              stack.push(neighbor);
            }
          }
        }
      }
    }
    
    return false;
  }

  /**
   * Returns true if a professional has anyone in their extended network (reachable through any number of links)
   * that works for the given company. The search includes the professional themself.
   * If the professional is null, returns false.
   *
   * @param person the professional to start the search from (may be null)
   * @param companyName the name of the company to check for employment
   * @return true if a person in the extended network works at the specified company, false otherwise
   */
  public static boolean hasExtendedConnectionAtCompany(Professional person, String companyName) {
    if (person == null || companyName == null) return false;

    Set<Professional> visited = new HashSet<>();
    Stack<Professional> stack = new Stack<>();
    stack.push(person);

    while (!stack.isEmpty()) {
      Professional current = stack.pop();

      if (current.getCompany().equals(companyName)) return true;

      if (!visited.contains(current)) {
        visited.add(current);

        for (Professional connection : current.getConnections()) {
          stack.push(connection);
        }
      }
    }
    
    return false;
  }
}
