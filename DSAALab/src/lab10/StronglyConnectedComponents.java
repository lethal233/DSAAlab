package lab10;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class StronglyConnectedComponents {
    private int V; // 顶点的数量（ vertices）
    private LinkedList<Integer>[] adj; // 临界表（Adjacency List）

    // 构造方法
    StronglyConnectedComponents(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    // 向图中加入边
    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    // 一个递归方法从v开始采用DFS算法打印结点
    void DFSUtil(int v, boolean visited[]) {
        // 标记当前顶点访问过并且打印
        visited[v] = true;
        System.out.print(v + " ");

        int n;

        for (Integer integer : adj[v]) {
            n = integer;
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }

    StronglyConnectedComponents getTranspose() {
        StronglyConnectedComponents g = new StronglyConnectedComponents(V);
        for (int v = 0; v < V; v++) {
            for (Integer integer : adj[v]) g.adj[integer].add(v);
        }
        return g;
    }

    void fillOrder(int v, boolean[] visited, Stack stack) {
        visited[v] = true;

        for (int n : adj[v]) {
            if (!visited[n])
                fillOrder(n, visited, stack);
        }

        stack.push(new Integer(v));
    }

    // 输出所有强连通量
    void printSCCs() {
        Stack stack = new Stack();

        // 标记所有的顶点为未访问状态（为了第一次DFS搜索）
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;

        for (int i = 0; i < V; i++)
            if (!visited[i])
                fillOrder(i, visited, stack);

        // 创建一个反转图
        StronglyConnectedComponents gr = getTranspose();

        // 标记所有的顶点为未访问状态（为了第二次DFS搜索）
        for (int i = 0; i < V; i++)
            visited[i] = false;

        //现在按照Stack中的顺序处理所有的顶点
        while (!stack.empty()) {
            // 从栈中弹出一个顶点
            int v = (int) stack.pop();

            // Print Strongly connected component of the popped vertex
            if (!visited[v]) {
                gr.DFSUtil(v, visited);
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        // 构造一个有向图
        StronglyConnectedComponents g = new StronglyConnectedComponents(5);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);

        System.out.println("Following are strongly connected components " + "in given graph ");
        g.printSCCs();
    }
}