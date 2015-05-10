package com.FCI.SWE.ServicesModels;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;


@Test
public class ConversationEntityTest {
	
  public void ConversationEntity() {
//    throw new RuntimeException("Test not implemented");
  }
  private final LocalServiceTestHelper helper =
	      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	  @Before
	  public void setUp() {
	    helper.setUp();
	  }

	  @After
	  public void tearDown() {
	    helper.tearDown();
	  }

  public void addToConversation() {
//	  UserEntity user = new UserEntity("test1", "test1@test.com", "123456");
//	  user.saveUser();
	  
//    throw new RuntimeException("Test not implemented");
  }

  public void createConversation() {
		UserEntity user = new UserEntity("test1", "test1@test.com", "123456");
		user.saveUser();

	  Assert.assertNotEquals(-1, ConversationEntity.createConversation(user.getName(), "test"));
//    throw new RuntimeException("Test not implemented");
  }

  public void getConversation() {
//    throw new RuntimeException("Test not implemented");
  }

  public void getDate() {
//    throw new RuntimeException("Test not implemented");
  }

  public void getId() {
//    throw new RuntimeException("Test not implemented");
  }

  public void getMembers() {
//    throw new RuntimeException("Test not implemented");
  }

  public void getName() {
//    throw new RuntimeException("Test not implemented");
  }

  public void getOwner() {
//    throw new RuntimeException("Test not implemented");
  }

  public void setDate() {
//    throw new RuntimeException("Test not implemented");
  }

  public void setId() {
//    throw new RuntimeException("Test not implemented");
  }

  public void setMembers() {
//    throw new RuntimeException("Test not implemented");
  }

  public void setName() {
//    throw new RuntimeException("Test not implemented");
  }

  public void setOwner() {
//    throw new RuntimeException("Test not implemented");
  }
}
