package com.wadhams.financials.db.load.app

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.service.DataBuilderService

class FinancialDBLoadApp {
	static main(args) {
		println 'FinancialDBLoadApp started...'
		println ''

		if (args.size() == 0 ) {
			DataBuilderService builderService = new DataBuilderService()
			builderService.buildAll()
		}
		else {
			println 'Unknown parameter. Application did not run.'
		}
		
		println ''
		println 'FinancialDBLoadApp ended.'
	}
}
