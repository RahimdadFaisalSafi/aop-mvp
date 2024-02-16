package software.potthoff.aopmvp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.potthoff.aopmvp.aspect.PerformanceLoggingAnnotation;

import java.util.Random;

@Service
@Slf4j
public class TestService {
    private final Random random = new Random();

    @PerformanceLoggingAnnotation("2000")
    public long someLongRunningMethod(long maxExecutionTimeInMillis) {
        long startTime = System.currentTimeMillis();

        long elapsedTimeAfterFirstStep = executeStepWithRandomDelay(maxExecutionTimeInMillis, startTime);

        // Check if there's still time left to execute the second step
        if (elapsedTimeAfterFirstStep < maxExecutionTimeInMillis) {
            // Calculate remaining time for the execution
            long remainingTime = maxExecutionTimeInMillis - elapsedTimeAfterFirstStep;

            // Optionally execute the second step based on the remaining time
            executeStepWithOptionalRandomDelay(remainingTime);
        }
        return System.currentTimeMillis()-startTime;
    }

    @PerformanceLoggingAnnotation("2000")
    private long executeStepWithRandomDelay(long maxExecutionTimeInMillis, long startTime) {
        long delay = getRandomDelay(maxExecutionTimeInMillis, startTime);
        log.info("Executing first step with delay: " + delay + "ms");

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("First step execution was interrupted.");
        }

        return System.currentTimeMillis() - startTime;
    }

    @PerformanceLoggingAnnotation("2000")
    private void executeStepWithOptionalRandomDelay(long remainingTimeInMillis) {
        if (remainingTimeInMillis > 0) {
            long delay = random.nextInt((int) Math.min(5000, remainingTimeInMillis));
            log.info("Executing second step with delay: " + delay + "ms");

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("Second step execution was interrupted.");
            }
        } else {
            log.info("No remaining time to execute the second step.");
        }
    }

    @PerformanceLoggingAnnotation("2000")
    private long getRandomDelay(long maxExecutionTimeInMillis, long startTime) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long remainingTime = maxExecutionTimeInMillis - elapsedTime;
        // Ensure there is a positive remaining time, and limit the delay to a maximum of 5 seconds or the remaining time
        return remainingTime > 0 ? random.nextInt((int) Math.min(5000, remainingTime)) : 0;
    }
}
