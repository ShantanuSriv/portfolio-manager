# portfolio-manager
Smallcase Backend Task

API Endppoints

1. GET http://3.141.32.29:8080/v1/trades/fetch/all - to fetch all trades
2. GET http://3.141.32.29:8080/v1/portfolios/fetch/all - to fetch all portfolios
3. POST http://3.141.32.29:8080/v1/trades/create - to create a new trade
   Request payload:
   
    *BUY*
     ```
     {
      "ticker_symbol": "REL",
      "shares": 8,
      "trade_type": "BUY",
      "price": 45
     }
     ```
     *SELL*
     ```
     {
      "ticker_symbol": "REL",
      "shares": 8,
      "trade_type": "SELL",
      "price": 45
     }
     ```
4. PUT http://3.141.32.29:8080/v1/trades/update/60585861fb5aab1af5ecd576 - to update a trade with trade id
   Request payload:
   ```
   {
    "ticker_symbol": "REL",
    "shares": 8,
    "trade_type": "BUY",
    "price": 45
   }
   ```
   
   
5. GET http://3.141.32.29:8080/v1/portfolios/fetch/returns - to fetch returns


7. DELETE http://3.141.32.29:8080/v1/trades/update/60585861fb5aab1af5ecd576 - to delete a trade
