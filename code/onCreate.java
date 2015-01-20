public void onCreate(SQLiteDatabase db) {
	
	String sql = "CREATE TABLE schiffe" +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"bezeichnung VARCHAR(20) NOT NULL, " +
				"captain VARCHAR(20) NOT NULL, " +
				"geburtsdatum DATE)";

	db.execSQL(db);
}