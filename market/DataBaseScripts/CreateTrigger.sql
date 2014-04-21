create or replace
TRIGGER bid_insert
BEFORE INSERT OR UPDATE ON BIDS
FOR EACH ROW
DECLARE
isSelf NUMBER := 0;
incBid NUMBER;
lastBid NUMBER;
bidCount NUMBER;
byItNowFlag VARCHAR2(1);
--time variables
timeleftHours NUMBER := 0;
/*
to set bid on self item number ORA -2301
to set bil little then access ORA -2302
*/
selfBid EXCEPTION;
littleBid EXCEPTION;
byItNowException EXCEPTION;
timeLeftException EXCEPTION;
PRAGMA EXCEPTION_INIT(selfBid, -2301);
PRAGMA EXCEPTION_INIT(littleBid, -2302);
PRAGMA EXCEPTION_INIT(byItNowException, -2303);
PRAGMA EXCEPTION_INIT(timeLeftException, -2304);
BEGIN
--if self insert
  SELECT COUNT(:NEW.bidid) INTO isSelf FROM bids b
  INNER JOIN items i  ON :NEW.itemid = i.itemid
  WHERE :NEW.bidderid = i.sellerid;
IF (isself != 0) THEN
  RAISE selfBid; --rise exception
END IF;
--if we try to set bid when exists flag byItNow
SELECT buyitnow INTO byItNowFlag FROM items
  WHERE itemid = :NEW.itemid;
  IF(byItNowFlag = 1) THEN
  SELECT bid INTO bidCount FROM bids
  WHERE itemid = :NEW.itemid;
  IF(bidCount IS NOT NULL) THEN
    RAISE byItNowException;
   END IF;
  END IF;
--if little bid
 SELECT bidincrement + startprice INTO incBid FROM items
 WHERE itemid = :NEW.itemid;
  IF(((:NEW.bid <= incBid) AND(byItNowFlag = 0))
        OR ((:NEW.bid != incBid) AND(byItNowFlag = 1))) THEN
    RAISE littleBid;
  END IF;
  SELECT MAX(bid) INTO lastBid FROM bids
  WHERE itemid = :NEW.itemid;
  IF(lastBid IS NOT NULL) THEN
  IF(:NEW.bid < lastBid) THEN
   RAISE littleBid;
  END IF;
  END IF;
--if timefor bid is ower
  SELECT FLOOR(SYSDATE - (startbiddingdate + (timeleft/24))) INTO timelefthours
  FROM items WHERE  itemid = :NEW.itemid;
  IF(timelefthours > 0) THEN
    RAISE timeLeftException;
  END IF;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      dbms_output.put_line('Okay');
END;
/