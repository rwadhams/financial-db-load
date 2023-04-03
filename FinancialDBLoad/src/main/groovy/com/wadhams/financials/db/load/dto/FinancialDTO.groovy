package com.wadhams.financials.db.load.dto

import com.wadhams.financials.db.common.type.Asset
import com.wadhams.financials.db.common.type.Category
import com.wadhams.financials.db.common.type.ReportGrouping
import com.wadhams.financials.db.common.type.SubCategory

import groovy.transform.ToString
import java.time.LocalDate

@ToString(includeNames=true)
class FinancialDTO {
	LocalDate transactionDate
	BigDecimal amount
	String payee
	String description
	
	Asset asset
	Category category
	SubCategory subCategory
	LocalDate startDate
	LocalDate endDate
	ReportGrouping rg1
	ReportGrouping rg2
	ReportGrouping rg3
}
