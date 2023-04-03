package com.wadhams.financials.db.load.service

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.common.type.Asset
import com.wadhams.financials.db.common.type.Category
import com.wadhams.financials.db.common.type.ReportGrouping
import com.wadhams.financials.db.common.type.SubCategory

class ValidationService {
	boolean isValid(FinancialDTO dto) {
		//Asset
		if (dto.asset == Asset.Invalid) {
			return false
		}
		
		//Category
		if (dto.category == Category.Invalid) {
			return false
		}
		
		//SubCategory
		if (dto.subCategory == SubCategory.Invalid) {
			return false
		}
		
		//Reporting Group type (1)
		if (dto.rg1 == ReportGrouping.Invalid) {
			return false
		}
		
		//Reporting Group type (2)
		if (dto.rg2 == ReportGrouping.Invalid) {
			return false
		}
		
		//Reporting Group type (3)
		if (dto.rg3 == ReportGrouping.Invalid) {
			return false
		}
		
		return true
	}
}
