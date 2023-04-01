<h1>Stock exchange using Binomial Heap</h1>

> Please find Java Doc here: target/site/apidocs/index.html

A stock is a type of security that indicates the holder owns a portion of the issuing corporation and is typically traded on stock exchanges. A stock exchange is a marketplace where buyers and sellers trade stocks and other securities. Exchanges in the stock market happens in real time and transations need to get executed as quickly as possible. Updates to the highest priority bids must be performed efficiently as new bids or asks arrive. Similarly, modifications or removal of existing bids must be carried out efficiently. As there is a massive amount of order data generated every second, a data structure with efficient access to highest priority bids to peek and update as and when new bids or asks arrive needs to be used. This is where Binomial Heaps come in. A binomial heap is a special case of the heap data structure. It is a data structure created by combining multiple binomial trees of varying orders. 

This project is an implementation of Binomial Heap in Stock Exchange. It demonstrates how a Binomial Heap is used to resolve bids and asks in Stock Exchanges. Insertion in a binomial heap involves creating a new heap with a single element and then merging it with the existing heap. The merging process involves linking the roots of the two heaps of the same order until there is no two heaps of the same order left. To de then the tree is restructured to satify the binomial heap properties. lete the root node in a binomial heap, first, the root is removed, and then the tree is restructured to satisfy the binomial heap properties. 


<h3>Contributors:</h3>

Vivek Murarka (22200673)

Nikhitha Grace Josh (22200726)

Purvish Shah (22200112)

Ravi Raj Pedada (22200547)

Meghana Kamsetty Ravikumar (22200568)
