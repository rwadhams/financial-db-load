package com.wadhams.financials.db.load.service

import com.wadhams.financials.db.load.dto.FinancialDTO

class InsertBuilderService {
	SQLBuilderService sqlBuilderService = new SQLBuilderService()
	
	def build(List<FinancialDTO> dtoList) {
		File fout = new File("out/sql-inserts.txt")
		fout.withPrintWriter {pw ->
			//output insert statements
			dtoList.each {dto ->
				pw.println "${sqlBuilderService.buildInsert(dto)};"
			}
		}
	}
	
}
