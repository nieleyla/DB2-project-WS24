install_db.sql

-- Creating MarineAnimals table
DROP TABLE IF EXISTS dbo.leniit01_MarineAnimals;
GO

CREATE TABLE dbo.leniit01_MarineAnimals (
    ID INT PRIMARY KEY IDENTITY,
    Species NVARCHAR(200) NOT NULL,
    Habitat NVARCHAR(100),
    Size INT,
    ConservationStatus NVARCHAR(50),
    LastModified DATETIME DEFAULT GETDATE()
);
GO

-- Inserting example data
INSERT INTO dbo.leniit01_MarineAnimals (Species, Habitat, Size, ConservationStatus)
VALUES ('Dumbo Octopus', 'Deep Sea', 20, 'Endangered');
GO

-- Creating a Stored Procedure to add a Marine Animal
DROP PROCEDURE IF EXISTS dbo.leniit01_AddMarineAnimal;
GO

CREATE PROCEDURE dbo.leniit01_AddMarineAnimal
    @Species NVARCHAR(200),
    @Habitat NVARCHAR(100),
    @Size INT,
    @ConservationStatus NVARCHAR(50)
AS
BEGIN
    INSERT INTO dbo.leniit01_MarineAnimals (Species, Habitat, Size, ConservationStatus)
    VALUES (@Species, @Habitat, @Size, @ConservationStatus);
END;
GO

-- Creating Trigger to update LastModified date
DROP TRIGGER IF EXISTS dbo.leniit01_UpdateLastModified;
GO

CREATE TRIGGER dbo.leniit01_UpdateLastModified
ON dbo.leniit01_MarineAnimals
AFTER INSERT, UPDATE
AS
BEGIN
    UPDATE dbo.leniit01_MarineAnimals
    SET LastModified = GETDATE()
    WHERE ID IN (SELECT ID FROM inserted);
END;
GO

-- Creating function to calculate the age of an animal record
DROP FUNCTION IF EXISTS dbo.leniit01_CalculateRecordAge;
GO

CREATE FUNCTION dbo.leniit01_CalculateRecordAge(@CreatedDate DATETIME)
RETURNS INT
AS
BEGIN
    RETURN DATEDIFF(DAY, @CreatedDate, GETDATE());
END;
GO