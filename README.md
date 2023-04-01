# StockExchange implementation using Binomial Heap
A stock exchange is a really important marketplace where people buy and sell things that show how well companies are doing. These things are called "securities" and include things like stocks and bonds. When companies first make these securities available, they do it on something called the "primary market." But after that, people can buy and sell them on the stock exchange.

Using the stock exchange is great for both companies and people who want to invest their money. Companies can get more money by selling their securities on the stock exchange, and they can also get more attention from possible investors. People who invest their money can buy and sell pieces of companies on the stock exchange, and maybe make some money if the companies do well.

There are two main kinds of things people can do on the stock exchange: buy and sell. To make it easier to keep track of these things, we're using a "binomial heap". A binomial heap is a special case of the heap data structure. It is a data structure created by combining multiple binomial trees of varying orders. It helps us quickly find and access the buy and sell orders for a specific stock. 

Both kinds of heaps: Min and Max binomial heap is used in the implementation. When deciding which orders to prioritize, we look at things like the price of the order and when it was created. For example, if someone wants to buy a stock for a higher price than someone else, we prioritize the higher-priced order over the other. And if someone wants to sell a stock for a lower price than someone else, we prioritize the lower-priced order first. We use a Max binomial heap for buy orders and a hashmap with a min binomial heap as value for sell orders.

## Contributors:
Vivek Murarka (22200673)

Nikhitha Grace Josh (22200726)

Purvish Shah (22200112)

Ravi Raj Pedada (22200547)

Meghana Kamsetty Ravikumar (22200568)
