package com.wadhams.financials.db.load.service

import java.text.SimpleDateFormat

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.ReportGrouping
import com.wadhams.financials.db.load.type.SubCategory

class DataFileService {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
	
	List<FinancialDTO> buildList(File file) {
		List<FinancialDTO> financialList = []

		file.eachLine {line ->
			println line
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
}
