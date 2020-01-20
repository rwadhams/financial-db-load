package com.wadhams.financials.db.load.service

import java.text.SimpleDateFormat

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.SubCategory

class InsertBuilderService {
	SimpleDateFormat db2date = new SimpleDateFormat("yyyy-MM-dd")
	 
	DataFileService dataFileService = new DataFileService()
	SQLBuilderService sqlBuilderService = new SQLBuilderService()
	
	def build() {
		File fout = new File("out/sql-inserts.txt")
		fout.withPrintWriter {pw ->
			File baseDir = new File('C:/Mongo/Financial_DB_Data')
			baseDir.eachFileMatch(~/.*\.txt/) {f ->
				println "${f.name}"
				dataFileService.verify(f)
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
