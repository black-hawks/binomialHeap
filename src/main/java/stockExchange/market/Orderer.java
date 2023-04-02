package stockExchange.market;

/**
 * The Orderer enum represents the different types of orders that can be placed in a market.
 * Each orderer has a priority rank that determines the order in which their orders are executed.
 */
public enum Orderer {

    /**
     * Represents an order placed by a client.
     */
    ClientOrder(1),

    /**
     * Represents an order placed by a foreign investor.
     */
    ForeignInvestorOrder(2),

    /**
     * Represents an order placed by the market controller.
     */
    MarketControllerOrder(3),

    /**
     * Represents an order placed by a mutual fund.
     */
    MutualFundOrder(4),

    /**
     * Represents an order placed by an issuer.
     */
    IssuerOrder(5),

    /**
     * Represents an order placed by a professional trader.
     */
    ProfessionalOrder(6),

    /**
     * Represents an order placed by an insider.
     */
    InsiderOrder(7);

    /**
     * The priority rank of the orderer.
     */
    public final int priorityRank;


    /**
     * Creates a new instance of the Orderer enum with the specified priority rank.
     *
     * @param priorityRank the priority rank of the orderer
     */
    Orderer(int priorityRank) {
        this.priorityRank = priorityRank;
    }


}
