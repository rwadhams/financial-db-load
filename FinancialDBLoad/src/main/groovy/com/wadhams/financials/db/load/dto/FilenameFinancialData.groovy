package com.wadhams.financials.db.load.dto

import groovy.transform.ToString

@ToString(includeNames=true)
class FilenameFinancialData {
	String filename
	List<FinancialDTO> dtoList = []
}
