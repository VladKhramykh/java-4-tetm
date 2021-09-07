package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dao.LinkRepo;
import entity.Link;
import entity.LinkDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/References")
public class References extends HttpServlet {
    private LinkRepo linkRepo;

    @Override
    public void init() {
        try {
            linkRepo = new LinkRepo(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String sessionId = "";
            for (Cookie cookie : request.getCookies())
                if (cookie.getName().equals("JSESSIONID"))
                    sessionId = cookie.getValue();
            String json = new Gson().toJson(linkRepo.getReferences(request.getParameter("filter")));
            response.setContentType("application/json");
            response.setHeader("sessionId", sessionId);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json = request.getReader().readLine();
            System.out.println(json);
            LinkDto linkDto = new Gson().fromJson(json, LinkDto.class);
            if (linkDto.getRole() != null && linkDto.getRole().equals("admin")) {
                Link link = new Link(
                        linkDto.getId(),
                        linkDto.getUrl(),
                        linkDto.getDescription(),
                        linkDto.getMinus(),
                        linkDto.getPlus()
                );
                linkRepo.addReference(link);
            } else {
                response.setStatus(403);
                response.getWriter().println("You dont have permissions");
            }
        } catch (EOFException | SQLException | JsonSyntaxException exception) {
            response.setStatus(403);
            response.getWriter().println("You dont have admin permissions");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json = request.getReader().readLine();
            LinkDto linkDto = new Gson().fromJson(json, LinkDto.class);
            Link link = new Link();
            link.setId(linkDto.getId());
            link.setPlus(linkDto.getPlus());
            link.setMinus(linkDto.getMinus());
            if (linkDto.getRole() != null && linkDto.getRole().equals("admin")) {
                if (linkDto.getDescription() != null) {
                    link.setDescription(linkDto.getDescription());
                }
                if (linkDto.getUrl() != null) {
                    link.setUrl(linkDto.getUrl());
                }
            } else {
                response.setStatus(403);
                response.getWriter().println("You dont have permissions");
            }
            linkRepo.updateReference(link);
        } catch (EOFException | SQLException e) {
            response.setStatus(403);
            response.getWriter().println("You dont have permissions");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("role") != null && request.getParameter("role").equals("admin")) {
                linkRepo.deleteReference(Integer.parseInt(request.getParameter("id")));
            } else {
                response.setStatus(403);
                response.getWriter().println("You dont have permissions");
            }
        } catch (Exception e) {
            response.setStatus(403);
            response.getWriter().println("You dont have permissions");
        }
    }
}
