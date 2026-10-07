package com.careerconnect;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        String sql = "INSERT INTO users (name, email, password, phone) VALUES (?, ?, ?, ?)";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);

            int result = ps.executeUpdate();

           if (result > 0) {
    response.sendRedirect("login.html");
}

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Registration Failed!</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}