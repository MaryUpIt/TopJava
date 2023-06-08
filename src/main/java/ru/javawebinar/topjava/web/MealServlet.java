package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.repository.MealsRepository;
import ru.javawebinar.topjava.repository.MemoryMealRepository;
import ru.javawebinar.topjava.util.MealsConstants;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealsRepository repository;

    @Override
    public void init() {
        repository = new MemoryMealRepository();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.mealsTo(repository.getAll(), MealsConstants.CALORIES_PER_DAY));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
        assert action != null;
        switch (action) {
            case "delete":
                Integer id = getId(request);
                log.info("delete id={}", id);
                repository.delete(id);
                response.sendRedirect("meals");
                break;
            case "add":
            case "edit":
                Meal meal = "add".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        repository.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        Integer id = getId(request);
        meal.setId(id);
        log.info(meal.isNew() ? "save" : "edit");
        repository.save(meal);
        response.sendRedirect("meals");


    }

    private Integer getId(HttpServletRequest request) {
        String paramId = request.getParameter("id");
        return paramId.isEmpty() ? null : Integer.parseInt(paramId);
    }
}
