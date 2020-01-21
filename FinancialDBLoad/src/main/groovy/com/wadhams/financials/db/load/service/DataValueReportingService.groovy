package com.wadhams.financials.db.load.service

import java.text.SimpleDateFormat

import com.wadhams.financials.db.load.dto.FinancialDTO
import com.wadhams.financials.db.load.type.Asset
import com.wadhams.financials.db.load.type.Category
import com.wadhams.financials.db.load.type.SubCategory
import groovy.sql.Sql

class DataValueReportingService {
	SQLBuilderService sqlBuilderService = new SQLBuilderService()
	
	def reportDistinctValues(PrintWriter pw) {
		Sql sql = Sql.newInstance('jdbc:h2:~/financial', 'sa', '', 'org.h2.Driver')

		String payeeCountTwoPlusQuery = sqlBuilderService.buildDistinctPayeeCountTwoPlus()
		println payeeCountTwoPlusQuery
		println ''
		
		List<String> payeeCountTwoPlusList = []
		sql.eachRow(payeeCountTwoPlusQuery) {row ->
			String c01 = row.COUNT
			String c02 = row.PAYEE
			payeeCountTwoPlusList << "$c02 ($c01)".padRight(30)
		}

		pw.println 'Distinct Payee Values (Count > 1)'
		pw.println '---------------------------------'
		multiColumnPrinting(payeeCountTwoPlusList, 20, pw)
		pw.println ''
		
		
		
		String payeeCountOneQuery = sqlBuilderService.buildDistinctPayeeCountOne()
		println payeeCountOneQuery
		println ''
		
		List<String> payeeCountOneList = []
		sql.eachRow(payeeCountOneQuery) {row ->
			String c01 = row.PAYEE
			payeeCountOneList << "$c01".padRight(30)
		}
		
		pw.println 'Distinct Payee Values (Count = 1)'
		pw.println '---------------------------------'
		multiColumnPrinting(payeeCountOneList, 30, pw)
		pw.println ''
		
		String categorySubCategoryQuery = sqlBuilderService.buildDistinctCategorySubCategorySelect()
		println categorySubCategoryQuery
		println ''
		
		pw.println 'Distinct Category/SubCategory Values'
		pw.println '------------------------------------'
		String category = ''
		sql.eachRow(categorySubCategoryQuery) {row ->
			String c01 = row.CAT
			String c02 = row.SUBCAT
			if (category != c01) {
				category = c01
				pw.println c01
			}
			if (c02) {
				pw.println "\t$c02"
			}
		}
		pw.println ''
		
		String assetQuery = sqlBuilderService.buildAssetSelectWithoutReportGrouping()
		println assetQuery
		println ''
		pw.println 'Asset Category SubCategory (not part of report grouping)'
		pw.println '--------------------------------------------------------'
		sql.eachRow(assetQuery) {row ->
			String c01 = row.PAYEE
			String c02 = row.DESC
			String c03 = row.ASSET
			String c04 = row.CAT
			String c05 = row.SUBCAT
			pw.println "$c03|$c04|$c05\t\t$c01, $c02"
		}
		pw.println ''

		String specificRunningCostQuery = sqlBuilderService.buildSpecificRunningCostSelect()
		println specificRunningCostQuery
		println ''
		pw.println '<rg1>SPECIFIC_RUNNING_COST</rg1>'
		println ''
		pw.println 'Specific Running Costs (Asset|Category)'
		pw.println '---------------------------------------'
		sql.eachRow(specificRunningCostQuery) {row ->
			String c01 = row.PAYEE
			String c02 = row.DESC
			String c03 = row.ASSET
			String c04 = row.CAT
			String c05 = row.START
			String c06 = row.END
			pw.println "$c03|$c04|$c05|$c06\t\t$c01, $c02"
		}
		pw.println ''

		String ongoingRunningCostQuery = sqlBuilderService.buildOngoingRunningCostSelect()
		println ongoingRunningCostQuery
		println ''
		pw.println '<rg1>ONGOING_RUNNING_COST</rg1>'
		println ''
		pw.println 'Ongoing Running Costs (Asset|Category)'
		pw.println '--------------------------------------'
		sql.eachRow(ongoingRunningCostQuery) {row ->
			String c01 = row.PAYEE
			String c02 = row.DESC
			String c03 = row.ASSET
			String c04 = row.CAT
			String c05 = row.START
			String c06 = row.END
			pw.println "$c03|$c04|$c05|$c06\t\t$c01, $c02"
		}
		pw.println ''

		String reportGrouping1Query = sqlBuilderService.buildDistinctReportGrouping1Select()
		println reportGrouping1Query
		println ''
		pw.println 'Distinct Report Grouping 1'
		pw.println '--------------------------'
		sql.eachRow(reportGrouping1Query) {row ->
			String c01 = row.RG1
			pw.println c01
		}
		pw.println ''
		
	}
	
	def multiColumnPrinting(List<String> list, int height, PrintWriter pw) {
		int total = list.size()
		int prev = total / height
		int columns = prev + 1
		height.times {row ->
			prev.times {col ->
				pw.print "${list[col*height+row]}"
			}
			if (height*prev+row >= total) {
				pw.println ''
			}
			else {
				pw.println "${list[prev*height+row]}"
			}
		}
	}
}
