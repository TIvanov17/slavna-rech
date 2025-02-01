package pu.fmi.slavnarech.repositories.message;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.springframework.stereotype.Repository;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.connection.Connection_;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.member.Member_;
import pu.fmi.slavnarech.entities.message.Message;
import pu.fmi.slavnarech.entities.message.Message_;
import pu.fmi.slavnarech.entities.message.dtos.MessageDTO;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.User_;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;

@Repository
public class MessageRepositoryImpl implements MessageRepositoryCustom {

  @PersistenceContext private EntityManager entityManager;

  @Override
  public List<MessageDTO> findByConnectionId(Long connectionId) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<MessageDTO> query = cb.createQuery(MessageDTO.class);
    Root<Message> messageRoot = query.from(Message.class);
    Join<Message, Member> senderJoin = messageRoot.join(Message_.sender);
    Join<Member, User> senderUserJoin = senderJoin.join(Member_.user);
    Join<Member, Connection> connectionJoin = senderJoin.join(Member_.connection);

    query
        .select(
            cb.construct(
                MessageDTO.class,
                connectionJoin.get(Connection_.id),
                cb.construct(
                    UserResponse.class,
                    senderUserJoin.get(User_.id),
                    senderUserJoin.get(User_.username),
                    senderUserJoin.get(User_.email),
                    senderUserJoin.get(User_.createdOn),
                    senderUserJoin.get(User_.isActive)),
                messageRoot.get(Message_.content),
                messageRoot.get(Message_.createdOn)))
        .where(cb.equal(connectionJoin.get(Connection_.id), connectionId));

    return entityManager.createQuery(query).getResultList();
  }
}
