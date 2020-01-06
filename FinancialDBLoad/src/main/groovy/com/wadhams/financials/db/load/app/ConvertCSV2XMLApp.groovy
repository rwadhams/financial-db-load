package com.wadhams.financials.db.load.app

class ConvertCSV2XMLApp {
	static main(args) {
		println 'ConvertCSV2XMLApp started...'
		println ''

		File baseDir = new File('C:/Mongo/Financial_DB_CSV_Data')
		baseDir.eachFileMatch(~/.*\.csv/) {f ->
			println "${f.name}"
			f.eachLine {line ->
				//println line
				def sa = line.split(/,/)
				assert sa.size() == 4

				print '<data>'
				
				//transactionDate
				print "<dt>${sa[0].substring(1,sa[0].size()-1)}</dt>"
				
				//amount
				print "<amt>${sa[2].substring(1,sa[2].size()-1)}</amt>"
				
				print "<payee></payee>"
				
				//description
				print "<desc>${sa[1].substring(1,sa[1].size()-1)}</desc>"
				
				println '<asset></asset><cat></cat><subcat></subcat><start></start><end></end><rg1></rg1><rg2></rg2><rg3></rg3></data>'
			}
			println ''
		}
		
		println ''
		println 'ConvertCSV2XMLApp ended.'
	}
}
