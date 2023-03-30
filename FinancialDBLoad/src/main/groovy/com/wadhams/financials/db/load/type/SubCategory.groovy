package com.wadhams.financials.db.load.type

enum SubCategory {
	Invalid('Invalid','Invalid'),
	Unknown('Unknown','Unknown');
	
	private static EnumSet<SubCategory> allEnums = EnumSet.allOf(SubCategory.class)
	
	private final String name
	private final String dbValue
	
	SubCategory(String name, String dbValue) {
		this.name = name
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
		
		println ''
		println "ZZZZ Invalid sub-category text: $text"
		println ''
		return SubCategory.Invalid
	}

	public String getName() {
		return name
	}

	public String getDbValue() {
		return dbValue
	}

}
