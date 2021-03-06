package servlets;

import lombok.SneakyThrows;
import random.MyRand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/GoSssXml")
public class Sss_Xml extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Thread.sleep(1000);
        int n = request.getIntHeader("XRand-N");
        int number = new MyRand().get(5, 10, true, true);
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\"  encoding = \"utf-8\" ?><rand>");
        for (int i = 0; i < number; i++)
            xmlString.append("<num>").append(new MyRand().get(-n, n, false, false).toString()).append("</num>");
        xmlString.append("</rand>");
        pw.println(xmlString);
    }
}
