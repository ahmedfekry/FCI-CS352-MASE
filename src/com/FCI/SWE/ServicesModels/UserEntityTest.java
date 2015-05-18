package com.FCI.SWE.ServicesModels;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.dev.HighRepJobPolicy;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;


public class UserEntityTest {

	private static final class CustomHighRepJobPolicy implements HighRepJobPolicy {
	    static int newJobCounter = 0;
	    static int existingJobCounter = 0;

	    public boolean shouldApplyNewJob1(Key entityGroup) {
	      // every other new job fails to apply
	      return newJobCounter++ % 2 == 0;
	    }

	    public boolean shouldRollForwardExistingJob1(Key entityGroup) {
	      // every other existing job fails to apply
	      return existingJobCounter++ % 2 == 0;
	    }

		@Override
		public boolean shouldApplyNewJob(Key arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean shouldRollForwardExistingJob(Key arg0) {
			// TODO Auto-generated method stub
			return false;
		}
	  }

	
	  private final LocalServiceTestHelper helper =
		      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
		          .setAlternateHighRepJobPolicyClass(CustomHighRepJobPolicy.class));


	
	@BeforeMethod
	public void setUp() {
		helper.setUp();
	}

	
	@AfterMethod
	public void tearDown() {
		helper.tearDown();
	}
	////////////////////////////////////////////////////////////
	//			{"","" ,"" , },
	//			{"","" ,"" , }
	// data providers
	@DataProvider(name = "saveProvider")
	public static Object [][]saveProvider(){
		return new Object[][]{
				{"hadad","hadad.com" , "123", true},
				{"hadad","hadad.com" , "123", false}
		};
	}

	@DataProvider(name = "existProvider")
	public static Object [][]existProvider(){
		return new Object[][]{
				{"hadad", true},	
				{"hhh",false }

		};
	}

	@DataProvider(name = "getUserProvider")
	public static Object [][]getUserProvider(){
		return new Object[][]{
				{"hadad", "123", "hadad", "123"},	
				{"hadad", "1234", null, null},
				{"hhh", "123", null, null}
		};
	}
	////////////////////////////////////////////
	@Test(dataProvider = "saveProvider")
	public void saveUser(String name, String email, String password, boolean expected) {
		
		UserEntity user = new UserEntity(name, email, password);
		Assert.assertEquals(user.saveUser(), expected);

	}

	@Test(dataProvider ="existProvider" , dependsOnMethods = {"saveUser"})
	public void isExist(String name, boolean expected) {
		Assert.assertEquals(UserEntity.isExist(name), expected);
	}

	@Test(dataProvider ="getUserProvider", dependsOnMethods = {"saveUser"})
	public void getUser(String name, String password, String username, String pass) {
		User u = UserEntity.getUser(name, password);
		String uName = null;
		String uPassword = null;
		
		if (u!=null)
		{
			uName = u.getName();
			uPassword = u.getPass();
		}
		Assert.assertEquals(uName, username);
		Assert.assertEquals(uPassword, pass);
		
		
	}

	@Test
	public void addFriend() {

	}

	@Test
	public void getFriends() {

	}



}
;