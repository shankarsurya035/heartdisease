package test;
import java.sql.*;
import java.util.HashMap;
public class DataFilter {

	Connection con = null;

	public static HashMap<String,String> map = new HashMap<String,String>();
	
	static
	{
		map.put("gender1", "male");
		map.put("gender0", "female");
		map.put("chest-pain1", "normal");
		map.put("chest-pain2", "high");
		map.put("chest-pain3", "severe");
		map.put("fasting-sugar1", "yes");
		map.put("fasting-sugar0", "no");
		map.put("rest-ecg2", "Definite left hypertrophy");
		map.put("rest-ecg1", "Wave abnormality");
		map.put("rest-ecg0", "normal");
		map.put("exang1", "yes");
		map.put("exang0", "no");
	}
	
	public void filterRecords() {
		con = Database.getConnection();
		try {
			Statement st = con.createStatement();
			emptyTable(con);
			PreparedStatement ps = con
					.prepareStatement("insert into filtered_records values(?,?,?,?,?,?,?,?,?,?,?,?)");
			String query = "select * from data";
			ResultSet rs = st.executeQuery(query);
			int id,age, gender, cholesterol, trest_bp, chest_pain, blood_sugar, rest_ecg, thalach, exang, thal, height, weight, bmi=0, heart_attack;
			while (rs.next()) {
				id = rs.getInt("patient_id");
				age = rs.getInt("age");
				gender = rs.getInt("gender");
				cholesterol = rs.getInt("cholesterol");
				if(cholesterol<200)
					cholesterol = 1;
				else if(cholesterol>=200 && cholesterol<=239)
					cholesterol = 2;
				else if(cholesterol>=240 && cholesterol<=280)
					cholesterol = 3;
				else
					cholesterol = 4;
				
				trest_bp = rs.getInt("trest_bp");
				if(trest_bp<90)
					trest_bp = 0;
				else if(trest_bp >=90 && trest_bp<120)
					trest_bp = 1;
				else if(trest_bp >=120 && trest_bp<140)
					trest_bp = 2;
				else
					trest_bp = 3;
				chest_pain = rs.getInt("chest_pain_type");
				blood_sugar = rs.getInt("fasting_blood_sugar");
				rest_ecg = rs.getInt("rest_ecg");
				thalach = rs.getInt("thalach");
				if(age>0 && age<=20)
				{
					if(thalach>170)
						thalach = 1;
					else if(thalach<100)
						thalach = 0;
					else
						thalach = 2;
				}
				else if(age>20 && age<=35)
				{
					if(thalach>162)
						thalach = 1;
					else if(thalach<93)
						thalach = 0;
					else 
						thalach = 2;
				}
				else if(age>35 && age<=45)
				{
					if(thalach>157)
						thalach = 1;
					else if(thalach<88)
						thalach = 0;
					else
						thalach = 2;
				}
				else if(age>45 && age<=60)
				{
					if(thalach>149)
						thalach = 1;
					else if(thalach<80)
						thalach = 0;
					else
						thalach = 2;
				}
				else if(age>60 && age<=100)
				{
					if(thalach>138)
						thalach = 1;
					else if(thalach<75)
						thalach = 0;
					else
						thalach = 2;
				}
				exang = rs.getInt("exang");
				thal = rs.getInt("thal");
				height = rs.getInt("height");
				weight = rs.getInt("weight");
				float value = (float)(703 * weight) / (float) (height*height);
				if(value < 18.5)
					bmi = 0;
				else if(value >= 18.5 && value <= 24.9)
					bmi = 1;
				else if(value >= 25 && value <= 29.9)
					bmi = 2;
				else if(value >=30)
					bmi = 3;
				heart_attack = rs.getInt("heart_attack");
				System.out.println(age + " " + gender + " " + cholesterol + " "
						+ trest_bp + " " + chest_pain + " " + blood_sugar + " "
						+ rest_ecg + " " + thalach + " " + exang + " " + thal
						+ " " + height + " " + weight + " " + heart_attack);
				ps.setInt(1, id);
				ps.setInt(2, gender);
				ps.setInt(3, chest_pain);
				ps.setInt(4, trest_bp);
				ps.setInt(5, cholesterol);
				ps.setInt(6, blood_sugar);
				ps.setInt(7, rest_ecg);
				ps.setInt(8, thalach);
				ps.setInt(9, exang);
				ps.setInt(10, thal);
				ps.setInt(11, bmi);
				ps.setInt(12, heart_attack);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			Database.close(con);
		}
	}
	public void perform()
	{
		long start = System.currentTimeMillis();
		filterRecords();
		long end = System.currentTimeMillis();
		System.out.println("Total time taken:"+(end-start));
		start = System.currentTimeMillis();
		CalculateProbability calculate = new CalculateProbability();
		calculate.initializeFactors();
		calculate.updateBothTables();
		CalculateProbability.calculatingTrue = true;
		calculate.calculateProbability();
		CalculateProbability.calculatingTrue = false;
		calculate.calculateProbability();
		end = System.currentTimeMillis();
	}
	public void emptyTable(Connection con)
	{
		try
		{
			Statement st = con.createStatement();
			String query = "truncate table filtered_records";
			st.executeUpdate(query);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
