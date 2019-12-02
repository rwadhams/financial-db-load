package com.wadhams.financials.db.load.type

enum Run {
	DML('DML'),			//Data Manipulation Language (Insert statements)
	Refresh('REFRESH'),	//Direct updates to database
	Unknown('Unknown');
	
	private static EnumSet<Run> allEnums = EnumSet.allOf(Run.class)
	
	private final String name

	Run(String name) {
		this.name = name
	}
	
	public static Run findByName(String text) {
		if (text) {
			text = text.toUpperCase()
			for (Run e : allEnums) {
				if (e.name.equals(text)) {
					return e
				}
			}
		}
		else {
			return Run.Unknown
		}
		return Run.Unknown
	}

}
