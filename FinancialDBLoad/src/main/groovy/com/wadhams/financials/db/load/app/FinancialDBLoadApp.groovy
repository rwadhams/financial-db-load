package com.wadhams.financials.db.load.app

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.service.InsertBuilderService

class FinancialDBLoadApp {
	static main(args) {
		println 'FinancialDBLoadApp started...'
		println ''

		if (args.size() == 0 ) {
			InsertBuilderService service = new InsertBuilderService()
			service.build()
		}
		else {
			println 'Unknown parameter. Application did not run.'
		}
		
		println ''
		println 'FinancialDBLoadApp ended.'
	}
}
