package com.wadhams.financials.db.load.service

import java.text.SimpleDateFormat

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.SubCategory
import com.wadhams.financials.db.load.type.ReportGrouping

class SQLBuilderService {
	SimpleDateFormat db2date = new SimpleDateFormat("yyyy-MM-dd")
	 
	String buildInsert(FinancialDTO dto) {
		StringBuilder sb = new StringBuilder()
		sb.append('INSERT INTO FINANCIAL(TRANSACTION_DT, AMOUNT, PAYEE, DESCRIPTION, ASSET, CATEGORY, SUB_CATEGORY, START_DT, END_DT, RPT_GRP_1, RPT_GRP_2, RPT_GRP_3) ')
		sb.append('VALUES(')
		
		sb.append("'${db2date.format(dto.transactionDate)}', ")

		sb.append("${dto.amount.toString()}, ")
		
		String p = dto.payee.replace('\'', '')
		sb.append("'$p', ")
		
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
			sb.append("'${db2date.format(dto.endDate)}', ")
		}
		else {
			sb.append('null, ')
		}
		
		//reporting group 1
		if (dto.rg1 == ReportGrouping.Unknown) {
			sb.append('null, ')
		}
		else {
			sb.append("'${dto.rg1.dbValue}', ")
		}
				
		//reporting group 2
		if (dto.rg2 == ReportGrouping.Unknown) {
			sb.append('null, ')
		}
		else {
			sb.append("'${dto.rg2.dbValue}', ")
		}
				
		//reporting group 3
		if (dto.rg3 == ReportGrouping.Unknown) {
			sb.append('null')
		}
		else {
			sb.append("'${dto.rg3.dbValue}'")
		}
				
		sb.append(')')
		
		return sb.toString()
	}
	
	String buildDeleteAll() {
		StringBuilder sb = new StringBuilder()
		
		sb.append('DELETE FROM FINANCIAL')
		
		return sb.toString()
	}
	
}
