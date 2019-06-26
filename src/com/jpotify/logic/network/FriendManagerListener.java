package com.jpotify.logic.network;

import com.jpotify.logic.PlayList;

/**
 * it's find friend manager needs.
 * this interface must be implemented for using <code>FriendManger</code> class.
 *
 * @author Morteza Mirzai
 * @see FriendManager
 */
public interface FriendManagerListener {

    /**
     * This method runs when your friend request you for send them your share playlist.
     *
     * @return shared playlist
     */
    PlayList getSharePlayList();

}
