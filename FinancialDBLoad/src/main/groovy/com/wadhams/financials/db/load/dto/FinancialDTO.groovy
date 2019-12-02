package com.wadhams.financials.db.load.dto

import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.SubCategory

import groovy.transform.ToString

@ToString(includeNames=true)
class FinancialDTO {
	Date transactionDate
	BigDecimal amount
	String payee
	String description
	
	Asset asset
	Category category
	SubCategory subCategory
	Date startDate
	Date endDate
}
