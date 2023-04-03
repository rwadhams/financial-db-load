package com.wadhams.financials.db.load.service

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.wadhams.financials.db.common.dto.FinancialDTO
import com.wadhams.financials.db.common.type.Asset
import com.wadhams.financials.db.common.type.Category
import com.wadhams.financials.db.common.type.ReportGrouping
import com.wadhams.financials.db.common.type.SubCategory
import com.wadhams.financials.db.load.dto.FilenameFinancialData

class DataFileService {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern('dd/MM/yyyy')
	
	List<FilenameFinancialData> buildFilenameFinancialDataList() {
		List<FilenameFinancialData> fnfdList = []
		
		File baseDir = new File('C:/Mongo/Financial_DB_XML_Data')
		baseDir.eachFileMatch(~/.*\.xml/) {f ->
			FilenameFinancialData fnfd = new FilenameFinancialData()
			fnfd.filename = f.name
			//println "${f.name}"
			fnfd.dtoList = buildFinancialDTOList(f)
			fnfdList << fnfd
		}

		return fnfdList
	}

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
	
}
