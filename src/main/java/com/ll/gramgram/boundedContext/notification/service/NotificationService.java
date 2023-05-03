package com.ll.gramgram.boundedContext.notification.service;

import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.notification.entity.Notification;
import com.ll.gramgram.boundedContext.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationSseService notificationSseService;

    @Transactional
    public void createNewNotify(LikeablePerson likeablePerson) {
        Notification notification = Notification.builder()
                .fromInstaMember(likeablePerson.getFromInstaMember())
                .toInstaMember(likeablePerson.getToInstaMember())
                .typeCode("Like")
                .oldGender(null)
                .newGender(likeablePerson.getFromInstaMember().getGender())
                .oldAttractiveTypeCode(0)
                .newAttractiveTypeCode(likeablePerson.getAttractiveTypeCode())
                .build();
        notificationRepository.save(notification);
        sendSseWithUnreadLists(likeablePerson.getToInstaMember(), notification);
    }

    @Transactional
    public void createModNotify(LikeablePerson likeablePerson, int oldAttractiveTypeCode) {
        Notification notification = Notification.builder()
                .fromInstaMember(likeablePerson.getFromInstaMember())
                .toInstaMember(likeablePerson.getToInstaMember())
                .typeCode("ModifyAttractiveType")
                .oldGender(null)
                .newGender(likeablePerson.getFromInstaMember().getGender())
                .oldAttractiveTypeCode(oldAttractiveTypeCode)
                .newAttractiveTypeCode(likeablePerson.getAttractiveTypeCode())
                .build();
        notificationRepository.save(notification);
        sendSseWithUnreadLists(likeablePerson.getToInstaMember(), notification);
    }

    public List<Notification> findByToInstaMember(InstaMember toInstaMember) {
        return notificationRepository.findByToInstaMember(toInstaMember);
    }

    public List<Notification> findUnreadByToInstaMember(InstaMember toInstaMember) {
        return notificationRepository.findByToInstaMemberAndReadDateIsNull(toInstaMember);
    }

    private void sendSseWithUnreadLists(InstaMember toInstaMember, Notification notification) {
        List<Notification> unreadNotifications = findUnreadByToInstaMember(toInstaMember);
        unreadNotifications.add(notification);
        notificationSseService.createUnreadNotificationsSseEmitter(toInstaMember, unreadNotifications);
    }
}
