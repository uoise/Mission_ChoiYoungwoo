package com.ll.gramgram.boundedContext.notification.eventListener;

import com.ll.gramgram.boundedContext.notification.event.EventModNotify;
import com.ll.gramgram.boundedContext.notification.event.EventNewNotify;
import com.ll.gramgram.boundedContext.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationEventListener {
    private final NotificationService notificationService;

    @EventListener
    public void listen(EventNewNotify event) {
        notificationService.createNewNotify(event.getLikeablePerson());
    }

    @EventListener
    public void listen(EventModNotify event) {
        notificationService.createModNotify(event.getLikeablePerson(), event.getOldAttractiveTypeCode());
    }
}
