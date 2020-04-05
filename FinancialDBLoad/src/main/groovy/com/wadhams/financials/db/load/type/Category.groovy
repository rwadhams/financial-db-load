package com.wadhams.financials.db.load.type

enum Category {
	FourWheelDriving('4WD','4WD'),
	Accomodation('ACCOMODATION','ACCOMODATION'),
	Alcohol('ALCOHOL','ALCOHOL'),
	Bath('BATH','BATH'),
	CampingEquipment('CAMPING_EQUIPMENT','CAMPING_EQUIPMENT'),
	CampingFees('CAMPING_FEES','CAMPING_FEES'),
	CampingSupplies('CAMPING_SUPPLIES','CAMPING_SUPPLIES'),
	CarEquipment('CAR_EQUIPMENT','CAR_EQUIPMENT'),
	CarInsurance('CAR_INSURANCE','CAR_INSURANCE'),
	CarMaintenance('CAR_MAINTENANCE','CAR_MAINTENANCE'),
	CarServicing('CAR_SERVICING','CAR_SERVICING'),
	CarRego('CAR_REGISTRATION','CAR_REGISTRATION'),
	CarSupplies('CAR_SUPPLIES','CAR_SUPPLIES'),
	CaravanEquipment('CARAVAN_EQUIPMENT','CARAVAN_EQUIPMENT'),
	CaravanInsurance('CARAVAN_INSURANCE','CARAVAN_INSURANCE'),
	CaravanMaintenance('CARAVAN_MAINTENANCE','CARAVAN_MAINTENANCE'),
	CaravanRego('CARAVAN_REGISTRATION','CARAVAN_REGISTRATION'),
	CaravanSupplies('CARAVAN_SUPPLIES','CARAVAN_SUPPLIES'),
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
	HouseMaintenance('HOUSE_MAINTENANCE','HOUSE_MAINTENANCE'),
	HouseSupplies('HOUSE_SUPPLIES','HOUSE_SUPPLIES'),
	HouseWares('HOUSEWARES','HOUSEWARES'),
	Income('INCOME','INCOME'),
	ITunes('ITUNES','ITUNES'),
	Medical('MEDICAL','MEDICAL'),
	RACQMembership('RACQ_MEMBERSHIP','RACQ_MEMBERSHIP'),
	RentalCar('RENTAL_CAR','RENTAL_CAR'),
	Misc('MISC','MISC'),
	Office('OFFICE','OFFICE'),
	Parking('PARKING','PARKING'),
	Pharmacy('PHARMACY','PHARMACY'),
	Purchase('PURCHASE','PURCHASE'),
	Rates('RATES','RATES'),
	Renovation('RENO','RENO'),
	PreparedFood('PREPARED_FOOD','PREPARED_FOOD'),
	Tax('TAX','TAX'),
	Technology('TECHNOLOGY','TECHNOLOGY'),
	Telstra('TELSTRA','TELSTRA'),
	Tools('TOOLS','TOOLS'),
	Travel('TRAVEL','TRAVEL'),
	TravelFees('TRAVEL_FEES','TRAVEL_FEES'),
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
		
		println ''
		println "ZZZZ Unknown category text: $text"
		println ''
		return Category.Unknown
	}

	public String getName() {
		return name
	}

	public String getDbValue() {
		return dbValue;
	}

}
