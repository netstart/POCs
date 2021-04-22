package com.specgraph.entitygraph.repository;

import com.specgraph.entitygraph.model.Comment;
import com.specgraph.entitygraph.model.Post;
import com.specgraph.entitygraph.model.User;
import com.specgraph.entitygraph.repository.specentitygraph.PostJpaSpecificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
//@RunWith(SpringRunner.class)
@Sql(scripts = "/entitygraph-data.sql")
public class PostTest {

  @Autowired
  private PostJpaSpecificationRepository repo;

  @Test
  public void find() {
    List<Post> post = repo.findSubjectSpecification("Como funciona?", "post-entity-graph");
    assertEquals(1, post.size());
    assertNotNull(post.get(0).getUser());
    String email = post.get(0).getUser().getEmail();
    assertNotNull(email);
  }

//  @Test
//  public void findWithEntityGraph() {
//    Post post = repo.findSubjectSpecification("Como funciona?", "post-entity-graph-with-comment-users");
//    assertNotNull(post.getUser());
//    String email = post.getUser().getEmail();
//    assertNotNull(email);
//    assertNotNull(post.getComments());
//    assertEquals(post.getComments().size(), 2);
//    Comment comment = post.getComments().get(0);
//    assertNotNull(comment);
//    User user = comment.getUser();
//    user.getEmail();
//  }
//
//  @Test
//  public void findWithEntityGraph2CommentWithUser() {
//    Post post = repo.findSubjectSpecification("Porque não ter uma classe email?", "post-entity-graph-with-comment-users");
//    assertNotNull(post.getComments());
//    assertEquals(post.getComments().size(), 2);
//    Comment comment = post.getComments().get(0);
//    assertNotNull(comment);
//    User user = comment.getUser();
//    assertNotNull(user);
//    assertEquals(user.getEmail(), "dany@gmail.com");
//  }

}