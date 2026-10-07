package com.careerconnect;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/apply")
public class ApplyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String job = request.getParameter("job");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Connection con = DBConnection.getConnection();

            // Find user ID using email
            String userSql = "SELECT id FROM users WHERE email = ?";

            PreparedStatement userPs = con.prepareStatement(userSql);
            userPs.setString(1, email);

            ResultSet userRs = userPs.executeQuery();

            if (!userRs.next()) {
                out.println("<h2>User not found!</h2>");
                out.println("<a href='apply.html'>Try Again</a>");

                userRs.close();
                userPs.close();
                con.close();
                return;
            }

            int userId = userRs.getInt("id");

            userRs.close();
            userPs.close();

            // Find job ID using job title
            String jobSql = "SELECT id FROM jobs WHERE title = ?";

            PreparedStatement jobPs = con.prepareStatement(jobSql);
            jobPs.setString(1, job);

            ResultSet jobRs = jobPs.executeQuery();

            if (!jobRs.next()) {
                out.println("<h2>Job not found!</h2>");
                out.println("<a href='jobs.html'>Back to Jobs</a>");

                jobRs.close();
                jobPs.close();
                con.close();
                return;
            }

            int jobId = jobRs.getInt("id");

            jobRs.close();
            jobPs.close();

            // Insert application
            String applicationSql =
                    "INSERT INTO applications (user_id, job_id, status) " +
                    "VALUES (?, ?, 'Applied')";

            PreparedStatement applicationPs =
                    con.prepareStatement(applicationSql);

            applicationPs.setInt(1, userId);
            applicationPs.setInt(2, jobId);

            int result = applicationPs.executeUpdate();

            if (result > 0) {
                out.println("<h2>Application Submitted Successfully!</h2>");
                out.println("<a href='jobs.html'>Back to Jobs</a>");
            }

            applicationPs.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<h2>Application Failed!</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}