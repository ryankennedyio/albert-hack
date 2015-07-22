package cba.hackathon.albertapp.models;

/**
 * Created by Konrad on 22/07/2015.
 */
public class App {
    private int mCurrentUser;

    private Cart mCart;

    public void setUser(int user) {
        mCurrentUser = user;
        mCart = new Cart();
    }

    public Cart getCart() {
        return mCart;
    }
}
