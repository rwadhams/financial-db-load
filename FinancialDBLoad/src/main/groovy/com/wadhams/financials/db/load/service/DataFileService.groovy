package com.wadhams.financials.db.load.service

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.regex.Matcher
import java.util.regex.Pattern
import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.dto.SuncorpDTO
import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.ReportGrouping
import com.wadhams.financials.db.load.type.SubCategory

class DataFileService {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
	
	List<FinancialDTO> buildFinancialDTOList(File file) {
		List<FinancialDTO> financialList = []

		file.eachLine {line ->
			//println line
			def xml= new XmlSlurper().parseText(line)
			
			FinancialDTO dto = new FinancialDTO()
			
			//transactionDate
			Date d = sdf.parse(xml.dt.text())
//			println d
			dto.transactionDate = d
			
			//amount
			BigDecimal bd = new BigDecimal(xml.amt.text())
//			println bd
			dto.amount = bd
			
			dto.payee = xml.payee.text().trim()
			
			dto.description = xml.desc.text().trim()

			//Asset type
			Asset a = Asset.findByName(xml.asset.text())
			dto.asset = a
			
			//Category type
			Category c = Category.findByName(xml.cat.text())
			dto.category = c
			
			//SubCategory type
			SubCategory sc = SubCategory.findByName(xml.subcat.text())
			dto.subCategory = sc
			
			//Duration
			String start = xml.start.text()
			String end = xml.end.text()
			if (start && end) {
				dto.startDate = sdf.parse(start)
				dto.endDate = sdf.parse(end)
			}
			
			//Reporting Group type (1)
			ReportGrouping rg1 = ReportGrouping.findByName(xml.rg1.text())
			dto.rg1 = rg1
			
			//Reporting Group type (2)
			ReportGrouping rg2 = ReportGrouping.findByName(xml.rg2.text())
			dto.rg2 = rg2
			
			//Reporting Group type (3)
			ReportGrouping rg3 = ReportGrouping.findByName(xml.rg3.text())
			dto.rg3 = rg3
			
			//println dto			
			financialList << dto
		}

		return financialList
	}
	
	List<SuncorpDTO> buildSuncorpDTOList(File file) {
		Pattern linePattern = ~/"(.*?)","(.*?)","(.*?)","(.*?)"/
		
		Pattern visaPurchasePattern = ~/VISA PURCHASE(.*)\d\d\/\d\d.*AUD/
		Pattern visaCreditPattern = ~/VISA CREDIT(.*)\d\d\/\d\d.*AUD/
		Pattern wdlPattern  = ~/EFTPOS WDL(.*)AU/
		Pattern depPattern  = ~/EFTPOS DEP(.*)AU/
		Pattern bpayPattern = ~/BPAY DEBIT VIA INTERNET(.*)REFERENCE NUMBER.*/
		
		NumberFormat cf = NumberFormat.getCurrencyInstance()
		NumberFormat nf = NumberFormat.getNumberInstance()
		nf.setMaximumFractionDigits(2)
		nf.setGroupingUsed(false)
		
		List<SuncorpDTO> suncorpList = []

		file.eachLine {line ->
			println line
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
			else if (suncorpDescription.startsWith('ATM WITHDRAWAL')) {
				parsedDescription = suncorpDescription.trim()
			}
			else if (suncorpDescription.startsWith('INTERNET TRANSFER CREDIT')) {
				return	//return from closure
			}
			else {
				println "ZZZZZZZZZZZ Unparsed description: $suncorpDescription"
				parsedDescription = suncorpDescription.trim()
			}
			//println parsedDescription
			dto.description = parsedDescription
			
			//println dto
			//println ''			
			suncorpList << dto
		}

		return suncorpList
	}
}
