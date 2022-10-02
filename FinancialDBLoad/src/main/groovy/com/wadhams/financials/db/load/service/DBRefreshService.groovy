package com.wadhams.financials.db.load.service

import com.wadhams.financials.db.load.dto.FinancialDTO

import groovy.sql.Sql

class DBRefreshService {
	DataFileService dataFileService = new DataFileService()
	SQLBuilderService sqlBuilderService = new SQLBuilderService()
	
	def refresh() {
		Sql sql = Sql.newInstance('jdbc:h2:~/financial', 'sa', '', 'org.h2.Driver')
		
		//purge financials from database
		sql.execute sqlBuilderService.buildDeleteAll()
			
		File baseDir = new File('C:/Mongo/Financial_DB_XML_Data')
		baseDir.eachFileMatch(~/.*\.xml/) {f ->
			println "${f.name}"
			List<FinancialDTO> financialList = dataFileService.buildFinancialDTOList(f)
			financialList.each {dto ->
				println dto
			}
			println ''

			//insert financials into database			
			financialList.each {dto ->
				sql.execute sqlBuilderService.buildInsert(dto)
			}
		}
	}
	
}
