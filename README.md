# cell-backend-app

1. tables creation ( create 2 tables in mssql server)
==================
   USE [sample]
   GO

/****** Object:  Table [dbo].[CELL_TOWER]    Script Date: 28-04-2022 22:53:54 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CELL_TOWER](
[TOWER_KEY] [bigint] NOT NULL,
[CELLTOWERID] [nvarchar](50) NULL,
[BTS_ID] [nvarchar](50) NULL,
[AREADESCRIPTION] [nvarchar](300) NULL,
[SITEADDRESS] [nvarchar](300) NULL,
[LAT] [numeric](18, 5) NOT NULL,
[LONG] [numeric](18, 5) NOT NULL,
[AZIMUTH] [numeric](18, 5) NOT NULL,
[OPERATOR] [nvarchar](50) NULL,
[STATE] [nvarchar](50) NULL,
[OTYPE] [nvarchar](50) NULL,
[LASTUPDATE] [datetime2](7) NOT NULL,
[OPID] [int] NOT NULL,
[STATE_KEY] [int] NOT NULL,
[PROVIDER_KEY] [int] NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CELL_TOWER] ADD  CONSTRAINT [DF_CELL_TOWER_TOWER_KEY]  DEFAULT ('') FOR [TOWER_KEY]
GO

ALTER TABLE [dbo].[CELL_TOWER] ADD  CONSTRAINT [DF_CELL_TOWER_LAT]  DEFAULT ((0)) FOR [LAT]
GO

ALTER TABLE [dbo].[CELL_TOWER] ADD  CONSTRAINT [DF_CELL_TOWER_LONG]  DEFAULT ((0)) FOR [LONG]
GO

ALTER TABLE [dbo].[CELL_TOWER] ADD  CONSTRAINT [DF_CELL_TOWER_AZIMUTH]  DEFAULT ((0)) FOR [AZIMUTH]
GO

ALTER TABLE [dbo].[CELL_TOWER] ADD  CONSTRAINT [DF_CELL_TOWER_LASTUPDATE]  DEFAULT (getdate()) FOR [LASTUPDATE]
GO

ALTER TABLE [dbo].[CELL_TOWER] ADD  CONSTRAINT [DF_CELL_TOWER_OPID]  DEFAULT ((0)) FOR [OPID]
GO

ALTER TABLE [dbo].[CELL_TOWER] ADD  CONSTRAINT [DF_CELL_TOWER_STATE_KEY]  DEFAULT ((0)) FOR [STATE_KEY]
GO

ALTER TABLE [dbo].[CELL_TOWER] ADD  CONSTRAINT [DF_CELL_TOWER_PROVIDER_KEY]  DEFAULT ((0)) FOR [PROVIDER_KEY]
GO


2
=================================

USE [sample]
GO

/****** Object:  Table [dbo].[DB_FILE]    Script Date: 28-04-2022 22:55:25 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[DB_FILE](
[DB_FILE_ID] [bigint] NOT NULL,
[FILE_NAME] [nvarchar](100) NOT NULL,
[RECEIVED_DATE] [datetime2](7) NULL,
CONSTRAINT [PK_DB_FILE] PRIMARY KEY CLUSTERED
(
[DB_FILE_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO





