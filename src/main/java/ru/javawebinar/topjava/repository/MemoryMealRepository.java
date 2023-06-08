package ru.javawebinar.topjava.repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsConstants;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MemoryMealRepository implements MealsRepository {
    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    {
        MealsConstants.MEALS.forEach(this::save);
    }


    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(idCounter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }
}
