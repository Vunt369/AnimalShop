USE master

CREATE DATABASE PRM392DB;

-- Use the database
USE PRM392DB;
ALTER DATABASE PRM392DB COLLATE SQL_Latin1_General_CP1_CI_AS;
CREATE TABLE [Role] (
	[RoleId] INT NOT NULL IDENTITY UNIQUE,
	[RName] NVARCHAR(255) NOT NULL,
	[Description] NVARCHAR(MAX),
	[CreateAt] DATETIME,
	PRIMARY KEY([RoleId])
);
GO
INSERT INTO [Role] ([RName], [Description], [CreateAt])
VALUES 
    ('Admin', 'Administrator role with full access', GETDATE()),
    ('Staff', 'Staff role with specific duties and permissions', GETDATE()),
    ('Manager', 'Manager role with permissions to manage resources', GETDATE()),
    ('Customer', 'Customer role with read-only access and buy items', GETDATE());
GO
CREATE TABLE [Users] (
	[UserId] INT NOT NULL IDENTITY UNIQUE,
	[Username] NVARCHAR(255) NOT NULL,
	[Password] NVARCHAR(255) NOT NULL,
	[FullName] NVARCHAR(255),
	[Gender] NVARCHAR(255),
	[Email] NVARCHAR(255),
	[Phone] NVARCHAR(255),
	[CreateAt] DATETIME,
	[RoleId] INT,
	PRIMARY KEY([UserId])
);
GO

CREATE TABLE [Categories] (
	[CategoryId] INT NOT NULL IDENTITY UNIQUE,
	[CName] NVARCHAR(255) NOT NULL,
	[Description] NVARCHAR(MAX),
	[CreateAt] DATETIME,
	PRIMARY KEY([CategoryId])
);
GO
INSERT INTO [Categories] ([CName], [Description], [CreateAt])
VALUES 
    (N'Thức ăn', N'Sản phẩm thức ăn và đồ ăn vặt cho thú cưng', GETDATE()),
    (N'Đồ chơi', N'Đồ chơi cho thú cưng', GETDATE()),
    (N'Sức khỏe', N'Sản phẩm chăm sóc sức khỏe cho thú cưng', GETDATE()),
    (N'Phụ kiện', N'Phụ kiện cho thú cưng như vòng cổ, dây dắt, v.v.', GETDATE()),
    (N'Chăm sóc', N'Dụng cụ chăm sóc lông và vệ sinh cho thú cưng', GETDATE());
GO

CREATE TABLE [Products] (
	[ProductId] INT NOT NULL IDENTITY UNIQUE,
	[PName] NVARCHAR(255) NOT NULL,
	[Description ] NVARCHAR(MAX),
	[Price] DECIMAL NOT NULL,
	[Quantity] BIGINT,
	[CategoryId] INT,
	[CreateAt] DATETIME,
	[ImageUrl] NVARCHAR(MAX)
	PRIMARY KEY([ProductId])
);
GO

CREATE TABLE [Carts] (
	[CartId] INT NOT NULL IDENTITY UNIQUE,
	[UserId] INT NOT NULL,
	PRIMARY KEY([CartId], [UserId])
);
GO

CREATE TABLE [CartItems] (
	[Id] INT NOT NULL IDENTITY UNIQUE,
	[ProductId] INT NOT NULL,
	[Quantity] INT,
	[TotalPrice] DECIMAL,
	[CartId] INT,
	[Status] BIT,
	PRIMARY KEY([Id])
);
GO
CREATE TABLE [Orders] (
    [OrderId] INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    [UserId] INT,
    [IntoMoney] DECIMAL,
    [TotalPrice] DECIMAL,
    [OrderCode] NVARCHAR(255),
    [ShipmentDetailId] INT UNIQUE,
    [Status] INT,
    [TranSportFee] DECIMAL,
    [CreateAt] DATETIME
);
GO
CREATE TABLE [ShipmentDetails] (
     [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    [FullName] NVARCHAR(255),
    [Address] NVARCHAR(MAX),
    [PhoneNumber] NVARCHAR(255),
    [UserId] INT NOT NULL,
    [OrderId] INT, -- Add OrderId to ShipmentDetails to establish the relationship
    FOREIGN KEY ([OrderId]) REFERENCES [Orders]([OrderId])	
);
GO
CREATE TABLE [OrderDetails] (
    [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    [ProductId] INT,
    [Quantity] INT,
    [UnitPrice] DECIMAL,
    [OrderId] INT,
    FOREIGN KEY ([OrderId]) REFERENCES [Orders]([OrderId])
);
GO

ALTER TABLE [Products]
ADD FOREIGN KEY([CategoryId]) REFERENCES [Categories]([CategoryId])
ON UPDATE NO ACTION ON DELETE NO ACTION;
GO
ALTER TABLE [Users]
ADD FOREIGN KEY([RoleId]) REFERENCES [Role]([RoleId])
ON UPDATE NO ACTION ON DELETE NO ACTION;
GO
ALTER TABLE [CartItems]
ADD FOREIGN KEY([CartId]) REFERENCES [Carts]([CartId])
ON UPDATE NO ACTION ON DELETE NO ACTION;
GO
ALTER TABLE [Carts]
ADD FOREIGN KEY([UserId]) REFERENCES [Users]([UserId])
ON UPDATE NO ACTION ON DELETE NO ACTION;
GO
ALTER TABLE [ShipmentDetails]
ADD FOREIGN KEY([UserId]) REFERENCES [Users]([UserId])
ON UPDATE NO ACTION ON DELETE NO ACTION;
GO
ALTER TABLE [OrderDetails]
ADD FOREIGN KEY([OrderId]) REFERENCES [Orders]([OrderId])
ON UPDATE NO ACTION ON DELETE NO ACTION;
GO
ALTER TABLE [Orders]
ADD FOREIGN KEY([UserId]) REFERENCES [Users]([UserId])
ON UPDATE NO ACTION ON DELETE NO ACTION;
GO
ALTER TABLE [OrderDetails]
ADD FOREIGN KEY([ProductId]) REFERENCES [Products]([ProductId])
ON UPDATE NO ACTION ON DELETE NO ACTION;
GO
ALTER TABLE [CartItems]
ADD FOREIGN KEY([ProductId]) REFERENCES [Products]([ProductId])
ON UPDATE NO ACTION ON DELETE NO ACTION;
GO
