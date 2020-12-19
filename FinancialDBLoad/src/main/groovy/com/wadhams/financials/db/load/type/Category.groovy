package com.wadhams.financials.db.load.type

enum Category {
	FourWheelDriving('4WD','4WD'),
	Accomodation('ACCOMODATION','ACCOMODATION'),
	Alcohol('ALCOHOL','ALCOHOL'),
	CampingEquipment('CAMPING_EQUIPMENT','CAMPING_EQUIPMENT'),
	CampingFees('CAMPING_FEES','CAMPING_FEES'),
	CampingSupplies('CAMPING_SUPPLIES','CAMPING_SUPPLIES'),
	CaravanEquipment('CARAVAN_EQUIPMENT','CARAVAN_EQUIPMENT'),
	CaravanInsurance('CARAVAN_INSURANCE','CARAVAN_INSURANCE'),
	CaravanMaintenance('CARAVAN_MAINTENANCE','CARAVAN_MAINTENANCE'),
	CaravanRego('CARAVAN_REGISTRATION','CARAVAN_REGISTRATION'),
	CaravanStorage('CARAVAN_STORAGE','CARAVAN_STORAGE'),
	CarEquipment('CAR_EQUIPMENT','CAR_EQUIPMENT'),
	CarInsurance('CAR_INSURANCE','CAR_INSURANCE'),
	CarMaintenance('CAR_MAINTENANCE','CAR_MAINTENANCE'),
	CarServicing('CAR_SERVICING','CAR_SERVICING'),
	CarRego('CAR_REGISTRATION','CAR_REGISTRATION'),
	CarSupplies('CAR_SUPPLIES','CAR_SUPPLIES'),
	Cash('CASH','CASH'),
	Clothing('CLOTHING','CLOTHING'),
	DataPlan('DATA_PLAN','DATA_PLAN'),
	Drinks('DRINKS','DRINKS'),
	DriversLicenseRob('DRIVERS_LICENSE_ROB','DRIVERS_LICENSE_ROB'),
	DriversLicenseMolly('DRIVERS_LICENSE_MOLLY','DRIVERS_LICENSE_MOLLY'),
	ElectricUtilities('ELECTRIC_UTILITIES','ELECTRIC_UTILITIES'),
	Entertainment('ENTERTAINMENT','ENTERTAINMENT'),
	Equipment('EQUIPMENT','EQUIPMENT'),
	Fishing('FISHING','FISHING'),
	Flights('FLIGHTS','FLIGHTS'),
	Food('FOOD','FOOD'),
	Fuel('FUEL','FUEL'),
	Furniture('FURNITURE','FURNITURE'),
	GasUtilities('GAS_UTILITIES','GAS_UTILITIES'),
	Gifts('GIFTS','GIFTS'),
	HomeBrew('HOME_BREW','HOME_BREW'),
	HouseWares('HOUSEWARES','HOUSEWARES'),
	HouseInsurance('HOUSE_INSURANCE','HOUSE_INSURANCE'),
	HouseMaintenance('HOUSE_MAINTENANCE','HOUSE_MAINTENANCE'),
	HouseSale('HOUSE_SALE','HOUSE_SALE'),
	HouseSupplies('HOUSE_SUPPLIES','HOUSE_SUPPLIES'),
	ITunes('ITUNES','ITUNES'),
	Laundry('LAUNDRY','LAUNDRY'),
	Medical('MEDICAL','MEDICAL'),
	Misc('MISC','MISC'),
	Office('OFFICE','OFFICE'),
	Parking('PARKING','PARKING'),
	Pharmacy('PHARMACY','PHARMACY'),
	PhonePlan('PHONE_PLAN','PHONE_PLAN'),
	PhoneDataPlan('PHONE_AND_DATA_PLAN','PHONE_AND_DATA_PLAN'),
	PreparedFood('PREPARED_FOOD','PREPARED_FOOD'),
	Purchase('PURCHASE','PURCHASE'),
	RACQMembership('RACQ_MEMBERSHIP','RACQ_MEMBERSHIP'),
	Rates('RATES','RATES'),
	Renovation('RENO','RENO'),
	RentalCar('RENTAL_CAR','RENTAL_CAR'),
	Technology('TECHNOLOGY','TECHNOLOGY'),
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
