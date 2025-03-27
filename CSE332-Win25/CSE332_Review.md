# Hash Table Notes

## Overview

- **O(1)** expected runtime for insert, find, delete.
- **Load factor (λ) = elements / table size.**
- Higher λ → more collisions → slower performance.
- not stored in order!

---

## Good Hash Function

- **Fast to compute.**
- **Distributes keys evenly** to minimize collisions.
- **Avoids clustering** by spreading values uniformly.
- **Uses all parts of the key** for better uniqueness.
- Return integer
- Same input result in same hashcode

---

## Collision Resolution Methods

### 1. Separate Chaining

- **Collisions:** Linked list (or other structure) at each bucket.
- **Find:** Worst case **O(n)** (all keys in one bucket).
- **Unsuccessful search:** ~λ comparisons.
- **Successful search:** ~λ/2 comparisons.
- **Table size:** ≈ number of elements for efficiency.
- Pros: No need to worry about load factor
- Cons: Take more space to store data

### 2. Open Addressing (Handles collisions within the table)

- **λ < 1** is required for efficient performance.
- **Less memory allocation** compared to separate chaining.

#### **Linear Probing**

- **Collisions:** If a bucket is full, check the next one (x+1, x+2, ...).
- **Find:** Scan until key or empty slot.
- **Worst case:** **O(n)** (due to primary clustering).
- **Problem:** Primary clustering leads to long sequences of occupied slots.

#### **Quadratic Probing**

- **Probing Pattern:** x + 1², x + 2², x + 3², ...
- **Reduces primary clustering** but still has **secondary clustering**.
- Table size is not prime, λ < 0.5 More than half full, can't guarantee a location to inseert
- **Secondary clustering:** Keys with the same initial hash go to fixed locations.

#### **Double Hashing**

- **Probing:** Uses a second hash function for step size.
  - **Formula:** (h(key) + i \* g(key)) % TableSize, where g(key) ≠ 0.
  - **h(key) = key mod TableSize**
  - **g(key) = 1 + ((key / TableSize) mod (TableSize - 1))**
- **Avoids both primary & secondary clustering.**
- **Most efficient open addressing method.**
- Items that have the same value for the first hash function f, will likely have different values for the second hash function g, leading to different probing step sizes.

---

## Strengths & Weaknesses of Collision Resolution Methods

| Method                | Strengths                                         | Weaknesses                                                     |
| --------------------- | ------------------------------------------------- | -------------------------------------------------------------- |
| **Separate Chaining** | Handles high load factors well. Simple to delete. | Uses extra memory for linked lists. Slower if chains are long. |
| **Linear Probing**    | Simple, cache-friendly.                           | Suffers from **primary clustering**. Slow at high λ.           |
| **Quadratic Probing** | Reduces primary clustering.                       | Still has **secondary clustering**. Needs prime table size.    |
| **Double Hashing**    | Avoids both primary & secondary clustering.       | More computation per probe but best for performance.           |

---

## Load Factor & Impact on Performance

- **Separate Chaining**: Can handle **λ > 1**, but longer chains reduce efficiency.
- **Open Addressing**: Must keep **λ < 1** to avoid excessive probes.
  - **Linear Probing degrades fast** as λ increases.
  - **Quadratic Probing** and **Double Hashing** perform better when λ < 0.5.

---

| Open Addressing                                                                                                           | Separate Chaining                                                                    |
| ------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------ |
| Handles collisions by searching for an open slot within the table itself.                                                 | Handles collisions by adding elements to a chain at the corresponding index.         |
| Can use less memory since all elements are stored within the table itself.                                                | Uses more memory since additional data structures are needed. Worse memory locality. |
| **Linear probing** suffers from primary clustering, but is guaranteed to find an open slot.                               | **Average runtime:** O(1 + λ)                                                        |
| **Quadratic probing** suffers from secondary clustering, and is only guaranteed to find an empty slot when λ < 0.5.       | **Best-case runtime:** O(1)                                                          |
| **Double hashing** does not suffer from clustering, but requires an additional hash function (computationally expensive). | **Worst-case runtime:** O(n)                                                         |

---

## Run-time Complexity

| Operation  | Separate Chaining            | Linear Probing               | Quadratic Probing            | Double Hashing               |
| ---------- | ---------------------------- | ---------------------------- | ---------------------------- | ---------------------------- |
| **Insert** | **O(1)** avg, **O(n)** worst | **O(1)** avg, **O(n)** worst | **O(1)** avg, **O(n)** worst | **O(1)** avg, **O(n)** worst |
| **Find**   | **O(1)** avg, **O(n)** worst | **O(1)** avg, **O(n)** worst | **O(1)** avg, **O(n)** worst | **O(1)** avg, **O(n)** worst |
| **Delete** | **O(1)** avg, **O(n)** worst | Requires **lazy deletion**   | Requires **lazy deletion**   | Requires **lazy deletion**   |

---

## Deletion in Open Addressing

- **Cannot simply remove** an element because it breaks the probe sequence.
- Uses **lazy deletion**: mark the slot as "deleted" and reuse it later.
- Fully removing an element requires **rehashing**.

---

## Rehashing (Resizing the Hash Table)

- When **λ gets too high**, performance suffers.
- **Steps for rehashing:**
  1. Create a **larger table** (typically **2× size**, but prime).
  2. **Reinsert all elements** by recalculating hashcode into the new table.
  3. Discard the old table.
- **Cost:** **O(n)** but maintains future operations at **O(1)**.

---

# Sorting Algorithms

## Simple Sorts

### Insertion Sort

- Like inserting cards into a hand from 2 to A.
- At step $k$, put the $k_{th}$ element in the correct position among the first $k$ elements.
- **Best Case:** $O(n)$ (sorted data)
- **Worst Case:** $O(n^2)$ (reverse sorted)
- **Average Case:** $O(n^2)$
- **Stable:** Yes
- **In-place:** Yes
- **How it works:** Iterate through the list, shifting elements to insert the current element in the correct position.

### Selection Sort

- At step $k$, find the smallest element among the not-yet sorted elements and place it at position $k$.
- **Best Case:** $O(n^2)$
- **Worst Case:** $O(n^2)$
- **Average Case:** $O(n^2)$
- **Stable:** No (swapping can change order)
- **In-place:** Yes
- **How it works:** Repeatedly find the minimum element from the unsorted part and move it to the sorted portion.

## Heap Sort

- **BuildHeap:** $O(n)$
- **deleteMin:** $O(n \log n)$
- **Overall Time Complexity:** $O(n \log n)$
- **Stable:** No
- **In-place:** No
- **How it works:** Uses a binary heap to extract the smallest element repeatedly and rebuild the heap.

## Merge Sort

- **Not in-place**, **Stable**.
- Recursively sort the left part and the right part, dividing by half.
- Merge two sorted halves into an auxiliary array using two pointers for left and right subarrays.
- **Best Case:** $O(n \log n)$
- **Worst Case:** $O(n \log n)$
- **Average Case:** $O(n \log n)$
- **How it works:** Recursively divide the list and merge sorted halves.
- **Recurrence relation:**
  $$
  T(n) = T(n/2) + T(n/2) + O(n)
  $$

## Quick Sort

- Pick a pivot (can be **lo**, **hi**, or **mid**) and choose the median.
- Partition into elements **less than pivot** and **greater than pivot**.
- Recursively sort each partition.
- **Best Case:** $O(n \log n)$
- **Worst Case:** $O(n^2)$ (when pivot selection is poor)
- **Average Case:** $O(n \log n)$
- **In-place:** Yes
- **Stable:** No
- **How it works:** Recursively partition the array around a pivot.

## Bucket Sort

- **Time Complexity:** $O(n)$ (assuming uniform distribution)
- **General Case Complexity:** $O(N + B)$ where $B$ is the number of buckets.
- Works well when all values are known to be from $[1, B]$.
- **Stable:** Yes
- **How it works:** Distribute elements into buckets, sort each bucket, and concatenate results.

## Radix Sort

- **Time Complexity:** $O(n)$
- Sort based on digit places (least significant to most significant).
- Example: Sorting numbers by the **1st place digits**.
- E.g., **3** and **143** go in the same bucket when sorting by units place.
- **Stable:** Yes
- **How it works:** Sort elements digit by digit using a stable sorting method (e.g., counting sort).

## Sorting Properties

- **In-place:** No extra array created.
- **Stable:** Equal values remain in the same original order.

## Lower Bound for Comparison-based Sorting

- The theoretical lower bound for any **comparison-based** sorting algorithm is **$\Omega(n \log n)$**.
- Sorting algorithms that rely on comparisons (e.g., Merge Sort, Quick Sort, Heap Sort) cannot be faster than this.
- **Won't be asked to give full proof** but should be familiar with proof techniques.

# Sorting Algorithm Summary

| Sorting Algorithm  | Best Case     | Worst Case    | Average Case  | Stable? | In-Place? | Example Usage                                                   |
| ------------------ | ------------- | ------------- | ------------- | ------- | --------- | --------------------------------------------------------------- |
| **Insertion Sort** | $O(n)$        | $O(n^2)$      | $O(n^2)$      | ✅ Yes  | ✅ Yes    | Small datasets, nearly sorted data                              |
| **Selection Sort** | $O(n^2)$      | $O(n^2)$      | $O(n^2)$      | ❌ No   | ✅ Yes    | When swaps are costly but comparisons are cheap                 |
| **Heap Sort**      | $O(n \log n)$ | $O(n \log n)$ | $O(n \log n)$ | ❌ No   | ❌ No     | Priority queues, scheduling tasks                               |
| **Merge Sort**     | $O(n \log n)$ | $O(n \log n)$ | $O(n \log n)$ | ✅ Yes  | ❌ No     | Sorting linked lists, external sorting (large datasets)         |
| **Quick Sort**     | $O(n \log n)$ | $O(n^2)$      | $O(n \log n)$ | ❌ No   | ✅ Yes    | General-purpose sorting, optimized implementations in libraries |
| **Bucket Sort**    | $O(n)$        | $O(n^2)$      | $O(n + B)$    | ✅ Yes  | ❌ No     | Sorting uniformly distributed floating-point numbers            |
| **Radix Sort**     | $O(n)$        | $O(n \log n)$ | $O(n)$        | ✅ Yes  | ❌ No     | Sorting large numbers, fixed-length strings                     |

### **Legend:**

- **Stable?** ✅ Yes = maintains relative order of equal elements, ❌ No = does not.
- **In-Place?** ✅ Yes = does not use extra memory, ❌ No = requires extra storage.

# Graphs

## Graph Basics

### **Degree of a Vertex**

- **Degree**: Number of edges that include the vertex.
- **In-degree**: Number of edges directed **to** the vertex.
- **Out-degree**: Number of edges directed **from** the vertex.
- **Self-edge**: A loop (edge that starts and ends at the same vertex).

### **Simple Graphs**

- No self-edges.
- No duplicate edges (no multiple edges between the same two vertices).

### **Graph Parameters**

- **V**: Number of vertices.
- **E**: Number of edges.
- - **Min edges:** $0$
  - **Max edges in an undirected graph:** $V(V-1)/2$
  - **Max edges in a directed graph:** $V^2$

### **Weight**

- Cost associated with each edge.

---

## **Graph Connectivity**

### **Undirected Graphs**

- **Connected**: If there is a path between every pair of vertices.
- **Complete**: If every pair of vertices is directly connected.

### **Directed Graphs**

- **Strongly connected**: A path exists between **every** pair of vertices in both directions.
- **Weakly connected**: A path exists between every pair of vertices **if** edge directions are ignored.
- **Complete**: Each vertex has a directed edge to every other vertex.

---

## **Trees and DAGs**

### **Tree**

- **Undirected**
- **Acyclic** (no cycles)
- **Connected** (single connected component)

### **DAG (Directed Acyclic Graphs)**

- **Every rooted directed tree is a DAG**, but not all DAGs are trees.
- **Every DAG is a directed graph**, but not all directed graphs are DAGs.

---

## **Graph Density**

- **Dense Graph**: $E = O(V^2)$
- **Sparse Graph**: $E = O(V)$

---

## **Graph Representations**

### **Adjacency Matrix**

- 2D matrix representation, where entry **(X, Y)** indicates an edge **from X to Y**.
- **Operations**:
  - Get a vertex's **out-edges**: $O(V)$
  - Get a vertex's **in-edges**: $O(V)$
  - Check if an edge exists: $O(1)$
  - Insert an edge: $O(1)$
  - Delete an edge: $O(1)$
- **Space Requirement**: $O(V^2)$
- **Best for**: **Dense graphs**.

### **Adjacency List**

- Array of size **V**, where each entry contains a list of **destination vertices**.
- **Operations**:
  - Get a vertex's **out-edges**: $O(d)$ (where $d$ = out-degree)
  - Get a vertex's **in-edges**: $O(V + E)$
  - Check if an edge exists: $O(d)$
  - Insert an edge: $O(1)$ (assuming duplicates are not checked)
  - Delete an edge: $O(d)$
- **Space Requirement**: $O(V + E)$
- **Best for**: **Sparse graphs**.

---

## **Graph Traversals**

### **Depth-First Search (DFS)**

- **Uses**: Stack (recursion or explicit)
- **Advantages**:
  - Uses less space when finding a path.
  - Never has more than **d × p** elements in memory, where:
    - $d$ = highest out-degree
    - $p$ = longest path

### **Breadth-First Search (BFS)**

- **Uses**: Queue
- **Advantages**:
  - Always finds the **shortest path** in an unweighted graph.
  - Queue may hold **$O(V)$** nodes at worst.

---

## **Graph Algorithms**

### **Dijkstra's Algorithm** (Single Source Shortest Path)

- Finds the shortest paths **from a single source** **when all weights are non-negative**.
- **Steps**:
  1. Pick the closest **unknown vertex** $v$.
  2. Add it to the **set of known vertices**.
  3. Update the cost of the paths from the source to this vertex.

### **Topological Sort**

- **Used on DAGs**.
- **Steps**:
  1. Find a vertex with **in-degree = 0**.
  2. Output the vertex and remove it.
  3. Repeat until all vertices are processed.
- **Applications**: Task scheduling, dependency resolution.

### **Minimum Spanning Tree (MST) Algorithms**

#### **Prim’s Algorithm**

- **Greedy approach**.
- Expands a tree by **adding the smallest edge that connects to a new vertex**.
- **Time Complexity**:
  - $O(V^2)$ (adjacency matrix)
  - $O(E \log V)$ (with priority queue)

#### **Kruskal’s Algorithm**

- **Greedy approach**.
- **Steps**:
  1. Sort all edges by weight.
  2. Add edges **one by one** to the MST **if** they do not form a cycle.
  3. Use **disjoint sets** (Union-Find) to detect cycles.
- **Time Complexity**: $O(E \log V)$

---

## **Greedy Algorithm Concept**

- In each step, pick the **locally optimal** choice.
- Example: **Dijkstra's Algorithm**, **Prim's Algorithm**, **Kruskal’s Algorithm**.

### **Dijkstra’s Algorithm Complexity**

\[
O(V + V \log V + V + E \log V)
\]

---

## **Graph Topics to Know**

### **Graph Basics**

- Definitions: Weights, directedness, degrees.
- Paths, cycles.
- Connectedness.
- DAGs.

### **Graph Representations**

- **Adjacency List**
- **Adjacency Matrix**
- **When to use each, their pros and cons.**

### **Graph Traversals**

- **BFS** (Queue-based)
- **DFS** (Stack-based)
- **Data structures associated with each.**

## Task Splitting

- **Small Array (Below Threshold):** Compute sum directly.
- **Large Array:** Split into two sub-tasks.
  - `fork()`: Recursively splits and runs the left task asynchronously.
  - `compute()`: Computes the right task synchronously.
  - `join()`: Waits for the left task to complete and retrieves the result.

## Parallelism Concepts

- **Work ($T_1$):** Time taken by a single processor.
- **Span ($T_\infty$):** Time taken with infinite processors.
- **Speed-up:** $T_1 / T_P$ (measures efficiency with $P$ processors).
- **Parallelism:** $T_1 / T_\infty$ (upper bound on speed-up).

## Good vs. Poor Parallelization

- **Parallel-friendly:** Maps/reduces over arrays and trees.
- **Not parallel-friendly:** Linked lists, sequential I/O, dependencies on previous steps.

---

## Concurrency Issues

### Race Conditions

- Occurs when multiple threads access shared data concurrently, leading to unpredictable results.

### Data Races

- Happens when:
  - Two or more threads access the same variable concurrently.
  - At least one access is a write (Read-Write or Write-Write).
  - No synchronization is in place.

---

## Graph Algorithms

### Minimum Spanning Tree (MST)

- **Given:** Undirected graph $G = (V, E)$.
- **Output:** Subset of edges $E'$ forming a tree that:
  - Is connected.
  - Has no cycles.
  - Contains $|V| - 1$ edges.

#### Prim’s Algorithm

- Similar to Dijkstra’s.
- Starts from one node and grows greedily.
- **Time Complexity:** $O(E \log V)$ using a priority queue.

#### Kruskal’s Algorithm

- Each vertex starts as its own tree.
- Processes edges in ascending weight order.
- Uses **Union-Find** to check for cycles.
- **Time Complexity:** $O(E \log E)$ or $O(E \log V)$.

---

## Topological Sort

- Order vertices such that for every directed edge $(u, v)$, $u$ appears before $v$.
- **Approach:** Start with nodes having the smallest in-degree.
- **Time Complexity:** $O(V^2 + E)$ (if adjacency matrix is used).

---

## Parallel Programming

### Fork-Join Parallelism

- **Applications:** Parallel sum, map-reduce operations, tree traversals.
- **Examples:**
  - **Reduce:** Sum, product, min, max, etc.
  - **Map:** Transform elements (e.g., string lengths, bit vector operations).
  - **Parallel Prefix Sum Algorithm, Filters, etc.**

### Analysis of Parallel Algorithms

#### Amdahl’s Law

- The theoretical speedup of a program using multiple processors.

  $$
  S(P) = \frac{1}{(1 - f) + \frac{f}{P}}
  $$

  - $S(P)$ = speedup with $P$ processors
  - $f$ = fraction of the program that is parallelizable
  - $P$ = number of processors

  - **Implication:** Speedup is limited by the sequential portion of the task. If $f$ is small, adding more processors has little effect.

---

## Synchronization & Concurrency Control

- **Locks:** Ensures mutual exclusion.
- **Reentrant Locks:** Allows a thread to re-acquire a lock it already holds.
- **Java’s `synchronized` statement:** Manages access to critical sections.

### Granularity & Critical Sections

- **Coarse-grained locks:** One lock for large sections of code.
- **Fine-grained locks:** Multiple locks for different sections to reduce contention.
- **Critical Section Size:** Too large → slows execution, too small → risk of frequent contention.

### Deadlock

- Occurs when multiple threads hold resources the others need, leading to a standstill.

---
