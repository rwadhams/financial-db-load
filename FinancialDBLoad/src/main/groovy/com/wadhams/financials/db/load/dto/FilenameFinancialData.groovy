package com.wadhams.financials.db.load.dto

import com.wadhams.financials.db.common.dto.FinancialDTO

import groovy.transform.ToString

@ToString(includeNames=true)
class FilenameFinancialData {
	String filename
	List<FinancialDTO> dtoList = []
}
