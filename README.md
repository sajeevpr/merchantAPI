1. Build the Project : mvn clean install
2. Test the project : mvn clean test
3. Generate javadoc : mvn javadoc:javadoc
4. This project is having the following structure
		
        a.com.dd.merchant.MerchantApiApplication - Application startup class
        b.com.dd.merchant.controller.PhoneController - REST controller for accepting POST request for uploading phone numbers
        c.com.dd.merchant.service.PhoneNumberReqHandlerServiceImpl - Service class to parse and persist phone numbers
        d.com.dd.merchant.service.ExtractPhoneNumberServiceImpl -  Service class to parse and validate raw phone number input																
        e.com.dd.merchant.service.PhoneNumberServiceImpl - Service class to persist the phone numbers in a transaction															
        f.com.dd.merchant.dao.PhoneNumberDAOImpl - DAO class to persist data into database																
        g.com.dd.merchant.exception - Exception classes for invalid input and data persistant exceptions
        h.com.dd.merchant.model - Model classes for UI and database
        i.com.dd.merchant.util - Utility classes
        j.com.dd.merchant.constants - Classes to hold constants and enums

5. Assumptions

       a.Phone number  and type combination is unique
       b.Phone numbers in a request, either Home or Cell can come in other requests
       c.Home and cell phone number combination is not unique
       d.Home and cell phone numbers may come in any order
       e.Home and cell phone numbers will always come in the request. If anything is missing it will throw error response

6. Design decisions

       a.The Merchant API has got two parts - parseAndValidate, persistData
       b.Since the phoneNumber and type combination is unique, the primary key is created by concatenating them. This saves on having an additional index.
       c.The request processing is done in a single JPA transaction. Therefore any failure will rollback both home and Cell phone numbers updation from DB
       d.Any failure in parsing or persisting messages will be thrown as an exception and the response will have the details for the caller
       d.H2 database is used for easy of development and testing
       d.The database table (PHONE_NUMBER) stores phone number and type as a single record

7. API Testing Samples

       a.First Request -> curl -X POST --header "Content-Type: application/json" -d '{"raw_phone_numbers": "(Home)415-415-4155 (Cell) 415-123-4567"}' http://localhost:8080/phone-numbers
             {"results":[{"id":"4154154155-home","phone_number":"4154154155","phone_type":"home","occurrences":1},{"id":"4151234567-cell","phone_number":"4151234567","phone_type":"cell","occurrences":1}]}
       b.Second Request -> curl -X POST --header "Content-Type: application/json" -d '{"raw_phone_numbers": "(Home)415-415-4155 (Cell) 415-123-4567"}' http://localhost:8080/phone-numbers
             {"results":[{"id":"4154154155-home","phone_number":"4154154155","phone_type":"home","occurrences":2},{"id":"4151234567-cell","phone_number":"4151234567","phone_type":"cell","occurrences":2}]}
       c.Reverse Order - curl -X POST --header "Content-Type: application/json" -d '{"raw_phone_numbers": "(Cell) 415-123-4567 (Home)415-415-4155"}' http://localhost:8080/phone-numbers
             {"results":[{"id":"4154154155-home","phone_number":"4154154155","phone_type":"home","occurrences":3},{"id":"4151234567-cell","phone_number":"4151234567","phone_type":"cell","occurrences":3}]}
       d.Invalid Request - curl -X POST --header "Content-Type: application/json" -d '{"raw_phone_numbers": "(Cell1) 415-123-4567 (Home1)415-415-4155"}' http://localhost:8080/phone-numbers
             {"results":null,"error":"Either home phone or cell phone details not found in the input"}
       e.Invalid number - curl -X POST --header "Content-Type: application/json" -d '{"raw_phone_numbers": "(Cell) 415-123-4567a (Home)415-415-4155a"}' http://localhost:8080/phone-numbers
             {"results":null,"error":"Error while parsing the second number"}
8. Swagger UI - http://localhost:8080/swagger-ui.html
