package stockExchange.market;

public enum Orderer {

    ClientOrder(1),
    ForeignInvestorOrder(2),
    MarketControllerOrder(3),
    MutualFundOrder(4),
    IssuerOrder(5),
    ProfessionalOrder(6),
    InsiderOrder(7);

    public final int priorityRank;

    Orderer(int priorityRank) {
        this.priorityRank = priorityRank;
    }


}
