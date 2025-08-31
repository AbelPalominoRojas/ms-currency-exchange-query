-- USE master
-- GO

-- CREATE DATABASE db_currency_exchange
-- GO

-- USE db_currency_exchange
-- GO

CREATE TABLE customer_exchange_rates (
     id BIGINT IDENTITY(1,1) PRIMARY KEY,
     customer_dni NVARCHAR(15) NOT NULL,
     exchange_date DATE NOT NULL,
     sunat_rate DECIMAL(10,6) NOT NULL,
     buy_rate DECIMAL(10,6) NOT NULL,
     sell_rate DECIMAL(10,6) NOT NULL,

     created_at DATETIME NOT NULL DEFAULT GETDATE(),
     updated_at DATETIME
)
GO

CREATE INDEX IX_customer_exchange_rates_dni_date
    ON customer_exchange_rates (customer_dni, exchange_date)
GO
