package com.wadhams.financials.db.load.service

import java.time.format.DateTimeFormatter

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.ReportGrouping
import com.wadhams.financials.db.load.type.SubCategory

class SQLBuilderService {
	DateTimeFormatter h2dtf = DateTimeFormatter.ofPattern('yyyy-MM-dd')
	
	String buildInsert(FinancialDTO dto) {
		StringBuilder sb = new StringBuilder()
		sb.append('INSERT INTO FINANCIAL(TRANSACTION_DT, AMOUNT, PAYEE, DESCRIPTION, ASSET, CATEGORY, SUB_CATEGORY, START_DT, END_DT, RPT_GRP_1, RPT_GRP_2, RPT_GRP_3) ')
		sb.append('VALUES(')
		
		sb.append("'${h2dtf.format(dto.transactionDate)}', ")

		sb.append("${dto.amount.toString()}, ")
		
		String p = dto.payee.replace('\'', '')
		sb.append("'$p', ")
		
		//description
		if (dto.description) {
			String d = dto.description.replace('\'', '')
			sb.append("'$d', ")
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
			sb.append("'${h2dtf.format(dto.startDate)}', ")
		}
		else {
			sb.append('null, ')
		}

		//endDate
		if (dto.endDate) {
			sb.append("'${h2dtf.format(dto.endDate)}', ")
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
	
	String buildDistinctPayeeCountOne() {
		StringBuilder sb = new StringBuilder()
		
		sb.append('SELECT PAYEE AS PAYEE ')
		sb.append('FROM FINANCIAL ')
		sb.append('GROUP BY PAYEE ')
		sb.append('HAVING COUNT(PAYEE) = 1 ')
		sb.append('ORDER BY 1')
		
		return sb.toString()
	}
	
	String buildDistinctPayeeCountTwoPlus() {
		StringBuilder sb = new StringBuilder()
		
		sb.append('SELECT PAYEE AS PAYEE, COUNT(PAYEE) AS COUNT ')
		sb.append('FROM FINANCIAL ')
		sb.append('GROUP BY PAYEE ')
		sb.append('HAVING COUNT(PAYEE) > 1 ')
		sb.append('ORDER BY 1')
		
		return sb.toString()
	}
	
	String buildDistinctCategorySubCategorySelect() {
		StringBuilder sb = new StringBuilder()
		
		sb.append('SELECT DISTINCT CATEGORY AS CAT, SUB_CATEGORY AS SUBCAT ')
		sb.append('FROM FINANCIAL ')
		sb.append('ORDER BY 1,2')
		
		return sb.toString()
	}
	
	String buildDistinctReportGrouping1Select() {
		StringBuilder sb = new StringBuilder()
		
		sb.append('SELECT DISTINCT RPT_GRP_1 AS RG1 ')
		sb.append('FROM FINANCIAL ')
		sb.append('WHERE RPT_GRP_1 IS NOT NULL ')
		sb.append('ORDER BY 1')
		
		return sb.toString()
	}
	
	String buildAssetSelectWithoutReportGrouping() {
		StringBuilder sb = new StringBuilder()
		
		sb.append('SELECT PAYEE AS PAYEE, DESCRIPTION AS DESC, ASSET AS ASSET, CATEGORY AS CAT, SUB_CATEGORY AS SUBCAT ')
		sb.append('FROM FINANCIAL ')
		sb.append('WHERE ASSET IS NOT NULL ')
		sb.append('AND RPT_GRP_1 IS NULL ')
		sb.append('ORDER BY ASSET, CATEGORY, SUB_CATEGORY')
		
		return sb.toString()
	}
	
	String buildSpecificRunningCostSelect() {
		StringBuilder sb = new StringBuilder()
		
		sb.append('SELECT PAYEE AS PAYEE, DESCRIPTION AS DESC, ASSET AS ASSET, CATEGORY AS CAT, START_DT AS START, END_DT AS END ')
		sb.append('FROM FINANCIAL ')
		sb.append('WHERE RPT_GRP_1 = \'SPECIFIC_RUNNING_COST\' ')
		sb.append('ORDER BY ASSET, CATEGORY, START_DT')
		
		return sb.toString()
	}
	
	String buildOngoingRunningCostSelect() {
		StringBuilder sb = new StringBuilder()
		
		sb.append('SELECT PAYEE AS PAYEE, DESCRIPTION AS DESC, ASSET AS ASSET, CATEGORY AS CAT, START_DT AS START, END_DT AS END ')
		sb.append('FROM FINANCIAL ')
		sb.append('WHERE RPT_GRP_1 = \'ONGOING_RUNNING_COST\' ')
		sb.append('ORDER BY ASSET, CATEGORY, START_DT')
		
		return sb.toString()
	}
	
}
