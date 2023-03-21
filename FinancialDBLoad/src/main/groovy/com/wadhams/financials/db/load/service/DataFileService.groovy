package com.wadhams.financials.db.load.service

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Matcher
import java.util.regex.Pattern

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.dto.SuncorpDTO
import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.ReportGrouping
import com.wadhams.financials.db.load.type.SubCategory

class DataFileService {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern('dd/MM/yyyy')
	
	List<FinancialDTO> buildFinancialDTOList(File xmlFile) {
		List<FinancialDTO> financialList = []

		def financials = new XmlSlurper().parse(xmlFile)
		
		def transactions = financials.data
		
		transactions.each {txn ->
			//println txn
			
			FinancialDTO dto = new FinancialDTO()
			
			//transactionDate
			LocalDate d = LocalDate.parse(txn.dt.text(), dtf)
//			println d
			dto.transactionDate = d
			
			//amount
			BigDecimal bd = new BigDecimal(txn.amt.text())
//			println bd
			dto.amount = bd
			
			dto.payee = txn.payee.text().trim()
			
			dto.description = txn.desc.text().trim()

			//Asset type
			Asset a = Asset.findByName(txn.asset.text())
			dto.asset = a
			
			//Category type
			Category c = Category.findByName(txn.cat.text())
			dto.category = c
			
			//SubCategory type
			SubCategory sc = SubCategory.findByName(txn.subcat.text())
			dto.subCategory = sc
			
			//Duration
			String start = txn.start.text()
			String end = txn.end.text()
			if (start && end) {
				dto.startDate = LocalDate.parse(start, dtf)
				dto.endDate = LocalDate.parse(end, dtf)
			}
			
			//Reporting Group type (1)
			ReportGrouping rg1 = ReportGrouping.findByName(txn.rg1.text())
			dto.rg1 = rg1
			
			//Reporting Group type (2)
			ReportGrouping rg2 = ReportGrouping.findByName(txn.rg2.text())
			dto.rg2 = rg2
			
			//Reporting Group type (3)
			ReportGrouping rg3 = ReportGrouping.findByName(txn.rg3.text())
			dto.rg3 = rg3
			
			//println dto			
			financialList << dto
		}

		return financialList
	}
	
	List<SuncorpDTO> buildSuncorpDTOList(File csvFile) {
		Pattern linePattern = ~/"(.*?)","(.*?)","(.*?)","(.*?)"/
		
		Pattern visaPurchasePattern = ~/VISA PURCHASE(.*)\d\d\/\d\d.*[A|U][U|S]D/	//ends in AUD or USD
		//Pattern visaPurchasePattern = ~/VISA PURCHASE(.*)\d\d\/\d\d.*AUD/
		Pattern visaCreditPattern = ~/VISA CREDIT(.*)\d\d\/\d\d.*AUD/
		Pattern wdlPattern  = ~/EFTPOS WDL(.*)AU/
		Pattern depPattern  = ~/EFTPOS DEP(.*)A?U?/
		Pattern bpayPattern = ~/BPAY DEBIT VIA INTERNET(.*)REFERENCE NUMBER.*/
		//Pattern ddPattern  = ~/DIRECT DEBIT(.*)\d{12}/	//DIRECT DEBIT    ORIGIN GAS 052606044487
		Pattern ddPattern  = ~/DIRECT DEBIT(.*)/
		Pattern foreignPattern  = ~/FOREIGN CURRENCY CONVERSION FEE/
		
		NumberFormat cf = NumberFormat.getCurrencyInstance()
		NumberFormat nf = NumberFormat.getNumberInstance()
		nf.setMinimumIntegerDigits(1)
		nf.setMinimumFractionDigits(2)
		nf.setMaximumFractionDigits(2)
		nf.setGroupingUsed(false)
		
		List<SuncorpDTO> suncorpList = []

		csvFile.eachLine {line ->
			println line
			line = line.replaceAll(/&/, '&amp;')
			Matcher lineMatcher = line =~ linePattern
			String regexDate = lineMatcher[0][1]
			String regexDesc = lineMatcher[0][2]
			String regexAmt = lineMatcher[0][3]
			
			SuncorpDTO dto = new SuncorpDTO()
			
			//transactionDate
			dto.transactionDate = regexDate
			
			//amount
			Number number = cf.parse(regexAmt)
			BigDecimal bd = new BigDecimal(nf.format(number)).negate()	//change sign
			//println bd
			dto.amount = bd
			
			Matcher descMatcher = null
			String suncorpDescription = regexDesc
			String parsedDescription = ''
			if (suncorpDescription.startsWith('VISA PURCHASE')) {
				descMatcher = suncorpDescription =~ visaPurchasePattern
				parsedDescription = descMatcher[0][1].trim()
			}
			else if (suncorpDescription.startsWith('EFTPOS WDL')) {
				descMatcher = suncorpDescription =~ wdlPattern
				parsedDescription = descMatcher[0][1].trim()
			}
			else if (suncorpDescription.startsWith('EFTPOS DEP')) {
				descMatcher = suncorpDescription =~ depPattern
				parsedDescription = descMatcher[0][1].trim()
			}
			else if (suncorpDescription.startsWith('VISA CREDIT')) {
				descMatcher = suncorpDescription =~ visaCreditPattern
				parsedDescription = descMatcher[0][1].trim()
			}
			else if (suncorpDescription.startsWith('BPAY DEBIT VIA INTERNET')) {
				descMatcher = suncorpDescription =~ bpayPattern
				parsedDescription = descMatcher[0][1].trim()
			}
			else if (suncorpDescription.startsWith('DIRECT DEBIT')) {
				descMatcher = suncorpDescription =~ ddPattern
				parsedDescription = descMatcher[0][1].trim()
			}
			else if (suncorpDescription.startsWith('ATM WITHDRAWAL')) {
				parsedDescription = suncorpDescription.trim()
			}
			else if (suncorpDescription.startsWith('INTERNET EXTERNAL TRANSFER')) {
				parsedDescription = 'External Transfer'
			}
			else if (suncorpDescription.equals('FOREIGN CURRENCY CONVERSION FEE')) {
				parsedDescription = 'Currency Conversion Fee'
			}
			else if (suncorpDescription.startsWith('INTERNET TRANSFER CREDIT')) {
				return	//return from closure
			}
			else if (suncorpDescription.startsWith('INTERNET TRANSFER DEBIT')) {
				return	//return from closure
			}
			else if (suncorpDescription.startsWith('CREDIT INTEREST')) {
				return	//return from closure
			}
			else {
				println "ZZZZZZZZZZZ Unparsed description: $suncorpDescription"
				parsedDescription = suncorpDescription.trim()
			}
			//println parsedDescription
			derivedValuesFromDescription(parsedDescription, dto)
			
			//println dto
			//println ''			
			suncorpList << dto
		}

		return suncorpList
	}
	
	def derivedValuesFromDescription(String parsedDescription, SuncorpDTO dto) {
		if (parsedDescription.matches(~/COLES \d\d\d\d.*/)) {
			dto.payee = 'COLES'
			dto.description = 'Groceries'
			dto.category = 'FOOD'
		}
		else if (parsedDescription.matches(~/WOOLWORTHS.*/)) {
			dto.payee = 'WOOLWORTHS'
			dto.description = 'Groceries'
			dto.category = 'FOOD'
		}
		else if (parsedDescription.matches(~/KMART.*/)) {
			dto.payee = 'KMART'
			dto.description = 'Caravan wares'
			dto.category = 'CARAVAN_EQUIPMENT'
		}
		else if (parsedDescription.matches(~/BUNNINGS.*/)) {
			dto.payee = 'BUNNINGS'
			dto.description = 'Caravan wares'
			dto.category = 'CARAVAN_EQUIPMENT'
		}
		else if (parsedDescription.matches(~/DAN MURPHY.*/)) {
			dto.payee = 'DAN MURPHYS'
			dto.description = 'Beer &amp; Wine'
			dto.category = 'ALCOHOL'
		}
		else if (parsedDescription.matches(~/1ST CHOICE.*/)) {
			dto.payee = '1ST CHOICE LIQUOR'
			dto.description = 'Beer &amp; Wine'
			dto.category = 'ALCOHOL'
		}
		else if (parsedDescription.matches(~/COLES EXPRESS.*/)) {
			dto.payee = 'COLES EXPRESS'
			dto.description = 'Fill-up'
			dto.category = 'FUEL'
		}
		else if (parsedDescription.matches(~/Belong.*/)) {
			dto.payee = 'BELONG MOBILE'
			dto.description = 'Cell phone'
			dto.category = 'PHONE_PLAN_ROB'
		}
		else if (parsedDescription.matches(~/Telstra.*/)) {
			dto.payee = 'TELSTRA'
			dto.description = 'Wifi data sim | Cell phone'
			dto.category = 'DATA_PLAN | PHONE_PLAN_MOLLY'
		}
		else if (parsedDescription.matches(~/APPLE.*/)) {
			dto.payee = 'APPLE'
			dto.description = 'Cloud Storage'
			dto.category = 'CLOUD_STORAGE'
		}
		else {
			dto.payee = 'N/A'
			dto.description = parsedDescription
			dto.category = ''
		}
	}
}
