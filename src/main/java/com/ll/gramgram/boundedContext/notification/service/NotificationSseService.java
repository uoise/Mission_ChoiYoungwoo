package com.ll.gramgram.boundedContext.notification.service;

import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationSseService {
    public SseEmitter createUnreadNotificationsSseEmitter(final InstaMember toInstaMember, final List<Notification> unreadNotifications) {
        SseEmitter emitter = new SseEmitter();
        unreadNotifications.forEach(notification -> {
            if (notification.getToInstaMember().equals(toInstaMember)) {
                try {
                    SseEmitter.SseEventBuilder eventBuilder = SseEmitter.event()
                            .id(Long.toString(notification.getId()))
                            .name(notification.getTypeCode())
                            .data(notification);
                    emitter.send(eventBuilder);
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }
        });

        return emitter;
    }
}
