package com.wadhams.financials.db.load.type

enum SubCategory {
	Payment('PAYMENT','Payment','PAYMENT'),
	StampDuty('STAMP_DUTY','Stamp Duty','STAMP_DUTY'),
	Deposit('DEPOSIT','Deposit','DEPOSIT'),
	Unknown('Unknown','Unknown','Unknown');
	
	private static EnumSet<SubCategory> allEnums = EnumSet.allOf(SubCategory.class)
	
	private final String name
	private final String description
	private final String dbValue
	
	SubCategory(String name, String description, String dbValue) {
		this.name = name
		this.description = description
		this.dbValue = dbValue
	}
	
	public static SubCategory findByName(String text) {
		if (text) {
			text = text.toUpperCase()
			for (SubCategory e : allEnums) {
				if (e.name.equals(text)) {
					return e
				}
			}
		}
		else {
			return SubCategory.Unknown
		}
		
		println "Unknown sub-category text: $text"
		return SubCategory.Unknown
	}

	public String getName() {
		return name
	}

	//fixed width
	public String getDescription() {
		return description.padRight(20);
	}

	public String getDbValue() {
		return dbValue;
	}

}
