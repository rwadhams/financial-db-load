package com.wadhams.financials.db.load.service

import java.text.SimpleDateFormat

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.SubCategory

class DBRefreshService {
	SimpleDateFormat db2date = new SimpleDateFormat("yyyy-MM-dd")
	 
	DataFileService dataFileService = new DataFileService()
	
	def refresh() {
		//TODO purge financials from database
			
		File baseDir = new File('C:/Mongo/Financial_DB_Data')
		baseDir.eachFileMatch(~/.*\.txt/) {f ->
			println "${f.name}"
			dataFileService.verify(f)
			List<FinancialDTO> financialList = dataFileService.buildList(f)
			financialList.each {dto ->
				println dto
			}
			println ''

			//TODO insert financials into database			
			financialList.each {dto ->
				//code here...
			}
		}
	}
	
	String buildInsert(FinancialDTO dto) {
		StringBuilder sb = new StringBuilder()
		sb.append('INSERT INTO FINANCIAL(TRANSACTION_DT, AMOUNT, PAYEE, DESCRIPTION, ASSET, CATEGORY, SUB_CATEGORY, START_DT, END_DT) ')
		sb.append('VALUES(')
		
		sb.append("'${db2date.format(dto.transactionDate)}', ")

		sb.append("${dto.amount.toString()}, ")
		
		sb.append("'${dto.payee}', ")
		
		//description
		if (dto.description) {
			sb.append("'${dto.description}', ")
		}
		else {
			sb.append('null, ')
		}
		
		//asset
		if (dto.asset == Asset.Unknown) {
			sb.append('null, ')
		}
		else {
			sb.append("'${dto.asset.dbValue}', ")
		}

		//category
		if (dto.category == Category.Unknown) {
			sb.append('null, ')
		}
		else {
			sb.append("'${dto.category.dbValue}', ")
		}

		//subCategory
		if (dto.subCategory == SubCategory.Unknown) {
			sb.append('null, ')
		}
		else {
			sb.append("'${dto.subCategory.dbValue}', ")
		}
				
		//startDate
		if (dto.startDate) {
			sb.append("'${db2date.format(dto.startDate)}', ")
		}
		else {
			sb.append('null, ')
		}

		//endDate
		if (dto.endDate) {
			sb.append("'${db2date.format(dto.endDate)}'")
		}
		else {
			sb.append('null')
		}
		
		sb.append(');')
		
		return sb.toString()
	}
	
}
