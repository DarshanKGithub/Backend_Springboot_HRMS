package com.hrms.notifications;

import com.hrms.notifications.entity.*;
import com.hrms.notifications.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NotificationDataAccessTest {

    @Autowired
    private NotificationTemplateRepository notificationTemplateRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationPreferenceRepository notificationPreferenceRepository;

    @Test
    void shouldSaveAndLoadNotificationEntities() {
        NotificationTemplate template = new NotificationTemplate();
        template.setName("Leave Approved");
        template.setDescription("Leave approval email");
        template.setEventType("leave_approved");
        template.setChannel("email");
        template.setSubject("Leave Approved");
        template.setBody("Your leave has been approved.");
        template.setActive(true);

        NotificationTemplate savedTemplate = notificationTemplateRepository.save(template);

        Notification notification = new Notification();
        notification.setRecipientId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        notification.setTemplateId(savedTemplate.getId());
        notification.setEventType("leave_approved");
        notification.setChannel("email");
        notification.setSubject("Leave Approved");
        notification.setMessage("Your leave has been approved.");
        notification.setData("{\"leaveId\":\"L-123\"}");
        notification.setStatus("Sent");
        notification.setSentAt(LocalDateTime.now());

        Notification savedNotification = notificationRepository.save(notification);

        NotificationPreference preference = new NotificationPreference();
        preference.setUserId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        preference.setEmailEnabled(true);
        preference.setSmsEnabled(false);
        preference.setInAppEnabled(true);
        preference.setPushEnabled(true);
        preference.setQuietHoursStart("22:00");
        preference.setQuietHoursEnd("07:00");

        NotificationPreference savedPreference = notificationPreferenceRepository.save(preference);

        assertThat(savedTemplate.getId()).isNotNull();
        assertThat(notificationTemplateRepository.findById(savedTemplate.getId())).isPresent();
        assertThat(savedNotification.getId()).isNotNull();
        assertThat(notificationRepository.findById(savedNotification.getId())).isPresent();
        assertThat(savedPreference.getId()).isNotNull();
        assertThat(notificationPreferenceRepository.findById(savedPreference.getId())).isPresent();
    }
}
