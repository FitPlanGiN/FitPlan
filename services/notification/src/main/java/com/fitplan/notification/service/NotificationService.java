package com.fitplan.notification.service;


import com.fitplan.workout.event.WorkoutCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "workout-created", groupId = "notification")
    public void listenWorkoutCreated(WorkoutCreatedEvent workoutCreatedEvent) {
        log.info("NotificationService - Workout created event received from Kafka topic workout-created: {}", workoutCreatedEvent);
        //send email to user
        String firstName = workoutCreatedEvent.getFirstName() != null ? workoutCreatedEvent.getFirstName().toString() : "Unknown";
        String lastName = workoutCreatedEvent.getLastName() != null ? workoutCreatedEvent.getLastName().toString() : "User";
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("fitplan@email.com");
            messageHelper.setTo(workoutCreatedEvent.getEmail().toString());
            messageHelper.setSubject(String.format("Your Workout with id %s is placed successfully", workoutCreatedEvent.getId()));
            messageHelper.setText(String.format("""
                            Hi, %s, %s

                            Your Workout with id %s is now placed successfully.

                            Best Regards
                            FitPlan Team
                            """,
                    firstName,
                    lastName,
                    workoutCreatedEvent.getEmail()));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Workout Notification email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail", e);
        }
    }
}

