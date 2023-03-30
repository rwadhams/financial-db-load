package com.wadhams.financials.db.load.type

enum Asset {
	CampHill('CAMP_HILL','CAMP_HILL'),
	Caravan('CARAVAN','CARAVAN'),
	Vehicle('VEHICLE','VEHICLE'),
	
	Invalid('Invalid','Invalid'),
	Unknown('Unknown','Unknown');
	
	private static EnumSet<Asset> allEnums = EnumSet.allOf(Asset.class)
	
	private final String name
	private final String dbValue

	Asset(String name, String dbValue) {
		this.name = name
		this.dbValue = dbValue
	}
	
	public static Asset findByName(String text) {
		if (text) {
			text = text.toUpperCase()
			for (Asset e : allEnums) {
				if (e.name.equals(text)) {
					return e
				}
			}
		}
		else {
			return Asset.Unknown
		}
		
		println ''
		println "ZZZZ Invalid asset text: $text"
		println ''
		return Asset.Invalid
	}

	public String getName() {
		return name
	}

	public String getDbValue() {
		return dbValue
	}

}
