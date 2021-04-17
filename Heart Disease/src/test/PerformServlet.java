package test;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Servlet implementation class PerformServlet
 */
@WebServlet("/PerformServlet")
public class PerformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PerformServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		int age = Integer.parseInt(request.getParameter("age"));
		int gender = Integer.parseInt(request.getParameter("gender"));
		int trest_bp = Integer.parseInt(request.getParameter("trest_bp"));
		trest_bp = getTrestBP(trest_bp);
		int chest_pain = Integer.parseInt(request.getParameter("chest_pain"));
		int cholesterol = Integer.parseInt(request.getParameter("cholesterol"));
		cholesterol = getCholesterol(cholesterol);
		int blood_sugar = Integer.parseInt(request
				.getParameter("fasting_sugar"));
		int rest_ecg = Integer.parseInt(request.getParameter("rest_ecg"));
		int exang = Integer.parseInt(request.getParameter("exang"));
		int thalach = Integer.parseInt(request.getParameter("thalach"));
		thalach = getThalach(age, thalach);
		int thal = Integer.parseInt(request.getParameter("thal"));
		int weight = Integer.parseInt(request.getParameter("weight"));
		int height = Integer.parseInt(request.getParameter("height"));
		int bmi = getBmi(weight, height);
		DataFilter filter = new DataFilter();
		filter.perform();
		try {
			Connection con = Database.getConnection();
			Statement st = con.createStatement();
			float probability_yes = 0, probability_no = 0;
			probability_yes = CalculateProbability.EXTERNAL_HA_YES
					* getProbability(con, "gender", gender, "yes")
					* getProbability(con, "chest_pain", chest_pain, "yes")
					* getProbability(con, "trest_bp", trest_bp, "yes")
					* getProbability(con, "cholesterol", cholesterol, "yes")
					* getProbability(con, "blood_sugar", blood_sugar, "yes")
					* getProbability(con, "rest_ecg", rest_ecg, "yes")
					* getProbability(con, "thalach", thalach, "yes")
					* getProbability(con, "exang", exang, "yes")
					* getProbability(con, "thal", thal, "yes")
					* getProbability(con, "bmi", bmi, "yes");
			System.out.println("HA_YES probability:"
					+ CalculateProbability.EXTERNAL_HA_YES + "*"
					+ getProbability(con, "gender", gender, "yes") + "*"
					+ getProbability(con, "chest_pain", chest_pain, "yes")
					+ "* " + getProbability(con, "trest_bp", trest_bp, "yes")
					+ "*"
					+ getProbability(con, "cholesterol", cholesterol, "yes")
					+ "*"
					+ getProbability(con, "blood_sugar", blood_sugar, "yes")
					+ "*" + getProbability(con, "rest_ecg", rest_ecg, "yes")
					+ "*" + getProbability(con, "thalach", thalach, "yes")
					+ "*" + getProbability(con, "exang", exang, "yes") + "*"
					+ getProbability(con, "thal", thal, "yes") + "*"
					+ getProbability(con, "bmi", bmi, "yes"));
			probability_no = CalculateProbability.EXTERNAL_HA_NO
					* getProbability(con, "gender", gender, "no")
					* getProbability(con, "chest_pain", chest_pain, "no")
					* getProbability(con, "trest_bp", trest_bp, "no")
					* getProbability(con, "cholesterol", cholesterol, "no")
					* getProbability(con, "blood_sugar", blood_sugar, "no")
					* getProbability(con, "rest_ecg", rest_ecg, "no")
					* getProbability(con, "thalach", thalach, "no")
					* getProbability(con, "exang", exang, "no")
					* getProbability(con, "thal", thal, "no")
					* getProbability(con, "bmi", bmi, "no");
			System.out.println("HA_YES probability:"
					+ CalculateProbability.EXTERNAL_HA_NO + "*"
					+ getProbability(con, "gender", gender, "no") + "*"
					+ getProbability(con, "chest_pain", chest_pain, "no") + "*"
					+ getProbability(con, "trest_bp", trest_bp, "no") + "*"
					+ getProbability(con, "cholesterol", cholesterol, "no")
					+ "*"
					+ getProbability(con, "blood_sugar", blood_sugar, "no")
					+ "*" + getProbability(con, "rest_ecg", rest_ecg, "no")
					+ "*" + getProbability(con, "thalach", thalach, "no") + "*"
					+ getProbability(con, "exang", exang, "no") + "*"
					+ getProbability(con, "thal", thal, "no") + "*"
					+ getProbability(con, "bmi", bmi, "no"));
			response.setContentType("text/html");
			String result = "";
			int reveal = Float.compare(probability_yes, probability_no);
			if (reveal > 0)
			{
				out.write("User having heart disease <br> Yes Probability:"
						+ probability_yes + " <br> No Probability"
						+ probability_no);
				result = "yes";
			}
			else
			{
				out.write("User not having heart disease <br> Yes Probability:"
						+ probability_yes + " <br> No Probability"
						+ probability_no);
				result = "no";
			}
			out.write("<br>Go back to main page? <a href=\"decide.jsp\">Click here</a>");
			PreparedStatement ps = con
					.prepareStatement("insert into history(age,gender,trest_bp,chest_pain,cholesterol,fasting_sugar,rest_ecg,exercise_angin,max_heart_rate,thal,weight,height,yes_probability,no_probability,result) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, age);
			ps.setString(2, DataFilter.map.get("gender"+request.getParameter("gender")));
			ps.setInt(3, trest_bp);
			ps.setString(4, DataFilter.map.get("chest-pain"+request.getParameter("chest_pain")));
			ps.setInt(5, cholesterol);
			ps.setString(6, DataFilter.map.get("fasting-sugar"+request.getParameter("fasting_sugar")));
			ps.setString(7, DataFilter.map.get("rest-ecg"+request.getParameter("rest_ecg")));
			ps.setString(8, DataFilter.map.get("exang"+request.getParameter("exang")));
			ps.setInt(9, thalach);
			ps.setInt(10, thal);
			ps.setInt(11, weight);
			ps.setInt(12, height);
			ps.setFloat(13, probability_yes);
			ps.setFloat(14, probability_no);
			ps.setString(15, result);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public int getTrestBP(int trest_bp) {
		if (trest_bp < 90)
			trest_bp = 0;
		else if (trest_bp >= 90 && trest_bp < 120)
			trest_bp = 1;
		else if (trest_bp >= 120 && trest_bp < 140)
			trest_bp = 2;
		else
			trest_bp = 3;
		return trest_bp;
	}

	public int getCholesterol(int cholesterol) {
		if (cholesterol < 200)
			cholesterol = 1;
		else if (cholesterol >= 200 && cholesterol <= 239)
			cholesterol = 2;
		else if (cholesterol >= 240 && cholesterol <= 280)
			cholesterol = 3;
		else
			cholesterol = 4;
		return cholesterol;
	}

	public int getThalach(int age, int thalach) {
		if (age > 0 && age <= 20) {
			if (thalach > 170)
				thalach = 1;
			else if (thalach < 100)
				thalach = 0;
			else
				thalach = 2;
		} else if (age > 20 && age <= 35) {
			if (thalach > 162)
				thalach = 1;
			else if (thalach < 93)
				thalach = 0;
			else
				thalach = 2;
		} else if (age > 35 && age <= 45) {
			if (thalach > 157)
				thalach = 1;
			else if (thalach < 88)
				thalach = 0;
			else
				thalach = 2;
		} else if (age > 45 && age <= 60) {
			if (thalach > 149)
				thalach = 1;
			else if (thalach < 80)
				thalach = 0;
			else
				thalach = 2;
		} else if (age > 60 && age <= 100) {
			if (thalach > 138)
				thalach = 1;
			else if (thalach < 75)
				thalach = 0;
			else
				thalach = 2;
		}
		return thalach;
	}

	public int getBmi(int weight, int height) {
		int bmi = 0;
		float value = (float) (703 * weight) / (float) (height * height);
		if (value < 18.5)
			bmi = 0;
		else if (value >= 18.5 && value <= 24.9)
			bmi = 1;
		else if (value >= 25 && value <= 29.9)
			bmi = 2;
		else if (value >= 30)
			bmi = 3;
		return bmi;
	}

	public float getProbability(Connection con, String attribute,
			int attributeValue, String yesno) {
		float value = 0;
		try {
			Statement st = con.createStatement();
			String query = "select probability from probability_storage_"
					+ yesno + " where attribute='" + attribute
					+ "' and attribute_value=" + attributeValue;
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
				value = rs.getFloat(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}

}
