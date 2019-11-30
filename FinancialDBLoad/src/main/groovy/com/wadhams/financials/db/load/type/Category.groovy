package com.wadhams.financials.db.load.type

enum Category {
	License('LICENSE','License','LICENSE'),
	Membership('MEMBERSHIP','Membership','MEMBERSHIP'),
	Purchase('PURCHASE','Purchase','PURCHASE'),
	Income('INCOME','Income','INCOME'),
	Alcohol('ALCOHOL','Drinking','ALCOHOL'),
	Camping('CAMPING','Camping','CAMPING'),
	Equipment('EQUIPMENT','Equipment','EQUIPMENT'),
	Dining('DINING','Dining','DINING'),
	Food('FOOD','Food','FOOD'),
	Fun('ENTERTAINMENT','Fun','ENTERTAINMENT'),
	Fuel('FUEL','Fuel','FUEL'),
	Furniture('FURNITURE','Furniture','FURNITURE'),
	Insurance('INSURANCE','Insurance','INSURANCE'),
	Health('HEALTH','Health Care','HEALTH'),
	HouseWares('HOUSEWARES','Housewares','HOUSEWARES'),
	Misc('MISC','Miscellaneous','MISC'),
	Transportation('TRANSPORTATION','Transportation','TRANSPORTATION'),
	Renovation('RENO','Renovation','RENO'),
	Tax('TAX','Tax','TAX'),
	Maintenance('MAINT','Maintenance','MAINT'),
	Utilities('UTILITIES','Utilities','UTILITIES'),
	Unknown('Unknown','Unknown','Unknown');
	
	private static EnumSet<Category> allEnums = EnumSet.allOf(Category.class)
	
	private final String name
	private final String description
	private final String dbValue

	Category(String name, String description, String dbValue) {
		this.name = name
		this.description = description
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

	//fixed width
	public String getDescription() {
		return description.padRight(10);
	}

	public String getDbValue() {
		return dbValue;
	}

}
