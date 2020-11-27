package Project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

public class FileUtil {

	public Map<String, UserInfo> readAccountsFromFile(String filename) {
		// Load USER information from User Account file
		Map<String, UserInfo> accountDirectory = new HashMap<>();

		try {
			FileReader fr = new FileReader(filename);
			BufferedReader bfr = new BufferedReader(fr);
			String userID1;
			String username1;
			String contactInfo1;
			String aboutMe1;

			String line = bfr.readLine();
			while (line != null) {
				// System.out.println (line);

				String[] userDetails = line.split("~");

				if (userDetails.length < 2) {
					line = bfr.readLine();
					continue;
				}

				userID1 = userDetails[0].toString();
				username1 = userDetails[1].toString();
				if (userDetails.length > 2)
					contactInfo1 = userDetails[2].toString();
				else
					contactInfo1 = null;

				if (userDetails.length > 3)
					aboutMe1 = userDetails[3].toString();
				else
					aboutMe1 = null;

				/*
				 * System.out.println( userDetails.length + ":" + userID1 + ":" + username1 +
				 * ":" + contactInfo1 + ":" + aboutMe1);
				 */
				UserInfo UserInfo1 = new UserInfo(userID1, username1, contactInfo1, aboutMe1);
				accountDirectory.put(UserInfo1.getUserID(), UserInfo1);
				line = bfr.readLine();
			}
			bfr.close();
			return accountDirectory;
		} catch (IOException e) {
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}

	public Map<String, Set<UserInfo>> readFriendsFromFile(String filename, Map<String, Set<UserInfo>> friendDirectory,
			Map<String, UserInfo> userAccounts) {
		// Load FRIEND information from Friends Network file
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader bfr = new BufferedReader(fr);
			String userID1;
			String friendID1;

			String line = bfr.readLine();
			while (line != null) {
				String[] friendInfo = line.split("~");

				if (friendInfo.length != 2) {
					line = bfr.readLine();
					continue;
				}

				userID1 = friendInfo[0].toString();
				friendID1 = friendInfo[1].toString();

				Set<UserInfo> friendsList = friendDirectory.getOrDefault(userID1, new HashSet<>());
				UserInfo friendUser = userAccounts.get(friendID1);
				friendsList.add(friendUser);
				friendDirectory.put(userID1, friendsList);

				line = bfr.readLine();
			}
			bfr.close();
			return friendDirectory;
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}

	public Map<String, ArrayList<String>> readHobbiesFromFile(String filename, Map<String, ArrayList<String>> userHobbies,
			Map<String, UserInfo> userAccounts) {
		// Load HOBBIES information from Friends Hobbies file
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader bfr = new BufferedReader(fr);
			ArrayList<String> hobbies = new ArrayList<>();
			String userID1;

			String line = bfr.readLine();
			while (line != null) {
				hobbies = new ArrayList<String>(); 
				String[] hobb = line.split("~");

				if (hobb.length < 2) {
					line = bfr.readLine();
					continue;
				}

				userID1 = hobb[0].toString();
				if (!(userAccounts.containsKey(userID1)))
					continue;
				
			    for (int i=1; i < hobb.length; i++)
			    {
			    	hobbies.add(hobb[i]);
			    }
			    userHobbies.put(userID1, hobbies);

				line = bfr.readLine();
			}
			bfr.close();
			return userHobbies;
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}
}
