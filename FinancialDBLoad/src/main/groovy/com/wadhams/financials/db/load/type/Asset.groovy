package com.wadhams.financials.db.load.type

enum Asset {
	CampHill('CAMP_HILL','Camp Hill','CAMP_HILL'),
	Caravan('CARAVAN','Caravan','CARAVAN'),
	Vehicle('VEHICLE','Vehicle','VEHICLE'),
	Unknown('Unknown','Unknown','Unknown');
	
	private static EnumSet<Asset> allEnums = EnumSet.allOf(Asset.class)
	
	private final String name
	private final String description
	private final String dbValue

	Asset(String name, String description, String dbValue) {
		this.name = name
		this.description = description
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
		
		println "Unknown asset text: $text"
		return Asset.Unknown
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
