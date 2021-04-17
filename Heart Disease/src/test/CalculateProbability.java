package test;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalculateProbability {

	float HA_YES, HA_NO; // important factors
	public static float EXTERNAL_HA_YES,EXTERNAL_HA_NO;
	Connection con = null;
	public static boolean calculatingTrue = true;

	public void calculateProbability() {
		con = Database.getConnection();
		try {
			Statement st = con.createStatement();
			String columnName = "";
			String query1 = "describe filtered_records";
			ResultSet columnResultSet = st.executeQuery(query1);
			while (columnResultSet.next()) {
				columnName = columnResultSet.getString(1);
				if (columnName.equals("id"))
					continue;
				else {
					List<Integer> list = getDistinctValues(con, columnName);
					for (int i = 0; i < list.size(); i++) {
						int value = (int) list.get(i);
						int result = getCount(con, columnName, value);
						float probability = 0.0f;
						if (calculatingTrue) {
							probability = EXTERNAL_HA_YES * (result / HA_YES);
							if (probability == 0.0f || probability == -0.0f
									|| Math.abs(probability) == 0)
								probability = 1;
							insertToDatabase(con, value, columnName, result,
									probability, calculatingTrue);
						} else {
							probability = EXTERNAL_HA_NO * (result / HA_NO);
							if (probability == 0 || probability == -0.0f
									|| Math.abs(probability) == 0)
								probability = 1;
							insertToDatabase(con, value, columnName, result,
									probability, calculatingTrue);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.close(con);
		}
	}

	public void initializeFactors() {
		Connection con1 = Database.getConnection();
		try {
			Statement st = con1.createStatement();
			ResultSet rs = st.executeQuery("select count(id) from filtered_records");
			int total = 1;
			while(rs.next())
				total = rs.getInt(1);
			String query = "select count(heart_attack) from filtered_records where heart_attack=1";
			rs = st.executeQuery(query);
			while (rs.next())
				HA_YES = (float)rs.getInt(1);

			query = "select count(heart_attack) from filtered_records where heart_attack=0";
			rs = st.executeQuery(query);
			while (rs.next())
				HA_NO = (float)rs.getInt(1);
			EXTERNAL_HA_YES = HA_YES/ (float) total;
			EXTERNAL_HA_NO = HA_NO / (float) total;
			System.out.println(HA_YES+" "+HA_NO);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Database.close(con1);
		}
	}

	public List<Integer> getDistinctValues(Connection con, String columnName) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			String query = "select distinct(" + columnName
					+ ") from filtered_records";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
				list.add(new Integer(rs.getInt(1)));
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public int getCount(Connection con, String columnName, int value) {
		int count = 0;
		try {
			String query = "select count(" + columnName
					+ ") from filtered_records where " + columnName + "="
					+ value;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
				count = rs.getInt(1);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void insertToDatabase(Connection con, int uniqueValue,
			String columnName, int count, float probability, boolean usingTrue) {
		try {
			if (usingTrue) {
				String query = "update probability_storage_yes set count="
						+ count + ",probability=" + probability
						+ " where attribute='" + columnName
						+ "' and attribute_value=" + uniqueValue;
				Statement st = con.createStatement();
				st.executeUpdate(query);
			} else {
				String query = "update probability_storage_no set count="
						+ count + ",probability=" + probability
						+ " where attribute='" + columnName
						+ "' and attribute_value=" + uniqueValue;
				Statement st = con.createStatement();
				st.executeUpdate(query);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateBothTables() {
		try {
			Connection con1 = Database.getConnection();
			Statement st = con1.createStatement();
			String query = "update probability_storage_yes set count=0,probability=0";
			st.executeUpdate(query);
			query = "update probability_storage_no set count=0,probability=0";
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
