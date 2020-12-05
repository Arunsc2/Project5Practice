import java.io.*;
import java.util.*;

/**
 * Methods for reading and writing a file
 *
 * @author Arunav Sen Choudhuri, Tanvi Sattar
 */
public class ReadAndWrite {
    public Map<String, Account> readAccountsFromFile(String filename) throws InvalidAccountFormatException{
        Map<String, Account> accountDirectory = new HashMap<String, Account>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            if(line == null) { // When running the program the first time, this file will be empty
                return Collections.emptyMap();
            }
            while(line != null) {
                String[] accountDetails = line.split("~");
                if(accountDetails.length != 6) {
                    throw new InvalidAccountFormatException("Not the correct number of fields!");
                }
                String username = accountDetails[0];
                String name = accountDetails[1];
                String password = accountDetails[2];
                String contactInfo = accountDetails[3];
                String likesAndInterests = accountDetails[4];
                String aboutMe = accountDetails[5];

                if(contactInfo.equals(" ")) {
                    contactInfo = "";
                }
                if(likesAndInterests.equals(" ")) {
                    likesAndInterests = "";
                }
                if(aboutMe.equals(" ")) {
                    aboutMe = "";
                }


                Account account = new Account(username, name, password, contactInfo, likesAndInterests, aboutMe);
                accountDirectory.put(account.getUsername(), account);
                line = bfr.readLine();
            }
            bfr.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
        return accountDirectory;
    }

    public Map<String, List<Account>> readFriendsFromFile(String filename, Map<String, List<Account>> friendDirectory,
                                                    Map<String, Account> accountDirectory) throws InvalidFriendsFormatException {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            if(line == null) { // When running the program the first time, this file will be empty
                return Collections.emptyMap();
            }
            while(line != null) {
                String[] friendDetails = line.split("~");
                ArrayList<String> fDetails = new ArrayList<String>();
                for(String s : friendDetails) {
                    fDetails.add(s);
                }
                String accountUsername = fDetails.remove(0);

                /**
                 * if the user exists in the friend directory, then it retrieves the friends of the user,
                 * otherwise it creates a new, empty ArrayList of friends
                 */
                List<Account> friendList = friendDirectory.getOrDefault(accountUsername, new ArrayList<Account>());

                for(String s : fDetails) {
                    Account account = accountDirectory.get(s);
                    friendList.add(account);
                }
                friendDirectory.put(accountUsername, friendList);

                line = bfr.readLine();
            }
            bfr.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidFriendsFormatException();
        }
        return friendDirectory;
    }

    public Map<String, List<Account>> readFriendRequestsFromFile(String filename,
                                                                 Map<String, List<Account>> friendRequestDirectory, Map<String, Account> accountDirectory) throws InvalidFriendsFormatException {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            if(line == null) { // When running the program the first time, this file will be empty
                return Collections.emptyMap();
            }
            while(line != null) {
                String[] friendRequestDetails = line.split("~");
                ArrayList<String> frDetails = new ArrayList<String>();
                for(String s : friendRequestDetails) {
                    frDetails.add(s);
                }
                String accountUsername = frDetails.remove(0);

                /**
                 * if the user exists in the friendRequest directory, then it retrieves the friend requests of the user,
                 * otherwise it creates a new, empty ArrayList of friend requests
                 */
                List<Account> friendRequestList = friendRequestDirectory.getOrDefault(accountUsername, new ArrayList<Account>());

                for(String s : frDetails) {
                    Account requestedAccount = accountDirectory.get(s);
                    friendRequestList.add(requestedAccount);
                }
                friendRequestDirectory.put(accountUsername, friendRequestList);
                line = bfr.readLine();
            }
            bfr.close();
            fr.close();
        } catch(IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidFriendsFormatException();
        }
        return friendRequestDirectory;
    }

    public void writeFriendsToFile(String filename, Map<String, List<Account>> friendDirectory) {
        // File toWrite = new File(filename);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

            for (Map.Entry<String, List<Account>> entry : friendDirectory.entrySet()) {
                bw.write(entry.getKey());
                for (Account account : entry.getValue()) {
                    bw.write("~");
                    bw.write(account.getUsername());
                }
                bw.newLine();
            }

            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAccountsToFile(String filename, Map<String, Account> accountDirectory) {
        // File toWrite = new File(filename);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Account> entry : accountDirectory.entrySet()) {
                bw.write(entry.getKey() + "~" + entry.getValue().getName() + "~" + entry.getValue().getPassword() +
                        "~");
                if (entry.getValue().getContactInfo().equals("")) {
                    bw.write(" ");
                    bw.write("~");
                } else {
                    bw.write(entry.getValue().getContactInfo());
                    bw.write("~");
                }
                if (entry.getValue().getLikesAndInterests().equals("")) {
                    bw.write(" ");
                    bw.write("~");
                } else {
                    bw.write(entry.getValue().getLikesAndInterests());
                    bw.write("~");
                }
                if (entry.getValue().getAboutMe().equals("")) {
                    bw.write(" ");
                    bw.write("~");
                } else {
                    bw.write(entry.getValue().getAboutMe());
                    bw.write("~");
                }
                bw.newLine();
            }
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFriendRequestsToFile(String filename, Map<String, List<Account>> friendRequestDirectory) {
       // File toWrite = new File(filename);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

            for (Map.Entry<String, List<Account>> entry : friendRequestDirectory.entrySet()) {
                bw.write(entry.getKey());
                List<Account> friendList = entry.getValue();
                for (Account account : entry.getValue()) {
                    bw.write("~");
                    bw.write(account.getUsername());
                }
                bw.newLine();
            }

            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeAllFiles(String accountsFilename, String friendsFilename, String friendRequestsFilename, Map<String, Account> accountDiretory,
                              Map<String, List<Account>> friendDirectory, Map<String, List<Account>> friendRequestDirectory) throws InvalidFriendsFormatException,
            InvalidAccountFormatException {
        readAccountsFromFile(accountsFilename);
        readFriendsFromFile(friendsFilename, friendDirectory, accountDiretory);
        readFriendRequestsFromFile(friendRequestsFilename, friendRequestDirectory, accountDiretory);
    }

    public void loadAllDataFromFiles(String accountsFilename, String friendsFilename,
                                            String friendRequestsFilename, Map<String, Account> accountDirectory,
                                            Map<String, List<Account>> friendDirectory,
                                            Map<String, List<Account>> friendRequestDirectory, Server server)
            throws InvalidAccountFormatException, InvalidFriendsFormatException {

        accountDirectory = readAccountsFromFile(accountsFilename);
        friendDirectory = readFriendsFromFile(friendsFilename, friendDirectory, accountDirectory);
        friendRequestDirectory = readFriendRequestsFromFile(friendRequestsFilename, friendRequestDirectory, accountDirectory);
        server.setAccountDirectory(accountDirectory);
        server.setFriendDirectory(friendDirectory);
        server.setFriendRequestDirectory(friendRequestDirectory);
    }

}
