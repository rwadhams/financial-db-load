package com.wadhams.financials.db.load.type

enum Category {
	Accomodation('ACCOMODATION','ACCOMODATION'),
	AccountingFees('ACCOUNTING_FEES','ACCOUNTING_FEES'),
	Alcohol('ALCOHOL','ALCOHOL'),
	BankingFees('BANKING_FEES','BANKING_FEES'),
	CampingEquipment('CAMPING_EQUIPMENT','CAMPING_EQUIPMENT'),
	CampingFees('CAMPING_FEES','CAMPING_FEES'),
	CampingSupplies('CAMPING_SUPPLIES','CAMPING_SUPPLIES'),
	CarEquipment('CAR_EQUIPMENT','CAR_EQUIPMENT'),
	CarInsurance('CAR_INSURANCE','CAR_INSURANCE'),
	CarRego('CAR_REGISTRATION','CAR_REGISTRATION'),
	CarRepair('CAR_REPAIR','CAR_REPAIR'),
	CarServicing('CAR_SERVICING','CAR_SERVICING'),
	CarSupplies('CAR_SUPPLIES','CAR_SUPPLIES'),
	CarTyres('CAR_TYRES','CAR_TYRES'),
	CaravanEquipment('CARAVAN_EQUIPMENT','CARAVAN_EQUIPMENT'),
	CaravanInsurance('CARAVAN_INSURANCE','CARAVAN_INSURANCE'),
	CaravanRego('CARAVAN_REGISTRATION','CARAVAN_REGISTRATION'),
	CaravanRepair('CARAVAN_REPAIR','CARAVAN_REPAIR'),
	CaravanServicing('CARAVAN_SERVICING','CARAVAN_SERVICING'),
	CaravanStorage('CARAVAN_STORAGE','CARAVAN_STORAGE'),
	CaravanSupplies('CARAVAN_SUPPLIES','CARAVAN_SUPPLIES'),
	Cash('CASH','CASH'),
	Clothing('CLOTHING','CLOTHING'),
	CloudStorage('CLOUD_STORAGE','CLOUD_STORAGE'),
	DataPlan('DATA_PLAN','DATA_PLAN'),
	Drinks('DRINKS','DRINKS'),
	DriversLicenseMolly('DRIVERS_LICENSE_MOLLY','DRIVERS_LICENSE_MOLLY'),
	DriversLicenseRob('DRIVERS_LICENSE_ROB','DRIVERS_LICENSE_ROB'),
	ElectricUtilities('ELECTRIC_UTILITIES','ELECTRIC_UTILITIES'),
	Entertainment('ENTERTAINMENT','ENTERTAINMENT'),
	Ferry('FERRY','FERRY'),
	Fishing('FISHING','FISHING'),
	Food('FOOD','FOOD'),
	Fuel('FUEL','FUEL'),
	Furniture('FURNITURE','FURNITURE'),
	GasUtilities('GAS_UTILITIES','GAS_UTILITIES'),
	Gifts('GIFTS','GIFTS'),
	HomeBrew('HOME_BREW','HOME_BREW'),
	HouseInsurance('HOUSE_INSURANCE','HOUSE_INSURANCE'),
	HouseMaintenance('HOUSE_MAINTENANCE','HOUSE_MAINTENANCE'),
	HouseSale('HOUSE_SALE','HOUSE_SALE'),
	HouseSupplies('HOUSE_SUPPLIES','HOUSE_SUPPLIES'),
	HouseWares('HOUSEWARES','HOUSEWARES'),
	Laundry('LAUNDRY','LAUNDRY'),
	Medical('MEDICAL','MEDICAL'),
	Misc('MISC','MISC'),
	Office('OFFICE','OFFICE'),
	Parking('PARKING','PARKING'),
	ParksPass('PARKS_PASS','PARKS_PASS'),
	PersonalGrooming('PERSONAL_GROOMING','PERSONAL_GROOMING'),
	Pharmacy('PHARMACY','PHARMACY'),
	PhoneDataPlan('PHONE_AND_DATA_PLAN','PHONE_AND_DATA_PLAN'),
	PhonePlanMolly('PHONE_PLAN_MOLLY','PHONE_PLAN_MOLLY'),
	PhonePlanRob('PHONE_PLAN_ROB','PHONE_PLAN_ROB'),
	PreparedFood('PREPARED_FOOD','PREPARED_FOOD'),
	PurchaseDeposit('PURCHASE_DEPOSIT','PURCHASE_DEPOSIT'),
	PurchasePayment('PURCHASE_PAYMENT','PURCHASE_PAYMENT'),
	PurchaseStampDuty('PURCHASE_STAMP_DUTY','PURCHASE_STAMP_DUTY'),
	RACQMembership('RACQ_MEMBERSHIP','RACQ_MEMBERSHIP'),
	Rates('RATES','RATES'),
	Renovation('RENO','RENO'),
	RenovationServices('RENO_SERVICES','RENO_SERVICES'),
	RentalCar('RENTAL_CAR','RENTAL_CAR'),
	Safety('SAFETY','SAFETY'),
	Technology('TECHNOLOGY','TECHNOLOGY'),
	Tolls('TOLLS','TOLLS'),
	Tools('TOOLS','TOOLS'),
	Transit('TRANSIT','TRANSIT'),
	TransmissionServicing('TRANSMISSION_SERVICING','TRANSMISSION_SERVICING'),
	Travel('TRAVEL','TRAVEL'),
	TravelPublication('TRAVEL_PUBLICATION','TRAVEL_PUBLICATION'),
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
