package com.wadhams.financials.db.load.service

import com.wadhams.financials.db.load.dto.FinancialDTO

import groovy.sql.Sql

class DBRefreshService {
	DataFileService dataFileService = new DataFileService()
	SQLBuilderService sqlBuilderService = new SQLBuilderService()
	ValidationService validationService = new ValidationService()
	
	def refresh() {
		boolean invalidDataFound = false
		List<FinancialDTO> mainFinancialList = []
		
		File baseDir = new File('C:/Mongo/Financial_DB_XML_Data')
		baseDir.eachFileMatch(~/.*\.xml/) {f ->
			boolean invalidFileDataFound = false
			println "${f.name}"
			List<FinancialDTO> financialList = dataFileService.buildFinancialDTOList(f)
			financialList.each {dto ->
				if (!validationService.isValid(dto)) {
					invalidFileDataFound = true
					println "\tInvalid DTO: $dto"
				}
			}
			if (invalidFileDataFound) {
				invalidDataFound = true
			}
			else {
				println '\tNo invalid data found.'
				mainFinancialList.addAll(financialList)	//add all DTO's to main list
			}
			println ''
		}
		
		if (invalidDataFound) {
			println 'Invalid data found. Investigate each DTO above.'
			println 'No database changes were made.'
			println ''
		}
		else {
			Sql sql = Sql.newInstance('jdbc:h2:~/financial', 'sa', '', 'org.h2.Driver')
			
			//purge financials from database
			sql.execute sqlBuilderService.buildDeleteAll()
			
			//insert financials into database			
			mainFinancialList.each {dto ->
				sql.execute sqlBuilderService.buildInsert(dto)
			}
			println 'Database refresh has completed.'
			println ''
		}
	}
	
}
