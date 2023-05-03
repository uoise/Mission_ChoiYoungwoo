package com.ll.gramgram.boundedContext.notification.controller;

import com.ll.gramgram.base.rq.Rq;
import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.notification.entity.Notification;
import com.ll.gramgram.boundedContext.notification.service.NotificationService;
import com.ll.gramgram.boundedContext.notification.service.NotificationSseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/sse/notification")
@RequiredArgsConstructor
public class NotificationSseController {
    private final NotificationService notificationService;
    private final NotificationSseService notificationSseService;
    private final Rq rq;

    @GetMapping("/unread")
    public SseEmitter getUnreadNotifications() {
        InstaMember toInstaMember = rq.getMember().getInstaMember();
        List<Notification> notifications = notificationService.findUnreadByToInstaMember(toInstaMember);
        return notificationSseService.createUnreadNotificationsSseEmitter(rq.getMember().getInstaMember(), notifications);
    }
}
