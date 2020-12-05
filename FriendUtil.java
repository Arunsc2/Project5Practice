import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Functions for dealing with friends
 *
 * @author Arunav Sen Choudhuri
 */
public class FriendUtil {

    /**
     * Allows user1 and user2 to be friends with each other
     * @param user1 the first account
     * @param user2 the second account
     * @param accountDirectory the map of all the accounts
     * @param friendDirectory the map of all the accounts with friends
     * @return the updated friend map (friendDirectory)
     * @throws AccountNotFoundException if either account is not found in the friend directory
     */
    public Map<String, List<Account>> addFriend(Account user1, Account user2, Map<String, Account> accountDirectory, Map<String, List<Account>> friendDirectory) throws AccountNotFoundException {
        if(!accountDirectory.containsKey(user1.getUsername()) || !accountDirectory.containsKey(user2.getUsername())) {
            throw new AccountNotFoundException("One of the accounts does not exist");
        }

        if(friendDirectory.containsKey(user1.getUsername()) && friendDirectory.containsKey(user2.getUsername())) {
            //add friend to user1
            List<Account> friendList1 = friendDirectory.get(user1.getUsername());
            friendList1.add(user2);
            friendDirectory.put(user1.getUsername(), friendList1);

            //add friend to user2
            List<Account> friendList2 = friendDirectory.get(user2.getUsername());
            friendList2.add(user1);
            friendDirectory.put(user2.getUsername(), friendList2);
        } else if (!friendDirectory.containsKey(user1.getUsername())) { // user1 is not in the friendDirectory, but user2 is
            //Create new friend list for user1, and add user2 to the list
            List<Account> friendList1 = new ArrayList<Account>();
            friendList1.add(user2);
            friendDirectory.put(user1.getUsername(), friendList1);

            //add friend to user2
            List<Account> friendList2 = friendDirectory.get(user2.getUsername());
            friendList2.add(user1);
            friendDirectory.put(user2.getUsername(), friendList2);
        } else { // user2 is not in the friend directory, but user1 is
            //add friend to user1
            List<Account> friendList1 = friendDirectory.get(user1.getUsername());
            friendList1.add(user2);
            friendDirectory.put(user1.getUsername(), friendList1);

            // Create new friend list for user2, and add user1 to the list
            List<Account> friendList2 = new ArrayList<Account>();
            friendList2.add(user1);
            friendDirectory.put(user2.getUsername(), friendList2);
        }

        return friendDirectory;
    }

    public Map<String, List<Account>> removeFriend(
            Account user1, Account user2, Map<String, Account> accountDirectory, Map<String, List<Account>> friendDirectory) throws FriendNotFoundException, AccountNotFoundException{
        if(!accountDirectory.containsKey(user1.getUsername()) || !accountDirectory.containsKey(user2.getUsername())) {
            throw new AccountNotFoundException("One of the accounts does not exist");
        }

        if(!friendDirectory.containsKey(user1.getUsername()) || !friendDirectory.containsKey(user2.getUsername())) {
            throw new FriendNotFoundException("One of or both users are not in the friendDirectory");
        }

        List<Account> friendListOfUser1 = friendDirectory.get(user1.getUsername());

        if(!friendListOfUser1.contains(user2)) {
            throw new FriendNotFoundException("The accounts are not friends with each other");
        }

        List<Account> friendListOfUser2 = friendDirectory.get(user2.getUsername());

        friendListOfUser1.remove(user2);
        friendListOfUser2.remove(user1);
        friendDirectory.put(user1.getUsername(), friendListOfUser1);
        friendDirectory.put(user2.getUsername(), friendListOfUser2);

        return friendDirectory;
    }

    public Map<String, List<Account>> sendFriendRequest(Account user1, Account user2,
                                                       Map<String, Account> accountDirectory, Map<String, List<Account>> friendRequestDirectory) throws AccountNotFoundException {


        if(!accountDirectory.containsKey(user1.getUsername()) || !accountDirectory.containsKey(user2.getUsername())) {
            throw new AccountNotFoundException("One of the accounts does not exist");
        }

        if(friendRequestDirectory.containsKey(user2)) {
            List<Account> friendRequestList2 = friendRequestDirectory.get(user2.getUsername());
            friendRequestList2.add(user1);
            friendRequestDirectory.put(user2.getUsername(), friendRequestList2);
        } else {
            List<Account> friendRequestList2 = new ArrayList<Account>();
            friendRequestList2.add(user1);
            friendRequestDirectory.put(user2.getUsername(), friendRequestList2);
        }
        return friendRequestDirectory;
    }

    public static void removeFriendRequest(
            Account user1, Account user2, Map<String, Account> accountDirectory, Map<String, List<Account>> friendRequestDirectory, AccountHandler server) throws FriendRequestNotFoundException, AccountNotFoundException {
        if(!accountDirectory.containsKey(user1.getUsername()) || !accountDirectory.containsKey(user2.getUsername())) {
            throw new AccountNotFoundException("One of the accounts does not exist");
        }

        if(!friendRequestDirectory.containsKey(user1.getUsername())) {
            throw new FriendRequestNotFoundException("One or both of the accounts is not in the friendRequestDirectory");
        }

        List<Account> friendRequestListOfUser1 = friendRequestDirectory.get(user1.getUsername());

        if(!friendRequestListOfUser1.contains(user2)) {
            throw new FriendRequestNotFoundException("The friend request does not exist");
        }

        friendRequestListOfUser1.remove(user2);
        friendRequestDirectory.put(user1.getUsername(), friendRequestListOfUser1);

        server.setFriendRequestDirectory(friendRequestDirectory);
    }

    public void acceptFriendRequest(Account user1, Account user2, Map<String, Account> accountDirectory,
                                    Map<String, List<Account>> friendDirectory, Map<String, List<Account>> friendRequestDirectory, AccountHandler handler)
            throws AccountNotFoundException, FriendRequestNotFoundException {

        removeFriendRequest(user1, user2, accountDirectory, friendRequestDirectory, handler);
        Map<String, List<Account>> updatedFriendDirectory= addFriend(user1, user2, accountDirectory, friendDirectory);
        handler.setFriendDirectory(updatedFriendDirectory);
    }

    // TODO Delete account

}
