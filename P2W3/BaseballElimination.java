import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;
//import java.util.Arrays;

public class BaseballElimination
{
    private class Element
    {
        public boolean eliminate;
        public boolean[] R;
    }
//    private class Pair
//    {
//        public int e1, e2;
//    }
    private LinearProbingHashST<String, Integer> st;
    private String[] idName;
    private LinearProbingHashST<String, Element> cache;
    private int[][] data;
    //private FlowNetwork G;
    private int numVertex;
    public BaseballElimination(String filename)                    
    // create a baseball division from given filename in format specified below
    {
        st = new LinearProbingHashST<String, Integer>();
        In in = new In(filename);
        int numTeam = Integer.parseInt(in.readLine());
        int id = 0;
        data = new int[numTeam][3+ numTeam];
        idName = new String[numTeam];
        while (!in.isEmpty()) {
                String s = in.readString();
                //String[] t_data = s.split("\\s+");
                st.put(s, id);
                idName[id] = s;
                for (int i = 1; i < 4 + numTeam; i++)
                {
                    data[id][i-1] = in.readInt();//Integer.parseInt(t_data[i]);
                }
                id++;
            }
        
        // build the flow network
        // number of match: (numTeam - 2) * (numTeam - 1) / 2
        // number of team : numTeam - 1
        // fiction vertex: 2
         numVertex= (numTeam - 2) * (numTeam - 1) / 2 + numTeam + 1; 
         cache = new LinearProbingHashST<String, Element>();
        
       }
       public              int numberOfTeams()                        
      // number of teams
       {
           return st.size();
       }
       public Iterable<String> teams()                 // all teams
       {
           return st.keys();
       }
       public              int wins(String team)                      
       // number of wins for given team
       {
           if (!st.contains(team))
               throw new java.lang.IllegalArgumentException();
           int t = st.get(team);
           return data[t][0];
       }
       
       public              int losses(String team)                    // number of losses for given team
       {
           if (!st.contains(team))
               throw new java.lang.IllegalArgumentException();
           int t = st.get(team);
           return data[t][1];
       }
       
       public              int remaining(String team)                 // number of remaining games for given team
       {
           if (!st.contains(team))
               throw new java.lang.IllegalArgumentException();
           int t = st.get(team);
           return data[t][2];
       }
       
       public              int against(String team1, String team2)    // number of remaining games between team1 and team2
       {
           if ((!st.contains(team1)) || (!st.contains(team2)))
               throw new java.lang.IllegalArgumentException();
           int t1 = st.get(team1);
           int t2 = st.get(team2);
           return data[t1][3+ t2];
       }
       
       public          boolean isEliminated(String team)              // is given team eliminated?
       {
           if (!st.contains(team))
               throw new java.lang.IllegalArgumentException();
           if (!cache.contains(team))
               compute(team);
           return cache.get(team).eliminate;
       }
       
       public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
       {
           if (!st.contains(team))
               throw new java.lang.IllegalArgumentException();
           if (!cache.contains(team))
               compute(team);
           if (!cache.get(team).eliminate)
               return null;
           Queue<String> result = new Queue<String>();
           for (int i = 0; i < st.size(); i++)
           {
               if (cache.get(team).R[i])
                   result.enqueue(idName[i]);
           }
           return result;
       }
       
       private int f(int id, int chosen)
       {
           if (id > chosen)
               return -1;
           return 0;
       }
       
       private void compute(String team)
       {
           int id = st.get(team);
           int numTeam = st.size();
           FlowNetwork G = new FlowNetwork(numVertex);
           int numberMatch = (numTeam - 2) * (numTeam - 1) / 2;
           //Pair[] map = new Pair[numberMatch];
           //int[] mapTeam = new int[st.size()];
           int idMatch = 2;
           int pref = wins(team) + remaining(team);
           
           //build Flow network
           for (int iTeam = 0; iTeam < st.size(); iTeam++)
           {
               if (iTeam != id)
               {
                   //double temp = Math.max(pref - data[iTeam][0], 0);
                   if (pref < data[iTeam][0])
                   {
                       Element t_e = new Element();
                       t_e.eliminate = true;
                       t_e.R = new boolean[st.size()];
                       t_e.R[iTeam] = true;
                       //Arrays.fill(t_e.R, true);
                       cache.put(team, t_e);
                       return;
                   }
                   FlowEdge e3 = new FlowEdge(2+ numberMatch + iTeam + f(iTeam, id),1,pref - data[iTeam][0],0);
                   G.addEdge(e3);
                   
                   for (int j = iTeam+ 1; j < st.size(); j++)
                   {
                       if (j != id)
                       {
                           //map[idMatch - 2]    = new Pair();
                           //map[idMatch - 2].e1 = iTeam     ;
                           //map[idMatch - 2].e2 = j         ;
                           
                           FlowEdge e = new FlowEdge(0, idMatch, data[iTeam][3+j],0);
                           G.addEdge(e);
                           
                           FlowEdge e1 = new FlowEdge(idMatch, 2+ numberMatch + iTeam + f(iTeam, id), Double.POSITIVE_INFINITY,0);
                           G.addEdge(e1);
                           
                           FlowEdge e2 = new FlowEdge(idMatch, 2+ numberMatch + j + f(j, id), Double.POSITIVE_INFINITY,0);
                           G.addEdge(e2);
                           
                           idMatch++;
                       }
                   }
               }
               
           }
           FordFulkerson f2 = new FordFulkerson(G,0,1);
           boolean t_eliminate = false;
           for (FlowEdge e : G.adj(0))
           {
               if(e.flow() < e.capacity())
               {
                   t_eliminate = true;
                   break;
               }
           }
           Element t_e = new Element();
           t_e.eliminate = t_eliminate;
           t_e.R= new boolean[st.size()];
           if (t_eliminate)
           {
               for (int v = 0; v < st.size() ; v++) 
               {
                   if (v != id)
                   {
                       t_e.R[v] = f2.inCut(2+ numberMatch + v + f(v, id));
                   }
                   else
                       t_e.R[v] = false;
               }
           }
           cache.put(team, t_e);
           //System.out.println(G.toString());
       }
       
       public static void main(String[] args) {
           BaseballElimination division = new BaseballElimination(args[0]);
           //division.isEliminated("Detroit");
           for (String team : division.teams()) {
               if (division.isEliminated(team)) {
                   StdOut.print(team + " is eliminated by the subset R = { ");
                   for (String t : division.certificateOfElimination(team)) {
                       StdOut.print(t + " ");
                   }
                   StdOut.println("}");
               }
               else {
                   StdOut.println(team + " is not eliminated");
               }
           }
       }
}