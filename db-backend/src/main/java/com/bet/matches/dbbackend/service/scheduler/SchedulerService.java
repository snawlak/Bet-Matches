package com.bet.matches.dbbackend.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final TaskScheduler scheduler;
    private final Map<Integer, ScheduledFuture<?>> tasks = new HashMap<>();

    public void addMatchUpdatingTaskToScheduler(final int id, final OffsetDateTime startDate, final long period, final Runnable task) {
        final long timestamp = startDate.toInstant().toEpochMilli();
        final ScheduledFuture<?> scheduledTask = scheduler.scheduleAtFixedRate(task, new Date(timestamp), period);
        tasks.put(id, scheduledTask);
        log.info("Task with id: " + id + " has been added. Start time: " + startDate);
    }

    public void addTeamPlaceUpdatingTaskToScheduler(final int id, final OffsetDateTime startDate, final Runnable task) {
        final long startTimeWithDelay = startDate
                .plusHours(1)
                .toInstant()
                .toEpochMilli();
        final Date startTime = new Date(startTimeWithDelay);
        final ScheduledFuture<?> scheduledTask = scheduler.schedule(task, startTime);
        tasks.put(id, scheduledTask);
        log.info("Task with id: " + id + " has been added. Start time: " + startTimeWithDelay);
    }

    public void finishTask(final int id) {
        final ScheduledFuture<?> scheduledTask = tasks.get(id);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            tasks.put(id, null);
        }
    }

    // A context refresh event listener
    @EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent() {
        // Get all tasks from DB and reschedule them in case of context restarted
    }
}
