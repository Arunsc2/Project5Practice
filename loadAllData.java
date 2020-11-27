package Project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class loadAllData {

	public static void main(String[] args) {
		loadAllDatafromFiles();
	}

	public static void loadAllDatafromFiles() {
		Map<String, UserInfo> userAccounts = new HashMap<String, UserInfo>();
		Map<String, Set<UserInfo>> friendDirectory = new HashMap<String, Set<UserInfo>>();
		Map<String, ArrayList<String>> userHobbies = new HashMap<String, ArrayList<String>>();

		FileUtil fileUtil = new FileUtil();
		FriendUtil friendUtil = new FriendUtil();

		try {
			String friendsFile = "C:\\Files\\friends.txt";
			String userAccountsFile = "C:\\Files\\useraccounts.txt";
			String userHobbiesFile = "C:\\Files\\userhobbies.txt";

			userAccounts = fileUtil.readAccountsFromFile(userAccountsFile);
			friendDirectory = fileUtil.readFriendsFromFile(friendsFile, friendDirectory, userAccounts);
			userHobbies = fileUtil.readHobbiesFromFile(userHobbiesFile, userHobbies, userAccounts);

			//friendUtil.printUserBasicInfo(userAccounts);
			//friendUtil.printFriendDirectory(friendDirectory);
			//friendUtil.printHobbies(userHobbies);

		} catch (Exception e) {
			return;
		}
	}

}
