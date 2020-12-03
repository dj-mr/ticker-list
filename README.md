# Ticker List 
This application provides Organization Details. Refresh operation refreshes the details. Data returned includes following fields:
 - CIK
 - Company Name
 - Headquarters
 - SIC Code
 
 Sample Request:
 ```
    curl --request GET \
      --url 'http://localhost:8443/tickers?number_of_samples=2'
 ```

 Sample Response:
 ```
    [
      {
        "cik": "0000320193",
        "ticker": "AAPL",
        "name": "Apple Inc.",
        "organizationDetails": {
          "name": "Apple Inc.",
          "cik": "0000320193",
          "irsNumber": "000000000",
          "reportingFileNumber": "001-36743",
          "regulatedEntityType": "N/A",
          "sicCode": "3571",
          "address": "ONE APPLE PARK WAY CUPERTINO, CALIFORNIA 95014",
          "phoneNumber": "000-000-0000",
          "stateOfIncorporation": "CALIFORNIA",
          "fiscalYearEnd": "0925",
          "dateOfLastUpdate": "2020-10-30 16:22:35.330989",
          "createdAt": "2020-11-27T05:14:06.318+00:00",
          "sicData": {
            "sicCode": "3571",
            "industry": "Office of Technology",
            "sicTitle": "ELECTRONIC COMPUTERS"
          }
        }
      },
      {
        "cik": "0000789019",
        "ticker": "MSFT",
        "name": "MICROSOFT CORP",
        "organizationDetails": {
          "name": "MICROSOFT CORP",
          "cik": "0000789019",
          "irsNumber": "000000000",
          "reportingFileNumber": "001-37845",
          "regulatedEntityType": "N/A",
          "sicCode": "7372",
          "address": "ONE MICROSOFT WAY REDMOND, WASHINGTON 98052-6399",
          "phoneNumber": "000-000-0000",
          "stateOfIncorporation": "WASHINGTON",
          "fiscalYearEnd": "0630",
          "dateOfLastUpdate": "2019-10-23 16:09:48.757256",
          "createdAt": "2020-11-27T05:14:06.727+00:00",
          "sicData": {
            "sicCode": "7372",
            "industry": "Office of Technology",
            "sicTitle": "SERVICES-PREPACKAGED SOFTWARE"
          }
        }
      }
    ]
 ```

 Swagger Document: {base-url}/swagger-ui.html

## Installation
This a Spring Boot project and has Maven Structure. To start the application, maven commands such as `mvn -U clean install` can be used. Prerequisites:
 - Java 8+
 - Maven
 - PostgreSQL
 
## License
[GNU General Public License](https://www.gnu.org/licenses/#GPL)