package com.wadhams.financials.db.load.app

import com.wadhams.financials.db.load.dto.SuncorpDTO
import com.wadhams.financials.db.load.service.DataFileService

class ConvertCSV2XMLApp {
	DataFileService dataFileService = new DataFileService()
	
	static main(args) {
		println 'ConvertCSV2XMLApp started...'
		println ''

		ConvertCSV2XMLApp app = new ConvertCSV2XMLApp()
		app.execute()
		
		println ''
		println 'ConvertCSV2XMLApp ended.'
	}
	
	def execute() {
		File baseDir = new File('C:/Mongo/Financial_DB_CSV_Data')
		baseDir.eachFileMatch(~/.*\.csv/) {f ->
			println "${f.name}"
			List<SuncorpDTO> suncorpDTOList = dataFileService.buildSuncorpDTOList(f)
			
			File fout = new File("C:/Mongo/Financial_DB_CSV_Data/${f.name- '.csv' + '.xml'}")
			PrintWriter pw = fout.newPrintWriter()
			
			suncorpDTOList.each {dto ->
				pw.print '<data>'
				
				//transactionDate
				pw.print "<dt>${dto.transactionDate}</dt>"
				
				//amount
				pw.print "<amt>${dto.amount}</amt>"
				
				pw.print "<payee>${dto.description}</payee>"
				
				//description
				pw.print "<desc>${dto.description}</desc>"
				
				pw.println '<asset></asset><cat></cat><subcat></subcat><start></start><end></end><rg1></rg1><rg2></rg2><rg3></rg3></data>'
			}
			pw.close()
			
			println ''
		}

	}
}
