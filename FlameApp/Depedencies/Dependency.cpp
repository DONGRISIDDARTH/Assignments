#include<iostream>
#include<bits/stdc++.h>
using namespace std;

class dependency{
    public:
    bool dfs(int node, vector<int> adj[], vector<int> &vis, vector<int> &recStack){
        vis[node] = 1;
        recStack[node] = 1;

        for(auto i : adj[node]){
            if(vis[i] == 0){
                if(dfs(i, adj, vis, recStack)) return true;
            } else if(recStack[i]) {
                return true;
            }
        }

        recStack[node] = 0;
        return false;
    }
    
    bool hasCircularDependency(vector<vector<int>> &edges,int n){
        vector<int> adj[n];
        for(auto i : edges){
            adj[i[0]].push_back(i[1]);
        }
        
        vector<int> vis(n, 0);
        vector<int> recStack(n, 0);
        
        for(int i = 0; i < n; i++){
            if(vis[i] == 0){
                if(dfs(i, adj, vis, recStack) == true){
                    return true;
                }
            }
        }
        return false;
    }
};

int main(){
    int n, e;
    cout << "Enter no of Nodes : ";
    cin >> n;
    cout << "Enter no of Edges: ";
    cin >> e;

    vector<vector<int>> edges;
    cout << "Enter (a , b):\n";
    for(int i = 0; i < e; ++i){
        int a, b;
        cin >> a >> b;
        edges.push_back({a, b});
    }
    dependency obj;
    if(obj.hasCircularDependency(edges, 4)){
        cout<<"True"<<endl;
    } else {
        cout<<"False"<<endl;
    }

    return 0;
}
