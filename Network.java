import java.io.*;
import java.util.*;

import java.util.regex.Pattern;
public class Network implements Serializable {
    static final long serialVersionUID = 55L;
    private List<Router> routers = new ArrayList<>();
    private List<Link> links = new ArrayList<>();
    public Network(String filename) throws FileNotFoundException {

        ArrayList<String> lines = new ArrayList<>();
        try {
            File file = new File(filename);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);}
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            if (line.startsWith("RouterIP:")) {
                Router rt = new Router(line.substring(9), this);
                routers.add(rt);
            } else if (line.startsWith("Link:")) {
                line = line.substring(5);
                Pattern ptn = Pattern.compile("(-)|( )");
                String[] parts = ptn.split(line);
                Link link = new Link(parts[0], parts[1], Integer.parseInt(parts[2].substring(10)));
                links.add(link);
            }
        }
        updateAllRoutingTables();
    }

    public void setRouters(List<Router> routers) {
        this.routers = routers;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }
    public static String routerRegularExpression() {
        String pattern = "(\\d{1,3}\\.\\d{1,3}\\.(\\d{1,3})\\.\\d{1,3})";
        return pattern;}

    public static String linkRegularExpression() {
        String pattern = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})-(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\D*(\\d+)\\D+";
        return pattern;
    }
    public List<Router> getRouters() {
        return routers;
    }
    public List<Link> getLinks() {
        return links;
    }
    public List<RoutingTable> getRoutingTablesOfAllRouters() {
        if (routers != null) {
            List<RoutingTable> routingTableList = new ArrayList<>();
            for (Router router : routers)
                routingTableList.add(router.getRoutingTable());
            return routingTableList;}
        return null;}
    public Router getRouterWithIp(String ip) {
        if (routers != null) {
            for (Router router : routers) {
                if (router.getIpAddress().equals(ip))
                    return router;}}
        return null;}
    public Link getLinkBetweenRouters(String ipAddr1, String ipAddr2) {
        if (links != null) {
            for (Link link : links) {
                if (link.getIpAddress1().equals(ipAddr1) && link.getIpAddress2().equals(ipAddr2)
                        || link.getIpAddress1().equals(ipAddr2) && link.getIpAddress2().equals(ipAddr1))
                    return link;}}
        return null;}

    public List<Link> getLinksOfRouter(Router router) {
        List<Link> routersLinks = new ArrayList<>();
        if (links != null) {
            for (Link link : links) {
                if (link.getIpAddress1().equals(router.getIpAddress()) ||
                        link.getIpAddress2().equals(router.getIpAddress())) {
                    routersLinks.add(link);}}}
        return routersLinks;}
    public void updateAllRoutingTables() {
        for (Router router : getRouters()) {
            router.getRoutingTable().updateTable();}}
    public void changeLinkCost(Link link, double newCost) {
        link.setCost(newCost);
        updateAllRoutingTables();}
    public void addLink(Link link) {
        links.add(link);
        updateAllRoutingTables();}
    public void removeLink(Link link) {
        links.remove(link);
        updateAllRoutingTables();}
    public void addRouter(Router router) {
        routers.add(router);
        updateAllRoutingTables();}
    public void removeRouter(Router router) {
        links.removeIf(link -> getRouterWithIp(link.getIpAddress1()) == router || getRouterWithIp(link.getIpAddress2()) == router);
        routers.remove(router);
       updateAllRoutingTables();}
    public void changeStateOfRouter(Router router, boolean isDown) {
    }
}
