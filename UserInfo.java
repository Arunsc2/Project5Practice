package Project1;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class UserInfo {
	private String userID;
	private String username;
	private String contactInfo;
	private String aboutMe;

	public UserInfo(String userID, String username, String contactInfo, String aboutMe) {
		this.userID = userID;
		this.username = username;
		this.contactInfo = contactInfo;
		this.aboutMe = aboutMe;
	}

	public String getUserID() {
		return userID;
	}

	public void addNewUser(String userID, String username, String contactInfo, String aboutMe,
			Map<String, Set<UserInfo>> friendDirectory, Map<String, UserInfo> userAccounts) {
		UserInfo user = new UserInfo(userID, username, contactInfo, aboutMe);
		userAccounts.put(userID, user);
		friendDirectory.put(userID, Collections.emptySet());
	}

	public String toString() {
		return ("User ID: " + this.userID + ", User Name: " + this.username + ", Contact Info: " + this.contactInfo
				+ ", About Me: " + this.aboutMe);
	}

}
