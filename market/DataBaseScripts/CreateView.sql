CREATE VIEW beanItems AS
SELECT "itemId", "title", "description", "category", "sellerId", "seller", "startPrice", "bidInc", "bestOffer", 
                                            "bidderId", "bidder", "startBiddingDate" ,"timeleft", "buyItNow"
FROM(
  SELECT items.itemid "itemId", items.title "title", items.description "description", ct.category "category",
  items.sellerId "sellerId",
  users.fullname "seller", items.startprice "startPrice", items.bidincrement  "bidInc", 
  p.bestOffer  "bestOffer", p.bidderId "bidderId",p.bidder "bidder", 
  items.startbiddingdate "startBiddingDate", items.timeLeft  "timeleft", items.buyItNow "buyItNow"
  FROM items
  LEFT JOIN users ON users.userId = items.sellerId 
  LEFT JOIN category ct ON ct.categoryId = items.category
  LEFT JOIN (SELECT itemID, bid bestOffer, us.userid bidderId,us.fullName bidder
            FROM bids bd INNER JOIN users us ON us.userId = bd.bidderid 
            WHERE bd.bidid IN (SELECT  max(bidid) FROM bids GROUP By itemid)) p
            ON items.itemID = p.itemID);