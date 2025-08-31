# ms-currency-exchange-query

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## 1. Database Setup

Before running the application, execute the following SQL Server script to create the necessary database tables:
`database/CurrencyExchange_Table_SQLServer.sql`

## 2. Configure Database Connection
Update the `src/main/resources/application.yml` file with your database connection details:

```yaml 
database-server: localhost                # Your database server address
database-port: 1433                       # Your database port
database-name: your_database_name         # Your database name
database-username: your_database_username # Your database username
database-password: your_database_password # Your database password
```
## 3. Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## 4. Testing the API with Postman
You can use the provided Postman collection to test the API endpoints. Import the `postman/Quarkus-CurrencyExchange.postman_collection.json` file into Postman.
