-- Creating MarineAnimals table
DROP TABLE IF EXISTS dbo.leniit01_Corals;
GO

CREATE TABLE dbo.leniit01_Corals (
    ID INT PRIMARY KEY IDENTITY,           
    Name NVARCHAR(100) NOT NULL,           
    Region NVARCHAR(100),                  
    RecoveryStatus NVARCHAR(50),           
    LastModified DATETIME DEFAULT GETDATE() 
);
GO

-- Inserting example data
INSERT INTO dbo.leniit01_Corals (Name, Region, RecoveryStatus)
VALUES 
('Staghorn Coral', 'Caribbean Sea', 'Critically Endangered'),
('Elkhorn Coral', 'Gulf of Mexico', 'Critically Endangered'),
('Brain Coral', 'Great Barrier Reef', 'Vulnerable'),
('Fire Coral', 'Red Sea', 'Least Concern'),
('Mushroom Coral', 'Coral Triangle', 'Least Concern');
GO

-- Creating a Stored Procedure to add a Coral
DROP PROCEDURE IF EXISTS dbo.leniit01_AddCoral;
GO

CREATE PROCEDURE dbo.leniit01_AddCoral
    @Name NVARCHAR(200),
    @Region NVARCHAR(100),
    @RecoveryStatus NVARCHAR(50)
AS
BEGIN
    INSERT INTO dbo.leniit01_Corals (Name, Region, RecoveryStatus)
    VALUES (@Name, @Region, @RecoveryStatus);
END;
GO

-- Creating Trigger to update LastModified date
DROP TRIGGER IF EXISTS dbo.leniit01_UpdateLastModified;
GO

CREATE TRIGGER dbo.leniit01_UpdateLastModified
ON dbo.leniit01_Corals
AFTER INSERT, UPDATE
AS
BEGIN
    UPDATE dbo.leniit01_Corals
    SET LastModified = GETDATE()
    WHERE ID IN (SELECT ID FROM inserted);
END;
GO