package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

	private static ConnessioneDatabase instance;
	/**
	 * La connessione al database.
	 */
	public Connection connection = null;
	private String nome = "postgres";
	private String password = "noelia4680";
	private String url = "jdbc:postgresql://127.0.0.1:5432/Tamagotchi";
	private String driver = "org.postgresql.Driver";

	private ConnessioneDatabase() throws SQLException {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, nome, password);

		} catch (ClassNotFoundException ex) {
			System.out.println("Database Connection Creation Failed : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Ottiene l'istanza univoca della classe ConnessioneDatabase, creandola se non esiste o se la connessione è stata chiusa.
	 *
	 * @return l'istanza corrente di {@link ConnessioneDatabase}.
	 * @throws SQLException se si verifica un errore durante la creazione della connessione al database.
	 */
	public static ConnessioneDatabase getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnessioneDatabase();
		} else if (instance.connection.isClosed()) {
			instance = new ConnessioneDatabase();
		}
		return instance;
	}
}