package com.wadhams.financials.db.load.service

import com.wadhams.financials.db.load.dto.FinancialDTO

class InsertBuilderService {
	DataFileService dataFileService = new DataFileService()
	SQLBuilderService sqlBuilderService = new SQLBuilderService()
	ValidationService validationService = new ValidationService()
	
	def build() {
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
			println 'No output file was produced.'
			println ''
		}
		else {
			File fout = new File("out/sql-inserts.txt")
			fout.withPrintWriter {pw ->
				//output insert statements
				mainFinancialList.each {dto ->
					pw.println "${sqlBuilderService.buildInsert(dto)};"
				}
			}
		}
	}
	
}
