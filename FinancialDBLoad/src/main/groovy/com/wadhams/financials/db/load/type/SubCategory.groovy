package com.wadhams.financials.db.load.type

enum SubCategory {
	Payment('PAYMENT','PAYMENT'),			//PURCHASE
	StampDuty('STAMP_DUTY','STAMP_DUTY'),	//PURCHASE
	Deposit('DEPOSIT','DEPOSIT'),			//PURCHASE
	Services('SERVICES','SERVICES'),		//Paid work and materials
	Camping('CAMPING','CAMPING'),			//EQUIPMENT, SUPPLIES
	FourWheelDriving('4WD','4WD'),			//EQUIPMENT
	Caravan('CARAVAN','CARAVAN'),			//EQUIPMENT, SUPPLIES
	Vehicle('VEHICLE','VEHICLE'),			//EQUIPMENT, SUPPLIES
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
		
		println "Unknown sub-category text: $text"
		return SubCategory.Unknown
	}

	public String getName() {
		return name
	}

	public String getDbValue() {
		return dbValue;
	}

}
