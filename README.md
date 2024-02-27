Steps to run:

1. Start registry
2. Start student, purchase & receipt services
3. Create student data 
POST localhost:8083/api/v1/students
{
   "name": "Nasim",
   "grade": "1",
   "school": "RKGIT",
   "contact":"+918450960808"
   }

4. Create purchase, Use student id from step 3
POST localhost:8081/api/v1/purchase

   {
       "studentId": 1,
       "cardNumber": "1234-5678-9012",
       "cardHolderName": "Nasim Akhtar",
       "cardType": "MaserCard",
       "items": [
           {
               "description":"Course 1",
               "count": 1,
               "amount": 100
           },
           {
               "description":"Course 2",
               "count": 2,
               "amount": 200
           }
       ]
   }

5. Fetch receipt
GET localhost:8082/api/v1/receipts/<reference number from step 4>


Notes:
Gateway & open-api is not working for now.
