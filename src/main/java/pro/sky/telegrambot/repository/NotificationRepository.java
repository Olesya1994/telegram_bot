package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.NotificationTask;

import java.util.Collection;

public interface NotificationRepository extends JpaRepository<NotificationTask, Long> {

    Collection<NotificationTask> findAllByDateTime();
}
