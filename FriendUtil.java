package Project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FriendUtil {
	
	public Map<String, Set<UserInfo>> addFriends(UserInfo user1, UserInfo user2, Map<String, Set<UserInfo>> friendDirectory) {
		if (!friendDirectory.containsKey(user1.getUserID()) || !friendDirectory.containsKey(user2.getUserID())){
			// throw exception
			System.out.println("User does not exist");
			return Collections.emptyMap();	
		}
		
		// add friend for user1
		Set<UserInfo> user1Friends = friendDirectory.get(user1);
		user1Friends.add(user2);
		friendDirectory.put(user1.getUserID(), user1Friends);
		
		// add friend for user2
		Set<UserInfo> user2Friends = friendDirectory.get(user2);
		user2Friends.add(user2);
		friendDirectory.put(user2.getUserID(), user2Friends);
		
		return friendDirectory;
	}
	
	public Map<String, Set<UserInfo>> deleteFriends(UserInfo user1, UserInfo user2, Map<String, Set<UserInfo>> friendDirectory) {
		
		if (!friendDirectory.containsKey(user1.getUserID()) || !friendDirectory.containsKey(user2.getUserID())){
			// unexpected issue - handle exception
			System.out.println("User does not exist");
			return Collections.emptyMap();	
		}
		
		// remove friend from user1
		Set<UserInfo> user1Friends = friendDirectory.get(user1);
		user1Friends.remove(user2);
		friendDirectory.put(user1.getUserID(), user1Friends);
		
		// remove friend from user2
		Set<UserInfo> user2Friends = friendDirectory.get(user2);
		user2Friends.remove(user2);
		friendDirectory.put(user2.getUserID(), user2Friends);
		
		return friendDirectory;
		
	}
	
	public void printUserBasicInfo(Map<String, UserInfo> userAccounts) {
		for (String user : userAccounts.keySet()) {
			System.out.println("Basic User Info for "+ user + ": ");
			System.out.println("     " +userAccounts.get(user));
		}
		System.out.println();
	}
	
	public void printFriendDirectory(Map<String, Set<UserInfo>> friendDirectory) {
		for (String user : friendDirectory.keySet()) {
			System.out.println("Friends of "+ user + ": ");
			for (UserInfo friend : friendDirectory.get(user)) {
				System.out.println("    ["+ friend + "], ");
			}
		}
		System.out.println();
	}
	
	public void printHobbies(Map<String, ArrayList<String>> userHobbies) {
		for (String user : userHobbies.keySet()) {
			System.out.println("Hobbies of "+ user + ": ");
			for (String str : userHobbies.get(user)) {
				System.out.println("    [" + str + "], ");
			}
			System.out.println();
		}
	}

	
	

}
