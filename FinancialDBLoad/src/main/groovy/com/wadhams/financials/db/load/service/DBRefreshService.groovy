package com.wadhams.financials.db.load.service

import com.wadhams.financials.db.load.dto.FinancialDTO

import groovy.sql.Sql

class DBRefreshService {
	SQLBuilderService sqlBuilderService = new SQLBuilderService()
	
	def refresh(List<FinancialDTO> dtoList) {
		Sql sql = Sql.newInstance('jdbc:h2:~/financial', 'sa', '', 'org.h2.Driver')
		
		//purge financials from database
		sql.execute sqlBuilderService.buildDeleteAll()
		
		//insert financials into database			
		dtoList.each {dto ->
			sql.execute sqlBuilderService.buildInsert(dto)
		}
		println 'Database refresh has completed.'
		println ''
	}
	
}
