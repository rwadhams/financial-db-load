package com.wadhams.financials.db.load.app

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.service.DBRefreshService
import com.wadhams.financials.db.load.service.InsertBuilderService
import com.wadhams.financials.db.load.type.Run
class FinancialDBLoadApp {
	static main(args) {
		println 'FinancialDBLoadApp started...'
		println ''

		if (args.size() == 1) {
			Run run = Run.findByName(args[0])
			println "Run parameter: $run" 
			println ''
			if (run == Run.DML) {
				InsertBuilderService service = new InsertBuilderService()
				service.build()
			}
			else if (run == Run.Refresh) {
				DBRefreshService service = new DBRefreshService()
				service.refresh()
			}
			else {
				println 'Unknown parameter. Application did not run.'
			}
		}
		else {
			println 'Missing parameter(s). Application did not run.'
		}
		
		println ''
		println 'FinancialDBLoadApp ended.'
	}
}
