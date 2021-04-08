package hooks;

import pageobjects.*;

public class WebPage {
    public TinhTeLoginPage tinhTeLoginPage;
    public HomePage homePage;
    public LoginPage loginPage;
    public ProfilePage profilePage;
    public LikePage likePage;
    public CollectionPage collectionPage;

    public WebPage(){
        tinhTeLoginPage = new TinhTeLoginPage();
        homePage = new HomePage();
        loginPage = new LoginPage();
        profilePage = new ProfilePage();
        likePage = new LikePage();
        collectionPage = new CollectionPage();
    }


}
