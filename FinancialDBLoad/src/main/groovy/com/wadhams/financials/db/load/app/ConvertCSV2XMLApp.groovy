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
			pw.print '<financials>'
			
			suncorpDTOList.each {dto ->
				pw.print '<data>'
				
				//transactionDate
				pw.print "<dt>${dto.transactionDate}</dt>"
				
				//amount
				pw.print "<amt>${dto.amount}</amt>"
				
				pw.print "<payee>${dto.payee}</payee>"
				
				//description
				pw.print "<desc>${dto.description}</desc>"
				
				pw.print '<asset></asset>'
				
				//category
				pw.print "<cat>${dto.category}</cat>"
				
				//large transaction amounts annotate RG3
				String rg3 = ''
				if (dto.amount > 100) {
					rg3 = '$$$'
				}
				pw.print "<subcat></subcat><start></start><end></end><rg1></rg1><rg2></rg2><rg3>$rg3</rg3></data>"
			}
			pw.println '</financials>'
			pw.close()
			
			println ''
		}

	}
}
