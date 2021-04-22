package com.specgraph.entitygraph.repository;

import com.specgraph.entitygraph.model.Comment;
import com.specgraph.entitygraph.model.Post;
import com.specgraph.entitygraph.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
//@RunWith(SpringRunner.class)
@Sql(scripts = "/entitygraph-data.sql")
public class PostTest {

  @Autowired
  private PostRepository postRepository;

  /*
   * Pode haver tomar um LazyInitializationException.class, aconteceria quando
   * cria EntityManager na mão, ao invés do gerenciado pelo Spring. Essa
   * diferença se deve porque a implementação concreta efetivamente é diferente,
   * a gerenciada pelo Spring alé de realizar outro select, ele faz o close() no
   * EntityManager
   */
  @Test
  public void find() {
    Post post = postRepository.find(1L);
    assertNotNull(post.getUser());
    String email = post.getUser().getEmail();
    assertNotNull(email);
  }

  @Test
  public void findWithEntityGraph() {
    Post post = postRepository.findWithEntityGraph(1L);
    assertNotNull(post.getUser());
    String email = post.getUser().getEmail();
    assertNotNull(email);
  }

  /*
   * Pode haver tomar um LazyInitializationException.class, aconteceria quando
   * cria EntityManager na mão, ao invés do gerenciado pelo Spring. Essa
   * diferença se deve porque a implementação concreta efetivamente é diferente,
   * a gerenciada pelo Spring alé de realizar outro select, ele faz o close() no
   * EntityManager
   */
  @Test()
  public void findWithEntityGraph_Comment_Without_User() {
    Post post = postRepository.findWithEntityGraph(1L);
    assertNotNull(post.getUser());
    String email = post.getUser().getEmail();
    assertNotNull(email);
    assertNotNull(post.getComments());
    assertEquals(post.getComments().size(), 2);
    Comment comment = post.getComments().get(0);
    assertNotNull(comment);
    User user = comment.getUser();
    user.getEmail();
  }

  @Test
  public void findWithEntityGraph2CommentWithUser() {
    Post post = postRepository.findWithEntityGraph2(1L);
    assertNotNull(post.getComments());
    assertEquals(post.getComments().size(), 2);
    Comment comment = post.getComments().get(0);
    assertNotNull(comment);
    User user = comment.getUser();
    assertNotNull(user);
    assertEquals(user.getEmail(), "dany@gmail.com");
  }

}