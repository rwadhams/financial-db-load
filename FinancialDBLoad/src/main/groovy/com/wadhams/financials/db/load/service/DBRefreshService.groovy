package com.wadhams.financials.db.load.service

import java.text.SimpleDateFormat

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.SubCategory
import groovy.sql.Sql

class DBRefreshService {
	SimpleDateFormat db2date = new SimpleDateFormat("yyyy-MM-dd")
	 
	DataFileService dataFileService = new DataFileService()
	SQLBuilderService sqlBuilderService = new SQLBuilderService()
	
	def refresh() {
		Sql sql = Sql.newInstance('jdbc:h2:~/financial', 'sa', '', 'org.h2.Driver')
		
		//purge financials from database
		sql.execute sqlBuilderService.buildDeleteAll()
			
		File baseDir = new File('C:/Mongo/Financial_DB_XML_Data')
		baseDir.eachFileMatch(~/.*\.txt/) {f ->
			println "${f.name}"
			List<FinancialDTO> financialList = dataFileService.buildList(f)
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
