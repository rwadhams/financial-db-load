package com.wadhams.financials.db.load.app

import com.wadhams.financials.db.load.controller.FinancialDBLoadController
import com.wadhams.financials.db.load.type.Run

class FinancialDBLoadApp {
	static main(args) {
		println 'FinancialDBLoadApp started...'
		println ''

		if (args.size() > 0) {
			Run run = Run.findByName(args[0])
			println "Run parameter: $run" 
			println ''
			if (run == Run.Refresh || run == Run.DML) {
				FinancialDBLoadController controller = new FinancialDBLoadController()
				controller.execute(run)
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
