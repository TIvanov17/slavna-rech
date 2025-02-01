package pu.fmi.slavnarech.repositories.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import pu.fmi.slavnarech.entities.connection.Connection;
import pu.fmi.slavnarech.entities.connection.ConnectionType;
import pu.fmi.slavnarech.entities.connection.Connection_;
import pu.fmi.slavnarech.entities.member.Member;
import pu.fmi.slavnarech.entities.member.MemberStatus;
import pu.fmi.slavnarech.entities.member.Member_;
import pu.fmi.slavnarech.entities.user.User;
import pu.fmi.slavnarech.entities.user.User_;
import pu.fmi.slavnarech.entities.user.dtos.UserConnectionResponse;
import pu.fmi.slavnarech.utils.PageFilter;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  public Page<UserConnectionResponse> getFriendInvitesFor(Long id, PageFilter pageFilter) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserConnectionResponse> query = criteriaBuilder.createQuery(
        UserConnectionResponse.class);
    Root<User> root = query.from(User.class);

    Join<User, Member> member = root.join(User_.members);
    Join<Member, Connection> connection = member.join(Member_.connection);

    Subquery<Long> subquery = query.subquery(Long.class);
    Root<Member> memberSubquery = subquery.from(Member.class);
    Join<Member, Connection> connectionSubquery = memberSubquery.join(Member_.connection);
    Join<Member, User> memberUser = memberSubquery.join(Member_.user);

    subquery.select(memberUser.get(User_.id));
    subquery.where(
        criteriaBuilder.equal(connectionSubquery.get(Connection_.id),
            connectionSubquery.get(Connection_.id)),
        criteriaBuilder.notEqual(memberSubquery.get(Member_.user).get(User_.id),
            root.get(User_.id)),
        criteriaBuilder.equal(memberSubquery.get(Member_.status), MemberStatus.INVITED)
    );

    query.select(
        criteriaBuilder.construct(
            UserConnectionResponse.class,
            connection.get(Connection_.id),
            connection.get(Connection_.createdOn),
            root.get(User_.id),
            root.get(User_.username),
            root.get(User_.email),
            root.get(User_.createdOn),
            root.get(User_.isActive)
        )
    ).where(
        criteriaBuilder.and(
            criteriaBuilder.exists(subquery),
            criteriaBuilder.notEqual(root.get(User_.id), id),
            criteriaBuilder.equal(member.get(Member_.status), MemberStatus.ACCEPTED)
        )
    );

    TypedQuery<UserConnectionResponse> typedQuery = entityManager.createQuery(query);

    typedQuery.setFirstResult((int) pageFilter.getOffset());
    typedQuery.setMaxResults(pageFilter.getPageSize());

    List<UserConnectionResponse> results = typedQuery.getResultList();
    return new PageImpl<>(results, pageFilter, results.size());
  }

  @Override
  public Page<UserConnectionResponse> getFriendsOfUser(Long id, PageFilter pageFilter) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<UserConnectionResponse> query = criteriaBuilder.createQuery(
        UserConnectionResponse.class);
    Root<User> root = query.from(User.class);

    Join<User, Member> memberJoin = root.join(User_.members);
    Join<Member, Connection> connectionJoin = memberJoin.join(Member_.connection);

    Subquery<Long> subquery = query.subquery(Long.class);
    Root<Member> memberSubquery = subquery.from(Member.class);
    Join<Member, Connection> connectionSubquery = memberSubquery.join(Member_.connection);
    Join<Member, User> userSubquery = memberSubquery.join(Member_.user);

    subquery.select(userSubquery.get(User_.id));
    subquery.where(
        criteriaBuilder.equal(connectionJoin.get(Connection_.id),connectionSubquery.get(Connection_.id)),
        criteriaBuilder.equal(memberSubquery.get(Member_.user).get(User_.id), id),
        criteriaBuilder.equal(memberSubquery.get(Member_.status), MemberStatus.ACCEPTED),
        criteriaBuilder.equal(connectionSubquery.get(Connection_.connectionType), ConnectionType.FRIENDS)
    );
    query.select(
        criteriaBuilder.construct(
            UserConnectionResponse.class,
            connectionJoin.get(Connection_.id),
            connectionJoin.get(Connection_.createdOn),
            root.get(User_.id),
            root.get(User_.username),
            root.get(User_.email),
            root.get(User_.createdOn),
            root.get(User_.isActive)
        )
    ).where(
        criteriaBuilder.and(
            criteriaBuilder.exists(subquery),
            criteriaBuilder.equal(memberJoin.get(Member_.status), MemberStatus.ACCEPTED),
            criteriaBuilder.equal(connectionJoin.get(Connection_.connectionType), ConnectionType.FRIENDS),
            criteriaBuilder.notEqual(root.get(User_.id), id)
        )
    );

    TypedQuery<UserConnectionResponse> typedQuery = entityManager.createQuery(query);

    typedQuery.setFirstResult((int) pageFilter.getOffset());
    typedQuery.setMaxResults(pageFilter.getPageSize());

    List<UserConnectionResponse> results = typedQuery.getResultList();
    return new PageImpl<>(results, pageFilter, results.size());
  }

  @Override
  public Page<Connection> getChannels(Long id, PageFilter pageFilter) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Connection> query = criteriaBuilder.createQuery(Connection.class);

    Root<User> root = query.from(User.class);
    Join<User, Member> memberJoin = root.join(User_.members);
    Join<Member, Connection> connectionJoin = memberJoin.join(Member_.connection);

    query.select(connectionJoin)
        .where(
          criteriaBuilder.and(
              criteriaBuilder.equal(connectionJoin.get(Connection_.connectionType), ConnectionType.CHANNEL),
              criteriaBuilder.equal(memberJoin.get(Member_.status), MemberStatus.ACCEPTED),
              criteriaBuilder.equal(root.get(User_.id), id)
          )
    );

    TypedQuery<Connection> typedQuery = entityManager.createQuery(query);
    typedQuery.setFirstResult((int) pageFilter.getOffset());
    typedQuery.setMaxResults(pageFilter.getPageSize());
    List<Connection> results = typedQuery.getResultList();
    return new PageImpl<>(results, pageFilter, results.size());
  }

}
