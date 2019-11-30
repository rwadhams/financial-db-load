package com.wadhams.financials.db.load.service

import java.text.SimpleDateFormat

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.SubCategory

class DataBuilderService {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
	SimpleDateFormat db2date = new SimpleDateFormat("yyyy-MM-dd")
	
	def buildAll() {
		File fout = new File("out/sql-inserts.txt")
		fout.withPrintWriter {pw ->
			File baseDir = new File('C:/Mongo/Financial_DB_Data')
			baseDir.eachFileMatch(~/.*\.txt/) {f ->
				println "${f.name}"
				verify(f)
				List<FinancialDTO> financialList = build(f)
				financialList.each {dto ->
					println dto
				}
				println ''

				//output insert statements				
				pw.println "${f.name}"
				financialList.each {dto ->
					pw.println buildInsert(dto)
				}
			}
		}
	}
	
	String buildInsert(FinancialDTO dto) {
		StringBuilder sb = new StringBuilder()
		sb.append('INSERT INTO FINANCIAL(TRANSACTION_DT, AMOUNT, PAYEE, DESCRIPTION, ASSET, CATEGORY, SUB_CATEGORY, START_DT, END_DT) ')
		sb.append('VALUES(')
		
		sb.append("'${db2date.format(dto.transactionDate)}', ")

		sb.append("${dto.amount.toString()}, ")
		
		sb.append("'${dto.payee}', ")
		
		//description
		if (dto.description) {
			sb.append("'${dto.description}', ")
		}
		else {
			sb.append('null, ')
		}
		
		//asset
		if (dto.asset == Asset.Unknown) {
			sb.append('null, ')
		}
		else {
			sb.append("'${dto.asset.dbValue}', ")
		}

		//category
		if (dto.category == Category.Unknown) {
			sb.append('null, ')
		}
		else {
			sb.append("'${dto.category.dbValue}', ")
		}

		//subCategory
		if (dto.subCategory == SubCategory.Unknown) {
			sb.append('null, ')
		}
		else {
			sb.append("'${dto.subCategory.dbValue}', ")
		}
				
		//startDate
		if (dto.startDate) {
			sb.append("'${db2date.format(dto.startDate)}', ")
		}
		else {
			sb.append('null, ')
		}

		//endDate
		if (dto.endDate) {
			sb.append("'${db2date.format(dto.endDate)}'")
		}
		else {
			sb.append('null')
		}
		
		sb.append(');')
		
		return sb.toString()
	}
	
	
	List<FinancialDTO> build(File file) {
		List<FinancialDTO> financialList = []

		file.eachLine {line ->
//			println line
			def sa = line.split(/\|/)
			
			FinancialDTO dto = new FinancialDTO()
			
			//transactionDate
			Date d = sdf.parse(sa[0])
//			println d
			dto.transactionDate = d
			
			//amount
			BigDecimal bd = new BigDecimal(sa[1])
//			println bd
			dto.amount = bd
			
			dto.payee = sa[2].trim()
			dto.description = sa[3].trim()

			def xml= new XmlSlurper().parseText(sa[4])
			
			//Asset type
			Asset a = Asset.findByName(xml.asset.text())
			dto.asset = a
			
			//Category type
			Category c = Category.findByName(xml.cat.text())
			dto.category = c
			
			//Category type
			SubCategory sc = SubCategory.findByName(xml.subcat.text())
			dto.subCategory = sc
			
			//Duration
			String start = xml.start.text()
			String end = xml.end.text()
			if (start && end) {
				dto.startDate = sdf.parse(start)
				dto.endDate = sdf.parse(end)
			}
			
			//println dto			
			financialList << dto
		}

		return financialList
	}
	
	def verify(File dataFile) {
		dataFile.eachLine {line ->
			assert line.split(/\|/).size() == 5
		}
	}
}
