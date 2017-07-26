package priceComparison.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ThankYou
 */
@WebServlet("/ThankYou")
public class ThankYou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ThankYou() { super(); }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher thankYouPage = request.getRequestDispatcher("WEB-INF/views/thankYou.jsp");
    	thankYouPage.forward(request, response);
    }
}
