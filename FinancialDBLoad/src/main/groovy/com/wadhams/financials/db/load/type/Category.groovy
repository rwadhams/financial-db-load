package com.wadhams.financials.db.load.type

enum Category {
	Alcohol('ALCOHOL','ALCOHOL'),
	Camping('CAMPING','CAMPING'),
	Cash('CASH','CASH'),
	Dining('DINING','DINING'),
	DriversLicense('DRIVERS_LICENSE','DRIVERS_LICENSE'),
	Entertainment('ENTERTAINMENT','ENTERTAINMENT'),
	Equipment('EQUIPMENT','EQUIPMENT'),
	Food('FOOD','FOOD'),
	Fuel('FUEL','FUEL'),
	Furniture('FURNITURE','FURNITURE'),
	Health('HEALTH','HEALTH'),
	HouseWares('HOUSEWARES','HOUSEWARES'),
	Income('INCOME','INCOME'),
	Insurance('INSURANCE','INSURANCE'),
	Maintenance('MAINTENANCE','MAINTENANCE'),
	Medical('MEDICAL','MEDICAL'),
	Membership('MEMBERSHIP','MEMBERSHIP'),
	Misc('MISC','MISC'),
	Purchase('PURCHASE','PURCHASE'),
	Rates('RATES','RATES'),
	Renovation('RENO','RENO'),
	Tax('TAX','TAX'),
	Telstra('TELSTRA','TELSTRA'),
	Transportation('TRANSPORTATION','TRANSPORTATION'),
	Travel('TRAVEL','TRAVEL'),
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
