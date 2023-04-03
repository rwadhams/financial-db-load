package com.wadhams.financials.db.load.controller

import com.wadhams.financials.db.load.dto.FilenameFinancialData
import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.service.DBRefreshService
import com.wadhams.financials.db.load.service.DataFileService
import com.wadhams.financials.db.load.service.InsertBuilderService
import com.wadhams.financials.db.load.service.SQLBuilderService
import com.wadhams.financials.db.load.service.ValidationService
import com.wadhams.financials.db.load.type.Run

class FinancialDBLoadController {
	DataFileService dataFileService = new DataFileService()
	ValidationService validationService = new ValidationService()
	
	def execute(Run run) {
		//build
		List<FilenameFinancialData> fnfdList = dataFileService.buildFilenameFinancialDataList()
		
		//validate each file for valid financial data. Report each invalid data incident.
		boolean invalidDataFound = false
		fnfdList.each {fnfd ->
			boolean invalidFileDataFound = false
			println "${fnfd.filename}"
			fnfd.dtoList.each {dto ->
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
			}
			println ''
		}
		
		if (invalidDataFound) {
			println 'Invalid data found. Investigate each DTO above.'
			println ''
			return
		}

		//consolidate all FinancialDTO list into one list		
		List<FinancialDTO> consolidatedList = []
		fnfdList.each {fnfd ->
			consolidatedList.addAll(fnfd.dtoList)
		}

		//run the validated financial dto list based on the type of execution required
		switch (run) {
			case Run.Refresh:
				DBRefreshService service = new DBRefreshService()
				service.refresh(consolidatedList)
				break
			case Run.DML:
				InsertBuilderService service = new InsertBuilderService()
				service.build(consolidatedList)
				break
			default:
				println 'Should never happen as the run parameter has been validated.'
				println ''
		}

	}
	
}
