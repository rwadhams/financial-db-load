package com.wadhams.financials.db.load.type

enum Category {
	License('LICENSE','LICENSE'),
	Membership('MEMBERSHIP','MEMBERSHIP'),
	Purchase('PURCHASE','PURCHASE'),
	Income('INCOME','INCOME'),
	Alcohol('ALCOHOL','ALCOHOL'),
	Camping('CAMPING','CAMPING'),
	Equipment('EQUIPMENT','EQUIPMENT'),
	Dining('DINING','DINING'),
	Food('FOOD','FOOD'),
	Entertainment('ENTERTAINMENT','ENTERTAINMENT'),
	Fuel('FUEL','FUEL'),
	Furniture('FURNITURE','FURNITURE'),
	Insurance('INSURANCE','INSURANCE'),
	Health('HEALTH','HEALTH'),
	Medical('MEDICAL','MEDICAL'),
	HouseWares('HOUSEWARES','HOUSEWARES'),
	Misc('MISC','MISC'),
	Transportation('TRANSPORTATION','TRANSPORTATION'),
	Renovation('RENO','RENO'),
	Tax('TAX','TAX'),
	Maintenance('MAINTENANCE','MAINTENANCE'),
	Utilities('UTILITIES','UTILITIES'),
	Unknown('Unknown','Unknown');
	
	private static EnumSet<Category> allEnums = EnumSet.allOf(Category.class)
	
	private final String name
	private final String dbValue

	Category(String name, String dbValue) {
		this.name = name
		this.dbValue = dbValue
	}
	
	public static Category findByName(String text) {
		if (text) {
			text = text.toUpperCase()
			for (Category e : allEnums) {
				if (e.name.equals(text)) {
					return e
				}
			}
		}
		else {
			return Category.Unknown
		}
		
		println "Unknown category text: $text"
		return Category.Unknown
	}

	public String getName() {
		return name
	}

	public String getDbValue() {
		return dbValue;
	}

}
