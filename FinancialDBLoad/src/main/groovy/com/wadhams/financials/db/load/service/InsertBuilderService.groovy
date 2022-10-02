package com.wadhams.financials.db.load.service

import com.wadhams.financials.db.load.dto.FinancialDTO

class InsertBuilderService {
	DataFileService dataFileService = new DataFileService()
	SQLBuilderService sqlBuilderService = new SQLBuilderService()
	
	def build() {
		File fout = new File("out/sql-inserts.txt")
		fout.withPrintWriter {pw ->
			File baseDir = new File('C:/Mongo/Financial_DB_XML_Data')
			baseDir.eachFileMatch(~/.*\.xml/) {f ->
				println "${f.name}"
				List<FinancialDTO> financialList = dataFileService.buildFinancialDTOList(f)
				financialList.each {dto ->
					println dto
				}
				println ''

				//output insert statements				
				pw.println "${f.name}"
				financialList.each {dto ->
					pw.println "${sqlBuilderService.buildInsert(dto)};"
				}
			}
		}
	}
	
}
