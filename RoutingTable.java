import java.io.Serializable;
import java.util.*;
public class RoutingTable implements Serializable {
    static final long serialVersionUID = 99L;
    private final Router router;
    private final Network network;
    private List<RoutingTableEntry> entryList;
    private List<Vertex> vtList;
    private  List<Edge> edList;
    private Map<String, Vertex> hMap;
    private Stack<Link> linkStack;
    public RoutingTable(Router router) {
        this.router = router;
        this.network = router.getNetwork();
        this.entryList = new ArrayList<>();}

    public void updateTable() {
        vtList = new ArrayList<>();
        edList = new ArrayList<>();
        hMap = new HashMap<>();
        linkStack = new Stack<>();
        entryList = new ArrayList<>();
        for(int i=0; i<network.getRouters().size(); i++){
            Vertex vt = new Vertex(network.getRouters().get(i).getIpAddress(),network.getRouters().get(i).getIpAddress(), network.getRouters().get(i));
            hMap.put(network.getRouters().get(i).getIpAddress(),vt);
            vtList.add(vt);}
        for(int j=0; j<network.getLinks().size();j++){
            Edge ed1 = new Edge(hMap.get(network.getLinks().get(j).getIpAddress1()),hMap.get(network.getLinks().get(j).getIpAddress2()), network.getLinks().get(j).getCost());
            Edge ed2 = new Edge(hMap.get(network.getLinks().get(j).getIpAddress2()),hMap.get(network.getLinks().get(j).getIpAddress1()), network.getLinks().get(j).getCost());
            edList.add(ed1);
            edList.add(ed2);}
        for(int i =0; i<network.getRouters().size();i++) {
            linkStack.clear();
            if(!Objects.equals(this.router.getIpAddress(), network.getRouters().get(i).getIpAddress())) {
                Graph gr = new Graph(vtList, edList);
                DijkstraAlgorithm djAl = new DijkstraAlgorithm(gr);
                djAl.execute(hMap.get(this.router.getIpAddress()));
                List<Vertex> list = djAl.getPath(hMap.get(network.getRouters().get(i).getIpAddress()));
                if(list==null) {
                    boolean flag=false;
                    RoutingTableEntry entry = new RoutingTableEntry(null, this.router.getIpAddress(), linkStack);
                    entryList.add(entry);
                    for(int s =0 ; s <network.getLinks().size() ; s++) {
                        if(network.getLinks().get(s).getIpAddress1().equals(this.router.getIpAddress()) || network.getLinks().get(s).getIpAddress2().equals(this.router.getIpAddress())) {
                            flag=true;
                            break;}}
                if(flag){continue;}
                    else{break;}}
                for(int j=0; j<list.size()-1; j++){
                    for(int l=0; l<network.getLinks().size();l++){
                        if((Objects.equals(network.getLinks().get(l).getIpAddress1(), list.get(j).getId()) && Objects.equals(network.getLinks().get(l).getIpAddress2(), list.get(j + 1).getId()))||(Objects.equals(network.getLinks().get(l).getIpAddress2(), list.get(j).getId()) && Objects.equals(network.getLinks().get(l).getIpAddress1(), list.get(j + 1).getId()))){
                            linkStack.push(network.getLinks().get(l));}}}
                RoutingTableEntry rtEnt = new RoutingTableEntry(router.getIpAddress(),list.get(list.size()-1).getName(),linkStack);
                entryList.add(rtEnt);}}}

    void sort(List<Link> arr) {
        int n = arr.size();
        for (int i = 0; i < n-1; i++)
        {
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr.get(j).getCost() < arr.get(min_idx).getCost())
                    min_idx = j;
            Link temp = arr.get(min_idx);
            arr.set(min_idx,arr.get(i));
            arr.set(i,temp);}}

    public Stack<Link> pathTo(Router destination) {
        Stack<Link> LinksStack = new Stack<>();
    return LinksStack;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutingTable that = (RoutingTable) o;
        return router.equals(that.router) && entryList.equals(that.entryList);}
    public List<RoutingTableEntry> getEntryList() {
        return entryList;
    }
}
