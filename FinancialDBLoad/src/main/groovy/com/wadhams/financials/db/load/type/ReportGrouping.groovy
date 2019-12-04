package com.wadhams.financials.db.load.type

enum ReportGrouping {
	SpecificRunningCost('SPECIFIC_RUNNING_COST','SPECIFIC_RUNNING_COST'),	//specific
	OngoingRunningCosts('ONGOING_RUNNING_COSTS','ONGOING_RUNNING_COSTS'),	//start and end dates are consecutive and don't overlap
	Unknown('Unknown','Unknown');
	
	private static EnumSet<ReportGrouping> allEnums = EnumSet.allOf(ReportGrouping.class)
	
	private final String name
	private final String dbValue

	ReportGrouping(String name, String dbValue) {
		this.name = name
		this.dbValue = dbValue
	}
	
	public static ReportGrouping findByName(String text) {
		if (text) {
			text = text.toUpperCase()
			for (ReportGrouping e : allEnums) {
				if (e.name.equals(text)) {
					return e
				}
			}
		}
		else {
			return ReportGrouping.Unknown
		}
		
		println "Unknown reporting group text: $text"
		return ReportGrouping.Unknown
	}

	public String getName() {
		return name
	}

	public String getDbValue() {
		return dbValue;
	}

}
