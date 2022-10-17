import java.io.Serializable;
import java.util.Objects;
public class Link implements Serializable {
    static final long serialVersionUID = 66L;
    private String ipAddress1;
    private String ipAddress2;
    private int bandwidthInMbps;
    private double cost;

    public Link(String ipAddress1, String ipAddress2, int bandwidthInMbps) {
        this.bandwidthInMbps = bandwidthInMbps;
        this.ipAddress1 = ipAddress1;
        this.ipAddress2 = ipAddress2;
        calculateAndSetCost();
    }

    void calculateAndSetCost() {
        double costval= (1000)/(double)getBandwidthInMbps();
        setCost(costval);}
    public void setCost(double cost) {
        this.cost = cost;
    }
    public int getBandwidthInMbps() {
        return bandwidthInMbps;
    }
    public void setBandwidthInMbps(int bandwidthInMbps) {
        this.bandwidthInMbps = bandwidthInMbps;
        calculateAndSetCost();}
    public double getCost() {
        return cost;
    }
    public void setIpAddress1(String ipAddress1) {
        this.ipAddress1 = ipAddress1;}
    public void setIpAddress2(String ipAddress2) {
        this.ipAddress2 = ipAddress2;}
    public String getIpAddress1() {
        return ipAddress1;
    }
    public String getIpAddress2() {
        return ipAddress2;
    }
    public String getOtherIpAddress(String ipAddress) {
        if (ipAddress1.equals(ipAddress))
            return ipAddress2;
        else
            return ipAddress1;}
    @Override
    public String toString() {
        return "Link{" +
                "ipAddress1='" + ipAddress1 + '\'' +
                ", ipAddress2='" + ipAddress2 + '\'' +
                ", bandwidthInMbps=" + bandwidthInMbps +
                ", cost=" + cost +
                '}';}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return bandwidthInMbps == link.bandwidthInMbps && Math.abs(link.cost - cost) <= 0.0001 && Objects.equals(ipAddress1, link.ipAddress1) && Objects.equals(ipAddress2, link.ipAddress2);}}
