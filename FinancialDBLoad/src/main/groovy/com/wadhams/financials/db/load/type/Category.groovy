package com.wadhams.financials.db.load.type

enum Category {
	Alcohol('ALCOHOL','ALCOHOL'),
	Bath('BATH','BATH'),
	CarInsurance('CAR_INSURANCE','CAR_INSURANCE'),
	CarService('CAR_SERVICE','CAR_SERVICE'),
	CaravanInsurance('CARAVAN_INSURANCE','CARAVAN_INSURANCE'),
	Cash('CASH','CASH'),
	Clothing('CLOTHING','CLOTHING'),
	DriversLicense('DRIVERS_LICENSE','DRIVERS_LICENSE'),
	ElectricUtilities('ELECTRIC_UTILITIES','ELECTRIC_UTILITIES'),
	Entertainment('ENTERTAINMENT','ENTERTAINMENT'),
	Equipment('EQUIPMENT','EQUIPMENT'),
	Food('FOOD','FOOD'),
	Fuel('FUEL','FUEL'),
	Furniture('FURNITURE','FURNITURE'),
	GasUtilities('GAS_UTILITIES','GAS_UTILITIES'),
	Gifts('GIFTS','GIFTS'),
	HouseInsurance('HOUSE_INSURANCE','HOUSE_INSURANCE'),
	HouseWares('HOUSEWARES','HOUSEWARES'),
	Income('INCOME','INCOME'),
	Maintenance('MAINTENANCE','MAINTENANCE'),
	Medical('MEDICAL','MEDICAL'),
	RACQMembership('RACQ_MEMBERSHIP','RACQ_MEMBERSHIP'),
	Misc('MISC','MISC'),
	Office('OFFICE','OFFICE'),
	Pharmacy('PHARMACY','PHARMACY'),
	Purchase('PURCHASE','PURCHASE'),
	Rates('RATES','RATES'),
	Renovation('RENO','RENO'),
	PreparedFood('PREPARED_FOOD','PREPARED_FOOD'),
	Supplies('SUPPLIES','SUPPLIES'),
	Tax('TAX','TAX'),
	Telstra('TELSTRA','TELSTRA'),
	Transportation('TRANSPORTATION','TRANSPORTATION'),
	Travel('TRAVEL','TRAVEL'),
	WaterUtilities('WATER_UTILITIES','WATER_UTILITIES'),
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
